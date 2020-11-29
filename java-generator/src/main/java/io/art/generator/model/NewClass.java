package io.art.generator.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
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

    public NewClass addImport(ImportModel importModel) {
        imports.add(importModel);
        return this;
    }

    public NewClass inner(NewClass newClass) {
        imports.addAll(newClass.imports);
        innerClasses.add(newClass);
        return this;
    }

    public NewClass inner(ImmutableArray<NewClass> newClasses) {
        newClasses.forEach(innerClass -> imports.addAll(innerClass.imports));
        innerClasses.addAll(newClasses.toMutable());
        return this;
    }

    public NewClass field(String name, NewField field) {
        fields.put(name, field);
        if (!field.type().isJdk() && !field.type().getPackageName().isEmpty()) {
            imports.add(classImport(field.type().getFullName()));
        }
        return this;
    }

    public NewClass method(String name, NewMethod method) {
        methods.put(name, method);
        return this;
    }

    public JCClassDecl generate() {
        JCModifiers modifiers = maker().Modifiers(this.modifiers);
        Name name = elements().getName(this.name);
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        for (NewField field : fields.values()) {
            definitions.add(field.generate());
        }
        for (NewMethod method : methods.values()) {
            definitions.add(method.generate());
        }
        for (NewClass newClass : innerClasses) {
            definitions.add(newClass.generate());
        }
        return maker().ClassDef(modifiers, name, nil(), null, nil(), definitions.toList());
    }

    public static NewClass newClass() {
        return new NewClass();
    }
}
