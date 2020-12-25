package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.caller.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.factory.ListFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.util.function.UnaryOperator.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class NewBuilder {
    private final TypeModel type;
    private java.util.List<NamedMethodCall> methods = linkedList();

    public NewBuilder method(String name, JCExpression argument) {
        return method(name, List.of(argument));
    }

    public NewBuilder method(String name, List<JCExpression> arguments) {
        methods.add(new NamedMethodCall(name, arguments));
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
        private final List<JCExpression> expressions;
    }
}
