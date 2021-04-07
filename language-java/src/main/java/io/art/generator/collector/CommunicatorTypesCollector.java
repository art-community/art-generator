package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.inspector.*;
import io.art.generator.type.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.formater.SignatureFormatter.*;
import java.lang.reflect.*;

@UtilityClass
public class CommunicatorTypesCollector {
    public static ImmutableSet<Type> collectCommunicatorTypes(CommunicatorModuleModel communicatorModel) {
        Builder<Type> types = immutableSetBuilder();
        ImmutableMap<String, CommunicatorModel> communicators = communicatorModel.getCommunicators();
        for (CommunicatorModel model : communicators.values()) {
            for (Method method : CommunicatorsMethodsInspector.getCommunicatorMethods(model.getCommunicatorInterface())) {
                Type[] parameterTypes = method.getGenericParameterTypes();
                if (parameterTypes.length > 1) {
                    throw new ValidationException(formatSignature(model.getCommunicatorInterface(), method), MORE_THAN_ONE_PARAMETER);
                }
                types.addAll(TypeCollector.collectModelTypes(method.getGenericReturnType()));
                if (isNotEmpty(parameterTypes)) {
                    types.addAll(TypeCollector.collectModelTypes(parameterTypes[0]));
                }
            }
        }
        return types.build();
    }
}
