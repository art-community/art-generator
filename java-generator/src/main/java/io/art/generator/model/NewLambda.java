package io.art.generator.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewLambda {
    private final ListBuffer<NewParameter> parameters = new ListBuffer<>();
    private final ListBuffer<Supplier<JCStatement>> statements = new ListBuffer<>();
    private Supplier<JCExpression> expression;

    public NewLambda parameter(NewParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public NewLambda expression(Supplier<JCExpression> expression) {
        this.expression = expression;
        return this;
    }

    public NewLambda addStatement(Supplier<JCStatement> statement) {
        statements.add(statement);
        return this;
    }

    public NewLambda addStatements(Collection<Supplier<JCStatement>> statements) {
        this.statements.addAll(statements);
        return this;
    }

    @SafeVarargs
    public final NewLambda addStatements(Supplier<JCStatement>... statements) {
        return addStatements(fixedArrayOf(statements));
    }

    public JCLambda generate() {
        JCBlock body = maker().Block(0L, statements.stream().map(Supplier::get).collect(toCollection(ListBuffer::new)).toList());
        com.sun.tools.javac.util.List<JCVariableDecl> parameters = this.parameters.stream()
                .map(NewParameter::generate)
                .collect(toCollection(ListBuffer::new))
                .toList();
        JCTree lambdaBody = nonNull(expression) ? expression.get() : body;
        return maker().Lambda(parameters, lambdaBody);
    }

    public static NewLambda newLambda() {
        return new NewLambda();
    }
}
