package io.art.generator.processor;

import io.art.core.constants.StringConstants.DOT
import io.art.generator.constants.Names.*
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.ClassBuilderFactory
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.components.isVararg
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes.*

class KotlinGeneratorClassBuilder(private val collector: MessageCollector, private val owner: JvmDeclarationOrigin, private val bindingContext: BindingContext, private val delegateBuilder: ClassBuilder) : DelegatingClassBuilder() {
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


class KotlinGeneratorExtension(private val messageCollector: MessageCollector) : ClassBuilderInterceptorExtension {
    override fun interceptClassBuilderFactory(interceptedFactory: ClassBuilderFactory, bindingContext: BindingContext, diagnostics: DiagnosticSink) =
            object : ClassBuilderFactory by interceptedFactory {
                override fun newClassBuilder(origin: JvmDeclarationOrigin) = KotlinGeneratorClassBuilder(messageCollector, origin, bindingContext, interceptedFactory.newClassBuilder(origin))
            }
}
