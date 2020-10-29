package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static ru.art.generator.javac.constants.GeneratorConstants.CLASS_KEYWORD;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.TypeModel.type;

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
        return maker().Return(maker().Apply(List.nil(), maker().Select(ident(variable), name(method)), List.nil()));
    }

    public JCMethodInvocation applyClassMethod(TypeModel classType, String method) {
        return applyClassMethod(classType, method, List.nil());
    }

    public JCMethodInvocation applyClassMethod(TypeModel classType, String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), maker().Select(classType.generate(), name(method)), arguments);
    }

    public JCMethodInvocation applyMethod(String method) {
        return maker().Apply(List.nil(), ident(method), List.nil());
    }

    public JCMethodInvocation applyMethod(String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), ident(method), arguments);
    }


    public JCMethodInvocation applyMethod(String owner, String method) {
        return maker().Apply(List.nil(), maker().Select(ident(owner), name(method)), List.nil());
    }

    public JCMethodInvocation applyMethod(String owner, String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), maker().Select(ident(owner), name(method)), arguments);
    }


    public JCNewClass newObject(TypeModel classType) {
        return maker().NewClass(null, List.nil(), classType.generate(), List.nil(), null);
    }

    public JCFieldAccess classReference(Class<?> owner) {
        return maker().Select(type(owner).generate(), name(CLASS_KEYWORD));
    }
}
