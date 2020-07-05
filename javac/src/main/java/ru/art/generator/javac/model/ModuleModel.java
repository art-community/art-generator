package ru.art.generator.javac.model;

import lombok.*;
import static java.util.Objects.*;
import java.util.*;
import java.util.function.*;

@Getter
@ToString
public class ModuleModel {
    private final ServerModel server = new ServerModel();

    public ModuleModel serve(Consumer<ServerModel> server) {
        server.accept(this.server);
        return this;
    }

    public static ModuleModel module() {
        return new ModuleModel();
    }

    @Getter
    @ToString
    public class ServerModel {
        public final Map<Class<?>, ServiceModel> services = new LinkedHashMap<>();
        public final Set<FunctionModel> functions = new LinkedHashSet<>();

        public ServerModel rsocket(Class<?> serviceClass) {
            services.put(serviceClass, ServiceModel.builder().rsocket(true).serviceClass(serviceClass).build());
            return this;
        }

        public ServerModel rsocket(Class<?> serviceClass, String method) {
            ServiceModel existed = services.get(serviceClass);
            if (nonNull(existed)) {
                existed.getServiceMethods().add(ServiceMethodModel.builder().rsocket(true).name(method).build());
                return this;
            }
            services.put(serviceClass, ServiceModel.builder().rsocket(true).serviceClass(serviceClass)
                    .method(ServiceMethodModel.builder().rsocket(true).name(method).build())
                    .build());
            return this;
        }

        public ServerModel rsocket(String function) {
            functions.add(FunctionModel.builder().rsocket(true).name(function).build());
            return this;
        }
    }

    @Value
    @Builder
    public static class ServiceModel {
        boolean rsocket;
        boolean grpc;
        boolean http;
        boolean soap;
        Class<?> serviceClass;
        @Singular("method")
        Set<ServiceMethodModel> serviceMethods;
    }

    @Value
    @Builder
    public static class ServiceMethodModel {
        boolean rsocket;
        boolean grpc;
        boolean http;
        boolean soap;
        String name;
    }

    @Value
    @Builder
    public static class FunctionModel {
        boolean rsocket;
        boolean grpc;
        boolean http;
        boolean soap;
        String name;
    }
}
