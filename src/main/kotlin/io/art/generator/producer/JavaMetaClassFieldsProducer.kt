package io.art.generator.producer;

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import io.art.generator.constants.JAVA_META_FIELD_CLASS_NAME
import io.art.generator.extension.asPoetType
import io.art.generator.extension.superFields
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaField
import io.art.generator.templates.javaRegisterMetaFieldStatement
import io.art.generator.templates.javaReturnStatement
import io.art.generator.templates.metaFieldName
import javax.lang.model.element.Modifier.*


internal fun TypeSpec.Builder.generateFields(metaClass: JavaMetaClass) {
    val parentFields = metaClass.superFields()
    parentFields.entries.forEach { field -> generateField(field.value, true) }
    metaClass.fields.entries.filter { field -> !parentFields.containsKey(field.key) }.forEach { field -> generateField(field.value, false) }
}

private fun TypeSpec.Builder.generateField(field: JavaMetaField, inherited: Boolean) {
    val fieldTypeName = field.type.asPoetType()
    val fieldMetaType = ParameterizedTypeName.get(JAVA_META_FIELD_CLASS_NAME, fieldTypeName.box())
    val fieldName = metaFieldName(field.name)
    FieldSpec.builder(fieldMetaType, fieldName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterMetaFieldStatement(field, inherited))
            .build()
            .apply(::addField)
    methodBuilder(fieldName)
            .addModifiers(PUBLIC)
            .returns(fieldMetaType)
            .addCode(javaReturnStatement(fieldName))
            .build()
            .let(::addMethod)
}
