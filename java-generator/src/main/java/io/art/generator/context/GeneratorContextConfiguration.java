package io.art.generator.context;

import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.*;
import java.util.*;

@Getter
@Builder
public class GeneratorContextConfiguration {
    private final JavacProcessingEnvironment processingEnvironment;
    private final Options options;
    private final JavaCompiler compiler;
    private final TreeMaker maker;
    private final JavacElements elements;
    private final ExistedClass mainClass;
    private final ExistedMethod mainMethod;
    private final ExistedMethod configureMethod;
    @Singular("exitedClass")
    private final Map<String, ExistedClass> existedClasses;
}
