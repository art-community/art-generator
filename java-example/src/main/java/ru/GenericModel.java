package ru;

import lombok.*;
import java.time.*;
import java.util.*;

@Value
@Builder
public class GenericModel<K extends GenericTypeModel, V extends GenericTypeModel> {
    K k;
    V v;

    K ka[];
    V va[];

    List<K> kList;
    List<V> vList;

    GenericModel<K, V> FModel;
    GenericModel<K, V>[] FAModel;

    List<GenericModel<K, V>>[] FALModel;
    Set<GenericModel<K, V>>[] FASModel;
    Collection<GenericModel<K, V>>[] FACModel;
    Map<String, GenericModel<K, V>>[] FAMModel;
}
