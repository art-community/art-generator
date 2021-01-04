package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.service.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewVariable {
    private String name;
    private long modifiers;
    private TypeModel type;
    private Supplier<JCExpression> initializer;

    public NewVariable asNull() {
        initializer = JavacService::nullValue;
        return this;
    }

    public NewVariable constant(Object value) {
        initializer = () -> maker().Literal(value);
        return this;
    }

    public NewVariable arrayOf(TypeModel type, ImmutableSet<String> otherVariables) {
        initializer = () -> {
            List<JCExpression> elements = otherVariables.stream()
                    .map(field -> (JCExpression) maker().Ident(elements().getName(field)))
                    .collect(toCollection(ListBuffer::new))
                    .toList();
            return maker().NewArray(maker().Ident(elements().getName(type.getName())), nil(), elements);
        };
        return this;
    }

    public JCVariableDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        JCExpression type = this.type.generateFullType();
        JCExpression initializationExpression = initializer.get();
        return maker().VarDef(modifiers, name, type, initializationExpression);
    }

    public static NewVariable newVariable() {
        return new NewVariable();
    }
}
