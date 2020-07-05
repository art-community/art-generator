package ru.art.generator.javac.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewField {
    private String name;
    private long modifiers;
    private TypeModel type;

    private Supplier<JCExpression> initializer;

    public NewField constant(Object value) {
        initializer = () -> maker().Literal(value);
        return this;
    }

    public NewField arrayOf(TypeModel type, Set<String> otherFields) {
        initializer = () -> {
            List<JCIdent> elements = from(otherFields.stream().map(field -> maker().Ident(elements().getName(field))).collect(toList()));
            return maker().NewArray(maker().Ident(elements().getName(type.getTypeSimpleName())), nil(), from(elements));
        };
        return this;
    }

    public JCVariableDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        JCExpression type = this.type.isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(this.type.getTypeSimpleName())))
                : maker().Ident(elements().getName(this.type.getTypeSimpleName()));
        JCExpression initializationExpression = initializer.get();
        return maker().VarDef(modifiers, name, type, initializationExpression);
    }

    public static NewField newField() {
        return new NewField();
    }
}
