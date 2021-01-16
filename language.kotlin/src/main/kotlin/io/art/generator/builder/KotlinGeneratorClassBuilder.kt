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

        return object : MethodVisitor(ASM5, null) {
            override fun visitCode() {
//                originFactory().apply {
//                    val providerByFileName = ownerFile?.packageFqName?.asString() + DOT + ownerFile?.name?.substringBeforeLast(DOT) + PROVIDER_CLASS_SUFFIX
//                    val providerByClassName = ownerClass?.fqName?.toString()
//                    val providerName = providerByClassName ?: providerByFileName
//                    visitMethodInsn(INVOKESTATIC, providerName, PROVIDE_NAME, RETURNING_MODULE_MODEL_SIGNATURE, false);
//                    visitMethodInsn(INVOKESTATIC, "io/art/launcher/ModuleLauncher", LAUNCH_NAME, PARAMETER_MODULE_MODEL_SIGNATURE, false);
//                    visitInsn(RETURN)
//                    visitMaxs(1, 0);
//                    visitEnd();
//                }
            }

        }
    }

    override fun getDelegate() = delegateBuilder

}
