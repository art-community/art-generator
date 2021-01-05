package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.collector.SetCollector.setCollector;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewMethod {
    private String name;
    private long modifiers;
    private TypeModel returnType;

    private Set<ImportModel> classImports = set();
    private Set<NewParameter> parameters = set();
    private ListBuffer<Supplier<JCStatement>> statements = new ListBuffer<>();

    public NewMethod addImport(ImportModel importModel) {
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
        JCExpression type = returnType.generateFullType();
        JCBlock body = maker().Block(0L, from(statements.stream()
                .map(Supplier::get)
                .collect(toCollection(ListBuffer::new))));
        com.sun.tools.javac.util.List<JCVariableDecl> parameters = this.parameters
                .stream()
                .map(NewParameter::generate)
                .collect(toCollection(ListBuffer::new))
                .toList();
        return maker().MethodDef(modifiers, name, type, nil(), parameters, nil(), body, null);
    }

    public static NewMethod newMethod() {
        return new NewMethod();
    }

    public static NewMethod overrideMethod(Method declaration) {
        return overrideMethod(declaration, type(declaration.getGenericReturnType()));
    }

    public static NewMethod overrideMethod(Method declaration, TypeModel returnTypeModel) {
        Set<NewParameter> parameters = stream(declaration.getParameters())
                .map(parameter -> newParameter(type(parameter.getParameterizedType()), parameter.getName()))
                .collect(setCollector());
        NewMethod method = newMethod()
                .returnType(returnTypeModel)
                .name(declaration.getName())
                .modifiers(PUBLIC)
                .parameters(parameters);
        if (!returnTypeModel.isJdk()) {
            method.addImport(classImport(returnTypeModel.getFullName()));
        }
        stream(declaration.getGenericParameterTypes())
                .map(TypeModel::type)
                .filter(type -> !type.isJdk())
                .map(TypeModel::getFullName)
                .map(ImportModel::classImport)
                .forEach(method::addImport);
        return method;
    }
}
