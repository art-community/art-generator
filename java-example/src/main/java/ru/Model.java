package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Model {
    String stringValue;
    Map<String, Model> models;
}
