package io.art.generator.normalizer;

import static io.art.core.constants.StringConstants.*;
import static io.art.core.determiner.SystemDeterminer.*;
import static java.lang.String.*;

public class ClassPathNormalizer {
    public static String normalizeClassPath(String[] paths) {
        if (isWindows()) {
            return join(SEMICOLON, paths);
        }
        return join(COLON, paths);
    }
}
