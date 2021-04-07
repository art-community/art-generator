package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewField {
    private String name;
    private boolean byConstructor;
    private long modifiers;
    private TypeModel type;
    private Supplier<JCExpression> initializer;

    public NewField constant(Object value) {
        initializer = () -> maker().Literal(value);
        return this;
    }

    public NewField arrayOf(TypeModel type, ImmutableSet<String> otherFields) {
        initializer = () -> {
            List<JCExpression> elements = otherFields.stream()
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
        JCExpression initializationExpression = let(initializer, Supplier::get);
        return maker().VarDef(modifiers, name, type, orNull(initializationExpression, !byConstructor));
    }

    public static NewField newField() {
        return new NewField();
    }
}
