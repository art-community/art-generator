package io.art.generator.model;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import lombok.Builder;
import lombok.*;
import lombok.experimental.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.core.caster.Caster.*;
import static io.art.core.collection.ImmutableMap.*;
import static io.art.core.collector.SetCollector.*;
import static io.art.generator.constants.Imports.*;
import static io.art.generator.constants.Names.CONSTRUCTOR_NAME;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.service.JavacService.*;
import static java.lang.reflect.Modifier.*;
import static java.util.stream.Collectors.*;

@Builder
@Getter
@Accessors(fluent = true)
public class StubClass implements GeneratedClass {
    private final JCModifiers modifiers;
    private final String name;
    private final List<JCTypeParameter> typarams;
    private final JCExpression extending;
    private final List<JCExpression> implementations;
    private final ListBuffer<JCTree> definitions;
    private final Set<ImportModel> imports;
    private final String packageName;

    public static StubClass from(ExistedClass existed){
        return from(existed.getDeclaration(), existed.getPackageUnit());
    }

    private static StubClass from(JCClassDecl declaration, JCCompilationUnit packageUnit){

        StubClassBuilder builder = StubClass.builder()
                .modifiers(stubModifiers(declaration.getModifiers()))
                .name(declaration.getSimpleName().toString())
                .typarams(declaration.getTypeParameters())
                .extending(declaration.getExtendsClause())
                .implementations(declaration.getImplementsClause())
                .imports(packageUnit.getImports().stream()
                        .map(jcImport -> new ImportModel(
                                jcImport.getQualifiedIdentifier().toString(),
                                jcImport.getQualifiedIdentifier().toString().endsWith("*"),
                                jcImport.isStatic()))
                        .collect(setCollector()))
                .packageName(packageUnit.getPackageName().toString());

        if(isInterface((int)declaration.mods.flags)) {
            builder.definitions(declaration.defs.stream().collect(toCollection(ListBuffer::new)));
            return builder.build();
        }

        builder.definitions(declaration.defs.stream()
                .map(definition -> defsMappers.get(definition.getClass()).apply(cast(definition)))
                .filter(Objects::nonNull)
                .collect(toCollection(ListBuffer::new)));

        builder.imports.add(NOT_IMPLEMENTED_EXCEPTION_MODEL);
        builder.definitions.add(generateDefaultConstructor());
        return builder.build();
    }

    @Override
    public JCClassDecl generate() {
        Name name = elements().getName(this.name);
        return maker().ClassDef(modifiers, name, typarams, extending, implementations, definitions.toList());
    }

    private static final Function<JCVariableDecl, JCTree> generateVariableStub = (existed) ->
            maker().VarDef(stubModifiers(existed.mods), existed.name, existed.vartype, null);

    private static final Function<JCMethodDecl, JCTree> generateMethodStub = (existed) ->{
            if (existed.name.toString().equals("<init>") && existed.params.length() == 0) return null;
            return maker().MethodDef(
                    stubModifiers(existed.mods),
                    existed.name,
                    existed.restype,
                    existed.typarams,
                    existed.recvparam,
                    existed.params,
                    existed.thrown,
                    maker().Block(0L, List.of(throwException(NOT_IMPLEMENTED_EXCEPTION_TYPE, literal(existed.name.toString())))),
                    existed.defaultValue);
    };

    private static final Function <JCClassDecl, JCTree> generateInnerClassStub = (existed) -> {
        String sourceFileName = existed.sym.sourcefile.getName().replace(".java", "");
        sourceFileName = sourceFileName.substring(sourceFileName.lastIndexOf(File.separatorChar) + 1);
        String packagePrefix = existed.sym.fullname.toString().substring(0, existed.sym.fullname.toString().indexOf(sourceFileName));
        return StubClass.from(existed, existedClass(packagePrefix + sourceFileName).getPackageUnit()).generate();
    };

    private static final ImmutableMap<Type, Function<JCTree, JCTree>> defsMappers = cast(immutableMapBuilder()
            .put(JCVariableDecl.class, generateVariableStub)
            .put(JCMethodDecl.class, generateMethodStub)
            .put(JCClassDecl.class, generateInnerClassStub)
            .build());

    private static JCModifiers stubModifiers(JCModifiers from){
        return maker().Modifiers(from.flags & (~FINAL));
    }

    private static JCTree generateDefaultConstructor(){
        return maker().MethodDef(
                maker().Modifiers(PUBLIC),
                elements().getName(CONSTRUCTOR_NAME),
                null,
                new ListBuffer<JCTypeParameter>().toList(),
                null,
                new ListBuffer<JCVariableDecl>().toList(),
                new ListBuffer<JCExpression>().toList(),
                maker().Block(0L, List.of(throwException(NOT_IMPLEMENTED_EXCEPTION_TYPE, literal("default constructor")))),
                null
        );
    }
}
