package ru.model;

import lombok.*;

@AllArgsConstructor
@Setter
@Value
public class HttpResponse {
    String message;

    public static HttpResponse httpResponse(String response){
        return new HttpResponse(response);
    }
}
