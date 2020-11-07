package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.toString;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ToModelMapperCreator {
    public JCLambda createToModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(Entity.class), VALUE_NAME))
                .expression(() -> createMapperContent(modelClass))
                .generate();
    }

    private JCMethodInvocation createMapperContent(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass.getName()), BUILDER_METHOD_NAME);
        for (Method method : modelClass.getDeclaredMethods()) {
            String getterName = method.getName();
            if (getterName.startsWith(GET_PREFIX)) {
                String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                Class<?> fieldType = method.getReturnType();
                builderInvocation = applyMethod(builderInvocation, fieldName, List.of(createFieldMapping(fieldName, fieldType)));
            }
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCMethodInvocation createFieldMapping(String fieldName, Class<?> fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(maker().Literal(fieldName));

        if (String.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toString));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Integer.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toInt));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Long.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toLong));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Boolean.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toBool));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Double.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toDouble));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Byte.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toByte));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        if (Float.class.equals(fieldType)) {
            mapping.add(select(type(PrimitiveMapping.class), toFloat));
            return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
        }

        return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
    }
}
