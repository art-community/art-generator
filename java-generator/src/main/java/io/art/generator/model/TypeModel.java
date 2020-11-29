package io.art.generator.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.factory.*;
import io.art.generator.exception.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.Type;
import java.lang.reflect.*;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private static final Map<Type, TypeModel> CACHE = concurrentHashMap();

    private Type type;
    private List<TypeModel> parameters;

    private String packageName = EMPTY_STRING;
    private String ownerName;
    private String fullName;
    private String name;

    private TypeTag primitive;
    private boolean jdk;
    private boolean array;

    private TypeModel(Type type) {
        if (type instanceof Class) {
            ofClass((Class<?>) type);
            return;
        }

        if (type instanceof ParameterizedType) {
            ofParametrizedType((ParameterizedType) type);
            return;
        }

        if (type instanceof GenericArrayType) {
            ofGenericArrayType((GenericArrayType) type);
            return;
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }

    @SuppressWarnings("all")
    private void ofClass(Class<?> typeClass) {
        this.type = typeClass;
        this.array = typeClass.isArray();
        final Class<?> adoptedTypeClass = array ? typeClass.getComponentType() : typeClass;
        this.ownerName = let(adoptedTypeClass.getDeclaringClass(), Class::getSimpleName);
        this.name = adoptedTypeClass.getSimpleName();
        this.packageName = emptyIfNull(let(adoptedTypeClass.getPackage(), Package::getName));
        this.jdk = this.packageName.startsWith(JAVA_PACKAGE_PREFIX);
        this.fullName = adoptedTypeClass.getName();
        this.parameters = stream(adoptedTypeClass.getTypeParameters())
                .filter(type -> !(type instanceof TypeVariable))
                .map(TypeModel::type)
                .collect(toCollection(ArrayFactory::dynamicArray));
        stream(TypeTag.values())
                .filter(tag -> tag.name().toLowerCase().equals(adoptedTypeClass.getSimpleName().toLowerCase()))
                .findFirst()
                .ifPresent(typeTag -> this.primitive = typeTag);
    }

    private void ofParametrizedType(ParameterizedType parameterizedType) {
        this.type = parameterizedType;
        Class<?> ownerClass = extractClass(parameterizedType);
        this.ownerName = let(ownerClass.getDeclaringClass(), Class::getSimpleName);
        this.name = ownerClass.getSimpleName();
        this.packageName = emptyIfNull(let(ownerClass.getPackage(), Package::getName));
        this.jdk = this.packageName.startsWith(JAVA_PACKAGE_PREFIX);
        this.fullName = ownerClass.getName();
        this.parameters = stream(parameterizedType.getActualTypeArguments()).map(TypeModel::new).collect(toCollection(ArrayFactory::dynamicArray));
    }

    private void ofGenericArrayType(GenericArrayType genericArrayType) {
        this.type = genericArrayType;
        this.array = true;
        Class<?> ownerClass = extractClass(genericArrayType);
        this.ownerName = let(ownerClass.getDeclaringClass(), Class::getSimpleName);
        this.name = ownerClass.getSimpleName();
        this.packageName = emptyIfNull(let(ownerClass.getPackage(), Package::getName));
        this.jdk = this.packageName.startsWith(JAVA_PACKAGE_PREFIX);
        this.fullName = ownerClass.getName();
        this.parameters = emptyList();
    }

    public JCExpression generate() {
        if (nonNull(primitive)) {
            return maker().TypeIdent(primitive);
        }
        if (!parameters.isEmpty()) {
            com.sun.tools.javac.util.List<JCExpression> arguments = com.sun.tools.javac.util.List.from(parameters
                    .stream()
                    .map(TypeModel::generate)
                    .collect(toCollection(ArrayFactory::dynamicArray)));
            return maker().TypeApply(isArray()
                    ? maker().TypeArray(maker().Ident(elements().getName(name)))
                    : maker().Ident(elements().getName(name)), arguments);
        }
        return isArray()
                ? maker().TypeArray(maker().Ident(elements().getName(name)))
                : maker().Ident(elements().getName(name));
    }

    public static TypeModel type(Type type) {
        TypeModel cached = CACHE.get(type);
        if (nonNull(cached)) {
            return cached;
        }
        CACHE.put(type, cached = new TypeModel(type));
        return cached;
    }
}
