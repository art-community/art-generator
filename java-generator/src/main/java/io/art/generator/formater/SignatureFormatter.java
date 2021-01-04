package io.art.generator.formater;

import io.art.core.factory.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class SignatureFormatter {
    public String formatSignature(Type type, Method method) {
        List<String> arguments = stream(method.getParameters()).map(parameter -> parameter.getParameterizedType().getTypeName()).collect(toCollection(ArrayFactory::dynamicArray));
        return format("{0} {1}.{2}({3})", method.getReturnType().getName(), type.getTypeName(), method.getName(), toCommaDelimitedString(arguments));
    }

    public String formatSignature(Type type, ExtractedProperty property) {
        return format("{0}: {1} {2}", type.getTypeName(), property.type(), property.name());
    }
}
