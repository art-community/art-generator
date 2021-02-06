package io.art.generator.constants;

import io.art.communicator.proxy.*;

import java.lang.reflect.*;

import static io.art.core.wrapper.ExceptionWrapper.*;

public interface CommunicatorConstants {
    interface CommunicatorProxyMethods {
        Method GET_ACTIONS_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getActions"));
        Method GET_PROTOCOL_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }
}
