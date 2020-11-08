package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Response {
    String stringValue;
    @Singular("listModel")
    List<Model> listModels;
    @Singular("stringModel")
    Map<String, Model> stringModels;
    @Singular("intModel")
    Map<Integer, Model> intModels;
}
