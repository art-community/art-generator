package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.toString;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewLambda.*;
import static ru.art.generator.javac.model.NewParameter.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.MakerService.*;
import java.lang.reflect.*;

@UtilityClass
public class ToModelMapperGenerationService {
    public static JCLambda generateToModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(Entity.class), VALUE))
                .expression(() -> generateContent(modelClass))
                .generate();
    }

    private static JCMethodInvocation generateContent(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass.getName()), BUILDER);
        for (Method method : modelClass.getDeclaredMethods()) {
            String getterName = method.getName();
            if (getterName.startsWith(GET_PREFIX)) {
                String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                Class<?> fieldType = method.getReturnType();
                builderInvocation = applyMethod(builderInvocation, fieldName, List.of(generateFieldMapping(fieldName, fieldType)));
            }
        }
        return applyMethod(builderInvocation, BUILD);
    }

    private static JCMethodInvocation generateFieldMapping(String fieldName, Class<?> fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(maker().Literal(fieldName));

        if (String.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toString));
        }

        if (Integer.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toInt));
        }

        return applyMethod(VALUE, MAP, mapping.toList());
    }
}
