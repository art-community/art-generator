package ru;

import lombok.*;
import ru.art.generator.javac.*;

public class Main {
    @Getter
    @Builder
    static
    class Request {
        private final String input;
    }

    @Getter
    @Builder
    static
    class Response {
        private final String output;
    }

    public static Response handle(Request request) {
        return Response.builder().output(request.input + "_output").build();
    }

    @Module
    public static void main(String[] args) {
        rsocket(handle);
    }
}
