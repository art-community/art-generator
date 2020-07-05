package ru.art.generator.javac.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.context.GenerationContext.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ParameterModel {
    private final String name;
    private final TypeModel type;

    public static ParameterModel parameter(TypeModel type, String name) {
        return new ParameterModel(name, type);
    }

    public JCVariableDecl generate() {
        JCModifiers modifiers = maker().Modifiers(PARAMETER);
        Name name = elements().getName(this.name);
        JCExpression type = this.type.isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(this.type.getTypeSimpleName())))
                : maker().Ident(elements().getName(this.type.getTypeSimpleName()));
        return maker().VarDef(modifiers, name, type, null);
    }
}
