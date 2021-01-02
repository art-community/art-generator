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
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static java.util.stream.Collectors.*;
import static lombok.AccessLevel.*;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = PRIVATE)
public class NewClass {
    private String name;
    private long modifiers;
    private Set<ImportModel> imports = set();
    private Map<String, NewField> fields = map();
    private Map<String, NewMethod> methods = map();
    private Set<NewClass> innerClasses = set();
    private Set<TypeModel> interfaceTypes = set();

    public NewClass addImport(ImportModel importModel) {
        imports.add(importModel);
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
        methods.put(method.name(), method);
        return this;
    }

    public JCClassDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        ListBuffer<JCTree> definitions = fields.values().stream().map(NewField::generate).collect(toCollection(ListBuffer::new));
        methods.values().stream().map(NewMethod::generate).forEach(definitions::add);
        innerClasses.stream().map(NewClass::generate).forEach(definitions::add);
        List<JCExpression> implementations = List.from(interfaceTypes.stream().map(TypeModel::generateFullType).collect(toList()));
        return maker().ClassDef(modifiers, name, nil(), null, implementations, definitions.toList());
    }

    public static NewClass newClass() {
        return new NewClass();
    }
}
