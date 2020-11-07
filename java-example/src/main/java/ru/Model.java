package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Model {
    String stringValue;
    List<Set<String>> stringListValue;
    Set<List<String>> stringSetValue;
    Collection<Collection<Collection<String>>> stringCollectionValue;
    Map<String, List<String>> mapValue;
    String[] stringArray;
}
