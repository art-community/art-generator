package ru;

import lombok.*;

@Getter
public class GenericTypeParameterImplementation<T1, T2> extends GenericTypeParameter<T1> {
    String variable;

    public GenericTypeParameterImplementation(String parent, String variable) {
        super(parent);
        this.variable = variable;
    }
}
