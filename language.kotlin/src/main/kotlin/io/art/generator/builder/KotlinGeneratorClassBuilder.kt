package io.art.generator.builder;

import io.art.core.constants.StringConstants.DOT
import io.art.generator.constants.Names.*
import io.art.generator.constants.PARAMETER_MODULE_MODEL_SIGNATURE
import io.art.generator.constants.RETURNING_MODULE_MODEL_SIGNATURE
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.calls.components.isVararg
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes.*

class KotlinGeneratorClassBuilder(private val owner: JvmDeclarationOrigin, private val delegateBuilder: ClassBuilder) : DelegatingClassBuilder() {
    override fun newMethod(origin: JvmDeclarationOrigin, access: Int, name: String, desc: String, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val originFactory = { super.newMethod(origin, access, name, desc, signature, exceptions) }
        val ownerFile = owner.element as? KtFile
        val ownerClass = owner.element as? KtClassOrObject
        ownerFile ?: ownerClass ?: return originFactory()
        val methodDescriptor = origin.descriptor as? FunctionDescriptor ?: return originFactory()
        if (methodDescriptor.name.asString() != MAIN_NAME) return originFactory()
        if (methodDescriptor.returnType.toString() != Unit::class.simpleName && methodDescriptor.returnType.toString() != Void::class.simpleName) return originFactory()
        if (methodDescriptor.valueParameters.size > 1) return originFactory()
        if (methodDescriptor.valueParameters.isNotEmpty()) {
            if (methodDescriptor.valueParameters[0].isVararg) {
                val hasStringVarargs = methodDescriptor.valueParameters[0]
                        .runCatching {
                            val builtIns = type.constructor.builtIns
                            builtIns.getArrayElementType(type).toString() == String::class.simpleName
                        }
                        .getOrNull() == true
                if (!hasStringVarargs) return originFactory()
            }
            if (!methodDescriptor.valueParameters[0].isVararg) {
                val hasStringArray = methodDescriptor.valueParameters[0]
                        .runCatching {
                            val builtIns = type.constructor.builtIns
                            builtIns.getArrayElementType(type).toString() == String::class.simpleName
                        }
                        .getOrNull() == true

                if (!hasStringArray) return originFactory()
            }
        }
        return object : MethodVisitor(ASM5, null) {
            override fun visitCode() {
                originFactory().apply {
                    val providerByFileName = ownerFile?.packageFqName?.asString() + DOT + ownerFile?.name?.substringBeforeLast(DOT) + PROVIDER_CLASS_SUFFIX
                    val providerByClassName = ownerClass?.fqName?.toString()
                    val providerName = providerByClassName ?: providerByFileName
                    visitMethodInsn(INVOKESTATIC, providerName, PROVIDE_NAME, RETURNING_MODULE_MODEL_SIGNATURE, false);
                    visitMethodInsn(INVOKESTATIC, "io/art/launcher/ModuleLauncher", LAUNCH_NAME, PARAMETER_MODULE_MODEL_SIGNATURE, false);
                    visitInsn(RETURN)
                    visitMaxs(1, 0);
                    visitEnd();
                }
            }

        }
    }

    override fun getDelegate() = delegateBuilder

}
