package io.art.generator.javac.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static io.art.core.constants.StringConstants.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.javac.constants.GeneratorConstants.*;
import static io.art.generator.javac.context.GenerationContext.*;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private final String outerTypeName;
    private final String innerTypeName;
    private final List<TypeModel> parameters;

    private String packageName = EMPTY_STRING;
    private String fullName;
    private String name;

    private TypeTag primitiveType;

    private boolean jdk;
    private boolean array;

    private TypeModel(String outerTypeName, String innerTypeName, List<TypeModel> parameters) {
        this.outerTypeName = outerTypeName;
        this.innerTypeName = innerTypeName;
        this.parameters = parameters;
        Optional<TypeTag> existedType = stream(TypeTag.values())
                .filter(tag -> tag.name().toLowerCase().equals(outerTypeName.toLowerCase()))
                .findFirst();
        if (existedType.isPresent()) {
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
        parseTypeName(outerTypeName);
    }

    private void parseTypeName(String outerTypeName) {
        if (outerTypeName.startsWith(JAVA_PACKAGE_PREFIX)) {
            jdk = true;
            packageName = outerTypeName.substring(0, outerTypeName.lastIndexOf(DOT));
            name = outerTypeName.substring(outerTypeName.lastIndexOf(packageName) + packageName.length() + 1);
            fullName = outerTypeName;
            return;
        }
        if (outerTypeName.contains(DOT)) {
            packageName = outerTypeName.substring(0, outerTypeName.lastIndexOf(DOT));
            String nameWithoutPackage = outerTypeName.substring(outerTypeName.lastIndexOf(packageName) + packageName.length() + 1);
            if (!innerTypeName.isEmpty()) {
                name = nameWithoutPackage + DOT + innerTypeName;
                fullName = outerTypeName + DOT + innerTypeName;
                return;
            }
            name = nameWithoutPackage;
            fullName = outerTypeName;
            return;
        }
        if (!innerTypeName.isEmpty()) {
            name = fullName = outerTypeName + DOT + innerTypeName;
            return;
        }
        this.name = fullName = outerTypeName;
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
            return maker().TypeApply(isArray()
                    ? maker().TypeArray(maker().Ident(elements().getName(name)))
                    : maker().Ident(elements().getName(name)), arguments);
        }
        return isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(name)))
                : maker().Ident(elements().getName(name));
    }


    public static TypeModel type(Class<?> typeClass) {
        return type(typeClass, emptyList());
    }

    public static TypeModel type(Class<?> typeClass, List<TypeModel> parameters) {
        return type(typeClass.getName(), parameters);
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
