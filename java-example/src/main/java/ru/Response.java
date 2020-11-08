package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Response {
    String stringValue;
    Model[] arrayModels;
    Collection<Collection<Model>> collectionModels;
    Collection<Model[]> collectionArrayModels;
    @Singular("stringModel")
    Map<String, Model> stringModels;
    @Singular("intModel")
    Map<Integer, Model> intModels;
}
