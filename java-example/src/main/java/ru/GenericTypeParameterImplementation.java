package ru;

import lombok.*;

@Getter
@Builder
public class GenericTypeParameterImplementation<T1, T2> extends GenericTypeParameter<T1> {
    String variable;
}
