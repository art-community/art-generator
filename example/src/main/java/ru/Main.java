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
//
//    interface Mappers {
//        MappersRegistry mappers = createMappers();
//
//        static MappersRegistry createMappers() {
//            MappersRegistry mappers = new MappersRegistry();
//
//            mappers.putToModel(Request.class, (Entity value) -> Request.builder()
//                    .value(value.map("value", PrimitiveMapping.toString))
//                    .build());
//
//            mappers.putFromModel(Request.class, (Request value) -> Entity.entityBuilder()
//                    .lazyPut("value", value::getValue, PrimitiveMapping.fromString)
//                    .build());
//
//            return mappers;
//        }
//    }
}
