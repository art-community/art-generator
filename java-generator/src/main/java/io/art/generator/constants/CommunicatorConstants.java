package io.art.generator.constants;

import io.art.communicator.proxy.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import java.lang.reflect.*;

public interface CommunicatorConstants {
    interface CommunicatorProxyMethods {
        Method GET_ACTIONS_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getActions"));
        Method GET_PROTOCOL_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }
}
