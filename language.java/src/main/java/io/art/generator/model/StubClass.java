package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import io.art.core.collection.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;
import java.lang.reflect.Type;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static com.sun.tools.javac.code.Flags.INTERFACE;
import static io.art.core.caster.Caster.cast;
import static io.art.core.collection.ImmutableMap.immutableMapBuilder;
import static io.art.core.collector.SetCollector.setCollector;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.generator.constants.Imports.NOT_IMPLEMENTED_EXCEPTION_MODEL;
import static io.art.generator.constants.TypeModels.NOT_IMPLEMENTED_EXCEPTION_TYPE;
import static io.art.generator.context.GeneratorContext.*;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.isInterface;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toCollection;
import static io.art.generator.service.JavacService.*;

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
                .modifiers(deleteAnnotations(declaration.getModifiers()))
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
            maker().VarDef(deleteAnnotations(existed.mods), existed.name, existed.vartype, null);

    private static final Function<JCMethodDecl, JCTree> generateMethodStub = (existed) ->{
            if (existed.name.toString().equals("<init>") && existed.params.length() == 0) return null;
            return maker().MethodDef(
                    deleteAnnotations(existed.mods),
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

    private static JCModifiers deleteAnnotations(JCModifiers from){
        return maker().Modifiers(from.flags);
    }

    private static JCTree generateDefaultConstructor(){
        return maker().MethodDef(
                maker().Modifiers(PUBLIC),
                elements().getName("<init>"),
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
