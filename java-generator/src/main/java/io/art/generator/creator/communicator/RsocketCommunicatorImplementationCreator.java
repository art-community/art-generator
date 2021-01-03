package io.art.generator.creator.communicator;

import io.art.generator.model.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.RsocketImplementationMethods.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.service.JavacService.*;
import static io.art.rsocket.constants.RsocketModuleConstants.CommunicationMode.*;
import java.lang.reflect.*;

@UtilityClass
public class RsocketCommunicatorImplementationCreator {
    public NewBuilder createRsocketCommunicatorImplementation(RsocketCommunicatorModel communicatorModel, Method method) {
        NewBuilder builder = newBuilder(RSOCKET_COMMUNICATOR_IMPLEMENTATION_TYPE)
                .method(CONNECTOR_ID, literal(communicatorModel.getProxyClass().getSimpleName()));
        if (isNotEmpty(communicatorModel.getTargetServiceId())) {
            builder.method(SETUP_PAYLOAD, newBuilder(RSOCKET_SETUP_PAYLOAD_TYPE)
                    .method(SERVICE_METHOD_NAME, method(SERVICE_METHOD_IDENTIFIER_TYPE, SERVICE_METHOD_NAME)
                            .addArgument(literal(communicatorModel.getTargetServiceId()))
                            .addArgument(literal(ifEmpty(communicatorModel.getTargetMethodId(), method.getName())))
                            .apply())
                    .generate());
        }
        if (method.getReturnType() == void.class) {
            if (method.getParameterCount() == 0) {
                return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, FIRE_AND_FORGET.name()));
            }
            Type parameter = method.getGenericParameterTypes()[0];
            if (isFlux(parameter)) {
                return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, REQUEST_CHANNEL.name()));
            }
            return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, REQUEST_RESPONSE.name()));
        }
        if (isFlux(method.getReturnType())) {
            if (method.getParameterCount() == 0) {
                return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, REQUEST_STREAM.name()));
            }
            Type parameter = method.getGenericParameterTypes()[0];
            if (isFlux(parameter)) {
                return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, REQUEST_CHANNEL.name()));
            }
        }
        return builder.method(COMMUNICATION_MODE, select(RSOCKET_COMMUNICATION_MODE_TYPE, REQUEST_RESPONSE.name()));
    }
}
