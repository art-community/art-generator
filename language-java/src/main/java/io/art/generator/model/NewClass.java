package io.art.generator.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static lombok.AccessLevel.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = PRIVATE)
public class NewClass {
    private String name;
    private long modifiers;
    private Set<ImportModel> imports = set();
    private Map<String, NewField> fields = map();
    private Set<NewMethod> methods = set();
    private Set<NewClass> innerClasses = set();
    private Set<TypeModel> interfaceTypes = set();

    public NewClass addImport(ImportModel importModel) {
        imports.add(importModel);
        return this;
    }

    public NewClass addImports(Set<ImportModel> importModels) {
        imports.addAll(importModels);
        return this;
    }

    public NewClass inner(NewClass newClass) {
        imports.addAll(newClass.imports);
        innerClasses.add(newClass);
        return this;
    }

    public NewClass inners(ImmutableArray<NewClass> newClasses) {
        newClasses.forEach(innerClass -> imports.addAll(innerClass.imports));
        innerClasses.addAll(newClasses.toMutable());
        return this;
    }

    public NewClass implement(TypeModel interfaceType) {
        imports.add(classImport(interfaceType.getFullName()));
        interfaceTypes.add(interfaceType);
        return this;
    }

    public NewClass implement(ImmutableArray<NewClass> newClasses) {
        newClasses.forEach(innerClass -> imports.addAll(innerClass.imports));
        innerClasses.addAll(newClasses.toMutable());
        return this;
    }

    public NewClass field(NewField field) {
        fields.put(field.name(), field);
        if (!field.type().isJdk() && !field.type().getPackageName().isEmpty()) {
            imports.add(classImport(field.type().getFullName()));
        }
        return this;
    }

    public NewClass method(NewMethod method) {
        methods.add(method);
        imports.addAll(method.classImports());
        return this;
    }

    public NewClass constructor(NewMethod constructor) {
        methods.add(constructor.name(CONSTRUCTOR_NAME).returnType(VOID_TYPE));
        imports.addAll(constructor.classImports());
        return this;
    }

    public NewClass constructors(NewMethod... constructors) {
        stream(constructors).forEach(this::constructor);
        return this;
    }

    public JCClassDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        if (fields.entrySet().stream().anyMatch(field -> field.getValue().byConstructor())) {
            generateConstructor();
        }
        ListBuffer<JCTree> definitions = fields.values()
                .stream()
                .map(NewField::generate)
                .collect(toCollection(ListBuffer::new));
        methods.stream()
                .map(NewMethod::generate)
                .forEach(definitions::add);
        innerClasses.stream()
                .map(NewClass::generate)
                .forEach(definitions::add);
        List<JCExpression> implementations = interfaceTypes.stream()
                .map(TypeModel::generateFullType)
                .collect(toCollection(ListBuffer::new))
                .toList();
        return maker().ClassDef(modifiers, name, nil(), null, implementations, definitions.toList());
    }

    private void generateConstructor() {
        Set<NewParameter> parameters = set();
        ListBuffer<Supplier<JCStatement>> statements = new ListBuffer<>();
        for (NewField field : fields.values()) {
            if (!field.byConstructor()) continue;
            if (isNull(field.initializer())) {
                parameters.add(newParameter(field.type(), field.name()));
                statements.add(() -> assign(select(THIS_NAME, field.name()), ident(field.name())));
                continue;
            }
            statements.add(() -> assign(select(THIS_NAME, field.name()), field.initializer().get()));
        }
        constructor(newMethod().parameters(parameters).modifiers(Modifier.PUBLIC).statements(statements));
    }

    public static NewClass newClass() {
        return new NewClass();
    }
}
