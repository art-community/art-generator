package io.art.generator.context;

import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.constants.*;
import io.art.generator.logger.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
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
    @Singular("exitedClass")
    private final Map<String, ExistedClass> existedClasses;
    @Singular("moduleClass")
    private final Map<String, ExistedClass> moduleClasses;
    private final CompilationService compilationService;
    private final Language language;
    private final GeneratorLogger logger;
}
