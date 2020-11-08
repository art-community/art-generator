package ru;

import lombok.*;

@Value
@Builder
public class Request {
    String stringValue;
    int intValue;
    Model model;
}
