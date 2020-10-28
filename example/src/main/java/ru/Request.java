package ru;

import lombok.*;

@Value
@Builder
public class Request {
    private final String value;
}
