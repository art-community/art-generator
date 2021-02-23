package io.art.generator.creator.storage;

import com.sun.tools.javac.tree.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.generator.state.*;
import io.art.model.implementation.storage.*;
import io.art.tarantool.model.record.*;
import io.art.tarantool.model.transaction.dependency.*;
import io.art.tarantool.space.*;
import io.art.tarantool.transaction.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;

import java.util.*;

import static io.art.core.caster.Caster.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collector.SetCollector.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.tarantool.space.TarantoolSpaceImplementation.*;
import static java.lang.reflect.Modifier.*;

public class TarantoolSpaceCreator {
    public static NewClass createTarantoolSpaceClass(TarantoolSpaceModel model){
        NewClass space = newClass()
                .name(model.getId() + STORAGE_SPACE_SUFFIX)
                .modifiers(PUBLIC | STATIC)
                .extend(type(parameterizedType(TarantoolSpaceImplementation.class, model.getSpaceModelClass(), model.getPrimaryKeyClass())).generateFullType())
                .implement(ident(GeneratorState.moduleClass().getName() + STORAGE_INTERFACES_SUFFIX + DOT + model.getId() + STORAGE_SPACE_SUFFIX))
                .constructor(constructor(model));
        indexKeyMappers(model.getSearchers()).forEach(space::field);
        model.getSearchers().entrySet().stream()
                .flatMap((entry) -> implementSearcher(entry.getKey(), entry.getValue(), model.getSpaceModelClass()).stream())
                .forEach(space::method);
        return space;
    }

    private static NewMethod constructor(TarantoolSpaceModel model){
        ImmutableArray<JCTree.JCExpression> superArgs = cast(immutableArrayBuilder()
                .add(literal(model.getSpace()))
                .add(ident("transactionManager"))
                .add(toModelMapper(model.getSpaceModelClass()))
                .add(fromModelMapper(model.getSpaceModelClass()))
                .add(fromModelMapper(model.getPrimaryKeyClass()))
                .build());
        return newMethod()
                .modifiers(PUBLIC)
                .parameter(newParameter(type(TarantoolTransactionManager.class), "transactionManager"))
                .statement(() -> JavacService.execMethodCall(ident("super"), superArgs));
    }

    private static Set<NewField> indexKeyMappers(Map<String, Class<?>> indices){
        return indices.entrySet().stream()
                .map((entry) -> newField()
                            .modifiers(PRIVATE | STATIC | FINAL)
                            .name(entry.getKey() + MAPPER_SUFFIX)
                            .type(type(parameterizedType(ValueFromModelMapper.class, entry.getValue(), Value.class)))
                            .initializer(() -> methodCall(ident("Caster.cast"), immutableArrayOf(fromModelMapper(entry.getValue())))))
                .collect(setCollector());
    }

    private static Set<NewMethod> implementSearcher(String indexName, Class<?> indexKeyModel, Class<?> modelClass){
        Set<NewMethod> methods = set();
        methods.add(newMethod()
                .name(nameWithPrefix(indexName, STORAGE_GET_BY_PREFIX))
                .modifiers(PUBLIC)
                .returnType(type(parameterizedType(TarantoolRecord.class, modelClass)))
                .parameter(newParameter(type(indexKeyModel), STORAGE_KEY_PARAMETER_NAME))
                .statement(() -> returnExpression(methodCall(ident(SPACE_GET_METHOD_NAME),
                        immutableArrayOf(
                                literal(indexName),
                                methodCall(indexName + MAPPER_SUFFIX, MAP_NAME, immutableArrayOf(ident(STORAGE_KEY_PARAMETER_NAME)))
                        ))))
        );
        methods.add(newMethod()
                .name(nameWithPrefix(indexName, STORAGE_GET_BY_PREFIX))
                .modifiers(PUBLIC)
                .returnType(type(parameterizedType(TarantoolRecord.class, modelClass)))
                .parameter(newParameter(type(TarantoolTransactionDependency.class), STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME))
                .statement(() -> returnExpression(methodCall(ident(SPACE_GET_METHOD_NAME),
                        immutableArrayOf(
                                literal(indexName),
                                ident(STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME)
                        ))))
        );
        methods.add(newMethod()
                .name(nameWithPrefix(indexName, STORAGE_SELECT_BY_PREFIX))
                .modifiers(PUBLIC)
                .returnType(type(parameterizedType(SelectRequest.class, modelClass)))
                .parameter(newParameter(type(indexKeyModel), STORAGE_KEY_PARAMETER_NAME))
                .statement(() -> returnExpression(methodCall(ident(SPACE_SELECT_METHOD_NAME),
                        immutableArrayOf(
                                literal(indexName),
                                methodCall(indexName + MAPPER_SUFFIX, MAP_NAME, immutableArrayOf(ident(STORAGE_KEY_PARAMETER_NAME)))
                        ))))
        );
        methods.add(newMethod()
                .name(nameWithPrefix(indexName, STORAGE_SELECT_BY_PREFIX))
                .modifiers(PUBLIC)
                .returnType(type(parameterizedType(SelectRequest.class, modelClass)))
                .parameter(newParameter(type(TarantoolTransactionDependency.class), STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME))
                .statement(() -> returnExpression(methodCall(ident(SPACE_SELECT_METHOD_NAME),
                        immutableArrayOf(
                                literal(indexName),
                                ident(STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME)
                        ))))
        );
        return methods;
    }

    private static String nameWithPrefix(String name, String prefix){
        return prefix + name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
