package ru.model;

public class HttpExampleException extends Throwable{
    public HttpExampleException(String description){
        super(description);
    }

    HttpExampleException(Throwable cause){
        super(cause);
    }
}
