package io.art.generator.formater;

import io.art.core.collection.*;
import io.art.generator.model.*;
import lombok.experimental.*;

import java.lang.reflect.*;

import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;

@UtilityClass
public class SignatureFormatter {
    public String formatSignature(Type type, Method method) {
        ImmutableArray<String> arguments = stream(method.getParameters())
                .map(parameter -> parameter.getParameterizedType().getTypeName())
                .collect(immutableArrayCollector());
        return format("{0} {1}.{2}({3})", method.getReturnType().getName(), type.getTypeName(), method.getName(), toCommaDelimitedString(arguments));
    }

    public String formatSignature(Type type) {
        return format("{0}",type.getTypeName());
    }

    public String formatSignature(ExtractedProperty property) {
        return format("{0}: {1} {2}", property.owner().getTypeName(), property.type(), property.name());
    }
}
