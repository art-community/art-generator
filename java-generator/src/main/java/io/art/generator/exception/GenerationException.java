package io.art.generator.exception;

import static com.google.common.base.Throwables.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static java.text.MessageFormat.*;

public class GenerationException extends RuntimeException {
    public GenerationException(String message) {
        super(message);
    }

    public GenerationException(Throwable throwable) {
        super(throwable);
    }

    public String write() {
        return format(GENERATION_EXCEPTION_MESSAGE_FORMAT, getStackTraceAsString(this));
    }
}
