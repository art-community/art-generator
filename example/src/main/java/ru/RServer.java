package ru;

import ru.art.rsocket.function.*;

public class RServer {
    public static RServer rsocket() {
        return new RServer();
    }

    public RServer serve(RsocketServiceFunction function) {
        return this;
    }
}
