package ru.art.generator.javac.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.util.List.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
public class NewClass {
    private String name;
    private long modifiers;
    private Set<ImportModel> imports = new LinkedHashSet<>();
    private Map<String, NewField> fields = new LinkedHashMap<>();
    private Map<String, NewMethod> methods = new LinkedHashMap<>();

    public NewClass withImport(ImportModel importModel) {
        imports.add(importModel);
        return this;
    }

    public NewClass field(String name, NewField field) {
        fields.put(name, field);
        if (!field.type().isJdk() && field.type().isHasPackage()) {
            imports.add(importClass(field.type().getTypeFullName()));
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
        return maker().ClassDef(modifiers, name, nil(), null, nil(), definitions.toList());
    }

    public static NewClass newClass() {
        return new NewClass();
    }
}
