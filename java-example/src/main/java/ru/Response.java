package ru;

import lombok.*;

@Value
@Builder
public class Response {
    String stringValue;
    Model model;
}
