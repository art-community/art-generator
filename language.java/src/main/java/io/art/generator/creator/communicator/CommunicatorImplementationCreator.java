package io.art.generator.creator.communicator;

import io.art.generator.model.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class CommunicatorImplementationCreator {
    public NewBuilder createCommunicatorImplementation(TypeModel type, CommunicatorModel model, Method method) {
        return newBuilder(type)
                .method(COMMUNICATOR_ACTION_ID_NAME, method(COMMUNICATOR_ACTION_IDENTIFIER_TYPE, COMMUNICATOR_ACTION_NAME)
                        .addArgument(literal(model.getId()))
                        .addArgument(literal(method.getName()))
                        .apply());
    }
}
