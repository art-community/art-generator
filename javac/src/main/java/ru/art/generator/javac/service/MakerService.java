package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.TypeTag.BOT;
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
}
