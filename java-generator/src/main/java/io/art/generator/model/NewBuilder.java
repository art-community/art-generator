package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.BUILDER_METHOD_NAME;
import static io.art.generator.constants.GeneratorConstants.Names.BUILD_METHOD_NAME;
import static io.art.generator.service.JavacService.*;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class NewBuilder {
    private final TypeModel type;
    private Map<String, List<JCExpression>> methods = mapOf();

    public NewBuilder method(String name, JCExpression argument) {
        return method(name, List.of(argument));
    }

    public NewBuilder method(String name, List<JCExpression> arguments) {
        methods.put(name, arguments);
        return this;
    }

    public JCMethodInvocation generate() {
        JCMethodInvocation invocation = applyClassMethod(type, BUILDER_METHOD_NAME);
        for (Map.Entry<String, List<JCExpression>> entry : methods.entrySet()) {
            invocation = applyMethod(invocation, entry.getKey(), entry.getValue());
        }
        return applyMethod(invocation, BUILD_METHOD_NAME);
    }

    public static NewBuilder newBuilder(TypeModel type) {
        return new NewBuilder(type);
    }
}
