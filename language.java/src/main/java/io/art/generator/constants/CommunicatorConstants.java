package io.art.generator.constants;

import io.art.communicator.constants.CommunicatorModuleConstants.*;
import io.art.communicator.proxy.*;
import io.art.core.collection.*;
import io.art.core.wrapper.*;
import io.art.generator.model.*;
import io.art.http.communicator.*;
import io.art.rsocket.communicator.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.http.constants.HttpModuleConstants.HttpProtocol.*;
import static io.art.rsocket.constants.RsocketModuleConstants.RsocketProtocol.*;
import java.lang.reflect.*;


public interface CommunicatorConstants {
    interface CommunicatorProxyMethods {
        Method GET_ACTIONS_METHOD = ExceptionWrapper.wrapExceptionCall(() -> CommunicatorProxy.class.getDeclaredMethod("getActions"));
        Method GET_PROTOCOL_METHOD = ExceptionWrapper.wrapExceptionCall(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }

    ImmutableMap<CommunicatorProtocol, TypeModel> PROTOCOLS = ImmutableMap.<CommunicatorProtocol, TypeModel>immutableMapBuilder()
            .put(RSOCKET, type(RsocketCommunicatorAction.class))
            .put(HTTP, type(HttpCommunicatorAction.class))
            .build();
}
