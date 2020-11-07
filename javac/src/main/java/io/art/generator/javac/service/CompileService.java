package io.art.generator.javac.service;

import com.google.common.collect.*;
import com.sun.tools.javac.api.*;
import lombok.experimental.*;
import static com.sun.tools.javac.main.Option.*;
import static io.art.core.constants.ArrayConstants.*;
import static java.util.stream.Collectors.*;
import static io.art.generator.javac.constants.GeneratorConstants.ProcessorOptions.*;
import static io.art.generator.javac.context.GenerationContext.*;
import java.io.*;

@UtilityClass
public class CompileService {
    public static void recompile() {
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_BYTES);
        ImmutableList.Builder<String> arguments = ImmutableList.builder();
        arguments.add(CLASSPATH.getText());
        arguments.add(options().get(CLASSPATH));
        arguments.add(D.getText());
        arguments.add(options().get(D));
        arguments.add(DISABLE_OPTION_ENABLED);
        arguments.addAll(getExistedClasses().values().stream().map(existed -> existed.getPackageUnit().getSourceFile().getName()).collect(toList()));
        javacTool.run(inputStream, outputStream, System.err, arguments.build().toArray(new String[0]));
    }
}
