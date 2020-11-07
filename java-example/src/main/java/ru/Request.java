package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Request {
    String stringValue;
    List<String> stringListValue;
    Set<String> stringSetValue;
    Collection<String> stringCollectionValue;
}
