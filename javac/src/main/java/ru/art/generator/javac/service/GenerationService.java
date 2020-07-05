package ru.art.generator.javac.service;

import com.sun.tools.javac.api.*;
import lombok.*;
import lombok.experimental.*;
import ru.art.generator.javac.annotation.*;
import ru.art.generator.javac.exception.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.main.Option.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.model.ParameterModel.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        generateClassMethodNames();
        if (processingEnvironment().getOptions().get("disable") != null) {
            return;
        }
        ModuleModel model = loadModel();
        System.out.println(model.getServices());
        if (isNull(mainMethod())) {
            addMethod(mainClass(), newMethod()
                    .modifiers(PUBLIC | STATIC)
                    .name("main")
                    .returnType(type(void.class.getName()))
                    .statement(() -> maker().Exec(
                            maker().Apply(
                                    nil(),
                                    maker().Select(
                                            maker().Select(maker().Ident(elements().getName("System")), elements().getName("out")),
                                            elements().getName("println")
                                    ),
                                    of(maker().Literal("Registered classes:" + model.getServices().stream().map(Class::getName).collect(joining())))

                            )
                            )
                    )
                    .parameter(parameter(type(String[].class.getName()), "args")));
        }
    }

    @SneakyThrows
    private ModuleModel loadModel() {
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        List<String> arguments = new LinkedList<>();
        arguments.add("-cp");
        arguments.add(options().get(CLASSPATH));
        arguments.add("-Adisable=true");
        arguments.addAll(getExistedClasses().values().stream().map(existed -> existed.getPackageUnit().getSourceFile().getName()).collect(toList()));

        javacTool.run(new ByteArrayInputStream(new byte[]{}), new ByteArrayOutputStream(), errorStream, arguments.toArray(new String[0]));
        System.out.println(errorStream.toString());
        URL[] urls = getExistedClasses().values()
                .stream()
                .map(existed -> existed.getPackageUnit()
                        .getSourceFile()
                        .getName()
                        .substring(0, existed.getPackageUnit().getSourceFile().getName().indexOf(existed.getPackageName())))
                .map(GenerationService::classFile)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(URL[]::new);
        URLClassLoader loader = new URLClassLoader(urls, GenerationService.class.getClassLoader());
        Class<?> mainClass = loader.loadClass(mainClass().getFullName());
        Method configuratorMethod = stream(mainClass.getMethods())
                .filter(method -> method.isAnnotationPresent(Configurator.class))
                .findFirst()
                .orElseThrow(() -> new GenerationException("Module configurator method not found"));
        ModuleModel model = (ModuleModel) configuratorMethod.invoke(null);
        loader.close();
        return model;
    }

    private URL classFile(String path) {
        try {
            return new File(path).toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void generateClassMethodNames() {
        getExistedClasses()
                .values()
                .forEach(existed -> addFields(existed, methodNames(existed).toClass().fields().values()));
    }
}
