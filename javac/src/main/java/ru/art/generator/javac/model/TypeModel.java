package ru.art.generator.javac.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static io.art.core.constants.StringConstants.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private final String outerTypeName;
    private final String innerTypeName;
    private String packageName;
    private final boolean array;
    private final List<TypeModel> parameters;
    private TypeTag primitiveType;
    private boolean jdk;
    private String fullName;
    private String name;

    private TypeModel(String outerTypeName, String innerTypeName, List<TypeModel> parameters) {
        this.outerTypeName = outerTypeName;
        this.innerTypeName = innerTypeName;
        this.parameters = parameters;
        Optional<TypeTag> existedType = stream(TypeTag.values())
                .filter(tag -> tag.name().toLowerCase().equals(outerTypeName.toLowerCase()))
                .findFirst();
        if (existedType.isPresent()) {
            array = false;
            primitiveType = existedType.get();
            name = outerTypeName;
            return;
        }
        if (outerTypeName.startsWith(ARRAY_MARKER)) {
            array = true;
            String elementsType = outerTypeName.substring(ARRAY_ELEMENTS_CLASS_NAME_INDEX);
            parseTypeName(elementsType.substring(0, elementsType.length() - 1));
            return;
        }
        array = false;
        parseTypeName(outerTypeName);
    }

    private void parseTypeName(String name) {
        if (name.startsWith(JAVA_PACKAGE_PREFIX)) {
            jdk = true;
            packageName = name.substring(name.lastIndexOf(DOT) + 1);
            this.name = fullName = name;
            return;
        }
        jdk = false;
        if (name.contains(DOT)) {
            packageName = name.substring(0, name.lastIndexOf(DOT));
            if (!innerTypeName.isEmpty()) {
                this.name = name.substring(name.lastIndexOf(packageName) + 1) + DOT + innerTypeName;
                fullName = name + DOT + innerTypeName;
            }
            this.name = name.substring(name.lastIndexOf(packageName) + 1) + DOT + innerTypeName;
            fullName = name;
            return;
        }
        packageName = EMPTY_STRING;
        if (!innerTypeName.isEmpty()) {
            this.name = fullName = name + DOT + innerTypeName;
            return;
        }
        this.name = fullName = name;
    }

    public JCExpression generate() {
        if (nonNull(primitiveType)) {
            return maker().TypeIdent(primitiveType);
        }
        if (!parameters.isEmpty()) {
            com.sun.tools.javac.util.List<JCExpression> arguments = com.sun.tools.javac.util.List.from(parameters
                    .stream()
                    .map(TypeModel::generate)
                    .collect(toList()));
            return maker().TypeApply(maker().Ident(elements().getName(name)), arguments);
        }
        return isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(name)))
                : maker().Ident(elements().getName(name));
    }

    public static TypeModel type(String name) {
        return type(name, emptyList());
    }

    public static TypeModel type(String name, List<TypeModel> parameters) {
        return new TypeModel(name, EMPTY_STRING, parameters);
    }

    public static TypeModel type(String outerName, String innerName) {
        return new TypeModel(outerName, innerName, emptyList());
    }

    public static TypeModel type(String outerName, String innerName, List<TypeModel> parameters) {
        return new TypeModel(outerName, innerName, parameters);
    }
}
