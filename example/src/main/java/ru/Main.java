package ru;

import io.art.model.annotation.*;
import io.art.model.module.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Main {
    @Configurator
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(MyService.class));
    }

    /* interface Mappers {
        ImmutableMap<Class, ValueToModelMapper> toModel = createToModelMappers();
        ImmutableMap<Class, ValueFromModelMapper> fromModel = createFromModelMappers();

        static ImmutableMap<Class, ValueToModelMapper> createToModelMappers() {
            ImmutableMap.Builder<Class, ValueToModelMapper> builder = ImmutableMap.builder();

            builder.put(Request.class, new ValueToModelMapper<Request, Entity>() {
                @Override
                public Request map(Entity value) {
                    return Request.builder()
                            .value(value.map("value", PrimitiveMapping.toString))
                            .build();
                }
            });

            return builder.build();
        }

        static ImmutableMap<Class, ValueFromModelMapper> createFromModelMappers() {
            ImmutableMap.Builder<Class, ValueFromModelMapper> builder = ImmutableMap.builder();

            builder.put(Request.class, new ValueFromModelMapper<Request, Entity>() {
                @Override
                public Entity map(Request value) {
                    return Entity.entityBuilder()
                            .lazyPut("value", value::getValue, PrimitiveMapping.fromString)
                            .build();
                }
            });

            return builder.build();
        }
    } */
}
