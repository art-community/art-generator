package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.caller.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.factory.ListFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.util.function.UnaryOperator.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class NewBuilder {
    private final TypeModel type;
    private List<NamedMethodCall> methods = linkedList();

    public NewBuilder method(String name, JCExpression argument) {
        return method(name, fixedArrayOf(argument));
    }

    public NewBuilder method(String name, Collection<JCExpression> arguments) {
        methods.add(new NamedMethodCall(name, fixedArrayOf(arguments)));
        return this;
    }

    public NewBuilder method(String name, JCExpression... arguments) {
        methods.add(new NamedMethodCall(name, fixedArrayOf(arguments)));
        return this;
    }

    public JCMethodInvocation generate() {
        return generate(identity());
    }

    public JCMethodInvocation generate(UnaryOperator<JCMethodInvocation> decorator) {
        JCMethodInvocation invocation = MethodCaller.method(type, BUILDER_METHOD_NAME).apply();
        for (NamedMethodCall call : methods) {
            invocation = MethodCaller.method(invocation, call.name).addArguments(call.expressions).apply();
        }
        return MethodCaller.method(decorator.apply(invocation), BUILD_METHOD_NAME).apply();
    }

    public static NewBuilder newBuilder(TypeModel type) {
        return new NewBuilder(type);
    }

    @AllArgsConstructor
    private static class NamedMethodCall {
        private final String name;
        private final Collection<JCExpression> expressions;
    }
}
