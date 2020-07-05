package ru.art.generator.javac.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import ru.art.generator.javac.exception.*;
import static com.sun.tools.javac.code.Symbol.*;
import static java.util.Arrays.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.context.GenerationContext.elements;
import static ru.art.generator.javac.context.GenerationContext.maker;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private final boolean array;
    private TypeTag primitiveTypeTag;
    private boolean jdk;
    private boolean hasPackage;
    private String typeFullName;
    private String typeSimpleName;

    private TypeModel(String name) {
        Optional<TypeTag> existedType = stream(TypeTag.values())
                .filter(tag -> tag.name().toLowerCase().equals(name.toLowerCase()))
                .findFirst();
        if (existedType.isPresent()) {
            array = false;
            primitiveTypeTag = existedType.get();
            return;
        }
        if (name.startsWith(ARRAY_MARKER )) {
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
            typeFullName = typeSimpleName = name.substring(name.lastIndexOf('.') + 1);
            return;
        }
        jdk = false;
        if (name.contains(".")) {
            hasPackage = true;
            typeFullName = name.substring(0, name.lastIndexOf('.'));
            typeSimpleName = name.substring(name.lastIndexOf('.') + 1);
            return;
        }
        hasPackage = false;
        typeFullName = typeSimpleName = name;
    }

    public JCExpression generate() {
        if (nonNull(primitiveTypeTag)) {
            return maker().TypeIdent(primitiveTypeTag);
        }
        return isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(getTypeSimpleName())))
                : maker().Ident(elements().getName(getTypeSimpleName()));
    }


    public static TypeModel type(String name) {
        return new TypeModel(name);
    }
}
