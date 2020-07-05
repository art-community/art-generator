package ru.art.generator.javac.model;

import lombok.*;
import static ru.art.generator.javac.constants.Constants.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private final boolean jdk;
    private final boolean hasPackage;
    private final String typeFullName;
    private final String typeSimpleName;

    private TypeModel(String name) {
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

    public static TypeModel type(String name) {
        return new TypeModel(name);
    }
}
