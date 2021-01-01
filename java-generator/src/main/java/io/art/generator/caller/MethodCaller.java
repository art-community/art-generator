package io.art.generator.caller;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;

@Builder
public class MethodCaller {
    private final TypeModel classType;
    private final String method;
    private final JCExpression owner;
    private final ListBuffer<TypeModel> typeParameters = new ListBuffer<>();
    private final ListBuffer<JCExpression> arguments = new ListBuffer<>();


    public static MethodCaller method(TypeModel classType, String method) {
        return MethodCaller.builder()
                .classType(classType)
                .method(method)
                .build();
    }

    public static MethodCaller method(JCExpression owner, String method) {
        return MethodCaller.builder()
                .owner(owner)
                .method(method)
                .build();
    }

    public static MethodCaller method(String owner, String method) {
        return method(ident(owner), method);
    }

    public static MethodCaller method(String method) {
        return MethodCaller.builder().method(method).build();
    }


    public MethodCaller addTypeParameter(TypeModel parameter) {
        return addTypeParameters(fixedArrayOf(parameter));
    }

    public MethodCaller addTypeParameters(Collection<TypeModel> parameters) {
        typeParameters.addAll(parameters);
        return this;
    }

    public MethodCaller addTypeParameters(TypeModel... parameters) {
        return addTypeParameters(fixedArrayOf(parameters));
    }


    public MethodCaller addArgument(JCExpression argument) {
        return addArguments(fixedArrayOf(argument));
    }

    public MethodCaller addArguments(Collection<JCExpression> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    public MethodCaller addArguments(JCExpression... arguments) {
        return addArguments(fixedArrayOf(arguments));
    }


    public JCTree.JCMethodInvocation apply() {
        if (isNull(classType) && isNull(owner)) {
            ListBuffer<JCExpression> parameters = new ListBuffer<>();
            parameters.addAll(typeParameters.stream().map(TypeModel::generateFullType).collect(toList()));
            return maker().Apply(parameters.toList(), ident(method), arguments.toList());
        }
        if (isNull(classType)) {
            ListBuffer<JCExpression> parameters = new ListBuffer<>();
            parameters.addAll(typeParameters.stream().map(TypeModel::generateFullType).collect(toList()));
            return maker().Apply(parameters.toList(), select(owner, method), arguments.toList());
        }
        if (isNotEmpty(classType.getParameters())) {
            return maker().Apply(classType.generateParameters(), select(classType, method), arguments.toList());
        }
        ListBuffer<JCExpression> parameters = new ListBuffer<>();
        parameters.addAll(typeParameters.stream().map(TypeModel::generateFullType).collect(toList()));
        return maker().Apply(parameters.toList(), select(classType, method), arguments.toList());
    }

    public JCExpressionStatement execute() {
        return maker().Exec(apply());
    }

    public MethodCaller next(String method) {
        return next(method, UnaryOperator.identity());
    }

    public MethodCaller next(String method, UnaryOperator<MethodCaller> caller) {
        return caller.apply(method(apply(), method));
    }
}
