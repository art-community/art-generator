package io.art.generator.parser

import com.sun.tools.javac.code.Type
import io.art.core.extensions.CollectionExtensions
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind
import javax.lang.model.type.TypeMirror

open class JavaTypeParser {
    private val typeCache = mutableMapOf<TypeMirror, JavaMetaType>()

    internal fun TypeMirror.asMetaType(): JavaMetaType = CollectionExtensions.putIfAbsent(typeCache, this) {
        when (this) {
            is Type.ArrayType -> asMetaType()

            is Type.WildcardType -> asMetaType()

            is Type.ClassType -> asMetaType()

            is Type -> when {
                isPrimitiveOrVoid -> JavaMetaType(
                        javaOriginalType = this,
                        kind = JavaMetaTypeKind.PRIMITIVE_KIND,
                        typeName = tsym.qualifiedName.toString()
                )
                else -> JavaMetaType(
                        javaOriginalType = this,
                        kind = JavaMetaTypeKind.UNKNOWN_KIND,
                        typeName = tsym?.qualifiedName?.toString() ?: toString()
                )
            }

            else -> JavaMetaType(javaOriginalType = this, kind = JavaMetaTypeKind.UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun Type.ClassType.asMetaType(): JavaMetaType {
        val type = CollectionExtensions.putIfAbsent(typeCache, this) {
            JavaMetaType(
                    javaOriginalType = this,
                    classFullName = tsym.qualifiedName.toString(),
                    kind = when {
                        !asElement().isEnum -> JavaMetaTypeKind.CLASS_KIND
                        asElement().isEnum -> JavaMetaTypeKind.ENUM_KIND
                        else -> JavaMetaTypeKind.UNKNOWN_KIND
                    },
                    typeName = tsym.qualifiedName.toString(),
                    className = tsym.simpleName.toString(),
                    classPackageName = tsym.packge().qualifiedName.toString()
            )
        }
        if (type.typeParameters.isEmpty()) {
            val newArguments = typeArguments
                    .asSequence()
                    .map { argument -> typeCache.getOrElse(argument) { argument.asMetaType() } }
            type.typeParameters.addAll(newArguments)
        }
        return type
    }

    private fun Type.ArrayType.asMetaType(): JavaMetaType = CollectionExtensions.putIfAbsent(typeCache, this) {
        JavaMetaType(
                javaOriginalType = this,
                kind = JavaMetaTypeKind.ARRAY_KIND,
                typeName = tsym.qualifiedName.toString(),
                arrayComponentType = componentType.asMetaType()
        )
    }

    private fun Type.WildcardType.asMetaType(): JavaMetaType = CollectionExtensions.putIfAbsent(typeCache, this) {
        JavaMetaType(
                javaOriginalType = this,
                kind = JavaMetaTypeKind.WILDCARD_KIND,
                typeName = type.toString(),
                wildcardSuperBound = superBound?.asMetaType(),
                wildcardExtendsBound = extendsBound?.asMetaType()
        )
    }
}
