package io.art.generator.javac.implementor.mapping;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.javac.constants.GeneratorConstants.*;
import static io.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.javac.model.NewLambda.*;
import static io.art.generator.javac.model.NewParameter.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.MakerService.*;
import java.lang.reflect.*;

@UtilityClass
public class FromModelMapperImplementor {
    public static JCLambda implementFromModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(modelClass), MODEL))
                .expression(() -> generateMapperContent(modelClass))
                .generate();
    }

    private static JCMethodInvocation generateMapperContent(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER);
        for (Method method : modelClass.getDeclaredMethods()) {
            String getterName = method.getName();
            if (getterName.startsWith(GET_PREFIX)) {
                String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                Class<?> fieldType = method.getReturnType();
                builderInvocation = generateFieldMapping(builderInvocation, fieldName, fieldType);
            }
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private static JCMethodInvocation generateFieldMapping(JCMethodInvocation builderInvocation, String fieldName, Class<?> fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(literal(fieldName));
        mapping.add(newLambda().expression(() -> applyMethod(MODEL, GET_PREFIX + capitalize(fieldName))).generate());

        if (String.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), fromString));
        }

        if (Integer.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), fromInt));
        }

        return applyMethod(builderInvocation, LAZY_PUT, mapping.toList());
    }
}
