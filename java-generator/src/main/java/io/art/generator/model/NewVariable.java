package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.factory.*;
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

    public NewVariable arrayOf(TypeModel type, Set<String> otherVariables) {
        initializer = () -> {
            List<JCIdent> elements = from(otherVariables.stream().map(field -> maker().Ident(elements().getName(field))).collect(toCollection(ArrayFactory::dynamicArray)));
            return maker().NewArray(maker().Ident(elements().getName(type.getName())), nil(), from(elements));
        };
        return this;
    }

    public JCVariableDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        JCExpression type = this.type.generate();
        JCExpression initializationExpression = initializer.get();
        return maker().VarDef(modifiers, name, type, initializationExpression);
    }

    public static NewVariable newVariable() {
        return new NewVariable();
    }
}
