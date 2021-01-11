package ru.model;

import lombok.*;
import java.util.*;

@Value
@Builder
public class GenericModel<F, S extends GenericTypeParameter<F>> {
    F first;
    S second;

    F[] fistArray;
    S[] secondArray;

    List<F> firstList;
    List<S> secondList;

    GenericModel<F, S> firstModel;
    GenericModel<F, S>[] firstModelArray;

    List<GenericModel<F, S>>[] firstModelList;
    Set<GenericModel<F, S>>[] firstModelSet;
    Collection<GenericModel<F, S>>[] firstModelCollection;
    Map<String, GenericModel<F, S>>[] firstModelMap;
}
