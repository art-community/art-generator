package io.art.generator.constants;

import io.art.communicator.proxy.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import java.lang.reflect.*;

public interface CommunicatorConstants {
    interface CommunicatorProxyMethods {
        Method GET_SPECIFICATIONS_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getSpecifications"));
        Method GET_PROTOCOL_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }
}
