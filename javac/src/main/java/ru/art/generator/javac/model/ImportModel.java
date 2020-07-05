package ru.art.generator.javac.model;

import lombok.*;

@Getter
public class ImportModel {
    private final String packagePart;
    private final String importPart;
    private final boolean asStatic;

    public ImportModel(String importText, boolean all, boolean asStatic) {
        this.asStatic = asStatic;
        if (!importText.contains(".")) {
            packagePart = importText;
            importPart = "";
            return;
        }
        packagePart = importText.substring(0, importText.lastIndexOf('.'));
        if (all) {
            importPart = "*";
            return;
        }
        importPart = importText.substring(importText.lastIndexOf('.') + 1);
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
