package io.art.generator.exception;

public class GenerationException extends RuntimeException {
    public GenerationException(String message) {
        super(message);
    }

    public GenerationException(Throwable throwable) {
        super(throwable);
    }
}
