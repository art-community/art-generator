package io.art.generator.service;

import com.google.common.collect.*;
import com.sun.tools.javac.api.*;
import lombok.experimental.*;
import static com.sun.tools.javac.main.Option.*;
import static io.art.core.constants.ArrayConstants.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.constants.GeneratorConstants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import java.io.*;
import java.util.*;

@UtilityClass
public class CompilationService {
    public void recompile() {
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_BYTES);
        ImmutableList.Builder<String> arguments = ImmutableList.builder();
        arguments.add(CLASSPATH.getText());
        arguments.add(options().get(CLASSPATH));
        arguments.add(D.getText());
        arguments.add(options().get(D));
        arguments.add(DISABLE_OPTION_ENABLED);
        List<String> classes = getExistedClasses()
                .values()
                .stream()
                .map(existed -> existed.getPackageUnit().getSourceFile().getName())
                .collect(toList());
        arguments.addAll(classes);
        javacTool.run(inputStream, outputStream, System.err, arguments.build().toArray(new String[0]));
    }
}
