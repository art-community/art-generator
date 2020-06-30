package ru;

import lombok.*;
import ru.art.entity.*;
import ru.art.entity.mapper.*;
import ru.art.generator.javac.Module;

public class Main {
    @Getter
    @Builder
    static
    class Request {
        private final String input;
        public static ValueFromModelMapper<Request, Entity> fromRequest = request -> Entity.entityBuilder()
                .stringField("input", request.input)
                .build();
    }

    @Getter
    @Builder
    static
    class Response {
        private final String output;

        public static ValueToModelMapper<Response, Entity> toResponse = entity -> Response.builder()
                .output(entity.getString("output"))
                .build();

        public static ValueFromModelMapper<Response, Entity> fromResponse = response -> Entity.entityBuilder()
                .stringField("output", response.output)
                .build();
    }

    public static Response handle(Request request) {
        return Response.builder().output(request.input + "_output").build();
    }

    @Module
    public static void main(String[] args) {
//        RsocketServiceFunction
//                .rsocket("handle")
//                .requestMapper(Request.toRequest)
//                .responseMapper(Response.fromResponse)
//                .handle(Main::handle);
        //RsocketServer.rsocketTcpServer().await();

        rsocket(handle);
    }
}
