package io.art.generator.creator.stub;

import com.sun.tools.javac.tree.*;
import io.art.core.collection.*;
import io.art.core.exception.*;
import io.art.generator.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.collector.SetCollector.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.Imports.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Arrays.*;
import java.util.*;

public class StubCreator {
    public NewClass createProviderStub(ExistedClass moduleClass) {
        return newClass().modifiers(PUBLIC)
                .addImports(stream(IMPORTING_CLASSES).map(ImportModel::classImport).collect(setCollector()))
                .addImport(classImport(moduleClass.getFullName()))
                .name(moduleClass.getName() + PROVIDER_CLASS_SUFFIX)
                .method(newMethod()
                        .name(PROVIDE_NAME)
                        .modifiers(PUBLIC | FINAL | STATIC)
                        .returnType(MODULE_MODEL_TYPE)
                        .statement(() -> throwException(NOT_IMPLEMENTED_EXCEPTION_TYPE, literal(PROVIDE_NAME))));
    }

    public ImmutableSet<NewClass> createModuleImportStubs(ExistedClass module){
        Set<String> classes = setOf(processingEnvironment().getOptions().get("art.generator.recompilation.sources").split(","));
        for (JCTree.JCImport jcImport: module.getPackageUnit().getImports()){
            throw new NotImplementedException("todo");
        }
        throw new NotImplementedException("todo");
    }

    public NewClass createExistedClassStub(ExistedClass existedClass){
        throw new NotImplementedException("todo");
    }


}
