package io.art.generator.creator.stub;

import com.sun.tools.javac.tree.JCTree;
import io.art.core.collection.ImmutableSet;
import io.art.core.exception.NotImplementedException;
import io.art.generator.model.ExistedClass;
import io.art.generator.model.ImportModel;
import io.art.generator.model.NewClass;
import lombok.experimental.UtilityClass;


import java.util.Set;

import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.collector.SetCollector.setCollector;
import static io.art.core.factory.SetFactory.setOf;
import static io.art.generator.constants.Imports.IMPORTING_CLASSES;
import static io.art.generator.constants.Names.PROVIDER_CLASS_SUFFIX;
import static io.art.generator.constants.Names.PROVIDE_NAME;
import static io.art.generator.constants.TypeModels.MODULE_MODEL_TYPE;
import static io.art.generator.constants.TypeModels.NOT_IMPLEMENTED_EXCEPTION_TYPE;
import static io.art.generator.context.GeneratorContext.processingEnvironment;
import static io.art.generator.model.ImportModel.classImport;
import static io.art.generator.model.NewClass.newClass;
import static io.art.generator.model.NewMethod.newMethod;
import static io.art.generator.service.JavacService.literal;
import static io.art.generator.service.JavacService.throwException;
import static java.util.Arrays.stream;

@UtilityClass
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
