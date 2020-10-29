package ru.art.generator.javac.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static io.art.core.constants.StringConstants.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private final boolean array;
    private final List<TypeModel> parameters;
    private TypeTag primitiveTypeTag;
    private boolean jdk;
    private boolean hasPackage;
    private String typeFullName;
    private String typeSimpleName;

    private TypeModel(String name, List<TypeModel> parameters) {
        this.parameters = parameters;
        Optional<TypeTag> existedType = stream(TypeTag.values())
                .filter(tag -> tag.name().toLowerCase().equals(name.toLowerCase()))
                .findFirst();
        if (existedType.isPresent()) {
            array = false;
            primitiveTypeTag = existedType.get();
            return;
        }
        if (name.startsWith(ARRAY_MARKER)) {
            array = true;
            String elementsType = name.substring(ARRAY_ELEMENTS_CLASS_NAME_INDEX);
            parseTypeName(elementsType.substring(0, elementsType.length() - 1));
            return;
        }
        array = false;
        parseTypeName(name);
    }

    private void parseTypeName(String name) {
        if (name.startsWith(JAVA_PACKAGE_PREFIX)) {
            jdk = true;
            hasPackage = false;
            typeFullName = typeSimpleName = name.substring(name.lastIndexOf(DOT) + 1);
            return;
        }
        jdk = false;
        if (name.contains(DOT)) {
            hasPackage = true;
            String packageName = name.substring(0, name.lastIndexOf(DOT));
            typeSimpleName = name.substring(name.lastIndexOf(DOT) + 1);
            typeFullName = packageName + DOT + typeSimpleName;
            return;
        }
        hasPackage = false;
        typeFullName = typeSimpleName = name;
    }

    public JCExpression generate() {
        if (nonNull(primitiveTypeTag)) {
            return maker().TypeIdent(primitiveTypeTag);
        }
        if (!parameters.isEmpty()) {
            com.sun.tools.javac.util.List<JCExpression> arguments = com.sun.tools.javac.util.List.from(parameters
                    .stream()
                    .map(TypeModel::generate)
                    .collect(toList()));
            return maker().TypeApply(maker().Ident(elements().getName(getTypeSimpleName())), arguments);
        }
        return isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(getTypeSimpleName())))
                : maker().Ident(elements().getName(getTypeSimpleName()));
    }

    public static TypeModel type(String name) {
        return type(name, emptyList());
    }

    public static TypeModel type(String name, List<TypeModel> parameters) {
        return new TypeModel(name, parameters);
    }
}
