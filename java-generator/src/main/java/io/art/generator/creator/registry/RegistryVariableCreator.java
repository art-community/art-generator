package io.art.generator.creator.registry;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class RegistryVariableCreator {
    public static JCVariableDecl createRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name(REGISTRY_NAME)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
