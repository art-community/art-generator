package io.art.generator.javac.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import io.art.generator.javac.service.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.javac.context.GenerationContext.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewVariable {
    private String name;
    private long modifiers;
    private TypeModel type;
    private Supplier<JCTree.JCExpression> initializer;

    public NewVariable asNull() {
        initializer = MakerService::nullValue;
        return this;
    }

    public NewVariable constant(Object value) {
        initializer = () -> maker().Literal(value);
        return this;
    }

    public NewVariable arrayOf(TypeModel type, Set<String> otherVariables) {
        initializer = () -> {
            List<JCTree.JCIdent> elements = from(otherVariables.stream().map(field -> maker().Ident(elements().getName(field))).collect(toList()));
            return maker().NewArray(maker().Ident(elements().getName(type.getName())), nil(), from(elements));
        };
        return this;
    }

    public JCTree.JCVariableDecl generate() {
        JCTree.JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        JCTree.JCExpression type = this.type.generate();
        JCTree.JCExpression initializationExpression = initializer.get();
        return maker().VarDef(modifiers, name, type, initializationExpression);
    }

    public static NewVariable newVariable() {
        return new NewVariable();
    }
}
