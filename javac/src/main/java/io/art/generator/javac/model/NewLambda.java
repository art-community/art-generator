package io.art.generator.javac.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.javac.context.GenerationContext.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewLambda {
    private String name;
    private long modifiers;
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

    public NewLambda blockStatement(Supplier<JCStatement> statement) {
        statements.add(statement);
        return this;
    }

    public JCLambda generate() {
        JCBlock body = maker().Block(0L, from(statements.stream().map(Supplier::get).collect(toList())));
        ListBuffer<JCVariableDecl> parameters = this.parameters.stream().map(NewParameter::generate).collect(toCollection(ListBuffer::new));
        JCTree lambdaBody = nonNull(expression) ? expression.get() : body;
        return maker().Lambda(parameters.toList(), lambdaBody);
    }

    public static NewLambda newLambda() {
        return new NewLambda();
    }
}
