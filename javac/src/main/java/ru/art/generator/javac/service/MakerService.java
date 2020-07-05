package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.experimental.*;
import static ru.art.generator.javac.context.GenerationContext.*;

@UtilityClass
public class MakerService {
    public JCIdent ident(String name) {
        return maker().Ident(elements().getName(name));
    }
}
