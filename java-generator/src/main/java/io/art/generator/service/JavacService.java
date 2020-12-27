package io.art.generator.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.TypeModel.*;

@UtilityClass
public class JavacService {
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
        return returnMethodCall(maker().Apply(List.nil(), select(variable, method), arguments));
    }

    public JCReturn returnMethodCall(JCExpression method) {
        return maker().Return(method);
    }


    public JCNewClass newObject(TypeModel classType) {
        return newObject(classType, List.nil());
    }


    public JCNewClass newObject(TypeModel classType, List<JCExpression> arguments) {
        return maker().NewClass(null, List.nil(), classType.generateFullType(), arguments, null);
    }

    public JCNewClass newObject(TypeModel classType, java.util.List<JCExpression> arguments) {
        return newObject(classType, List.from(arguments));
    }


    public JCExpression classReference(Class<?> owner) {
        return select(type(owner), CLASS_KEYWORD);
    }


    public JCExpression select(TypeModel owner, String member) {
        return maker().Select(owner.generateBaseType(), name(member));
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

    public JCMemberReference invokeReference(TypeModel type, String name) {
        return maker().Reference(INVOKE, name(name), type.generateBaseType(), null);
    }

    public JCMemberReference newReference(TypeModel type) {
        return maker().Reference(NEW, null, type.generateBaseType(), null);
    }
}
