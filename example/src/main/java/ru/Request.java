package ru;

import lombok.*;
import ru.art.entity.*;
import static ru.art.entity.Entity.*;


public class Request {
    private Entity entity;
    @Getter(lazy = true)
    private static final Entity entity1 = entityBuilder().build();

    private Request(Entity entity) {
        this.entity = entity;
    }

    public String getInput() {
        return entity.getString("input");
    }

    public static RequestBuilder builder() {
        return new RequestBuilder(entityBuilder(), entityBuilder().build());
    }

    public RequestBuilder toBuilder() {
        return new RequestBuilder(entityBuilder(), entity);
    }

    public static class RequestBuilder {
        private final Entity current;
        private final Entity.EntityBuilder builder;

        public RequestBuilder(EntityBuilder builder, Entity current) {
            this.builder = builder;
            this.current = current;
        }

        public RequestBuilder input(String input) {
            builder.stringField("input", input);
            return this;
        }

        public Request build() {
            return new Request(merge(current, builder.build()));
        }
    }
}
