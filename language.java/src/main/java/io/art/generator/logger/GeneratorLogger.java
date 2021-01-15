package io.art.generator.logger;

import io.art.core.colorizer.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.checker.NullityChecker.let;
import static io.art.core.constants.DateTimeConstants.*;
import static io.art.core.extensions.StringExtensions.emptyIfNull;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import java.time.*;

@UtilityClass
public class GeneratorLogger {
    public void message(Object message) {
        System.out.println(format("[{0}] {1}: {2}", now(), emptyIfNull(let(moduleClass(), ExistedClass::getName)), message));
    }

    public void info(Object message) {
        message(message.toString());
    }

    public void debug(Object message) {
        if (compiler().verbose) {
            message(AnsiColorizer.additional(message.toString()));
        }
    }

    public void success(Object message) {
        message(AnsiColorizer.success(message.toString()));
    }

    public void warning(Object message) {
        message(AnsiColorizer.warning(message.toString()));
    }

    public void error(Object message) {
        System.err.println(AnsiColorizer.error(format("[{0}] {1}: {2}", now(), emptyIfNull(let(moduleClass(), ExistedClass::getName)), message)));
    }

    private String now() {
        return DD_MM_YYYY_HH_MM_SS_24H_DASH_FORMAT.format(LocalDateTime.now());
    }
}
