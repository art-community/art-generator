package io.art.generator.model;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import lombok.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.Type;
import java.lang.reflect.*;
import java.util.*;

@Getter
@EqualsAndHashCode
public class TypeModel {
    private static final Map<Type, TypeModel> CACHE = map();

    private Type type;
    private ImmutableArray<TypeModel> parameters;

    private String packageName = EMPTY_STRING;
    private String ownerName;
    private String fullName;
    private String name;

    private TypeTag primitive;
    private boolean jdk;
    private boolean array;

    private TypeModel(Type type) {
        if (isClass(type)) {
            ofClass((Class<?>) type);
            return;
        }

        if (isParametrized(type)) {
            ofParametrizedType((ParameterizedType) type);
            return;
        }

        if (isGenericArray(type)) {
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
        this.jdk = adoptedTypeClass.isPrimitive() || this.packageName.startsWith(JAVA_PACKAGE_PREFIX);
        this.fullName = adoptedTypeClass.getName();
        this.parameters = stream(adoptedTypeClass.getTypeParameters())
                .filter(type -> !isVariable(type))
                .map(TypeModel::type)
                .collect(immutableArrayCollector());
        if (!adoptedTypeClass.isPrimitive()) return;
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
        this.parameters = stream(parameterizedType.getActualTypeArguments()).map(TypeModel::new).collect(immutableArrayCollector());
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
        this.parameters = emptyImmutableArray();
    }

    public JCExpression generateFullType() {
        if (nonNull(primitive)) {
            return maker().TypeIdent(primitive);
        }
        if (!parameters.isEmpty()) {
            ListBuffer<JCExpression> arguments = parameters
                    .stream()
                    .map(TypeModel::generateFullType)
                    .collect(toCollection(ListBuffer::new));
            return maker().TypeApply(isArray()
                    ? maker().TypeArray(generateArrayFullType())
                    : maker().Ident(elements().getName(name)), arguments.toList());
        }
        return isArray()
                ? maker().TypeArray(generateArrayFullType())
                : maker().Ident(elements().getName(name));
    }

    public JCExpression generateBaseType() {
        if (nonNull(primitive)) {
            return maker().TypeIdent(primitive);
        }
        return isArray()
                ? maker().TypeArray(generateArrayBaseType())
                : maker().Ident(elements().getName(name));
    }

    private JCExpression generateArrayFullType() {
        if (isGenericArray(type)) {
            return type(((GenericArrayType) type).getGenericComponentType()).generateFullType();
        }
        return type(((Class<?>) type).getComponentType()).generateFullType();
    }

    private JCExpression generateArrayBaseType() {
        if (isGenericArray(type)) {
            return type(((GenericArrayType) type).getGenericComponentType()).generateBaseType();
        }
        return type(((Class<?>) type).getComponentType()).generateBaseType();
    }

    public List<JCExpression> generateParameters() {
        ImmutableArray<TypeModel> parameters = getParameters();
        if (isEmpty(parameters)) {
            return nil();
        }
        ListBuffer<JCExpression> expressions = parameters.stream()
                .map(TypeModel::generateFullType)
                .collect(toCollection(ListBuffer::new));
        return expressions.toList();
    }

    public static TypeModel type(Type type) {
        TypeModel cached = CACHE.get(type);
        if (nonNull(cached)) return cached;
        CACHE.put(type, cached = new TypeModel(type));
        return cached;
    }
}
