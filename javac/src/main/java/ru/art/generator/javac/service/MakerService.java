package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.TypeModel.*;

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


    public JCLiteral literal(String literal) {
        return maker().Literal(literal);
    }


    public JCExpressionStatement execMethodCall(String variable, String method, List<JCExpression> arguments) {
        return maker().Exec(maker().Apply(List.nil(), select(variable, method), arguments));
    }

    public JCReturn returnMethodCall(String variable, String method, List<JCExpression> arguments) {
        return maker().Return(maker().Apply(List.nil(), select(variable, method), arguments));
    }


    public JCMethodInvocation applyClassMethod(TypeModel classType, String method) {
        return applyClassMethod(classType, method, List.nil());
    }

    public JCMethodInvocation applyClassMethod(TypeModel classType, String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), select(classType, method), arguments);
    }


    public JCMethodInvocation applyMethod(String method) {
        return maker().Apply(List.nil(), ident(method), List.nil());
    }

    public JCMethodInvocation applyMethod(String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), ident(method), arguments);
    }


    public JCMethodInvocation applyMethod(String owner, String method) {
        return maker().Apply(List.nil(), select(owner, method), List.nil());
    }

    public JCMethodInvocation applyMethod(String owner, String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), select(owner, method), arguments);
    }


    public JCMethodInvocation applyMethod(JCExpression owner, String method) {
        return maker().Apply(List.nil(), select(owner, method), List.nil());
    }

    public JCMethodInvocation applyMethod(JCExpression owner, String method, List<JCExpression> arguments) {
        return maker().Apply(List.nil(), select(owner, method), arguments);
    }


    public JCNewClass newObject(TypeModel classType) {
        return maker().NewClass(null, List.nil(), classType.generate(), List.nil(), null);
    }


    public JCExpression classReference(Class<?> owner) {
        return select(type(owner), CLASS_KEYWORD);
    }


    public JCExpression select(TypeModel owner, String member) {
        return maker().Select(owner.generate(), name(member));
    }

    public JCExpression select(String owner, String member) {
        return maker().Select(ident(owner), name(member));
    }

    public JCExpression select(JCExpression owner, String member) {
        return maker().Select(owner, name(member));
    }


    public JCReturn returnVariable(String reference) {
        return maker().Return(ident(reference));
    }
}
