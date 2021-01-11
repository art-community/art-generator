package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.model.implementation.server.*;
import lombok.experimental.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceTypesCollector {
    public ImmutableSet<Type> collectServerTypes(ServerModuleModel serverModel) {
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        ImmutableMap<String, ServiceModel> services = serverModel.getServices();
        for (ServiceModel service : services.values()) {
            for (Method method : getServiceMethods(service.getServiceClass())) {
                Type[] parameterTypes = method.getGenericParameterTypes();
                if (parameterTypes.length > 1) {
                    throw new ValidationException(formatSignature(service.getServiceClass(), method), MORE_THAN_ONE_PARAMETER);
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
