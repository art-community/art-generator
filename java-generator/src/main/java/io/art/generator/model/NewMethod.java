package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.factory.CollectionsFactory.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.context.GeneratorContext.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewMethod {
    private String name;
    private long modifiers;
    private TypeModel returnType;

    private Set<ImportModel> classImports = setOf();

    private java.util.List<NewParameter> parameters = new LinkedList<>();
    private java.util.List<Supplier<JCStatement>> statements = new LinkedList<>();

    public NewMethod addClassImport(ImportModel importModel) {
        classImports.add(importModel);
        return this;
    }

    public NewMethod parameter(NewParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public NewMethod statement(Supplier<JCStatement> statement) {
        statements.add(statement);
        return this;
    }

    public JCMethodDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        JCExpression type = returnType.generate();
        JCBlock body = maker().Block(0L, from(statements.stream().map(Supplier::get).collect(toList())));
        List<JCVariableDecl> parameters = this.parameters.stream().map(NewParameter::generate).collect(toList());
        return maker().MethodDef(modifiers, name, type, nil(), from(parameters), nil(), body, null);
    }

    public static NewMethod newMethod() {
        return new NewMethod();
    }
}
