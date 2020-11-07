package io.art.generator.javac.implementor.model;

import com.sun.tools.javac.util.*;
import io.art.core.constants.*;
import io.art.generator.javac.exception.*;
import io.art.generator.javac.model.*;
import io.art.launcher.*;
import io.art.model.configurator.*;
import io.art.model.module.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.model.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.generator.javac.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.javac.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.javac.constants.GeneratorConstants.Names.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.model.ServerModelImplementor.*;
import static io.art.generator.javac.model.ImportModel.*;
import static io.art.generator.javac.model.NewClass.*;
import static io.art.generator.javac.model.NewMethod.*;
import static io.art.generator.javac.model.NewParameter.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.ClassMutationService.*;
import static io.art.generator.javac.service.MakerService.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class ModuleModelImplementor {
    public static void implementModuleModel() {
        ModuleModel model = loadModel();
        String providerClassName = mainClass().getName() + PROVIDER_CLASS_NAME_SUFFIX;
        NewClass providerClass = newClass()
                .modifiers(PUBLIC | STATIC)
                .name(providerClassName)
                .addImport(classImport(PrimitiveMapping.class.getName()))
                .addImport(classImport(ArrayMapping.class.getName()))
                .addImport(classImport(EntityMapping.class.getName()))
                .addImport(classImport(BinaryMapping.class.getName()))
                .addImport(classImport(ArrayValue.class.getName()))
                .addImport(classImport(BinaryValue.class.getName()))
                .addImport(classImport(Entity.class.getName()))
                .addImport(classImport(Value.class.getName()))
                .addImport(classImport(ValueToModelMapper.class.getName()))
                .addImport(classImport(ValueFromModelMapper.class.getName()))
                .addImport(classImport(MappersRegistry.class.getName()))
                .addImport(classImport(ServiceSpecificationRegistry.class.getName()))
                .addImport(classImport(ServiceSpecification.class.getName()))
                .addImport(classImport(ServiceMethodSpecification.class.getName()))
                .addImport(classImport(ServiceMethodImplementation.class.getName()))
                .addImport(classImport(ModuleModel.class.getName()))
                .addImport(classImport(ServiceLoggingDecorator.class.getName()))
                .addImport(classImport(ServiceMethodIdentifier.class.getName()))
                .addImport(classImport(MethodProcessingMode.class.getName()))
                .addImport(classImport(MethodDecoratorScope.class.getName()))
                .addImport(classImport(ConfiguratorModel.class.getName()))
                .addImport(classImport(ValueConfiguratorModel.class.getName()))
                .addImport(classImport(ServerConfiguratorModel.class.getName()))
                .addImport(classImport(ModuleLauncher.class.getName()));
        implementServerModel(providerClass, model.getServerModel());
        replaceInnerClass(mainClass(), providerClass);
        replaceMethod(mainClass(), generateMainMethod(providerClassName));

    }

    private ModuleModel loadModel() {
        try {
            Class<?> mainClass = classLoader().loadClass(mainClass().getFullName());
            Method configuratorMethod = stream(mainClass.getMethods())
                    .filter(ModuleModelImplementor::hasConfiguratorAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND_EXCEPTION));
            return (ModuleModel) configuratorMethod.invoke(null);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasConfiguratorAnnotation(Method method) {
        return stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType().getName().equals(CONFIGURATOR_ANNOTATION_NAME));
    }

    private static NewMethod generateMainMethod(String providerClassName) {
        JCMethodInvocation launchMethod = applyClassMethod(type(ModuleLauncher.class), LAUNCH_NAME, List.of(select(providerClassName, MODEL_NAME)));
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(MAIN_NAME)
                .returnType(type(void.class))
                .parameter(newParameter(type(String[].class), MAIN_METHOD_ARGUMENTS_NAME))
                .statement(() -> maker().Exec(launchMethod));
    }
}
