package io.art.generator.parser

import com.google.devtools.ksp.symbol.*
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.KotlinMetaType
import io.art.generator.model.KotlinMetaTypeKind.*
import io.art.generator.model.KotlinTypeVariance
import io.art.generator.service.analyzing.KotlinAnalyzerBuiltins

open class KotlinTypeParser {
    private val cache = mutableMapOf<KSType, KotlinMetaType>()

    protected fun KSType.resolved(): Boolean = !isError && arguments.all { argument -> argument.type != null }

    protected fun KSTypeReference.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = resolve().asMetaType(variance)

    protected fun KSType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = putIfAbsent(cache, this) {
        if (isError) {
            return@putIfAbsent KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
        return@putIfAbsent when {
            (declaration is KSClassDeclaration && (declaration as KSClassDeclaration).classKind == ClassKind.ENUM_CLASS) -> {
                val declaration = (declaration as KSClassDeclaration)
                KotlinMetaType(
                    originalType = this,
                    kind = ENUM_KIND,
                    classFullName = declaration.qualifiedName!!.asString(),
                    className = declaration.simpleName.getShortName(),
                    classPackageName = declaration.packageName.asString(),
                    typeName = declaration.qualifiedName!!.asString(),
                    nullable = isMarkedNullable,
                )
            }

            (this == KotlinAnalyzerBuiltins.builtins.arrayType) -> KotlinMetaType(
                originalType = this,
                kind = ARRAY_KIND,
                arrayComponentType = arguments.first().asMetaType(),
                typeName = toString(),
                nullable = isMarkedNullable,
            )

            (declaration is KSFunctionDeclaration) -> putIfAbsent(cache, this) {
                KotlinMetaType(
                    originalType = this,
                    kind = FUNCTION_KIND,
                    nullable = isMarkedNullable,
                    typeName = declaration.simpleName.asString(),
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
                    .map { projection -> cache.getOrElse(projection.type!!.resolve()) { projection.asMetaType() } }
                    .forEach(lambdaArgumentTypes::add)
            }

            (declaration is KSClassDeclaration) -> {
                putIfAbsent(cache, this) {
                    KotlinMetaType(
                        originalType = this,
                        kind = CLASS_KIND,
                        classFullName = declaration.qualifiedName!!.asString(),
                        className = declaration.simpleName.getShortName(),
                        classPackageName = declaration.packageName.asString(),
                        typeName = declaration.qualifiedName!!.asString(),
                        typeVariance = variance,
                        nullable = isMarkedNullable,
                    )
                }.apply {
                    if (typeParameters.isNotEmpty()) return@apply
                    arguments
                        .asSequence()
                        .map { projection -> cache.getOrElse(projection.type!!.resolve()) { projection.asMetaType() } }
                        .forEach(typeParameters::add)
                }
            }

            else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun KSTypeArgument.asMetaType(): KotlinMetaType = when (variance) {
        Variance.STAR -> KotlinMetaType(kind = WILDCARD_KIND, originalType = type!!.resolve(), typeName = toString())
        Variance.INVARIANT -> type!!.asMetaType(KotlinTypeVariance.INVARIANT)
        Variance.COVARIANT -> type!!.asMetaType(KotlinTypeVariance.IN)
        Variance.CONTRAVARIANT -> type!!.asMetaType(KotlinTypeVariance.OUT)
    }
}
