package io.art.generator.javac.model;

import lombok.*;
import static io.art.core.constants.StringConstants.*;

@Getter
@EqualsAndHashCode
public class ImportModel {
    private final String packagePart;
    private final String importPart;
    private final boolean asStatic;

    public ImportModel(String importText, boolean all, boolean asStatic) {
        this.asStatic = asStatic;
        if (!importText.contains(DOT)) {
            packagePart = importText;
            importPart = EMPTY_STRING;
            return;
        }
        packagePart = importText.substring(0, importText.lastIndexOf(DOT));
        if (all) {
            importPart = WILDCARD;
            return;
        }
        importPart = importText.substring(importText.lastIndexOf(DOT) + 1);
    }

    public static ImportModel importClass(String importText) {
        return new ImportModel(importText, false, false);
    }

    public static ImportModel importStaticClass(String importText) {
        return new ImportModel(importText, false, true);
    }

    public static ImportModel importPackage(String importText) {
        return new ImportModel(importText, true, false);
    }
}
