package io.art.generator.logger;

import io.art.core.colorizer.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.constants.DateTimeConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.state.GeneratorState.*;
import static java.text.MessageFormat.*;
import java.time.*;
import java.util.function.*;


@AllArgsConstructor
public class GeneratorLogger {
    private final Consumer<String> message;
    private final Consumer<String> error;

    public static void message(Object message) {
        logger().message.accept(format("[{0}] {1}: {2}", now(), emptyIfNull(let(moduleClass(), ExistedClass::getName)), message));
    }

    public static void info(Object message) {
        if (compiler().verbose) {
            message(message.toString());
        }
    }

    public static void debug(Object message) {
        if (compiler().verbose) {
            message(AnsiColorizer.additional(message.toString()));
        }
    }

    public static void success(Object message) {
        message(AnsiColorizer.success(message.toString()));
    }

    public static void warning(Object message) {
        message(AnsiColorizer.warning(message.toString()));
    }

    public static void error(Object message) {
        logger().error.accept(AnsiColorizer.error(format("[{0}] {1}: {2}", now(), emptyIfNull(let(moduleClass(), ExistedClass::getName)), message)));
    }

    private static String now() {
        return DD_MM_YYYY_HH_MM_SS_24H_DASH_FORMAT.format(LocalDateTime.now());
    }
}
