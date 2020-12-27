package io.art.generator.logger;

import io.art.core.colorizer.*;
import lombok.experimental.*;
import static io.art.core.constants.DateTimeConstants.*;
import static java.text.MessageFormat.*;
import java.time.*;

@UtilityClass
public class GeneratorLogger {
    public void info(Object message) {
        System.out.println(format("[{0}]: {1}", now(), message));
    }

    public void success(Object message) {
        info(AnsiColorizer.success(message.toString()));
    }

    public void warning(Object message) {
        info(AnsiColorizer.warning(message.toString()));
    }

    public void error(Object message) {
        System.err.println(AnsiColorizer.error(format("[{0}]: {1}", now(), message)));
    }

    private String now() {
        return DD_MM_YYYY_HH_MM_SS_24H_DASH_FORMAT.format(LocalDateTime.now());
    }
}
