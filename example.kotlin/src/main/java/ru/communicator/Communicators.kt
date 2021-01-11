package ru.communicator

import io.art.communicator.module.CommunicatorModule.communicator

fun myClient(): MyClient? {
    return communicator(MyClient::class.java)
}
