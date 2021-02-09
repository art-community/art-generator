package io.art.generator.constants;

import io.art.communicator.proxy.*;
import io.art.core.wrapper.*;
import java.lang.reflect.*;

public interface CommunicatorConstants {
    interface CommunicatorProxyMethods {
        Method GET_ACTIONS_METHOD = ExceptionWrapper.wrapExceptionCall(() -> CommunicatorProxy.class.getDeclaredMethod("getActions"));
        Method GET_PROTOCOL_METHOD = ExceptionWrapper.wrapExceptionCall(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }
}
