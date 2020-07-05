package ru.art.generator.javac.model;

import lombok.*;
import java.util.*;

@Getter
public class ModuleModel {
    public final List<Class<?>> services = new LinkedList<>();

    //Stub method
    public ModuleModel service(Class<?> service) {
        services.add(service);
        return this;
    }

    public static ModuleModel module() {
        return new ModuleModel();
    }
}
