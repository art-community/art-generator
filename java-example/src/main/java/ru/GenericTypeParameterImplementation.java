package ru;

import lombok.*;

@Getter
@Builder
public class GenericTypeParameterImplementation<T> extends GenericTypeParameter<T> {
    String variable;
}
