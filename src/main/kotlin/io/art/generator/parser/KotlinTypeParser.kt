package io.art.generator.parser

import io.art.core.extensions.CollectionExtensions.checkOrPut
import io.art.generator.model.KotlinMetaType
import io.art.generator.model.KotlinMetaTypeKind.*
import io.art.generator.model.KotlinTypeVariance
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.typeUtil.isEnum

open class KotlinTypeParser {
    private val typeCache = mutableMapOf<KotlinType, KotlinMetaType>()

    protected fun KotlinType.resolved(): Boolean = this !is UnresolvedType && arguments.all { argument -> argument.type.resolved() }

    protected fun KotlinType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = checkOrPut(typeCache, this) {
        when (this) {
            is SimpleType -> asMetaType(variance)
            is FlexibleType -> asMetaType(variance)
            is DeferredType -> asMetaType(variance)
            else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    protected fun SimpleType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = when {
        isEnum() -> {
            val classId = constructor.declarationDescriptor?.classId!!
            KotlinMetaType(
                    originalType = this,
                    kind = ENUM_KIND,
                    classFullName = classId.asSingleFqName().asString(),
                    className = classId.relativeClassName.pathSegments().last().asString(),
                    classPackageName = classId.packageFqName.asString(),
                    typeName = classId.asSingleFqName().asString(),
                    nullable = TypeUtils.isNullableType(this),
            )
        }

        KotlinBuiltIns.isArray(this) -> KotlinMetaType(
                originalType = this,
                kind = ARRAY_KIND,
                arrayComponentType = constructor.builtIns.getArrayElementType(this).asMetaType(),
                typeName = toString(),
                nullable = TypeUtils.isNullableType(this)
        )

        constructor.declarationDescriptor is FunctionClassDescriptor -> checkOrPut(typeCache, this) {
            KotlinMetaType(
                    originalType = this,
                    kind = FUNCTION_KIND,
                    nullable = TypeUtils.isNullableType(this),
                    typeName = constructor.declarationDescriptor!!.name.asString(),
                    lambdaResultType = arguments
                            .takeLast(1)
                            .firstOrNull()
                            ?.asMetaType(),
                    typeVariance = variance
            )
        }.apply {
            if (lambdaArgumentTypes.isNotEmpty()) return@apply
            arguments
                    .dropLast(1)
                    .asSequence()
                    .map { projection -> typeCache.getOrElse(projection.type) { projection.asMetaType() } }
                    .forEach(lambdaArgumentTypes::add)
        }

        constructor.declarationDescriptor is ClassDescriptor -> {
            val classId = constructor.declarationDescriptor?.classId!!
            checkOrPut(typeCache, this) {
                KotlinMetaType(
                        originalType = this,
                        kind = CLASS_KIND,
                        classFullName = classId.asSingleFqName().asString(),
                        className = classId.relativeClassName.pathSegments().last().asString(),
                        classPackageName = classId.packageFqName.asString(),
                        typeName = classId.asSingleFqName().asString(),
                        typeVariance = variance,
                        nullable = TypeUtils.isNullableType(this),
                )
            }.apply {
                if (typeParameters.isNotEmpty()) return@apply
                arguments
                        .asSequence()
                        .map { projection -> typeCache.getOrElse(projection.type) { projection.asMetaType() } }
                        .forEach(typeParameters::add)
            }
        }

        else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun FlexibleType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType {
        return delegate.asMetaType(variance)
    }

    private fun DeferredType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType {
        return delegate.asMetaType(variance)
    }

    private fun TypeProjection.asMetaType(): KotlinMetaType {
        if (isStarProjection) {
            return KotlinMetaType(
                    originalType = type,
                    kind = WILDCARD_KIND,
                    typeName = toString()
            )
        }
        return type.asMetaType(when (projectionKind) {
            Variance.INVARIANT -> KotlinTypeVariance.INVARIANT
            Variance.IN_VARIANCE -> KotlinTypeVariance.IN
            Variance.OUT_VARIANCE -> KotlinTypeVariance.OUT
        })
    }
}
