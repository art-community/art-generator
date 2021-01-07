package ru.communicator;

import static io.art.communicator.module.CommunicatorModule.*;

public interface Communicators {
    static MyClient myClient() {
        return communicator(MyClient.class);
    }
}
