package io.art.generator.service;

import com.sun.tools.javac.api.*;
import io.art.core.collection.*;
import lombok.experimental.*;
import static com.sun.tools.javac.main.Option.*;
import static io.art.core.collection.ImmutableArray.immutableArrayBuilder;
import static io.art.core.constants.ArrayConstants.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.constants.GeneratorConstants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import java.io.*;
import java.util.*;

@UtilityClass
public class CompilationService {
    public void recompile() {
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_BYTES);
        ImmutableArray.Builder<String> arguments = immutableArrayBuilder();
        arguments.add(CLASSPATH.getText());
        arguments.add(options().get(CLASSPATH));
        arguments.add(D.getText());
        arguments.add(options().get(D));
        arguments.add(DISABLE_OPTION_ENABLED);
        List<String> classes = existedClasses()
                .values()
                .stream()
                .map(existed -> existed.getPackageUnit().getSourceFile().getName())
                .collect(toList());
        arguments.addAll(classes);
        javacTool.run(inputStream, outputStream, System.err, arguments.build().toArray(new String[0]));
    }
}
