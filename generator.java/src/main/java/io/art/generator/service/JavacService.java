package io.art.generator.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.TypeModel.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

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


    public JCLiteral literal(Object literal) {
        return maker().Literal(literal);
    }


    public JCExpressionStatement execMethodCall(String variable, String method, ImmutableArray<JCExpression> arguments) {
        return maker().Exec(maker().Apply(List.nil(), select(variable, method), List.from(arguments)));
    }

    public JCReturn returnMethodCall(String variable, String method, ImmutableArray<JCExpression> arguments) {
        return returnExpression(maker().Apply(List.nil(), select(variable, method), List.from(arguments)));
    }

    public JCReturn returnMethodCall(String variable, String method, JCExpression... arguments) {
        return returnExpression(maker().Apply(List.nil(), select(variable, method), List.from(arguments)));
    }

    public JCReturn returnExpression(JCExpression method) {
        return maker().Return(method);
    }


    public JCNewClass newObject(TypeModel classType) {
        return newObject(classType, emptyImmutableArray());
    }

    public JCNewArray newArray(TypeModel classType, Integer... sizes) {
        List<JCExpression> dimensions = stream(sizes)
                .map(size -> (JCExpression) literal(size))
                .collect(toCollection(ListBuffer::new))
                .toList();
        return maker().NewArray(classType.generateBaseType(), dimensions, null);
    }

    public JCNewClass newObject(String className) {
        return newObject(className, emptyImmutableArray());
    }


    public JCNewClass newObject(TypeModel classType, ImmutableArray<JCExpression> arguments) {
        return maker().NewClass(null, List.nil(), classType.generateFullType(), List.from(arguments), null);
    }

    public JCNewClass newObject(String className, ImmutableArray<JCExpression> arguments) {
        return maker().NewClass(null, List.nil(), ident(className), List.from(arguments), null);
    }


    public JCNewClass newObject(String className, JCExpression... arguments) {
        return newObject(className, immutableArrayOf(arguments));
    }

    public JCNewClass newObject(TypeModel classType, JCExpression... arguments) {
        return newObject(classType, immutableArrayOf(arguments));
    }


    public JCExpression classReference(Class<?> owner) {
        return select(type(owner), CLASS_KEYWORD);
    }

    public JCExpression classReference(String owner) {
        return select(ident(owner), CLASS_KEYWORD);
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

    public JCMemberReference invokeReference(JCExpression owner, String name) {
        return maker().Reference(INVOKE, name(name), owner, null);
    }

    public JCMemberReference newReference(TypeModel type) {
        return maker().Reference(NEW, null, type.generateBaseType(), null);
    }

    public JCMemberReference newReference(String className) {
        return maker().Reference(NEW, null, ident(className), null);
    }

    public JCStatement assign(JCExpression left, JCExpression right) {
        return maker().Exec(maker().Assign(left, right));
    }
}
