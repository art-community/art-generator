package io.art.generator.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.factory.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewLambda {
    private List<NewParameter> parameters = new LinkedList<>();
    private Supplier<JCExpression> expression;
    private List<Supplier<JCStatement>> statements = new LinkedList<>();

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
        JCBlock body = maker().Block(0L, from(statements.stream().map(Supplier::get).collect(toCollection(ArrayFactory::dynamicArray))));
        ListBuffer<JCVariableDecl> parameters = this.parameters.stream().map(NewParameter::generate).collect(toCollection(ListBuffer::new));
        JCTree lambdaBody = nonNull(expression) ? expression.get() : body;
        return maker().Lambda(parameters.toList(), lambdaBody);
    }

    public static NewLambda newLambda() {
        return new NewLambda();
    }
}
