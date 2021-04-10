package io.art.generator.exception;

import lombok.*;

import static com.google.common.base.Throwables.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static java.text.MessageFormat.*;

@Getter
public class ValidationException extends RuntimeException {
    private final String signature;

    public ValidationException(String signature, String message) {
        super(message);
        this.signature = signature;
    }

    public ValidationException(Throwable throwable, String signature) {
        super(throwable);
        this.signature = signature;
    }

    public String write() {
        return format(VALIDATION_EXCEPTION_MESSAGE_FORMAT, signature, getStackTraceAsString(this));
    }
}
