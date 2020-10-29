package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static com.sun.tools.javac.util.List.*;
import static ru.art.generator.javac.context.GenerationContext.*;

@UtilityClass
public class MakerService {
    public JCIdent ident(String name) {
        return maker().Ident(name(name));
    }

    public Name name(String name) {
        return elements().getName(name);
    }

    public JCModifiers emptyModifiers() {
        return maker().Modifiers(0L);
    }

    public JCLiteral nullValue() {
        return maker().Literal(BOT, null);
    }

    public JCReturn returnMethodCall(String variable, String method) {
        return maker().Return(maker().Apply(nil(), maker().Select(ident(variable), elements().getName(method)), nil()));
    }

    public JCMethodInvocation callClassMethod(TypeModel classType, String method) {
        return maker().Apply(nil(), maker().Select(classType.generate(), elements().getName(method)), nil());
    }

    public JCMethodInvocation callMethod(String method) {
        return maker().Apply(nil(), ident(method), nil());
    }

    public JCNewClass newObject(TypeModel classType) {
        return maker().NewClass(null, List.<JCExpression>nil(), classType.generate(), nil(), null);
    }
}
