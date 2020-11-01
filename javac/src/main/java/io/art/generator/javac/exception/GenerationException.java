package io.art.generator.javac.exception;

public class GenerationException extends RuntimeException {
    public GenerationException(String message) {
        super(message);
    }

    public GenerationException(Throwable throwable) {
        super(throwable);
    }
}
