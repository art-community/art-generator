package ru;

import lombok.*;

@Value
@Builder
public class Request {
    String stringValue;
    Model model;
}
