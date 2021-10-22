package meta;

import static io.art.meta.model.MetaType.metaArray;
import static io.art.meta.model.MetaType.metaEnum;
import static io.art.meta.model.MetaType.metaType;

import io.art.meta.model.InstanceMetaMethod;
import io.art.meta.model.MetaClass;
import io.art.meta.model.MetaConstructor;
import io.art.meta.model.MetaField;
import io.art.meta.model.MetaLibrary;
import io.art.meta.model.MetaMethod;
import io.art.meta.model.MetaPackage;
import io.art.meta.model.MetaParameter;
import io.art.meta.model.MetaProxy;
import io.art.meta.model.StaticMetaMethod;
import java.util.function.Function;

@SuppressWarnings({"all","unchecked","unused"})
public class MetaExampleMixedSourcesJava extends MetaLibrary {
  private final MetaModelPackage modelPackage = register(new MetaModelPackage());

  public MetaExampleMixedSourcesJava(MetaLibrary... dependencies) {
    super(dependencies);
  }

  public MetaModelPackage modelPackage() {
    return modelPackage;
  }

  public static final class MetaModelPackage extends MetaPackage {
    private final MetaMixedJavaClassClass mixedJavaClassClass = register(new MetaMixedJavaClassClass());

    private final MetaMixedJavaEmptyClassClass mixedJavaEmptyClassClass = register(new MetaMixedJavaEmptyClassClass());

    private final MetaMixedJavaInterfaceClass mixedJavaInterfaceClass = register(new MetaMixedJavaInterfaceClass());

    private final MetaMixedJavaModelClass mixedJavaModelClass = register(new MetaMixedJavaModelClass());

    private final MetaOtherPackage otherPackage = register(new MetaOtherPackage());

    private final MetaModelPackage1 modelPackage = register(new MetaModelPackage1());

    private MetaModelPackage() {
      super("model");
    }

    public MetaMixedJavaClassClass mixedJavaClassClass() {
      return mixedJavaClassClass;
    }

    public MetaMixedJavaEmptyClassClass mixedJavaEmptyClassClass() {
      return mixedJavaEmptyClassClass;
    }

    public MetaMixedJavaInterfaceClass mixedJavaInterfaceClass() {
      return mixedJavaInterfaceClass;
    }

    public MetaMixedJavaModelClass mixedJavaModelClass() {
      return mixedJavaModelClass;
    }

    public MetaOtherPackage otherPackage() {
      return otherPackage;
    }

    public MetaModelPackage1 modelPackage() {
      return modelPackage;
    }

    public static final class MetaMixedJavaClassClass extends MetaClass<model.MixedJavaClass> {
      private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),false));

      private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),false));

      private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),false));

      private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),false));

      private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

      private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

      private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

      private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

      private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

      private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

      private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

      private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

      private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

      private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

      private MetaMixedJavaClassClass() {
        super(metaType(model.MixedJavaClass.class));
      }

      public MetaField<java.lang.Boolean> privateFieldField() {
        return privateFieldField;
      }

      public MetaField<java.lang.Boolean> protectedFieldField() {
        return protectedFieldField;
      }

      public MetaField<java.lang.Boolean> publicFieldField() {
        return publicFieldField;
      }

      public MetaField<java.lang.Boolean> internalFieldField() {
        return internalFieldField;
      }

      public MetaPublicMethodMethod publicMethodMethod() {
        return publicMethodMethod;
      }

      public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
        return publicAbstractMethodMethod;
      }

      public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
        return isPrivateFieldMethod;
      }

      public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
        return isProtectedFieldMethod;
      }

      public MetaIsPublicFieldMethod isPublicFieldMethod() {
        return isPublicFieldMethod;
      }

      public MetaIsInternalFieldMethod isInternalFieldMethod() {
        return isInternalFieldMethod;
      }

      public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
        return setPrivateFieldMethod;
      }

      public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
        return setProtectedFieldMethod;
      }

      public MetaSetPublicFieldMethod setPublicFieldMethod() {
        return setPublicFieldMethod;
      }

      public MetaSetInternalFieldMethod setInternalFieldMethod() {
        return setInternalFieldMethod;
      }

      public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaPublicMethodMethod() {
          super("publicMethod",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.publicMethod();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.publicMethod();
        }
      }

      public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaPublicAbstractMethodMethod() {
          super("publicAbstractMethod",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.publicAbstractMethod();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.publicAbstractMethod();
        }
      }

      public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaIsPrivateFieldMethod() {
          super("isPrivateField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isPrivateField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.isPrivateField();
        }
      }

      public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaIsProtectedFieldMethod() {
          super("isProtectedField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isProtectedField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.isProtectedField();
        }
      }

      public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaIsPublicFieldMethod() {
          super("isPublicField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isPublicField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.isPublicField();
        }
      }

      public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, java.lang.Boolean> {
        private MetaIsInternalFieldMethod() {
          super("isInternalField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isInternalField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance) throws Throwable {
          return instance.isInternalField();
        }
      }

      public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, Void> {
        private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

        private MetaSetPrivateFieldMethod() {
          super("setPrivateField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setPrivateField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object argument)
            throws Throwable {
          instance.setPrivateField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> privateFieldParameter() {
          return privateFieldParameter;
        }
      }

      public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, Void> {
        private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

        private MetaSetProtectedFieldMethod() {
          super("setProtectedField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setProtectedField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object argument)
            throws Throwable {
          instance.setProtectedField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
          return protectedFieldParameter;
        }
      }

      public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, Void> {
        private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

        private MetaSetPublicFieldMethod() {
          super("setPublicField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setPublicField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object argument)
            throws Throwable {
          instance.setPublicField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> publicFieldParameter() {
          return publicFieldParameter;
        }
      }

      public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.MixedJavaClass, Void> {
        private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

        private MetaSetInternalFieldMethod() {
          super("setInternalField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setInternalField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaClass instance, java.lang.Object argument)
            throws Throwable {
          instance.setInternalField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> internalFieldParameter() {
          return internalFieldParameter;
        }
      }
    }

    public static final class MetaMixedJavaEmptyClassClass extends MetaClass<model.MixedJavaEmptyClass> {
      private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

      private MetaMixedJavaEmptyClassClass() {
        super(metaType(model.MixedJavaEmptyClass.class));
      }

      public MetaConstructorConstructor constructor() {
        return constructor;
      }

      public static final class MetaConstructorConstructor extends MetaConstructor<model.MixedJavaEmptyClass> {
        private MetaConstructorConstructor() {
          super(metaType(model.MixedJavaEmptyClass.class));
        }

        @Override
        public model.MixedJavaEmptyClass invoke(java.lang.Object[] arguments) throws Throwable {
          return new model.MixedJavaEmptyClass();
        }

        @Override
        public model.MixedJavaEmptyClass invoke() throws Throwable {
          return new model.MixedJavaEmptyClass();
        }
      }
    }

    public static final class MetaMixedJavaInterfaceClass extends MetaClass<model.MixedJavaInterface> {
      private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

      private final MetaDefaultMethodMethod defaultMethodMethod = register(new MetaDefaultMethodMethod());

      private MetaMixedJavaInterfaceClass() {
        super(metaType(model.MixedJavaInterface.class));
      }

      public MetaImplementableMethodMethod implementableMethodMethod() {
        return implementableMethodMethod;
      }

      public MetaDefaultMethodMethod defaultMethodMethod() {
        return defaultMethodMethod;
      }

      @Override
      public MetaProxy proxy(
          java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
        return new MetaMixedJavaInterfaceProxy(invocations);
      }

      public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.MixedJavaInterface, java.lang.String> {
        private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

        private MetaImplementableMethodMethod() {
          super("implementableMethod",metaType(java.lang.String.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaInterface instance,
            java.lang.Object[] arguments) throws Throwable {
          return instance.implementableMethod((java.lang.String)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaInterface instance, java.lang.Object argument)
            throws Throwable {
          return instance.implementableMethod((java.lang.String)(argument));
        }

        public MetaParameter<java.lang.String> argumentParameter() {
          return argumentParameter;
        }
      }

      public static final class MetaDefaultMethodMethod extends InstanceMetaMethod<model.MixedJavaInterface, java.lang.String> {
        private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

        private MetaDefaultMethodMethod() {
          super("defaultMethod",metaType(java.lang.String.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaInterface instance,
            java.lang.Object[] arguments) throws Throwable {
          return instance.defaultMethod((java.lang.String)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaInterface instance, java.lang.Object argument)
            throws Throwable {
          return instance.defaultMethod((java.lang.String)(argument));
        }

        public MetaParameter<java.lang.String> argumentParameter() {
          return argumentParameter;
        }
      }

      public class MetaMixedJavaInterfaceProxy extends MetaProxy implements model.MixedJavaInterface {
        private final Function<java.lang.Object, java.lang.Object> implementableMethodInvocation;

        public MetaMixedJavaInterfaceProxy(
            java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
          super(invocations);
          implementableMethodInvocation = invocations.get(implementableMethodMethod);
        }

        @Override
        public java.lang.String implementableMethod(java.lang.String argument) {
          return (java.lang.String)(implementableMethodInvocation.apply(argument));
        }
      }
    }

    public static final class MetaMixedJavaModelClass extends MetaClass<model.MixedJavaModel> {
      private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

      private final MetaConstructor_1Constructor constructor_1 = register(new MetaConstructor_1Constructor());

      private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),true));

      private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),true));

      private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),true));

      private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),true));

      private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

      private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

      private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

      private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

      private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

      private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

      private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

      private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

      private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

      private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

      private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

      private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

      private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

      private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

      private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

      private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

      private final MetaField<model.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.MixedJavaModel.InnerModel.class),false));

      private final MetaField<java.util.List<model.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class)),false));

      private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

      private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

      private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

      private final MetaField<model.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),false));

      private final MetaField<java.util.List<model.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

      private final MetaField<model.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

      private final MetaField<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

      private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))),false));

      private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))),false));

      private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))),false));

      private final MetaField<model.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

      private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

      private final MetaStaticMethod_1Method staticMethod_1Method = register(new MetaStaticMethod_1Method());

      private final MetaStaticMethod_2Method staticMethod_2Method = register(new MetaStaticMethod_2Method());

      private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

      private final MetaInstanceMethod_1Method instanceMethod_1Method = register(new MetaInstanceMethod_1Method());

      private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

      private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

      private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

      private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

      private final MetaGetF4Method getF4Method = register(new MetaGetF4Method());

      private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

      private final MetaGetF6Method getF6Method = register(new MetaGetF6Method());

      private final MetaGetF7Method getF7Method = register(new MetaGetF7Method());

      private final MetaGetF8Method getF8Method = register(new MetaGetF8Method());

      private final MetaGetF9Method getF9Method = register(new MetaGetF9Method());

      private final MetaGetF10Method getF10Method = register(new MetaGetF10Method());

      private final MetaGetF11Method getF11Method = register(new MetaGetF11Method());

      private final MetaGetF12Method getF12Method = register(new MetaGetF12Method());

      private final MetaGetF13Method getF13Method = register(new MetaGetF13Method());

      private final MetaGetF14Method getF14Method = register(new MetaGetF14Method());

      private final MetaGetF15Method getF15Method = register(new MetaGetF15Method());

      private final MetaGetF16Method getF16Method = register(new MetaGetF16Method());

      private final MetaGetF17Method getF17Method = register(new MetaGetF17Method());

      private final MetaGetF18Method getF18Method = register(new MetaGetF18Method());

      private final MetaGetF19Method getF19Method = register(new MetaGetF19Method());

      private final MetaGetF20Method getF20Method = register(new MetaGetF20Method());

      private final MetaGetF21Method getF21Method = register(new MetaGetF21Method());

      private final MetaGetF22Method getF22Method = register(new MetaGetF22Method());

      private final MetaGetF23Method getF23Method = register(new MetaGetF23Method());

      private final MetaGetF24Method getF24Method = register(new MetaGetF24Method());

      private final MetaGetF25Method getF25Method = register(new MetaGetF25Method());

      private final MetaGetF26Method getF26Method = register(new MetaGetF26Method());

      private final MetaGetF27Method getF27Method = register(new MetaGetF27Method());

      private final MetaGetF28Method getF28Method = register(new MetaGetF28Method());

      private final MetaGetF29Method getF29Method = register(new MetaGetF29Method());

      private final MetaGetF30Method getF30Method = register(new MetaGetF30Method());

      private final MetaGetF31Method getF31Method = register(new MetaGetF31Method());

      private final MetaGetF32Method getF32Method = register(new MetaGetF32Method());

      private final MetaGetF33Method getF33Method = register(new MetaGetF33Method());

      private final MetaGetF34Method getF34Method = register(new MetaGetF34Method());

      private final MetaGetF35Method getF35Method = register(new MetaGetF35Method());

      private final MetaGetF36Method getF36Method = register(new MetaGetF36Method());

      private final MetaGetF37Method getF37Method = register(new MetaGetF37Method());

      private final MetaGetF38Method getF38Method = register(new MetaGetF38Method());

      private final MetaGetF39Method getF39Method = register(new MetaGetF39Method());

      private final MetaGetF40Method getF40Method = register(new MetaGetF40Method());

      private final MetaGetF41Method getF41Method = register(new MetaGetF41Method());

      private final MetaGetGenericFieldMethod getGenericFieldMethod = register(new MetaGetGenericFieldMethod());

      private final MetaSetF1Method setF1Method = register(new MetaSetF1Method());

      private final MetaSetF2Method setF2Method = register(new MetaSetF2Method());

      private final MetaSetF3Method setF3Method = register(new MetaSetF3Method());

      private final MetaSetF4Method setF4Method = register(new MetaSetF4Method());

      private final MetaSetF5Method setF5Method = register(new MetaSetF5Method());

      private final MetaSetF6Method setF6Method = register(new MetaSetF6Method());

      private final MetaSetF7Method setF7Method = register(new MetaSetF7Method());

      private final MetaSetF8Method setF8Method = register(new MetaSetF8Method());

      private final MetaSetF9Method setF9Method = register(new MetaSetF9Method());

      private final MetaSetF10Method setF10Method = register(new MetaSetF10Method());

      private final MetaSetF11Method setF11Method = register(new MetaSetF11Method());

      private final MetaSetF12Method setF12Method = register(new MetaSetF12Method());

      private final MetaSetF13Method setF13Method = register(new MetaSetF13Method());

      private final MetaSetF14Method setF14Method = register(new MetaSetF14Method());

      private final MetaSetF15Method setF15Method = register(new MetaSetF15Method());

      private final MetaSetF16Method setF16Method = register(new MetaSetF16Method());

      private final MetaSetF17Method setF17Method = register(new MetaSetF17Method());

      private final MetaSetF18Method setF18Method = register(new MetaSetF18Method());

      private final MetaSetF19Method setF19Method = register(new MetaSetF19Method());

      private final MetaSetF20Method setF20Method = register(new MetaSetF20Method());

      private final MetaSetF21Method setF21Method = register(new MetaSetF21Method());

      private final MetaSetF22Method setF22Method = register(new MetaSetF22Method());

      private final MetaSetF23Method setF23Method = register(new MetaSetF23Method());

      private final MetaSetF24Method setF24Method = register(new MetaSetF24Method());

      private final MetaSetF25Method setF25Method = register(new MetaSetF25Method());

      private final MetaSetF26Method setF26Method = register(new MetaSetF26Method());

      private final MetaSetF27Method setF27Method = register(new MetaSetF27Method());

      private final MetaSetF28Method setF28Method = register(new MetaSetF28Method());

      private final MetaSetF29Method setF29Method = register(new MetaSetF29Method());

      private final MetaSetF30Method setF30Method = register(new MetaSetF30Method());

      private final MetaSetF31Method setF31Method = register(new MetaSetF31Method());

      private final MetaSetF32Method setF32Method = register(new MetaSetF32Method());

      private final MetaSetF33Method setF33Method = register(new MetaSetF33Method());

      private final MetaSetF34Method setF34Method = register(new MetaSetF34Method());

      private final MetaSetF35Method setF35Method = register(new MetaSetF35Method());

      private final MetaSetF36Method setF36Method = register(new MetaSetF36Method());

      private final MetaSetF37Method setF37Method = register(new MetaSetF37Method());

      private final MetaSetF38Method setF38Method = register(new MetaSetF38Method());

      private final MetaSetF39Method setF39Method = register(new MetaSetF39Method());

      private final MetaSetF40Method setF40Method = register(new MetaSetF40Method());

      private final MetaSetF41Method setF41Method = register(new MetaSetF41Method());

      private final MetaSetGenericFieldMethod setGenericFieldMethod = register(new MetaSetGenericFieldMethod());

      private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

      private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

      private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

      private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

      private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

      private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

      private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

      private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

      private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

      private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

      private final MetaMixedJavaModelBuilderClass mixedJavaModelBuilderClass = register(new MetaMixedJavaModelBuilderClass());

      private final MetaInnerModelClass innerModelClass = register(new MetaInnerModelClass());

      private MetaMixedJavaModelClass() {
        super(metaType(model.MixedJavaModel.class));
      }

      public MetaConstructorConstructor constructor() {
        return constructor;
      }

      public MetaConstructor_1Constructor constructor_1() {
        return constructor_1;
      }

      public MetaField<java.lang.Boolean> privateFieldField() {
        return privateFieldField;
      }

      public MetaField<java.lang.Boolean> protectedFieldField() {
        return protectedFieldField;
      }

      public MetaField<java.lang.Boolean> publicFieldField() {
        return publicFieldField;
      }

      public MetaField<java.lang.Boolean> internalFieldField() {
        return internalFieldField;
      }

      public MetaField<java.lang.Boolean> f1Field() {
        return f1Field;
      }

      public MetaField<java.lang.String> f2Field() {
        return f2Field;
      }

      public MetaField<java.util.List<java.lang.String>> f3Field() {
        return f3Field;
      }

      public MetaField<java.util.List<?>> f4Field() {
        return f4Field;
      }

      public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
        return f5Field;
      }

      public MetaField<java.util.List<? super java.lang.String>> f6Field() {
        return f6Field;
      }

      public MetaField<boolean[]> f7Field() {
        return f7Field;
      }

      public MetaField<java.util.List<java.lang.String>[]> f8Field() {
        return f8Field;
      }

      public MetaField<java.util.List<?>[]> f9Field() {
        return f9Field;
      }

      public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
        return f10Field;
      }

      public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
        return f11Field;
      }

      public MetaField<boolean[][]> f12Field() {
        return f12Field;
      }

      public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
        return f13Field;
      }

      public MetaField<java.util.List<?>[][]> f14Field() {
        return f14Field;
      }

      public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
        return f15Field;
      }

      public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
        return f16Field;
      }

      public MetaField<java.util.List<boolean[]>> f17Field() {
        return f17Field;
      }

      public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
        return f18Field;
      }

      public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
        return f19Field;
      }

      public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
        return f20Field;
      }

      public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
        return f21Field;
      }

      public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
        return f22Field;
      }

      public MetaField<java.util.List<java.util.List<?>>> f23Field() {
        return f23Field;
      }

      public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
        return f24Field;
      }

      public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
        return f25Field;
      }

      public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
        return f26Field;
      }

      public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
        return f27Field;
      }

      public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field() {
        return f28Field;
      }

      public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
        return f29Field;
      }

      public MetaField<model.MixedJavaModel.InnerModel> f30Field() {
        return f30Field;
      }

      public MetaField<java.util.List<model.MixedJavaModel.InnerModel>> f31Field() {
        return f31Field;
      }

      public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
        return f32Field;
      }

      public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
        return f33Field;
      }

      public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
        return f34Field;
      }

      public MetaField<model.MixedJavaModel.Enum> f35Field() {
        return f35Field;
      }

      public MetaField<java.util.List<model.MixedJavaModel.Enum>> f36Field() {
        return f36Field;
      }

      public MetaField<model.MixedJavaModel.Enum[]> f37Field() {
        return f37Field;
      }

      public MetaField<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Field(
          ) {
        return f38Field;
      }

      public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Field(
          ) {
        return f39Field;
      }

      public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Field(
          ) {
        return f40Field;
      }

      public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Field(
          ) {
        return f41Field;
      }

      public MetaField<model.MixedJavaModel.GenericClass<?>> genericFieldField() {
        return genericFieldField;
      }

      public MetaStaticMethodMethod staticMethodMethod() {
        return staticMethodMethod;
      }

      public MetaStaticMethod_1Method staticMethod_1Method() {
        return staticMethod_1Method;
      }

      public MetaStaticMethod_2Method staticMethod_2Method() {
        return staticMethod_2Method;
      }

      public MetaInstanceMethodMethod instanceMethodMethod() {
        return instanceMethodMethod;
      }

      public MetaInstanceMethod_1Method instanceMethod_1Method() {
        return instanceMethod_1Method;
      }

      public MetaImplementableMethodMethod implementableMethodMethod() {
        return implementableMethodMethod;
      }

      public MetaIsF1Method isF1Method() {
        return isF1Method;
      }

      public MetaGetF2Method getF2Method() {
        return getF2Method;
      }

      public MetaGetF3Method getF3Method() {
        return getF3Method;
      }

      public MetaGetF4Method getF4Method() {
        return getF4Method;
      }

      public MetaGetF5Method getF5Method() {
        return getF5Method;
      }

      public MetaGetF6Method getF6Method() {
        return getF6Method;
      }

      public MetaGetF7Method getF7Method() {
        return getF7Method;
      }

      public MetaGetF8Method getF8Method() {
        return getF8Method;
      }

      public MetaGetF9Method getF9Method() {
        return getF9Method;
      }

      public MetaGetF10Method getF10Method() {
        return getF10Method;
      }

      public MetaGetF11Method getF11Method() {
        return getF11Method;
      }

      public MetaGetF12Method getF12Method() {
        return getF12Method;
      }

      public MetaGetF13Method getF13Method() {
        return getF13Method;
      }

      public MetaGetF14Method getF14Method() {
        return getF14Method;
      }

      public MetaGetF15Method getF15Method() {
        return getF15Method;
      }

      public MetaGetF16Method getF16Method() {
        return getF16Method;
      }

      public MetaGetF17Method getF17Method() {
        return getF17Method;
      }

      public MetaGetF18Method getF18Method() {
        return getF18Method;
      }

      public MetaGetF19Method getF19Method() {
        return getF19Method;
      }

      public MetaGetF20Method getF20Method() {
        return getF20Method;
      }

      public MetaGetF21Method getF21Method() {
        return getF21Method;
      }

      public MetaGetF22Method getF22Method() {
        return getF22Method;
      }

      public MetaGetF23Method getF23Method() {
        return getF23Method;
      }

      public MetaGetF24Method getF24Method() {
        return getF24Method;
      }

      public MetaGetF25Method getF25Method() {
        return getF25Method;
      }

      public MetaGetF26Method getF26Method() {
        return getF26Method;
      }

      public MetaGetF27Method getF27Method() {
        return getF27Method;
      }

      public MetaGetF28Method getF28Method() {
        return getF28Method;
      }

      public MetaGetF29Method getF29Method() {
        return getF29Method;
      }

      public MetaGetF30Method getF30Method() {
        return getF30Method;
      }

      public MetaGetF31Method getF31Method() {
        return getF31Method;
      }

      public MetaGetF32Method getF32Method() {
        return getF32Method;
      }

      public MetaGetF33Method getF33Method() {
        return getF33Method;
      }

      public MetaGetF34Method getF34Method() {
        return getF34Method;
      }

      public MetaGetF35Method getF35Method() {
        return getF35Method;
      }

      public MetaGetF36Method getF36Method() {
        return getF36Method;
      }

      public MetaGetF37Method getF37Method() {
        return getF37Method;
      }

      public MetaGetF38Method getF38Method() {
        return getF38Method;
      }

      public MetaGetF39Method getF39Method() {
        return getF39Method;
      }

      public MetaGetF40Method getF40Method() {
        return getF40Method;
      }

      public MetaGetF41Method getF41Method() {
        return getF41Method;
      }

      public MetaGetGenericFieldMethod getGenericFieldMethod() {
        return getGenericFieldMethod;
      }

      public MetaSetF1Method setF1Method() {
        return setF1Method;
      }

      public MetaSetF2Method setF2Method() {
        return setF2Method;
      }

      public MetaSetF3Method setF3Method() {
        return setF3Method;
      }

      public MetaSetF4Method setF4Method() {
        return setF4Method;
      }

      public MetaSetF5Method setF5Method() {
        return setF5Method;
      }

      public MetaSetF6Method setF6Method() {
        return setF6Method;
      }

      public MetaSetF7Method setF7Method() {
        return setF7Method;
      }

      public MetaSetF8Method setF8Method() {
        return setF8Method;
      }

      public MetaSetF9Method setF9Method() {
        return setF9Method;
      }

      public MetaSetF10Method setF10Method() {
        return setF10Method;
      }

      public MetaSetF11Method setF11Method() {
        return setF11Method;
      }

      public MetaSetF12Method setF12Method() {
        return setF12Method;
      }

      public MetaSetF13Method setF13Method() {
        return setF13Method;
      }

      public MetaSetF14Method setF14Method() {
        return setF14Method;
      }

      public MetaSetF15Method setF15Method() {
        return setF15Method;
      }

      public MetaSetF16Method setF16Method() {
        return setF16Method;
      }

      public MetaSetF17Method setF17Method() {
        return setF17Method;
      }

      public MetaSetF18Method setF18Method() {
        return setF18Method;
      }

      public MetaSetF19Method setF19Method() {
        return setF19Method;
      }

      public MetaSetF20Method setF20Method() {
        return setF20Method;
      }

      public MetaSetF21Method setF21Method() {
        return setF21Method;
      }

      public MetaSetF22Method setF22Method() {
        return setF22Method;
      }

      public MetaSetF23Method setF23Method() {
        return setF23Method;
      }

      public MetaSetF24Method setF24Method() {
        return setF24Method;
      }

      public MetaSetF25Method setF25Method() {
        return setF25Method;
      }

      public MetaSetF26Method setF26Method() {
        return setF26Method;
      }

      public MetaSetF27Method setF27Method() {
        return setF27Method;
      }

      public MetaSetF28Method setF28Method() {
        return setF28Method;
      }

      public MetaSetF29Method setF29Method() {
        return setF29Method;
      }

      public MetaSetF30Method setF30Method() {
        return setF30Method;
      }

      public MetaSetF31Method setF31Method() {
        return setF31Method;
      }

      public MetaSetF32Method setF32Method() {
        return setF32Method;
      }

      public MetaSetF33Method setF33Method() {
        return setF33Method;
      }

      public MetaSetF34Method setF34Method() {
        return setF34Method;
      }

      public MetaSetF35Method setF35Method() {
        return setF35Method;
      }

      public MetaSetF36Method setF36Method() {
        return setF36Method;
      }

      public MetaSetF37Method setF37Method() {
        return setF37Method;
      }

      public MetaSetF38Method setF38Method() {
        return setF38Method;
      }

      public MetaSetF39Method setF39Method() {
        return setF39Method;
      }

      public MetaSetF40Method setF40Method() {
        return setF40Method;
      }

      public MetaSetF41Method setF41Method() {
        return setF41Method;
      }

      public MetaSetGenericFieldMethod setGenericFieldMethod() {
        return setGenericFieldMethod;
      }

      public MetaPublicMethodMethod publicMethodMethod() {
        return publicMethodMethod;
      }

      public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
        return publicAbstractMethodMethod;
      }

      public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
        return isPrivateFieldMethod;
      }

      public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
        return isProtectedFieldMethod;
      }

      public MetaIsPublicFieldMethod isPublicFieldMethod() {
        return isPublicFieldMethod;
      }

      public MetaIsInternalFieldMethod isInternalFieldMethod() {
        return isInternalFieldMethod;
      }

      public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
        return setPrivateFieldMethod;
      }

      public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
        return setProtectedFieldMethod;
      }

      public MetaSetPublicFieldMethod setPublicFieldMethod() {
        return setPublicFieldMethod;
      }

      public MetaSetInternalFieldMethod setInternalFieldMethod() {
        return setInternalFieldMethod;
      }

      public MetaMixedJavaModelBuilderClass mixedJavaModelBuilderClass() {
        return mixedJavaModelBuilderClass;
      }

      public MetaInnerModelClass innerModelClass() {
        return innerModelClass;
      }

      public static final class MetaConstructorConstructor extends MetaConstructor<model.MixedJavaModel> {
        private MetaConstructorConstructor() {
          super(metaType(model.MixedJavaModel.class));
        }

        @Override
        public model.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
          return new model.MixedJavaModel();
        }

        @Override
        public model.MixedJavaModel invoke() throws Throwable {
          return new model.MixedJavaModel();
        }
      }

      public static final class MetaConstructor_1Constructor extends MetaConstructor<model.MixedJavaModel> {
        private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

        private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

        private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(3, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

        private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(4, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(5, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(6, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

        private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(7, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(8, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

        private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(9, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(10, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(11, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

        private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(12, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(13, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

        private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(14, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(15, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(16, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

        private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(17, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(18, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(19, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(20, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

        private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(21, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(22, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

        private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(23, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(24, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(25, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

        private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(26, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(27, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(28, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private final MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(29, "f30",metaType(model.MixedJavaModel.InnerModel.class)));

        private final MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(30, "f31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class))));

        private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(31, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

        private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(32, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

        private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(33, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

        private final MetaParameter<model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(34, "f35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));

        private final MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(35, "f36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private final MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(36, "f37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(37, "f38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(38, "f39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))));

        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(39, "f40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(40, "f41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

        private final MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(41, "genericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

        private MetaConstructor_1Constructor() {
          super(metaType(model.MixedJavaModel.class));
        }

        @Override
        public model.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
          return new model.MixedJavaModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(java.util.List<java.lang.String>)(arguments[2]),(java.util.List<?>)(arguments[3]),(java.util.List<? extends java.lang.String>)(arguments[4]),(java.util.List<? super java.lang.String>)(arguments[5]),(boolean[])(arguments[6]),(java.util.List<java.lang.String>[])(arguments[7]),(java.util.List<?>[])(arguments[8]),(java.util.List<? extends java.lang.String>[])(arguments[9]),(java.util.List<? super java.lang.String>[])(arguments[10]),(boolean[][])(arguments[11]),(java.util.List<java.lang.String>[][])(arguments[12]),(java.util.List<?>[][])(arguments[13]),(java.util.List<? extends java.lang.String>[][])(arguments[14]),(java.util.List<? super java.lang.String>[][])(arguments[15]),(java.util.List<boolean[]>)(arguments[16]),(java.util.List<java.lang.String[]>[])(arguments[17]),(java.util.List<? extends java.lang.String[]>[])(arguments[18]),(java.util.List<? super java.lang.String[]>[])(arguments[19]),(java.util.List<java.util.List<java.lang.Boolean>>)(arguments[20]),(java.util.List<java.util.List<java.lang.String>>)(arguments[21]),(java.util.List<java.util.List<?>>)(arguments[22]),(java.util.List<java.util.List<? extends java.lang.String>>)(arguments[23]),(java.util.List<java.util.List<? super java.lang.String>>)(arguments[24]),(java.util.List<java.util.List<boolean[]>>)(arguments[25]),(java.util.List<java.util.List<java.lang.String[]>>)(arguments[26]),(java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[27]),(java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[28]),(model.MixedJavaModel.InnerModel)(arguments[29]),(java.util.List<model.MixedJavaModel.InnerModel>)(arguments[30]),(java.util.Map<java.lang.String, java.lang.String>)(arguments[31]),(java.util.Map<?, java.lang.String>)(arguments[32]),(java.util.Map<java.lang.String, ?>)(arguments[33]),(model.MixedJavaModel.Enum)(arguments[34]),(java.util.List<model.MixedJavaModel.Enum>)(arguments[35]),(model.MixedJavaModel.Enum[])(arguments[36]),(java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>)(arguments[37]),(java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>)(arguments[38]),(java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>)(arguments[39]),(java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>)(arguments[40]),(model.MixedJavaModel.GenericClass<?>)(arguments[41]));
        }

        public MetaParameter<java.lang.Boolean> f1Parameter() {
          return f1Parameter;
        }

        public MetaParameter<java.lang.String> f2Parameter() {
          return f2Parameter;
        }

        public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
          return f3Parameter;
        }

        public MetaParameter<java.util.List<?>> f4Parameter() {
          return f4Parameter;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
          return f5Parameter;
        }

        public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
          return f6Parameter;
        }

        public MetaParameter<boolean[]> f7Parameter() {
          return f7Parameter;
        }

        public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
          return f8Parameter;
        }

        public MetaParameter<java.util.List<?>[]> f9Parameter() {
          return f9Parameter;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
          return f10Parameter;
        }

        public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
          return f11Parameter;
        }

        public MetaParameter<boolean[][]> f12Parameter() {
          return f12Parameter;
        }

        public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
          return f13Parameter;
        }

        public MetaParameter<java.util.List<?>[][]> f14Parameter() {
          return f14Parameter;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
          return f15Parameter;
        }

        public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
          return f16Parameter;
        }

        public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
          return f17Parameter;
        }

        public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
          return f18Parameter;
        }

        public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
          return f19Parameter;
        }

        public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
          return f20Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
          return f21Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
          return f22Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
          return f23Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
            ) {
          return f24Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
            ) {
          return f25Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
          return f26Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
          return f27Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
            ) {
          return f28Parameter;
        }

        public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
            ) {
          return f29Parameter;
        }

        public MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter() {
          return f30Parameter;
        }

        public MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter() {
          return f31Parameter;
        }

        public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
          return f32Parameter;
        }

        public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
          return f33Parameter;
        }

        public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
          return f34Parameter;
        }

        public MetaParameter<model.MixedJavaModel.Enum> f35Parameter() {
          return f35Parameter;
        }

        public MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter() {
          return f36Parameter;
        }

        public MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter() {
          return f37Parameter;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter(
            ) {
          return f38Parameter;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter(
            ) {
          return f39Parameter;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter(
            ) {
          return f40Parameter;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter(
            ) {
          return f41Parameter;
        }

        public MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
          return genericFieldParameter;
        }
      }

      public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
        private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

        private MetaStaticMethodMethod() {
          super("staticMethod",metaType(int.class));
        }

        @Override
        public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
          return model.MixedJavaModel.staticMethod((int)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
          return model.MixedJavaModel.staticMethod((int)(argument));
        }

        public MetaParameter<Integer> argumentParameter() {
          return argumentParameter;
        }
      }

      public static final class MetaStaticMethod_1Method extends StaticMetaMethod<java.lang.String> {
        private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

        private MetaStaticMethod_1Method() {
          super("staticMethod",metaType(java.lang.String.class));
        }

        @Override
        public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
          return model.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
          return model.MixedJavaModel.staticMethod((java.lang.String)(argument));
        }

        public MetaParameter<java.lang.String> argumentParameter() {
          return argumentParameter;
        }
      }

      public static final class MetaStaticMethod_2Method extends StaticMetaMethod<Integer> {
        private final MetaParameter<java.lang.String> argument1Parameter = register(new MetaParameter<>(0, "argument1",metaType(java.lang.String.class)));

        private final MetaParameter<int[]> argument2Parameter = register(new MetaParameter<>(1, "argument2",metaArray(int[].class, int[]::new, metaType(int.class))));

        private MetaStaticMethod_2Method() {
          super("staticMethod",metaType(int.class));
        }

        @Override
        public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
          return model.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]),(int[])(arguments[1]));
        }

        public MetaParameter<java.lang.String> argument1Parameter() {
          return argument1Parameter;
        }

        public MetaParameter<int[]> argument2Parameter() {
          return argument2Parameter;
        }
      }

      public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.MixedJavaModel, Integer> {
        private final MetaParameter<Integer> p1Parameter = register(new MetaParameter<>(0, "p1",metaType(int.class)));

        private MetaInstanceMethodMethod() {
          super("instanceMethod",metaType(int.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.instanceMethod((int)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          return instance.instanceMethod((int)(argument));
        }

        public MetaParameter<Integer> p1Parameter() {
          return p1Parameter;
        }
      }

      public static final class MetaInstanceMethod_1Method extends InstanceMetaMethod<model.MixedJavaModel, Integer> {
        private final MetaParameter<int[]> p1Parameter = register(new MetaParameter<>(0, "p1",metaArray(int[].class, int[]::new, metaType(int.class))));

        private MetaInstanceMethod_1Method() {
          super("instanceMethod",metaType(int.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.instanceMethod((int[])(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          return instance.instanceMethod((int[])(argument));
        }

        public MetaParameter<int[]> p1Parameter() {
          return p1Parameter;
        }
      }

      public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.String> {
        private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

        private MetaImplementableMethodMethod() {
          super("implementableMethod",metaType(java.lang.String.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.implementableMethod((java.lang.String)(arguments[0]));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          return instance.implementableMethod((java.lang.String)(argument));
        }

        public MetaParameter<java.lang.String> argumentParameter() {
          return argumentParameter;
        }
      }

      public static final class MetaIsF1Method extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaIsF1Method() {
          super("isF1",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isF1();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.isF1();
        }
      }

      public static final class MetaGetF2Method extends InstanceMetaMethod<model.MixedJavaModel, java.lang.String> {
        private MetaGetF2Method() {
          super("getF2",metaType(java.lang.String.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF2();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF2();
        }
      }

      public static final class MetaGetF3Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.lang.String>> {
        private MetaGetF3Method() {
          super("getF3",metaType(java.util.List.class,metaType(java.lang.String.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF3();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF3();
        }
      }

      public static final class MetaGetF4Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<?>> {
        private MetaGetF4Method() {
          super("getF4",metaType(java.util.List.class,metaType(java.lang.Object.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF4();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF4();
        }
      }

      public static final class MetaGetF5Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? extends java.lang.String>> {
        private MetaGetF5Method() {
          super("getF5",metaType(java.util.List.class,metaType(java.lang.String.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF5();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF5();
        }
      }

      public static final class MetaGetF6Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? super java.lang.String>> {
        private MetaGetF6Method() {
          super("getF6",metaType(java.util.List.class,metaType(java.lang.String.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF6();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF6();
        }
      }

      public static final class MetaGetF7Method extends InstanceMetaMethod<model.MixedJavaModel, boolean[]> {
        private MetaGetF7Method() {
          super("getF7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF7();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF7();
        }
      }

      public static final class MetaGetF8Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.lang.String>[]> {
        private MetaGetF8Method() {
          super("getF8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF8();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF8();
        }
      }

      public static final class MetaGetF9Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<?>[]> {
        private MetaGetF9Method() {
          super("getF9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF9();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF9();
        }
      }

      public static final class MetaGetF10Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? extends java.lang.String>[]> {
        private MetaGetF10Method() {
          super("getF10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF10();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF10();
        }
      }

      public static final class MetaGetF11Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? super java.lang.String>[]> {
        private MetaGetF11Method() {
          super("getF11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF11();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF11();
        }
      }

      public static final class MetaGetF12Method extends InstanceMetaMethod<model.MixedJavaModel, boolean[][]> {
        private MetaGetF12Method() {
          super("getF12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF12();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF12();
        }
      }

      public static final class MetaGetF13Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.lang.String>[][]> {
        private MetaGetF13Method() {
          super("getF13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF13();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF13();
        }
      }

      public static final class MetaGetF14Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<?>[][]> {
        private MetaGetF14Method() {
          super("getF14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF14();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF14();
        }
      }

      public static final class MetaGetF15Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? extends java.lang.String>[][]> {
        private MetaGetF15Method() {
          super("getF15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF15();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF15();
        }
      }

      public static final class MetaGetF16Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? super java.lang.String>[][]> {
        private MetaGetF16Method() {
          super("getF16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF16();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF16();
        }
      }

      public static final class MetaGetF17Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<boolean[]>> {
        private MetaGetF17Method() {
          super("getF17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF17();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF17();
        }
      }

      public static final class MetaGetF18Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.lang.String[]>[]> {
        private MetaGetF18Method() {
          super("getF18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF18();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF18();
        }
      }

      public static final class MetaGetF19Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? extends java.lang.String[]>[]> {
        private MetaGetF19Method() {
          super("getF19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF19();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF19();
        }
      }

      public static final class MetaGetF20Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<? super java.lang.String[]>[]> {
        private MetaGetF20Method() {
          super("getF20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF20();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF20();
        }
      }

      public static final class MetaGetF21Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<java.lang.Boolean>>> {
        private MetaGetF21Method() {
          super("getF21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF21();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF21();
        }
      }

      public static final class MetaGetF22Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<java.lang.String>>> {
        private MetaGetF22Method() {
          super("getF22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF22();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF22();
        }
      }

      public static final class MetaGetF23Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<?>>> {
        private MetaGetF23Method() {
          super("getF23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF23();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF23();
        }
      }

      public static final class MetaGetF24Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String>>> {
        private MetaGetF24Method() {
          super("getF24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF24();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF24();
        }
      }

      public static final class MetaGetF25Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String>>> {
        private MetaGetF25Method() {
          super("getF25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF25();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF25();
        }
      }

      public static final class MetaGetF26Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<boolean[]>>> {
        private MetaGetF26Method() {
          super("getF26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF26();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF26();
        }
      }

      public static final class MetaGetF27Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<java.lang.String[]>>> {
        private MetaGetF27Method() {
          super("getF27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF27();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF27();
        }
      }

      public static final class MetaGetF28Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String[]>>> {
        private MetaGetF28Method() {
          super("getF28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF28();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF28();
        }
      }

      public static final class MetaGetF29Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String[]>>> {
        private MetaGetF29Method() {
          super("getF29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF29();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF29();
        }
      }

      public static final class MetaGetF30Method extends InstanceMetaMethod<model.MixedJavaModel, model.MixedJavaModel.InnerModel> {
        private MetaGetF30Method() {
          super("getF30",metaType(model.MixedJavaModel.InnerModel.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF30();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF30();
        }
      }

      public static final class MetaGetF31Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<model.MixedJavaModel.InnerModel>> {
        private MetaGetF31Method() {
          super("getF31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF31();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF31();
        }
      }

      public static final class MetaGetF32Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<java.lang.String, java.lang.String>> {
        private MetaGetF32Method() {
          super("getF32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF32();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF32();
        }
      }

      public static final class MetaGetF33Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<?, java.lang.String>> {
        private MetaGetF33Method() {
          super("getF33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF33();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF33();
        }
      }

      public static final class MetaGetF34Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<java.lang.String, ?>> {
        private MetaGetF34Method() {
          super("getF34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF34();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF34();
        }
      }

      public static final class MetaGetF35Method extends InstanceMetaMethod<model.MixedJavaModel, model.MixedJavaModel.Enum> {
        private MetaGetF35Method() {
          super("getF35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF35();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF35();
        }
      }

      public static final class MetaGetF36Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.List<model.MixedJavaModel.Enum>> {
        private MetaGetF36Method() {
          super("getF36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF36();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF36();
        }
      }

      public static final class MetaGetF37Method extends InstanceMetaMethod<model.MixedJavaModel, model.MixedJavaModel.Enum[]> {
        private MetaGetF37Method() {
          super("getF37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF37();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF37();
        }
      }

      public static final class MetaGetF38Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> {
        private MetaGetF38Method() {
          super("getF38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF38();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF38();
        }
      }

      public static final class MetaGetF39Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> {
        private MetaGetF39Method() {
          super("getF39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF39();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF39();
        }
      }

      public static final class MetaGetF40Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> {
        private MetaGetF40Method() {
          super("getF40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF40();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF40();
        }
      }

      public static final class MetaGetF41Method extends InstanceMetaMethod<model.MixedJavaModel, java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> {
        private MetaGetF41Method() {
          super("getF41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getF41();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getF41();
        }
      }

      public static final class MetaGetGenericFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, model.MixedJavaModel.GenericClass<?>> {
        private MetaGetGenericFieldMethod() {
          super("getGenericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.getGenericField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.getGenericField();
        }
      }

      public static final class MetaSetF1Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

        private MetaSetF1Method() {
          super("setF1",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF1((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF1((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> f1Parameter() {
          return f1Parameter;
        }
      }

      public static final class MetaSetF2Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

        private MetaSetF2Method() {
          super("setF2",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF2((java.lang.String)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF2((java.lang.String)(argument));
          return null;
        }

        public MetaParameter<java.lang.String> f2Parameter() {
          return f2Parameter;
        }
      }

      public static final class MetaSetF3Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private MetaSetF3Method() {
          super("setF3",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF3((java.util.List<java.lang.String>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF3((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
          return f3Parameter;
        }
      }

      public static final class MetaSetF4Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

        private MetaSetF4Method() {
          super("setF4",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF4((java.util.List<?>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF4((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<?>> f4Parameter() {
          return f4Parameter;
        }
      }

      public static final class MetaSetF5Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private MetaSetF5Method() {
          super("setF5",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF5((java.util.List<? extends java.lang.String>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF5((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
          return f5Parameter;
        }
      }

      public static final class MetaSetF6Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

        private MetaSetF6Method() {
          super("setF6",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF6((java.util.List<? super java.lang.String>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF6((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
          return f6Parameter;
        }
      }

      public static final class MetaSetF7Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

        private MetaSetF7Method() {
          super("setF7",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF7((boolean[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF7((boolean[])(argument));
          return null;
        }

        public MetaParameter<boolean[]> f7Parameter() {
          return f7Parameter;
        }
      }

      public static final class MetaSetF8Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF8Method() {
          super("setF8",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF8((java.util.List<java.lang.String>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF8((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
          return f8Parameter;
        }
      }

      public static final class MetaSetF9Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

        private MetaSetF9Method() {
          super("setF9",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF9((java.util.List<?>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF9((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<?>[]> f9Parameter() {
          return f9Parameter;
        }
      }

      public static final class MetaSetF10Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF10Method() {
          super("setF10",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF10((java.util.List<? extends java.lang.String>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF10((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
          return f10Parameter;
        }
      }

      public static final class MetaSetF11Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF11Method() {
          super("setF11",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF11((java.util.List<? super java.lang.String>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF11((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
          return f11Parameter;
        }
      }

      public static final class MetaSetF12Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

        private MetaSetF12Method() {
          super("setF12",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF12((boolean[][])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF12((boolean[][])(argument));
          return null;
        }

        public MetaParameter<boolean[][]> f12Parameter() {
          return f12Parameter;
        }
      }

      public static final class MetaSetF13Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private MetaSetF13Method() {
          super("setF13",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF13((java.util.List<java.lang.String>[][])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF13((java.util.List[][])(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
          return f13Parameter;
        }
      }

      public static final class MetaSetF14Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

        private MetaSetF14Method() {
          super("setF14",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF14((java.util.List<?>[][])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF14((java.util.List[][])(argument));
          return null;
        }

        public MetaParameter<java.util.List<?>[][]> f14Parameter() {
          return f14Parameter;
        }
      }

      public static final class MetaSetF15Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private MetaSetF15Method() {
          super("setF15",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF15((java.util.List[][])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
          return f15Parameter;
        }
      }

      public static final class MetaSetF16Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

        private MetaSetF16Method() {
          super("setF16",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF16((java.util.List<? super java.lang.String>[][])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF16((java.util.List[][])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
          return f16Parameter;
        }
      }

      public static final class MetaSetF17Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

        private MetaSetF17Method() {
          super("setF17",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF17((java.util.List<boolean[]>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF17((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
          return f17Parameter;
        }
      }

      public static final class MetaSetF18Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF18Method() {
          super("setF18",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF18((java.util.List<java.lang.String[]>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF18((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
          return f18Parameter;
        }
      }

      public static final class MetaSetF19Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF19Method() {
          super("setF19",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF19((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
          return f19Parameter;
        }
      }

      public static final class MetaSetF20Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF20Method() {
          super("setF20",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF20((java.util.List[])(argument));
          return null;
        }

        public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
          return f20Parameter;
        }
      }

      public static final class MetaSetF21Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

        private MetaSetF21Method() {
          super("setF21",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF21((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
          return f21Parameter;
        }
      }

      public static final class MetaSetF22Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF22Method() {
          super("setF22",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF22((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
          return f22Parameter;
        }
      }

      public static final class MetaSetF23Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

        private MetaSetF23Method() {
          super("setF23",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF23((java.util.List<java.util.List<?>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF23((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
          return f23Parameter;
        }
      }

      public static final class MetaSetF24Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF24Method() {
          super("setF24",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF24((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
            ) {
          return f24Parameter;
        }
      }

      public static final class MetaSetF25Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

        private MetaSetF25Method() {
          super("setF25",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF25((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
            ) {
          return f25Parameter;
        }
      }

      public static final class MetaSetF26Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

        private MetaSetF26Method() {
          super("setF26",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF26((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
          return f26Parameter;
        }
      }

      public static final class MetaSetF27Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF27Method() {
          super("setF27",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF27((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
          return f27Parameter;
        }
      }

      public static final class MetaSetF28Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF28Method() {
          super("setF28",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF28((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
            ) {
          return f28Parameter;
        }
      }

      public static final class MetaSetF29Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

        private MetaSetF29Method() {
          super("setF29",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF29((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
            ) {
          return f29Parameter;
        }
      }

      public static final class MetaSetF30Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.MixedJavaModel.InnerModel.class)));

        private MetaSetF30Method() {
          super("setF30",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF30((model.MixedJavaModel.InnerModel)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF30((model.MixedJavaModel.InnerModel)(argument));
          return null;
        }

        public MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter() {
          return f30Parameter;
        }
      }

      public static final class MetaSetF31Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class))));

        private MetaSetF31Method() {
          super("setF31",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF31((java.util.List<model.MixedJavaModel.InnerModel>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF31((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter() {
          return f31Parameter;
        }
      }

      public static final class MetaSetF32Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

        private MetaSetF32Method() {
          super("setF32",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF32((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
          return f32Parameter;
        }
      }

      public static final class MetaSetF33Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

        private MetaSetF33Method() {
          super("setF33",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF33((java.util.Map<?, java.lang.String>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF33((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
          return f33Parameter;
        }
      }

      public static final class MetaSetF34Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

        private MetaSetF34Method() {
          super("setF34",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF34((java.util.Map<java.lang.String, ?>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF34((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
          return f34Parameter;
        }
      }

      public static final class MetaSetF35Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));

        private MetaSetF35Method() {
          super("setF35",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF35((model.MixedJavaModel.Enum)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF35((model.MixedJavaModel.Enum)(argument));
          return null;
        }

        public MetaParameter<model.MixedJavaModel.Enum> f35Parameter() {
          return f35Parameter;
        }
      }

      public static final class MetaSetF36Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private MetaSetF36Method() {
          super("setF36",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF36((java.util.List<model.MixedJavaModel.Enum>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF36((java.util.List)(argument));
          return null;
        }

        public MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter() {
          return f36Parameter;
        }
      }

      public static final class MetaSetF37Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private MetaSetF37Method() {
          super("setF37",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF37((model.MixedJavaModel.Enum[])(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF37((model.MixedJavaModel.Enum[])(argument));
          return null;
        }

        public MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter() {
          return f37Parameter;
        }
      }

      public static final class MetaSetF38Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

        private MetaSetF38Method() {
          super("setF38",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF38((java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF38((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter(
            ) {
          return f38Parameter;
        }
      }

      public static final class MetaSetF39Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))));

        private MetaSetF39Method() {
          super("setF39",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF39((java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF39((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter(
            ) {
          return f39Parameter;
        }
      }

      public static final class MetaSetF40Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

        private MetaSetF40Method() {
          super("setF40",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF40((java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF40((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter(
            ) {
          return f40Parameter;
        }
      }

      public static final class MetaSetF41Method extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

        private MetaSetF41Method() {
          super("setF41",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setF41((java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setF41((java.util.Map)(argument));
          return null;
        }

        public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter(
            ) {
          return f41Parameter;
        }
      }

      public static final class MetaSetGenericFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

        private MetaSetGenericFieldMethod() {
          super("setGenericField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setGenericField((model.MixedJavaModel.GenericClass<?>)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setGenericField((model.MixedJavaModel.GenericClass)(argument));
          return null;
        }

        public MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
          return genericFieldParameter;
        }
      }

      public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaPublicMethodMethod() {
          super("publicMethod",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.publicMethod();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.publicMethod();
        }
      }

      public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaPublicAbstractMethodMethod() {
          super("publicAbstractMethod",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.publicAbstractMethod();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.publicAbstractMethod();
        }
      }

      public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaIsPrivateFieldMethod() {
          super("isPrivateField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isPrivateField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.isPrivateField();
        }
      }

      public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaIsProtectedFieldMethod() {
          super("isProtectedField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isProtectedField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.isProtectedField();
        }
      }

      public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaIsPublicFieldMethod() {
          super("isPublicField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isPublicField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.isPublicField();
        }
      }

      public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, java.lang.Boolean> {
        private MetaIsInternalFieldMethod() {
          super("isInternalField",metaType(boolean.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          return instance.isInternalField();
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance) throws Throwable {
          return instance.isInternalField();
        }
      }

      public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

        private MetaSetPrivateFieldMethod() {
          super("setPrivateField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setPrivateField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setPrivateField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> privateFieldParameter() {
          return privateFieldParameter;
        }
      }

      public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

        private MetaSetProtectedFieldMethod() {
          super("setProtectedField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setProtectedField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setProtectedField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
          return protectedFieldParameter;
        }
      }

      public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

        private MetaSetPublicFieldMethod() {
          super("setPublicField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setPublicField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setPublicField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> publicFieldParameter() {
          return publicFieldParameter;
        }
      }

      public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.MixedJavaModel, Void> {
        private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

        private MetaSetInternalFieldMethod() {
          super("setInternalField",metaType(Void.class));
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object[] arguments)
            throws Throwable {
          instance.setInternalField((boolean)(arguments[0]));
          return null;
        }

        @Override
        public java.lang.Object invoke(model.MixedJavaModel instance, java.lang.Object argument)
            throws Throwable {
          instance.setInternalField((boolean)(argument));
          return null;
        }

        public MetaParameter<java.lang.Boolean> internalFieldParameter() {
          return internalFieldParameter;
        }
      }

      public static final class MetaMixedJavaModelBuilderClass extends MetaClass<model.MixedJavaModel.MixedJavaModelBuilder> {
        private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

        private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

        private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

        private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

        private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<model.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.MixedJavaModel.InnerModel.class),false));

        private final MetaField<java.util.List<model.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class)),false));

        private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

        private final MetaField<model.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),false));

        private final MetaField<java.util.List<model.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<model.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))),false));

        private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<model.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

        private final MetaF1Method f1Method = register(new MetaF1Method());

        private final MetaF2Method f2Method = register(new MetaF2Method());

        private final MetaF3Method f3Method = register(new MetaF3Method());

        private final MetaF4Method f4Method = register(new MetaF4Method());

        private final MetaF5Method f5Method = register(new MetaF5Method());

        private final MetaF6Method f6Method = register(new MetaF6Method());

        private final MetaF7Method f7Method = register(new MetaF7Method());

        private final MetaF8Method f8Method = register(new MetaF8Method());

        private final MetaF9Method f9Method = register(new MetaF9Method());

        private final MetaF10Method f10Method = register(new MetaF10Method());

        private final MetaF11Method f11Method = register(new MetaF11Method());

        private final MetaF12Method f12Method = register(new MetaF12Method());

        private final MetaF13Method f13Method = register(new MetaF13Method());

        private final MetaF14Method f14Method = register(new MetaF14Method());

        private final MetaF15Method f15Method = register(new MetaF15Method());

        private final MetaF16Method f16Method = register(new MetaF16Method());

        private final MetaF17Method f17Method = register(new MetaF17Method());

        private final MetaF18Method f18Method = register(new MetaF18Method());

        private final MetaF19Method f19Method = register(new MetaF19Method());

        private final MetaF20Method f20Method = register(new MetaF20Method());

        private final MetaF21Method f21Method = register(new MetaF21Method());

        private final MetaF22Method f22Method = register(new MetaF22Method());

        private final MetaF23Method f23Method = register(new MetaF23Method());

        private final MetaF24Method f24Method = register(new MetaF24Method());

        private final MetaF25Method f25Method = register(new MetaF25Method());

        private final MetaF26Method f26Method = register(new MetaF26Method());

        private final MetaF27Method f27Method = register(new MetaF27Method());

        private final MetaF28Method f28Method = register(new MetaF28Method());

        private final MetaF29Method f29Method = register(new MetaF29Method());

        private final MetaF30Method f30Method = register(new MetaF30Method());

        private final MetaF31Method f31Method = register(new MetaF31Method());

        private final MetaF32Method f32Method = register(new MetaF32Method());

        private final MetaF33Method f33Method = register(new MetaF33Method());

        private final MetaF34Method f34Method = register(new MetaF34Method());

        private final MetaF35Method f35Method = register(new MetaF35Method());

        private final MetaF36Method f36Method = register(new MetaF36Method());

        private final MetaF37Method f37Method = register(new MetaF37Method());

        private final MetaF38Method f38Method = register(new MetaF38Method());

        private final MetaF39Method f39Method = register(new MetaF39Method());

        private final MetaF40Method f40Method = register(new MetaF40Method());

        private final MetaF41Method f41Method = register(new MetaF41Method());

        private final MetaGenericFieldMethod genericFieldMethod = register(new MetaGenericFieldMethod());

        private final MetaBuildMethod buildMethod = register(new MetaBuildMethod());

        private MetaMixedJavaModelBuilderClass() {
          super(metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
        }

        public MetaField<java.lang.Boolean> f1Field() {
          return f1Field;
        }

        public MetaField<java.lang.String> f2Field() {
          return f2Field;
        }

        public MetaField<java.util.List<java.lang.String>> f3Field() {
          return f3Field;
        }

        public MetaField<java.util.List<?>> f4Field() {
          return f4Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
          return f5Field;
        }

        public MetaField<java.util.List<? super java.lang.String>> f6Field() {
          return f6Field;
        }

        public MetaField<boolean[]> f7Field() {
          return f7Field;
        }

        public MetaField<java.util.List<java.lang.String>[]> f8Field() {
          return f8Field;
        }

        public MetaField<java.util.List<?>[]> f9Field() {
          return f9Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
          return f10Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
          return f11Field;
        }

        public MetaField<boolean[][]> f12Field() {
          return f12Field;
        }

        public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
          return f13Field;
        }

        public MetaField<java.util.List<?>[][]> f14Field() {
          return f14Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
          return f15Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
          return f16Field;
        }

        public MetaField<java.util.List<boolean[]>> f17Field() {
          return f17Field;
        }

        public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
          return f18Field;
        }

        public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
          return f19Field;
        }

        public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
          return f20Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
          return f21Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
          return f22Field;
        }

        public MetaField<java.util.List<java.util.List<?>>> f23Field() {
          return f23Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
          return f24Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
          return f25Field;
        }

        public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
          return f26Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
          return f27Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field() {
          return f28Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
          return f29Field;
        }

        public MetaField<model.MixedJavaModel.InnerModel> f30Field() {
          return f30Field;
        }

        public MetaField<java.util.List<model.MixedJavaModel.InnerModel>> f31Field() {
          return f31Field;
        }

        public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
          return f32Field;
        }

        public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
          return f33Field;
        }

        public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
          return f34Field;
        }

        public MetaField<model.MixedJavaModel.Enum> f35Field() {
          return f35Field;
        }

        public MetaField<java.util.List<model.MixedJavaModel.Enum>> f36Field() {
          return f36Field;
        }

        public MetaField<model.MixedJavaModel.Enum[]> f37Field() {
          return f37Field;
        }

        public MetaField<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Field(
            ) {
          return f38Field;
        }

        public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Field(
            ) {
          return f39Field;
        }

        public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Field(
            ) {
          return f40Field;
        }

        public MetaField<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Field(
            ) {
          return f41Field;
        }

        public MetaField<model.MixedJavaModel.GenericClass<?>> genericFieldField() {
          return genericFieldField;
        }

        public MetaF1Method f1Method() {
          return f1Method;
        }

        public MetaF2Method f2Method() {
          return f2Method;
        }

        public MetaF3Method f3Method() {
          return f3Method;
        }

        public MetaF4Method f4Method() {
          return f4Method;
        }

        public MetaF5Method f5Method() {
          return f5Method;
        }

        public MetaF6Method f6Method() {
          return f6Method;
        }

        public MetaF7Method f7Method() {
          return f7Method;
        }

        public MetaF8Method f8Method() {
          return f8Method;
        }

        public MetaF9Method f9Method() {
          return f9Method;
        }

        public MetaF10Method f10Method() {
          return f10Method;
        }

        public MetaF11Method f11Method() {
          return f11Method;
        }

        public MetaF12Method f12Method() {
          return f12Method;
        }

        public MetaF13Method f13Method() {
          return f13Method;
        }

        public MetaF14Method f14Method() {
          return f14Method;
        }

        public MetaF15Method f15Method() {
          return f15Method;
        }

        public MetaF16Method f16Method() {
          return f16Method;
        }

        public MetaF17Method f17Method() {
          return f17Method;
        }

        public MetaF18Method f18Method() {
          return f18Method;
        }

        public MetaF19Method f19Method() {
          return f19Method;
        }

        public MetaF20Method f20Method() {
          return f20Method;
        }

        public MetaF21Method f21Method() {
          return f21Method;
        }

        public MetaF22Method f22Method() {
          return f22Method;
        }

        public MetaF23Method f23Method() {
          return f23Method;
        }

        public MetaF24Method f24Method() {
          return f24Method;
        }

        public MetaF25Method f25Method() {
          return f25Method;
        }

        public MetaF26Method f26Method() {
          return f26Method;
        }

        public MetaF27Method f27Method() {
          return f27Method;
        }

        public MetaF28Method f28Method() {
          return f28Method;
        }

        public MetaF29Method f29Method() {
          return f29Method;
        }

        public MetaF30Method f30Method() {
          return f30Method;
        }

        public MetaF31Method f31Method() {
          return f31Method;
        }

        public MetaF32Method f32Method() {
          return f32Method;
        }

        public MetaF33Method f33Method() {
          return f33Method;
        }

        public MetaF34Method f34Method() {
          return f34Method;
        }

        public MetaF35Method f35Method() {
          return f35Method;
        }

        public MetaF36Method f36Method() {
          return f36Method;
        }

        public MetaF37Method f37Method() {
          return f37Method;
        }

        public MetaF38Method f38Method() {
          return f38Method;
        }

        public MetaF39Method f39Method() {
          return f39Method;
        }

        public MetaF40Method f40Method() {
          return f40Method;
        }

        public MetaF41Method f41Method() {
          return f41Method;
        }

        public MetaGenericFieldMethod genericFieldMethod() {
          return genericFieldMethod;
        }

        public MetaBuildMethod buildMethod() {
          return buildMethod;
        }

        public static final class MetaF1Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private MetaF1Method() {
            super("f1",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f1((boolean)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f1((boolean)(argument));
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }
        }

        public static final class MetaF2Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

          private MetaF2Method() {
            super("f2",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f2((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f2((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }
        }

        public static final class MetaF3Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaF3Method() {
            super("f3",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f3((java.util.List<java.lang.String>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f3((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
            return f3Parameter;
          }
        }

        public static final class MetaF4Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

          private MetaF4Method() {
            super("f4",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f4((java.util.List<?>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f4((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<?>> f4Parameter() {
            return f4Parameter;
          }
        }

        public static final class MetaF5Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaF5Method() {
            super("f5",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f5((java.util.List<? extends java.lang.String>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f5((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
            return f5Parameter;
          }
        }

        public static final class MetaF6Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaF6Method() {
            super("f6",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f6((java.util.List<? super java.lang.String>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f6((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
            return f6Parameter;
          }
        }

        public static final class MetaF7Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

          private MetaF7Method() {
            super("f7",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f7((boolean[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f7((boolean[])(argument));
          }

          public MetaParameter<boolean[]> f7Parameter() {
            return f7Parameter;
          }
        }

        public static final class MetaF8Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF8Method() {
            super("f8",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f8((java.util.List<java.lang.String>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f8((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
            return f8Parameter;
          }
        }

        public static final class MetaF9Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaF9Method() {
            super("f9",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f9((java.util.List<?>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f9((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<?>[]> f9Parameter() {
            return f9Parameter;
          }
        }

        public static final class MetaF10Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF10Method() {
            super("f10",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f10((java.util.List<? extends java.lang.String>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f10((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
            return f10Parameter;
          }
        }

        public static final class MetaF11Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF11Method() {
            super("f11",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f11((java.util.List<? super java.lang.String>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f11((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
            return f11Parameter;
          }
        }

        public static final class MetaF12Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaF12Method() {
            super("f12",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f12((boolean[][])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f12((boolean[][])(argument));
          }

          public MetaParameter<boolean[][]> f12Parameter() {
            return f12Parameter;
          }
        }

        public static final class MetaF13Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaF13Method() {
            super("f13",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f13((java.util.List<java.lang.String>[][])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f13((java.util.List[][])(argument));
          }

          public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
            return f13Parameter;
          }
        }

        public static final class MetaF14Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

          private MetaF14Method() {
            super("f14",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f14((java.util.List<?>[][])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f14((java.util.List[][])(argument));
          }

          public MetaParameter<java.util.List<?>[][]> f14Parameter() {
            return f14Parameter;
          }
        }

        public static final class MetaF15Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaF15Method() {
            super("f15",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f15((java.util.List[][])(argument));
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
            return f15Parameter;
          }
        }

        public static final class MetaF16Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaF16Method() {
            super("f16",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f16((java.util.List<? super java.lang.String>[][])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f16((java.util.List[][])(argument));
          }

          public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
            return f16Parameter;
          }
        }

        public static final class MetaF17Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaF17Method() {
            super("f17",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f17((java.util.List<boolean[]>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f17((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
            return f17Parameter;
          }
        }

        public static final class MetaF18Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF18Method() {
            super("f18",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f18((java.util.List<java.lang.String[]>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f18((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
            return f18Parameter;
          }
        }

        public static final class MetaF19Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF19Method() {
            super("f19",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f19((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
            return f19Parameter;
          }
        }

        public static final class MetaF20Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF20Method() {
            super("f20",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f20((java.util.List[])(argument));
          }

          public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
            return f20Parameter;
          }
        }

        public static final class MetaF21Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

          private MetaF21Method() {
            super("f21",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f21((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
            return f21Parameter;
          }
        }

        public static final class MetaF22Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF22Method() {
            super("f22",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f22((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
            return f22Parameter;
          }
        }

        public static final class MetaF23Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaF23Method() {
            super("f23",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f23((java.util.List<java.util.List<?>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f23((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
            return f23Parameter;
          }
        }

        public static final class MetaF24Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF24Method() {
            super("f24",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f24((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
              ) {
            return f24Parameter;
          }
        }

        public static final class MetaF25Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaF25Method() {
            super("f25",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f25((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
              ) {
            return f25Parameter;
          }
        }

        public static final class MetaF26Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

          private MetaF26Method() {
            super("f26",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f26((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
            return f26Parameter;
          }
        }

        public static final class MetaF27Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF27Method() {
            super("f27",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f27((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
            return f27Parameter;
          }
        }

        public static final class MetaF28Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF28Method() {
            super("f28",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f28((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
              ) {
            return f28Parameter;
          }
        }

        public static final class MetaF29Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaF29Method() {
            super("f29",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f29((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
              ) {
            return f29Parameter;
          }
        }

        public static final class MetaF30Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.MixedJavaModel.InnerModel.class)));

          private MetaF30Method() {
            super("f30",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f30((model.MixedJavaModel.InnerModel)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f30((model.MixedJavaModel.InnerModel)(argument));
          }

          public MetaParameter<model.MixedJavaModel.InnerModel> f30Parameter() {
            return f30Parameter;
          }
        }

        public static final class MetaF31Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.MixedJavaModel.InnerModel.class))));

          private MetaF31Method() {
            super("f31",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f31((java.util.List<model.MixedJavaModel.InnerModel>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f31((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<model.MixedJavaModel.InnerModel>> f31Parameter() {
            return f31Parameter;
          }
        }

        public static final class MetaF32Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

          private MetaF32Method() {
            super("f32",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f32((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
            return f32Parameter;
          }
        }

        public static final class MetaF33Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

          private MetaF33Method() {
            super("f33",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f33((java.util.Map<?, java.lang.String>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f33((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
            return f33Parameter;
          }
        }

        public static final class MetaF34Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

          private MetaF34Method() {
            super("f34",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f34((java.util.Map<java.lang.String, ?>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f34((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
            return f34Parameter;
          }
        }

        public static final class MetaF35Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)));

          private MetaF35Method() {
            super("f35",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f35((model.MixedJavaModel.Enum)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f35((model.MixedJavaModel.Enum)(argument));
          }

          public MetaParameter<model.MixedJavaModel.Enum> f35Parameter() {
            return f35Parameter;
          }
        }

        public static final class MetaF36Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

          private MetaF36Method() {
            super("f36",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f36((java.util.List<model.MixedJavaModel.Enum>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f36((java.util.List)(argument));
          }

          public MetaParameter<java.util.List<model.MixedJavaModel.Enum>> f36Parameter() {
            return f36Parameter;
          }
        }

        public static final class MetaF37Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

          private MetaF37Method() {
            super("f37",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f37((model.MixedJavaModel.Enum[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f37((model.MixedJavaModel.Enum[])(argument));
          }

          public MetaParameter<model.MixedJavaModel.Enum[]> f37Parameter() {
            return f37Parameter;
          }
        }

        public static final class MetaF38Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))));

          private MetaF38Method() {
            super("f38",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f38((java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f38((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, model.MixedJavaModel.Enum>> f38Parameter(
              ) {
            return f38Parameter;
          }
        }

        public static final class MetaF39Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf)))));

          private MetaF39Method() {
            super("f39",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f39((java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f39((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum>>> f39Parameter(
              ) {
            return f39Parameter;
          }
        }

        public static final class MetaF40Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

          private MetaF40Method() {
            super("f40",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f40((java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f40((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<model.MixedJavaModel.Enum[]>>> f40Parameter(
              ) {
            return f40Parameter;
          }
        }

        public static final class MetaF41Method extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.MixedJavaModel.Enum[].class, model.MixedJavaModel.Enum[]::new, metaEnum(model.MixedJavaModel.Enum.class, model.MixedJavaModel.Enum::valueOf))))));

          private MetaF41Method() {
            super("f41",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.f41((java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.f41((java.util.Map)(argument));
          }

          public MetaParameter<java.util.Map<model.MixedJavaModel.Enum, java.util.List<? extends model.MixedJavaModel.Enum[]>>> f41Parameter(
              ) {
            return f41Parameter;
          }
        }

        public static final class MetaGenericFieldMethod extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

          private MetaGenericFieldMethod() {
            super("genericField",metaType(model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.genericField((model.MixedJavaModel.GenericClass<?>)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object argument) throws Throwable {
            return instance.genericField((model.MixedJavaModel.GenericClass)(argument));
          }

          public MetaParameter<model.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
            return genericFieldParameter;
          }
        }

        public static final class MetaBuildMethod extends InstanceMetaMethod<model.MixedJavaModel.MixedJavaModelBuilder, model.MixedJavaModel> {
          private MetaBuildMethod() {
            super("build",metaType(model.MixedJavaModel.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.build();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.MixedJavaModelBuilder instance) throws
              Throwable {
            return instance.build();
          }
        }
      }

      public static final class MetaInnerModelClass extends MetaClass<model.MixedJavaModel.InnerModel> {
        private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

        private final MetaField<Long> serialVersionUIDField = register(new MetaField<>("serialVersionUID",metaType(long.class),true));

        private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

        private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

        private final MetaField<Integer> f3Field = register(new MetaField<>("f3",metaType(int.class),false));

        private final MetaField<model.MixedJavaModel> f5Field = register(new MetaField<>("f5",metaType(model.MixedJavaModel.class),false));

        private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

        private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

        private final MetaCompareMethod compareMethod = register(new MetaCompareMethod());

        private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

        private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

        private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

        private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

        private final MetaIntValueMethod intValueMethod = register(new MetaIntValueMethod());

        private final MetaLongValueMethod longValueMethod = register(new MetaLongValueMethod());

        private final MetaFloatValueMethod floatValueMethod = register(new MetaFloatValueMethod());

        private final MetaDoubleValueMethod doubleValueMethod = register(new MetaDoubleValueMethod());

        private final MetaByteValueMethod byteValueMethod = register(new MetaByteValueMethod());

        private final MetaShortValueMethod shortValueMethod = register(new MetaShortValueMethod());

        private MetaInnerModelClass() {
          super(metaType(model.MixedJavaModel.InnerModel.class));
        }

        public MetaConstructorConstructor constructor() {
          return constructor;
        }

        public MetaField<Long> serialVersionUIDField() {
          return serialVersionUIDField;
        }

        public MetaField<java.lang.Boolean> f1Field() {
          return f1Field;
        }

        public MetaField<java.lang.String> f2Field() {
          return f2Field;
        }

        public MetaField<Integer> f3Field() {
          return f3Field;
        }

        public MetaField<model.MixedJavaModel> f5Field() {
          return f5Field;
        }

        public MetaStaticMethodMethod staticMethodMethod() {
          return staticMethodMethod;
        }

        public MetaInstanceMethodMethod instanceMethodMethod() {
          return instanceMethodMethod;
        }

        public MetaCompareMethod compareMethod() {
          return compareMethod;
        }

        public MetaIsF1Method isF1Method() {
          return isF1Method;
        }

        public MetaGetF2Method getF2Method() {
          return getF2Method;
        }

        public MetaGetF3Method getF3Method() {
          return getF3Method;
        }

        public MetaGetF5Method getF5Method() {
          return getF5Method;
        }

        public MetaIntValueMethod intValueMethod() {
          return intValueMethod;
        }

        public MetaLongValueMethod longValueMethod() {
          return longValueMethod;
        }

        public MetaFloatValueMethod floatValueMethod() {
          return floatValueMethod;
        }

        public MetaDoubleValueMethod doubleValueMethod() {
          return doubleValueMethod;
        }

        public MetaByteValueMethod byteValueMethod() {
          return byteValueMethod;
        }

        public MetaShortValueMethod shortValueMethod() {
          return shortValueMethod;
        }

        public static final class MetaConstructorConstructor extends MetaConstructor<model.MixedJavaModel.InnerModel> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

          private final MetaParameter<Integer> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(int.class)));

          private final MetaParameter<model.MixedJavaModel> f5Parameter = register(new MetaParameter<>(3, "f5",metaType(model.MixedJavaModel.class)));

          private MetaConstructorConstructor() {
            super(metaType(model.MixedJavaModel.InnerModel.class));
          }

          @Override
          public model.MixedJavaModel.InnerModel invoke(java.lang.Object[] arguments) throws
              Throwable {
            return new model.MixedJavaModel.InnerModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(int)(arguments[2]),(model.MixedJavaModel)(arguments[3]));
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }

          public MetaParameter<Integer> f3Parameter() {
            return f3Parameter;
          }

          public MetaParameter<model.MixedJavaModel> f5Parameter() {
            return f5Parameter;
          }
        }

        public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
          private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

          private MetaStaticMethodMethod() {
            super("staticMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.MixedJavaModel.InnerModel.staticMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
            return model.MixedJavaModel.InnerModel.staticMethod((int)(argument));
          }

          public MetaParameter<Integer> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Integer> {
          private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

          private MetaInstanceMethodMethod() {
            super("instanceMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.instanceMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.instanceMethod((int)(argument));
          }

          public MetaParameter<Integer> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaCompareMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Integer> {
          private final MetaParameter<java.lang.String> o1Parameter = register(new MetaParameter<>(0, "o1",metaType(java.lang.String.class)));

          private final MetaParameter<java.lang.String> o2Parameter = register(new MetaParameter<>(1, "o2",metaType(java.lang.String.class)));

          private MetaCompareMethod() {
            super("compare",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.compare((java.lang.String)(arguments[0]),(java.lang.String)(arguments[1]));
          }

          public MetaParameter<java.lang.String> o1Parameter() {
            return o1Parameter;
          }

          public MetaParameter<java.lang.String> o2Parameter() {
            return o2Parameter;
          }
        }

        public static final class MetaIsF1Method extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, java.lang.Boolean> {
          private MetaIsF1Method() {
            super("isF1",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isF1();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.isF1();
          }
        }

        public static final class MetaGetF2Method extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, java.lang.String> {
          private MetaGetF2Method() {
            super("getF2",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF2();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.getF2();
          }
        }

        public static final class MetaGetF3Method extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Integer> {
          private MetaGetF3Method() {
            super("getF3",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF3();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.getF3();
          }
        }

        public static final class MetaGetF5Method extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, model.MixedJavaModel> {
          private MetaGetF5Method() {
            super("getF5",metaType(model.MixedJavaModel.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF5();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.getF5();
          }
        }

        public static final class MetaIntValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Integer> {
          private MetaIntValueMethod() {
            super("intValue",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.intValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.intValue();
          }
        }

        public static final class MetaLongValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Long> {
          private MetaLongValueMethod() {
            super("longValue",metaType(long.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.longValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.longValue();
          }
        }

        public static final class MetaFloatValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Float> {
          private MetaFloatValueMethod() {
            super("floatValue",metaType(float.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.floatValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.floatValue();
          }
        }

        public static final class MetaDoubleValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Double> {
          private MetaDoubleValueMethod() {
            super("doubleValue",metaType(double.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.doubleValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.doubleValue();
          }
        }

        public static final class MetaByteValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Byte> {
          private MetaByteValueMethod() {
            super("byteValue",metaType(byte.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.byteValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.byteValue();
          }
        }

        public static final class MetaShortValueMethod extends InstanceMetaMethod<model.MixedJavaModel.InnerModel, Short> {
          private MetaShortValueMethod() {
            super("shortValue",metaType(short.class));
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.shortValue();
          }

          @Override
          public java.lang.Object invoke(model.MixedJavaModel.InnerModel instance) throws
              Throwable {
            return instance.shortValue();
          }
        }
      }
    }

    public static final class MetaOtherPackage extends MetaPackage {
      private final MetaMixedJavaClassClass1 mixedJavaClassClass = register(new MetaMixedJavaClassClass1());

      private final MetaMixedJavaInterfaceClass1 mixedJavaInterfaceClass = register(new MetaMixedJavaInterfaceClass1());

      private final MetaMixedJavaModelClass1 mixedJavaModelClass = register(new MetaMixedJavaModelClass1());

      private MetaOtherPackage() {
        super("other");
      }

      public MetaMixedJavaClassClass1 mixedJavaClassClass() {
        return mixedJavaClassClass;
      }

      public MetaMixedJavaInterfaceClass1 mixedJavaInterfaceClass() {
        return mixedJavaInterfaceClass;
      }

      public MetaMixedJavaModelClass1 mixedJavaModelClass() {
        return mixedJavaModelClass;
      }

      public static final class MetaMixedJavaClassClass1 extends MetaClass<model.other.MixedJavaClass> {
        private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),false));

        private final MetaField<model.other.MixedJavaInterface> importedFieldField = register(new MetaField<>("importedField",metaType(model.other.MixedJavaInterface.class),false));

        private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

        private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

        private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

        private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

        private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

        private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

        private final MetaGetImportedFieldMethod getImportedFieldMethod = register(new MetaGetImportedFieldMethod());

        private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

        private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

        private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

        private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

        private final MetaSetImportedFieldMethod setImportedFieldMethod = register(new MetaSetImportedFieldMethod());

        private MetaMixedJavaClassClass1() {
          super(metaType(model.other.MixedJavaClass.class));
        }

        public MetaField<java.lang.Boolean> privateFieldField() {
          return privateFieldField;
        }

        public MetaField<java.lang.Boolean> protectedFieldField() {
          return protectedFieldField;
        }

        public MetaField<java.lang.Boolean> publicFieldField() {
          return publicFieldField;
        }

        public MetaField<java.lang.Boolean> internalFieldField() {
          return internalFieldField;
        }

        public MetaField<model.other.MixedJavaInterface> importedFieldField() {
          return importedFieldField;
        }

        public MetaPublicMethodMethod publicMethodMethod() {
          return publicMethodMethod;
        }

        public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
          return publicAbstractMethodMethod;
        }

        public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
          return isPrivateFieldMethod;
        }

        public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
          return isProtectedFieldMethod;
        }

        public MetaIsPublicFieldMethod isPublicFieldMethod() {
          return isPublicFieldMethod;
        }

        public MetaIsInternalFieldMethod isInternalFieldMethod() {
          return isInternalFieldMethod;
        }

        public MetaGetImportedFieldMethod getImportedFieldMethod() {
          return getImportedFieldMethod;
        }

        public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
          return setPrivateFieldMethod;
        }

        public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
          return setProtectedFieldMethod;
        }

        public MetaSetPublicFieldMethod setPublicFieldMethod() {
          return setPublicFieldMethod;
        }

        public MetaSetInternalFieldMethod setInternalFieldMethod() {
          return setInternalFieldMethod;
        }

        public MetaSetImportedFieldMethod setImportedFieldMethod() {
          return setImportedFieldMethod;
        }

        public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaPublicMethodMethod() {
            super("publicMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicMethod();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.publicMethod();
          }
        }

        public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaPublicAbstractMethodMethod() {
            super("publicAbstractMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicAbstractMethod();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.publicAbstractMethod();
          }
        }

        public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaIsPrivateFieldMethod() {
            super("isPrivateField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPrivateField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.isPrivateField();
          }
        }

        public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaIsProtectedFieldMethod() {
            super("isProtectedField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isProtectedField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.isProtectedField();
          }
        }

        public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaIsPublicFieldMethod() {
            super("isPublicField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPublicField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.isPublicField();
          }
        }

        public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, java.lang.Boolean> {
          private MetaIsInternalFieldMethod() {
            super("isInternalField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isInternalField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.isInternalField();
          }
        }

        public static final class MetaGetImportedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, model.other.MixedJavaInterface> {
          private MetaGetImportedFieldMethod() {
            super("getImportedField",metaType(model.other.MixedJavaInterface.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getImportedField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance) throws Throwable {
            return instance.getImportedField();
          }
        }

        public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

          private MetaSetPrivateFieldMethod() {
            super("setPrivateField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPrivateField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setPrivateField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> privateFieldParameter() {
            return privateFieldParameter;
          }
        }

        public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

          private MetaSetProtectedFieldMethod() {
            super("setProtectedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setProtectedField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setProtectedField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
            return protectedFieldParameter;
          }
        }

        public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

          private MetaSetPublicFieldMethod() {
            super("setPublicField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPublicField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setPublicField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> publicFieldParameter() {
            return publicFieldParameter;
          }
        }

        public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

          private MetaSetInternalFieldMethod() {
            super("setInternalField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setInternalField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setInternalField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> internalFieldParameter() {
            return internalFieldParameter;
          }
        }

        public static final class MetaSetImportedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaClass, Void> {
          private final MetaParameter<model.other.MixedJavaInterface> importedFieldParameter = register(new MetaParameter<>(0, "importedField",metaType(model.other.MixedJavaInterface.class)));

          private MetaSetImportedFieldMethod() {
            super("setImportedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setImportedField((model.other.MixedJavaInterface)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setImportedField((model.other.MixedJavaInterface)(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaInterface> importedFieldParameter() {
            return importedFieldParameter;
          }
        }
      }

      public static final class MetaMixedJavaInterfaceClass1 extends MetaClass<model.other.MixedJavaInterface> {
        private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

        private final MetaDefaultMethodMethod defaultMethodMethod = register(new MetaDefaultMethodMethod());

        private MetaMixedJavaInterfaceClass1() {
          super(metaType(model.other.MixedJavaInterface.class));
        }

        public MetaImplementableMethodMethod implementableMethodMethod() {
          return implementableMethodMethod;
        }

        public MetaDefaultMethodMethod defaultMethodMethod() {
          return defaultMethodMethod;
        }

        @Override
        public MetaProxy proxy(
            java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
          return new MetaMixedJavaInterfaceProxy(invocations);
        }

        public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.other.MixedJavaInterface, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaImplementableMethodMethod() {
            super("implementableMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaInterface instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.implementableMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaInterface instance,
              java.lang.Object argument) throws Throwable {
            return instance.implementableMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaDefaultMethodMethod extends InstanceMetaMethod<model.other.MixedJavaInterface, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaDefaultMethodMethod() {
            super("defaultMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaInterface instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.defaultMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaInterface instance,
              java.lang.Object argument) throws Throwable {
            return instance.defaultMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public class MetaMixedJavaInterfaceProxy extends MetaProxy implements model.other.MixedJavaInterface {
          private final Function<java.lang.Object, java.lang.Object> implementableMethodInvocation;

          public MetaMixedJavaInterfaceProxy(
              java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
            super(invocations);
            implementableMethodInvocation = invocations.get(implementableMethodMethod);
          }

          @Override
          public java.lang.String implementableMethod(java.lang.String argument) {
            return (java.lang.String)(implementableMethodInvocation.apply(argument));
          }
        }
      }

      public static final class MetaMixedJavaModelClass1 extends MetaClass<model.other.MixedJavaModel> {
        private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

        private final MetaConstructor_1Constructor constructor_1 = register(new MetaConstructor_1Constructor());

        private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),true));

        private final MetaField<model.other.MixedJavaInterface> importedFieldField = register(new MetaField<>("importedField",metaType(model.other.MixedJavaInterface.class),true));

        private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

        private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

        private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

        private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

        private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<model.other.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.other.MixedJavaModel.InnerModel.class),false));

        private final MetaField<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class)),false));

        private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

        private final MetaField<model.other.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),false));

        private final MetaField<java.util.List<model.other.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<model.other.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))),false));

        private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<model.other.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

        private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

        private final MetaStaticMethod_1Method staticMethod_1Method = register(new MetaStaticMethod_1Method());

        private final MetaStaticMethod_2Method staticMethod_2Method = register(new MetaStaticMethod_2Method());

        private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

        private final MetaInstanceMethod_1Method instanceMethod_1Method = register(new MetaInstanceMethod_1Method());

        private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

        private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

        private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

        private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

        private final MetaGetF4Method getF4Method = register(new MetaGetF4Method());

        private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

        private final MetaGetF6Method getF6Method = register(new MetaGetF6Method());

        private final MetaGetF7Method getF7Method = register(new MetaGetF7Method());

        private final MetaGetF8Method getF8Method = register(new MetaGetF8Method());

        private final MetaGetF9Method getF9Method = register(new MetaGetF9Method());

        private final MetaGetF10Method getF10Method = register(new MetaGetF10Method());

        private final MetaGetF11Method getF11Method = register(new MetaGetF11Method());

        private final MetaGetF12Method getF12Method = register(new MetaGetF12Method());

        private final MetaGetF13Method getF13Method = register(new MetaGetF13Method());

        private final MetaGetF14Method getF14Method = register(new MetaGetF14Method());

        private final MetaGetF15Method getF15Method = register(new MetaGetF15Method());

        private final MetaGetF16Method getF16Method = register(new MetaGetF16Method());

        private final MetaGetF17Method getF17Method = register(new MetaGetF17Method());

        private final MetaGetF18Method getF18Method = register(new MetaGetF18Method());

        private final MetaGetF19Method getF19Method = register(new MetaGetF19Method());

        private final MetaGetF20Method getF20Method = register(new MetaGetF20Method());

        private final MetaGetF21Method getF21Method = register(new MetaGetF21Method());

        private final MetaGetF22Method getF22Method = register(new MetaGetF22Method());

        private final MetaGetF23Method getF23Method = register(new MetaGetF23Method());

        private final MetaGetF24Method getF24Method = register(new MetaGetF24Method());

        private final MetaGetF25Method getF25Method = register(new MetaGetF25Method());

        private final MetaGetF26Method getF26Method = register(new MetaGetF26Method());

        private final MetaGetF27Method getF27Method = register(new MetaGetF27Method());

        private final MetaGetF28Method getF28Method = register(new MetaGetF28Method());

        private final MetaGetF29Method getF29Method = register(new MetaGetF29Method());

        private final MetaGetF30Method getF30Method = register(new MetaGetF30Method());

        private final MetaGetF31Method getF31Method = register(new MetaGetF31Method());

        private final MetaGetF32Method getF32Method = register(new MetaGetF32Method());

        private final MetaGetF33Method getF33Method = register(new MetaGetF33Method());

        private final MetaGetF34Method getF34Method = register(new MetaGetF34Method());

        private final MetaGetF35Method getF35Method = register(new MetaGetF35Method());

        private final MetaGetF36Method getF36Method = register(new MetaGetF36Method());

        private final MetaGetF37Method getF37Method = register(new MetaGetF37Method());

        private final MetaGetF38Method getF38Method = register(new MetaGetF38Method());

        private final MetaGetF39Method getF39Method = register(new MetaGetF39Method());

        private final MetaGetF40Method getF40Method = register(new MetaGetF40Method());

        private final MetaGetF41Method getF41Method = register(new MetaGetF41Method());

        private final MetaGetGenericFieldMethod getGenericFieldMethod = register(new MetaGetGenericFieldMethod());

        private final MetaSetF1Method setF1Method = register(new MetaSetF1Method());

        private final MetaSetF2Method setF2Method = register(new MetaSetF2Method());

        private final MetaSetF3Method setF3Method = register(new MetaSetF3Method());

        private final MetaSetF4Method setF4Method = register(new MetaSetF4Method());

        private final MetaSetF5Method setF5Method = register(new MetaSetF5Method());

        private final MetaSetF6Method setF6Method = register(new MetaSetF6Method());

        private final MetaSetF7Method setF7Method = register(new MetaSetF7Method());

        private final MetaSetF8Method setF8Method = register(new MetaSetF8Method());

        private final MetaSetF9Method setF9Method = register(new MetaSetF9Method());

        private final MetaSetF10Method setF10Method = register(new MetaSetF10Method());

        private final MetaSetF11Method setF11Method = register(new MetaSetF11Method());

        private final MetaSetF12Method setF12Method = register(new MetaSetF12Method());

        private final MetaSetF13Method setF13Method = register(new MetaSetF13Method());

        private final MetaSetF14Method setF14Method = register(new MetaSetF14Method());

        private final MetaSetF15Method setF15Method = register(new MetaSetF15Method());

        private final MetaSetF16Method setF16Method = register(new MetaSetF16Method());

        private final MetaSetF17Method setF17Method = register(new MetaSetF17Method());

        private final MetaSetF18Method setF18Method = register(new MetaSetF18Method());

        private final MetaSetF19Method setF19Method = register(new MetaSetF19Method());

        private final MetaSetF20Method setF20Method = register(new MetaSetF20Method());

        private final MetaSetF21Method setF21Method = register(new MetaSetF21Method());

        private final MetaSetF22Method setF22Method = register(new MetaSetF22Method());

        private final MetaSetF23Method setF23Method = register(new MetaSetF23Method());

        private final MetaSetF24Method setF24Method = register(new MetaSetF24Method());

        private final MetaSetF25Method setF25Method = register(new MetaSetF25Method());

        private final MetaSetF26Method setF26Method = register(new MetaSetF26Method());

        private final MetaSetF27Method setF27Method = register(new MetaSetF27Method());

        private final MetaSetF28Method setF28Method = register(new MetaSetF28Method());

        private final MetaSetF29Method setF29Method = register(new MetaSetF29Method());

        private final MetaSetF30Method setF30Method = register(new MetaSetF30Method());

        private final MetaSetF31Method setF31Method = register(new MetaSetF31Method());

        private final MetaSetF32Method setF32Method = register(new MetaSetF32Method());

        private final MetaSetF33Method setF33Method = register(new MetaSetF33Method());

        private final MetaSetF34Method setF34Method = register(new MetaSetF34Method());

        private final MetaSetF35Method setF35Method = register(new MetaSetF35Method());

        private final MetaSetF36Method setF36Method = register(new MetaSetF36Method());

        private final MetaSetF37Method setF37Method = register(new MetaSetF37Method());

        private final MetaSetF38Method setF38Method = register(new MetaSetF38Method());

        private final MetaSetF39Method setF39Method = register(new MetaSetF39Method());

        private final MetaSetF40Method setF40Method = register(new MetaSetF40Method());

        private final MetaSetF41Method setF41Method = register(new MetaSetF41Method());

        private final MetaSetGenericFieldMethod setGenericFieldMethod = register(new MetaSetGenericFieldMethod());

        private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

        private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

        private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

        private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

        private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

        private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

        private final MetaGetImportedFieldMethod getImportedFieldMethod = register(new MetaGetImportedFieldMethod());

        private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

        private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

        private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

        private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

        private final MetaSetImportedFieldMethod setImportedFieldMethod = register(new MetaSetImportedFieldMethod());

        private final MetaMixedJavaModelBuilderClass1 mixedJavaModelBuilderClass = register(new MetaMixedJavaModelBuilderClass1());

        private final MetaInnerModelClass1 innerModelClass = register(new MetaInnerModelClass1());

        private MetaMixedJavaModelClass1() {
          super(metaType(model.other.MixedJavaModel.class));
        }

        public MetaConstructorConstructor constructor() {
          return constructor;
        }

        public MetaConstructor_1Constructor constructor_1() {
          return constructor_1;
        }

        public MetaField<java.lang.Boolean> privateFieldField() {
          return privateFieldField;
        }

        public MetaField<java.lang.Boolean> protectedFieldField() {
          return protectedFieldField;
        }

        public MetaField<java.lang.Boolean> publicFieldField() {
          return publicFieldField;
        }

        public MetaField<java.lang.Boolean> internalFieldField() {
          return internalFieldField;
        }

        public MetaField<model.other.MixedJavaInterface> importedFieldField() {
          return importedFieldField;
        }

        public MetaField<java.lang.Boolean> f1Field() {
          return f1Field;
        }

        public MetaField<java.lang.String> f2Field() {
          return f2Field;
        }

        public MetaField<java.util.List<java.lang.String>> f3Field() {
          return f3Field;
        }

        public MetaField<java.util.List<?>> f4Field() {
          return f4Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
          return f5Field;
        }

        public MetaField<java.util.List<? super java.lang.String>> f6Field() {
          return f6Field;
        }

        public MetaField<boolean[]> f7Field() {
          return f7Field;
        }

        public MetaField<java.util.List<java.lang.String>[]> f8Field() {
          return f8Field;
        }

        public MetaField<java.util.List<?>[]> f9Field() {
          return f9Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
          return f10Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
          return f11Field;
        }

        public MetaField<boolean[][]> f12Field() {
          return f12Field;
        }

        public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
          return f13Field;
        }

        public MetaField<java.util.List<?>[][]> f14Field() {
          return f14Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
          return f15Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
          return f16Field;
        }

        public MetaField<java.util.List<boolean[]>> f17Field() {
          return f17Field;
        }

        public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
          return f18Field;
        }

        public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
          return f19Field;
        }

        public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
          return f20Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
          return f21Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
          return f22Field;
        }

        public MetaField<java.util.List<java.util.List<?>>> f23Field() {
          return f23Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
          return f24Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
          return f25Field;
        }

        public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
          return f26Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
          return f27Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field() {
          return f28Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
          return f29Field;
        }

        public MetaField<model.other.MixedJavaModel.InnerModel> f30Field() {
          return f30Field;
        }

        public MetaField<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Field() {
          return f31Field;
        }

        public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
          return f32Field;
        }

        public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
          return f33Field;
        }

        public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
          return f34Field;
        }

        public MetaField<model.other.MixedJavaModel.Enum> f35Field() {
          return f35Field;
        }

        public MetaField<java.util.List<model.other.MixedJavaModel.Enum>> f36Field() {
          return f36Field;
        }

        public MetaField<model.other.MixedJavaModel.Enum[]> f37Field() {
          return f37Field;
        }

        public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Field(
            ) {
          return f38Field;
        }

        public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Field(
            ) {
          return f39Field;
        }

        public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Field(
            ) {
          return f40Field;
        }

        public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Field(
            ) {
          return f41Field;
        }

        public MetaField<model.other.MixedJavaModel.GenericClass<?>> genericFieldField() {
          return genericFieldField;
        }

        public MetaStaticMethodMethod staticMethodMethod() {
          return staticMethodMethod;
        }

        public MetaStaticMethod_1Method staticMethod_1Method() {
          return staticMethod_1Method;
        }

        public MetaStaticMethod_2Method staticMethod_2Method() {
          return staticMethod_2Method;
        }

        public MetaInstanceMethodMethod instanceMethodMethod() {
          return instanceMethodMethod;
        }

        public MetaInstanceMethod_1Method instanceMethod_1Method() {
          return instanceMethod_1Method;
        }

        public MetaImplementableMethodMethod implementableMethodMethod() {
          return implementableMethodMethod;
        }

        public MetaIsF1Method isF1Method() {
          return isF1Method;
        }

        public MetaGetF2Method getF2Method() {
          return getF2Method;
        }

        public MetaGetF3Method getF3Method() {
          return getF3Method;
        }

        public MetaGetF4Method getF4Method() {
          return getF4Method;
        }

        public MetaGetF5Method getF5Method() {
          return getF5Method;
        }

        public MetaGetF6Method getF6Method() {
          return getF6Method;
        }

        public MetaGetF7Method getF7Method() {
          return getF7Method;
        }

        public MetaGetF8Method getF8Method() {
          return getF8Method;
        }

        public MetaGetF9Method getF9Method() {
          return getF9Method;
        }

        public MetaGetF10Method getF10Method() {
          return getF10Method;
        }

        public MetaGetF11Method getF11Method() {
          return getF11Method;
        }

        public MetaGetF12Method getF12Method() {
          return getF12Method;
        }

        public MetaGetF13Method getF13Method() {
          return getF13Method;
        }

        public MetaGetF14Method getF14Method() {
          return getF14Method;
        }

        public MetaGetF15Method getF15Method() {
          return getF15Method;
        }

        public MetaGetF16Method getF16Method() {
          return getF16Method;
        }

        public MetaGetF17Method getF17Method() {
          return getF17Method;
        }

        public MetaGetF18Method getF18Method() {
          return getF18Method;
        }

        public MetaGetF19Method getF19Method() {
          return getF19Method;
        }

        public MetaGetF20Method getF20Method() {
          return getF20Method;
        }

        public MetaGetF21Method getF21Method() {
          return getF21Method;
        }

        public MetaGetF22Method getF22Method() {
          return getF22Method;
        }

        public MetaGetF23Method getF23Method() {
          return getF23Method;
        }

        public MetaGetF24Method getF24Method() {
          return getF24Method;
        }

        public MetaGetF25Method getF25Method() {
          return getF25Method;
        }

        public MetaGetF26Method getF26Method() {
          return getF26Method;
        }

        public MetaGetF27Method getF27Method() {
          return getF27Method;
        }

        public MetaGetF28Method getF28Method() {
          return getF28Method;
        }

        public MetaGetF29Method getF29Method() {
          return getF29Method;
        }

        public MetaGetF30Method getF30Method() {
          return getF30Method;
        }

        public MetaGetF31Method getF31Method() {
          return getF31Method;
        }

        public MetaGetF32Method getF32Method() {
          return getF32Method;
        }

        public MetaGetF33Method getF33Method() {
          return getF33Method;
        }

        public MetaGetF34Method getF34Method() {
          return getF34Method;
        }

        public MetaGetF35Method getF35Method() {
          return getF35Method;
        }

        public MetaGetF36Method getF36Method() {
          return getF36Method;
        }

        public MetaGetF37Method getF37Method() {
          return getF37Method;
        }

        public MetaGetF38Method getF38Method() {
          return getF38Method;
        }

        public MetaGetF39Method getF39Method() {
          return getF39Method;
        }

        public MetaGetF40Method getF40Method() {
          return getF40Method;
        }

        public MetaGetF41Method getF41Method() {
          return getF41Method;
        }

        public MetaGetGenericFieldMethod getGenericFieldMethod() {
          return getGenericFieldMethod;
        }

        public MetaSetF1Method setF1Method() {
          return setF1Method;
        }

        public MetaSetF2Method setF2Method() {
          return setF2Method;
        }

        public MetaSetF3Method setF3Method() {
          return setF3Method;
        }

        public MetaSetF4Method setF4Method() {
          return setF4Method;
        }

        public MetaSetF5Method setF5Method() {
          return setF5Method;
        }

        public MetaSetF6Method setF6Method() {
          return setF6Method;
        }

        public MetaSetF7Method setF7Method() {
          return setF7Method;
        }

        public MetaSetF8Method setF8Method() {
          return setF8Method;
        }

        public MetaSetF9Method setF9Method() {
          return setF9Method;
        }

        public MetaSetF10Method setF10Method() {
          return setF10Method;
        }

        public MetaSetF11Method setF11Method() {
          return setF11Method;
        }

        public MetaSetF12Method setF12Method() {
          return setF12Method;
        }

        public MetaSetF13Method setF13Method() {
          return setF13Method;
        }

        public MetaSetF14Method setF14Method() {
          return setF14Method;
        }

        public MetaSetF15Method setF15Method() {
          return setF15Method;
        }

        public MetaSetF16Method setF16Method() {
          return setF16Method;
        }

        public MetaSetF17Method setF17Method() {
          return setF17Method;
        }

        public MetaSetF18Method setF18Method() {
          return setF18Method;
        }

        public MetaSetF19Method setF19Method() {
          return setF19Method;
        }

        public MetaSetF20Method setF20Method() {
          return setF20Method;
        }

        public MetaSetF21Method setF21Method() {
          return setF21Method;
        }

        public MetaSetF22Method setF22Method() {
          return setF22Method;
        }

        public MetaSetF23Method setF23Method() {
          return setF23Method;
        }

        public MetaSetF24Method setF24Method() {
          return setF24Method;
        }

        public MetaSetF25Method setF25Method() {
          return setF25Method;
        }

        public MetaSetF26Method setF26Method() {
          return setF26Method;
        }

        public MetaSetF27Method setF27Method() {
          return setF27Method;
        }

        public MetaSetF28Method setF28Method() {
          return setF28Method;
        }

        public MetaSetF29Method setF29Method() {
          return setF29Method;
        }

        public MetaSetF30Method setF30Method() {
          return setF30Method;
        }

        public MetaSetF31Method setF31Method() {
          return setF31Method;
        }

        public MetaSetF32Method setF32Method() {
          return setF32Method;
        }

        public MetaSetF33Method setF33Method() {
          return setF33Method;
        }

        public MetaSetF34Method setF34Method() {
          return setF34Method;
        }

        public MetaSetF35Method setF35Method() {
          return setF35Method;
        }

        public MetaSetF36Method setF36Method() {
          return setF36Method;
        }

        public MetaSetF37Method setF37Method() {
          return setF37Method;
        }

        public MetaSetF38Method setF38Method() {
          return setF38Method;
        }

        public MetaSetF39Method setF39Method() {
          return setF39Method;
        }

        public MetaSetF40Method setF40Method() {
          return setF40Method;
        }

        public MetaSetF41Method setF41Method() {
          return setF41Method;
        }

        public MetaSetGenericFieldMethod setGenericFieldMethod() {
          return setGenericFieldMethod;
        }

        public MetaPublicMethodMethod publicMethodMethod() {
          return publicMethodMethod;
        }

        public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
          return publicAbstractMethodMethod;
        }

        public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
          return isPrivateFieldMethod;
        }

        public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
          return isProtectedFieldMethod;
        }

        public MetaIsPublicFieldMethod isPublicFieldMethod() {
          return isPublicFieldMethod;
        }

        public MetaIsInternalFieldMethod isInternalFieldMethod() {
          return isInternalFieldMethod;
        }

        public MetaGetImportedFieldMethod getImportedFieldMethod() {
          return getImportedFieldMethod;
        }

        public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
          return setPrivateFieldMethod;
        }

        public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
          return setProtectedFieldMethod;
        }

        public MetaSetPublicFieldMethod setPublicFieldMethod() {
          return setPublicFieldMethod;
        }

        public MetaSetInternalFieldMethod setInternalFieldMethod() {
          return setInternalFieldMethod;
        }

        public MetaSetImportedFieldMethod setImportedFieldMethod() {
          return setImportedFieldMethod;
        }

        public MetaMixedJavaModelBuilderClass1 mixedJavaModelBuilderClass() {
          return mixedJavaModelBuilderClass;
        }

        public MetaInnerModelClass1 innerModelClass() {
          return innerModelClass;
        }

        public static final class MetaConstructorConstructor extends MetaConstructor<model.other.MixedJavaModel> {
          private MetaConstructorConstructor() {
            super(metaType(model.other.MixedJavaModel.class));
          }

          @Override
          public model.other.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
            return new model.other.MixedJavaModel();
          }

          @Override
          public model.other.MixedJavaModel invoke() throws Throwable {
            return new model.other.MixedJavaModel();
          }
        }

        public static final class MetaConstructor_1Constructor extends MetaConstructor<model.other.MixedJavaModel> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

          private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(3, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

          private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(4, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(5, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(6, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

          private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(7, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(8, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(9, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(10, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(11, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(12, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(13, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

          private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(14, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(15, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(16, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(17, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(18, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(19, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(20, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(21, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(22, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(23, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(24, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(25, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(26, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(27, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(28, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(29, "f30",metaType(model.other.MixedJavaModel.InnerModel.class)));

          private final MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(30, "f31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class))));

          private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(31, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

          private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(32, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

          private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(33, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

          private final MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(34, "f35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));

          private final MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(35, "f36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(36, "f37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(37, "f38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(38, "f39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))));

          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(39, "f40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(40, "f41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

          private final MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(41, "genericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

          private MetaConstructor_1Constructor() {
            super(metaType(model.other.MixedJavaModel.class));
          }

          @Override
          public model.other.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
            return new model.other.MixedJavaModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(java.util.List<java.lang.String>)(arguments[2]),(java.util.List<?>)(arguments[3]),(java.util.List<? extends java.lang.String>)(arguments[4]),(java.util.List<? super java.lang.String>)(arguments[5]),(boolean[])(arguments[6]),(java.util.List<java.lang.String>[])(arguments[7]),(java.util.List<?>[])(arguments[8]),(java.util.List<? extends java.lang.String>[])(arguments[9]),(java.util.List<? super java.lang.String>[])(arguments[10]),(boolean[][])(arguments[11]),(java.util.List<java.lang.String>[][])(arguments[12]),(java.util.List<?>[][])(arguments[13]),(java.util.List<? extends java.lang.String>[][])(arguments[14]),(java.util.List<? super java.lang.String>[][])(arguments[15]),(java.util.List<boolean[]>)(arguments[16]),(java.util.List<java.lang.String[]>[])(arguments[17]),(java.util.List<? extends java.lang.String[]>[])(arguments[18]),(java.util.List<? super java.lang.String[]>[])(arguments[19]),(java.util.List<java.util.List<java.lang.Boolean>>)(arguments[20]),(java.util.List<java.util.List<java.lang.String>>)(arguments[21]),(java.util.List<java.util.List<?>>)(arguments[22]),(java.util.List<java.util.List<? extends java.lang.String>>)(arguments[23]),(java.util.List<java.util.List<? super java.lang.String>>)(arguments[24]),(java.util.List<java.util.List<boolean[]>>)(arguments[25]),(java.util.List<java.util.List<java.lang.String[]>>)(arguments[26]),(java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[27]),(java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[28]),(model.other.MixedJavaModel.InnerModel)(arguments[29]),(java.util.List<model.other.MixedJavaModel.InnerModel>)(arguments[30]),(java.util.Map<java.lang.String, java.lang.String>)(arguments[31]),(java.util.Map<?, java.lang.String>)(arguments[32]),(java.util.Map<java.lang.String, ?>)(arguments[33]),(model.other.MixedJavaModel.Enum)(arguments[34]),(java.util.List<model.other.MixedJavaModel.Enum>)(arguments[35]),(model.other.MixedJavaModel.Enum[])(arguments[36]),(java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>)(arguments[37]),(java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>)(arguments[38]),(java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>)(arguments[39]),(java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>)(arguments[40]),(model.other.MixedJavaModel.GenericClass<?>)(arguments[41]));
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
            return f3Parameter;
          }

          public MetaParameter<java.util.List<?>> f4Parameter() {
            return f4Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
            return f5Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
            return f6Parameter;
          }

          public MetaParameter<boolean[]> f7Parameter() {
            return f7Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
            return f8Parameter;
          }

          public MetaParameter<java.util.List<?>[]> f9Parameter() {
            return f9Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
            return f10Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
            return f11Parameter;
          }

          public MetaParameter<boolean[][]> f12Parameter() {
            return f12Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
            return f13Parameter;
          }

          public MetaParameter<java.util.List<?>[][]> f14Parameter() {
            return f14Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
            return f15Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
            return f16Parameter;
          }

          public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
            return f17Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
            return f18Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
            return f19Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
            return f20Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
            return f21Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
            return f22Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
            return f23Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
              ) {
            return f24Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
              ) {
            return f25Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
            return f26Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
            return f27Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
              ) {
            return f28Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
              ) {
            return f29Parameter;
          }

          public MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter() {
            return f30Parameter;
          }

          public MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter(
              ) {
            return f31Parameter;
          }

          public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
            return f32Parameter;
          }

          public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
            return f33Parameter;
          }

          public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
            return f34Parameter;
          }

          public MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter() {
            return f35Parameter;
          }

          public MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter() {
            return f36Parameter;
          }

          public MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter() {
            return f37Parameter;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter(
              ) {
            return f38Parameter;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter(
              ) {
            return f39Parameter;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter(
              ) {
            return f40Parameter;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter(
              ) {
            return f41Parameter;
          }

          public MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
            return genericFieldParameter;
          }
        }

        public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
          private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

          private MetaStaticMethodMethod() {
            super("staticMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.other.MixedJavaModel.staticMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
            return model.other.MixedJavaModel.staticMethod((int)(argument));
          }

          public MetaParameter<Integer> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaStaticMethod_1Method extends StaticMetaMethod<java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaStaticMethod_1Method() {
            super("staticMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.other.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
            return model.other.MixedJavaModel.staticMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaStaticMethod_2Method extends StaticMetaMethod<Integer> {
          private final MetaParameter<java.lang.String> argument1Parameter = register(new MetaParameter<>(0, "argument1",metaType(java.lang.String.class)));

          private final MetaParameter<int[]> argument2Parameter = register(new MetaParameter<>(1, "argument2",metaArray(int[].class, int[]::new, metaType(int.class))));

          private MetaStaticMethod_2Method() {
            super("staticMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.other.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]),(int[])(arguments[1]));
          }

          public MetaParameter<java.lang.String> argument1Parameter() {
            return argument1Parameter;
          }

          public MetaParameter<int[]> argument2Parameter() {
            return argument2Parameter;
          }
        }

        public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Integer> {
          private final MetaParameter<Integer> p1Parameter = register(new MetaParameter<>(0, "p1",metaType(int.class)));

          private MetaInstanceMethodMethod() {
            super("instanceMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.instanceMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.instanceMethod((int)(argument));
          }

          public MetaParameter<Integer> p1Parameter() {
            return p1Parameter;
          }
        }

        public static final class MetaInstanceMethod_1Method extends InstanceMetaMethod<model.other.MixedJavaModel, Integer> {
          private final MetaParameter<int[]> p1Parameter = register(new MetaParameter<>(0, "p1",metaArray(int[].class, int[]::new, metaType(int.class))));

          private MetaInstanceMethod_1Method() {
            super("instanceMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.instanceMethod((int[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.instanceMethod((int[])(argument));
          }

          public MetaParameter<int[]> p1Parameter() {
            return p1Parameter;
          }
        }

        public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaImplementableMethodMethod() {
            super("implementableMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.implementableMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.implementableMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaIsF1Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaIsF1Method() {
            super("isF1",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isF1();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.isF1();
          }
        }

        public static final class MetaGetF2Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.String> {
          private MetaGetF2Method() {
            super("getF2",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF2();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF2();
          }
        }

        public static final class MetaGetF3Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.lang.String>> {
          private MetaGetF3Method() {
            super("getF3",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF3();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF3();
          }
        }

        public static final class MetaGetF4Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<?>> {
          private MetaGetF4Method() {
            super("getF4",metaType(java.util.List.class,metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF4();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF4();
          }
        }

        public static final class MetaGetF5Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? extends java.lang.String>> {
          private MetaGetF5Method() {
            super("getF5",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF5();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF5();
          }
        }

        public static final class MetaGetF6Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? super java.lang.String>> {
          private MetaGetF6Method() {
            super("getF6",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF6();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF6();
          }
        }

        public static final class MetaGetF7Method extends InstanceMetaMethod<model.other.MixedJavaModel, boolean[]> {
          private MetaGetF7Method() {
            super("getF7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF7();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF7();
          }
        }

        public static final class MetaGetF8Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.lang.String>[]> {
          private MetaGetF8Method() {
            super("getF8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF8();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF8();
          }
        }

        public static final class MetaGetF9Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<?>[]> {
          private MetaGetF9Method() {
            super("getF9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF9();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF9();
          }
        }

        public static final class MetaGetF10Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? extends java.lang.String>[]> {
          private MetaGetF10Method() {
            super("getF10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF10();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF10();
          }
        }

        public static final class MetaGetF11Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? super java.lang.String>[]> {
          private MetaGetF11Method() {
            super("getF11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF11();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF11();
          }
        }

        public static final class MetaGetF12Method extends InstanceMetaMethod<model.other.MixedJavaModel, boolean[][]> {
          private MetaGetF12Method() {
            super("getF12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF12();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF12();
          }
        }

        public static final class MetaGetF13Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.lang.String>[][]> {
          private MetaGetF13Method() {
            super("getF13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF13();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF13();
          }
        }

        public static final class MetaGetF14Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<?>[][]> {
          private MetaGetF14Method() {
            super("getF14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF14();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF14();
          }
        }

        public static final class MetaGetF15Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? extends java.lang.String>[][]> {
          private MetaGetF15Method() {
            super("getF15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF15();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF15();
          }
        }

        public static final class MetaGetF16Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? super java.lang.String>[][]> {
          private MetaGetF16Method() {
            super("getF16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF16();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF16();
          }
        }

        public static final class MetaGetF17Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<boolean[]>> {
          private MetaGetF17Method() {
            super("getF17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF17();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF17();
          }
        }

        public static final class MetaGetF18Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.lang.String[]>[]> {
          private MetaGetF18Method() {
            super("getF18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF18();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF18();
          }
        }

        public static final class MetaGetF19Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? extends java.lang.String[]>[]> {
          private MetaGetF19Method() {
            super("getF19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF19();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF19();
          }
        }

        public static final class MetaGetF20Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<? super java.lang.String[]>[]> {
          private MetaGetF20Method() {
            super("getF20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF20();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF20();
          }
        }

        public static final class MetaGetF21Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<java.lang.Boolean>>> {
          private MetaGetF21Method() {
            super("getF21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF21();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF21();
          }
        }

        public static final class MetaGetF22Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<java.lang.String>>> {
          private MetaGetF22Method() {
            super("getF22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF22();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF22();
          }
        }

        public static final class MetaGetF23Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<?>>> {
          private MetaGetF23Method() {
            super("getF23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF23();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF23();
          }
        }

        public static final class MetaGetF24Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String>>> {
          private MetaGetF24Method() {
            super("getF24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF24();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF24();
          }
        }

        public static final class MetaGetF25Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String>>> {
          private MetaGetF25Method() {
            super("getF25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF25();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF25();
          }
        }

        public static final class MetaGetF26Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<boolean[]>>> {
          private MetaGetF26Method() {
            super("getF26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF26();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF26();
          }
        }

        public static final class MetaGetF27Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<java.lang.String[]>>> {
          private MetaGetF27Method() {
            super("getF27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF27();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF27();
          }
        }

        public static final class MetaGetF28Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String[]>>> {
          private MetaGetF28Method() {
            super("getF28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF28();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF28();
          }
        }

        public static final class MetaGetF29Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String[]>>> {
          private MetaGetF29Method() {
            super("getF29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF29();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF29();
          }
        }

        public static final class MetaGetF30Method extends InstanceMetaMethod<model.other.MixedJavaModel, model.other.MixedJavaModel.InnerModel> {
          private MetaGetF30Method() {
            super("getF30",metaType(model.other.MixedJavaModel.InnerModel.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF30();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF30();
          }
        }

        public static final class MetaGetF31Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<model.other.MixedJavaModel.InnerModel>> {
          private MetaGetF31Method() {
            super("getF31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF31();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF31();
          }
        }

        public static final class MetaGetF32Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<java.lang.String, java.lang.String>> {
          private MetaGetF32Method() {
            super("getF32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF32();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF32();
          }
        }

        public static final class MetaGetF33Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<?, java.lang.String>> {
          private MetaGetF33Method() {
            super("getF33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF33();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF33();
          }
        }

        public static final class MetaGetF34Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<java.lang.String, ?>> {
          private MetaGetF34Method() {
            super("getF34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF34();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF34();
          }
        }

        public static final class MetaGetF35Method extends InstanceMetaMethod<model.other.MixedJavaModel, model.other.MixedJavaModel.Enum> {
          private MetaGetF35Method() {
            super("getF35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF35();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF35();
          }
        }

        public static final class MetaGetF36Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.List<model.other.MixedJavaModel.Enum>> {
          private MetaGetF36Method() {
            super("getF36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF36();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF36();
          }
        }

        public static final class MetaGetF37Method extends InstanceMetaMethod<model.other.MixedJavaModel, model.other.MixedJavaModel.Enum[]> {
          private MetaGetF37Method() {
            super("getF37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF37();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF37();
          }
        }

        public static final class MetaGetF38Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> {
          private MetaGetF38Method() {
            super("getF38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF38();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF38();
          }
        }

        public static final class MetaGetF39Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> {
          private MetaGetF39Method() {
            super("getF39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF39();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF39();
          }
        }

        public static final class MetaGetF40Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> {
          private MetaGetF40Method() {
            super("getF40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF40();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF40();
          }
        }

        public static final class MetaGetF41Method extends InstanceMetaMethod<model.other.MixedJavaModel, java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> {
          private MetaGetF41Method() {
            super("getF41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF41();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getF41();
          }
        }

        public static final class MetaGetGenericFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, model.other.MixedJavaModel.GenericClass<?>> {
          private MetaGetGenericFieldMethod() {
            super("getGenericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getGenericField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getGenericField();
          }
        }

        public static final class MetaSetF1Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private MetaSetF1Method() {
            super("setF1",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF1((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF1((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }
        }

        public static final class MetaSetF2Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

          private MetaSetF2Method() {
            super("setF2",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF2((java.lang.String)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF2((java.lang.String)(argument));
            return null;
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }
        }

        public static final class MetaSetF3Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF3Method() {
            super("setF3",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF3((java.util.List<java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF3((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
            return f3Parameter;
          }
        }

        public static final class MetaSetF4Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

          private MetaSetF4Method() {
            super("setF4",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF4((java.util.List<?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF4((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>> f4Parameter() {
            return f4Parameter;
          }
        }

        public static final class MetaSetF5Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF5Method() {
            super("setF5",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF5((java.util.List<? extends java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF5((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
            return f5Parameter;
          }
        }

        public static final class MetaSetF6Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF6Method() {
            super("setF6",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF6((java.util.List<? super java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF6((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
            return f6Parameter;
          }
        }

        public static final class MetaSetF7Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

          private MetaSetF7Method() {
            super("setF7",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF7((boolean[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF7((boolean[])(argument));
            return null;
          }

          public MetaParameter<boolean[]> f7Parameter() {
            return f7Parameter;
          }
        }

        public static final class MetaSetF8Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF8Method() {
            super("setF8",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF8((java.util.List<java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF8((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
            return f8Parameter;
          }
        }

        public static final class MetaSetF9Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaSetF9Method() {
            super("setF9",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF9((java.util.List<?>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF9((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>[]> f9Parameter() {
            return f9Parameter;
          }
        }

        public static final class MetaSetF10Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF10Method() {
            super("setF10",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF10((java.util.List<? extends java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF10((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
            return f10Parameter;
          }
        }

        public static final class MetaSetF11Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF11Method() {
            super("setF11",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF11((java.util.List<? super java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF11((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
            return f11Parameter;
          }
        }

        public static final class MetaSetF12Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaSetF12Method() {
            super("setF12",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF12((boolean[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF12((boolean[][])(argument));
            return null;
          }

          public MetaParameter<boolean[][]> f12Parameter() {
            return f12Parameter;
          }
        }

        public static final class MetaSetF13Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF13Method() {
            super("setF13",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF13((java.util.List<java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF13((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
            return f13Parameter;
          }
        }

        public static final class MetaSetF14Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

          private MetaSetF14Method() {
            super("setF14",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF14((java.util.List<?>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF14((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>[][]> f14Parameter() {
            return f14Parameter;
          }
        }

        public static final class MetaSetF15Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF15Method() {
            super("setF15",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF15((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
            return f15Parameter;
          }
        }

        public static final class MetaSetF16Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF16Method() {
            super("setF16",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF16((java.util.List<? super java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF16((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
            return f16Parameter;
          }
        }

        public static final class MetaSetF17Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaSetF17Method() {
            super("setF17",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF17((java.util.List<boolean[]>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF17((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
            return f17Parameter;
          }
        }

        public static final class MetaSetF18Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF18Method() {
            super("setF18",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF18((java.util.List<java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF18((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
            return f18Parameter;
          }
        }

        public static final class MetaSetF19Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF19Method() {
            super("setF19",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF19((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
            return f19Parameter;
          }
        }

        public static final class MetaSetF20Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF20Method() {
            super("setF20",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF20((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
            return f20Parameter;
          }
        }

        public static final class MetaSetF21Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

          private MetaSetF21Method() {
            super("setF21",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF21((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
            return f21Parameter;
          }
        }

        public static final class MetaSetF22Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF22Method() {
            super("setF22",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF22((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
            return f22Parameter;
          }
        }

        public static final class MetaSetF23Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaSetF23Method() {
            super("setF23",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF23((java.util.List<java.util.List<?>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF23((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
            return f23Parameter;
          }
        }

        public static final class MetaSetF24Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF24Method() {
            super("setF24",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF24((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
              ) {
            return f24Parameter;
          }
        }

        public static final class MetaSetF25Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF25Method() {
            super("setF25",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF25((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
              ) {
            return f25Parameter;
          }
        }

        public static final class MetaSetF26Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

          private MetaSetF26Method() {
            super("setF26",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF26((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
            return f26Parameter;
          }
        }

        public static final class MetaSetF27Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF27Method() {
            super("setF27",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF27((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
            return f27Parameter;
          }
        }

        public static final class MetaSetF28Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF28Method() {
            super("setF28",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF28((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
              ) {
            return f28Parameter;
          }
        }

        public static final class MetaSetF29Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF29Method() {
            super("setF29",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF29((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
              ) {
            return f29Parameter;
          }
        }

        public static final class MetaSetF30Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.other.MixedJavaModel.InnerModel.class)));

          private MetaSetF30Method() {
            super("setF30",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF30((model.other.MixedJavaModel.InnerModel)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF30((model.other.MixedJavaModel.InnerModel)(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter() {
            return f30Parameter;
          }
        }

        public static final class MetaSetF31Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class))));

          private MetaSetF31Method() {
            super("setF31",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF31((java.util.List<model.other.MixedJavaModel.InnerModel>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF31((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter(
              ) {
            return f31Parameter;
          }
        }

        public static final class MetaSetF32Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

          private MetaSetF32Method() {
            super("setF32",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF32((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
            return f32Parameter;
          }
        }

        public static final class MetaSetF33Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

          private MetaSetF33Method() {
            super("setF33",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF33((java.util.Map<?, java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF33((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
            return f33Parameter;
          }
        }

        public static final class MetaSetF34Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

          private MetaSetF34Method() {
            super("setF34",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF34((java.util.Map<java.lang.String, ?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF34((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
            return f34Parameter;
          }
        }

        public static final class MetaSetF35Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));

          private MetaSetF35Method() {
            super("setF35",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF35((model.other.MixedJavaModel.Enum)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF35((model.other.MixedJavaModel.Enum)(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter() {
            return f35Parameter;
          }
        }

        public static final class MetaSetF36Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private MetaSetF36Method() {
            super("setF36",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF36((java.util.List<model.other.MixedJavaModel.Enum>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF36((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter() {
            return f36Parameter;
          }
        }

        public static final class MetaSetF37Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private MetaSetF37Method() {
            super("setF37",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF37((model.other.MixedJavaModel.Enum[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF37((model.other.MixedJavaModel.Enum[])(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter() {
            return f37Parameter;
          }
        }

        public static final class MetaSetF38Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

          private MetaSetF38Method() {
            super("setF38",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF38((java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF38((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter(
              ) {
            return f38Parameter;
          }
        }

        public static final class MetaSetF39Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))));

          private MetaSetF39Method() {
            super("setF39",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF39((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF39((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter(
              ) {
            return f39Parameter;
          }
        }

        public static final class MetaSetF40Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

          private MetaSetF40Method() {
            super("setF40",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF40((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF40((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter(
              ) {
            return f40Parameter;
          }
        }

        public static final class MetaSetF41Method extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

          private MetaSetF41Method() {
            super("setF41",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF41((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF41((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter(
              ) {
            return f41Parameter;
          }
        }

        public static final class MetaSetGenericFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

          private MetaSetGenericFieldMethod() {
            super("setGenericField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setGenericField((model.other.MixedJavaModel.GenericClass<?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setGenericField((model.other.MixedJavaModel.GenericClass)(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
            return genericFieldParameter;
          }
        }

        public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaPublicMethodMethod() {
            super("publicMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicMethod();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.publicMethod();
          }
        }

        public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaPublicAbstractMethodMethod() {
            super("publicAbstractMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicAbstractMethod();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.publicAbstractMethod();
          }
        }

        public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaIsPrivateFieldMethod() {
            super("isPrivateField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPrivateField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.isPrivateField();
          }
        }

        public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaIsProtectedFieldMethod() {
            super("isProtectedField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isProtectedField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.isProtectedField();
          }
        }

        public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaIsPublicFieldMethod() {
            super("isPublicField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPublicField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.isPublicField();
          }
        }

        public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, java.lang.Boolean> {
          private MetaIsInternalFieldMethod() {
            super("isInternalField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isInternalField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.isInternalField();
          }
        }

        public static final class MetaGetImportedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, model.other.MixedJavaInterface> {
          private MetaGetImportedFieldMethod() {
            super("getImportedField",metaType(model.other.MixedJavaInterface.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getImportedField();
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance) throws Throwable {
            return instance.getImportedField();
          }
        }

        public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

          private MetaSetPrivateFieldMethod() {
            super("setPrivateField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPrivateField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setPrivateField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> privateFieldParameter() {
            return privateFieldParameter;
          }
        }

        public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

          private MetaSetProtectedFieldMethod() {
            super("setProtectedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setProtectedField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setProtectedField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
            return protectedFieldParameter;
          }
        }

        public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

          private MetaSetPublicFieldMethod() {
            super("setPublicField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPublicField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setPublicField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> publicFieldParameter() {
            return publicFieldParameter;
          }
        }

        public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

          private MetaSetInternalFieldMethod() {
            super("setInternalField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setInternalField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setInternalField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> internalFieldParameter() {
            return internalFieldParameter;
          }
        }

        public static final class MetaSetImportedFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel, Void> {
          private final MetaParameter<model.other.MixedJavaInterface> importedFieldParameter = register(new MetaParameter<>(0, "importedField",metaType(model.other.MixedJavaInterface.class)));

          private MetaSetImportedFieldMethod() {
            super("setImportedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setImportedField((model.other.MixedJavaInterface)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.other.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setImportedField((model.other.MixedJavaInterface)(argument));
            return null;
          }

          public MetaParameter<model.other.MixedJavaInterface> importedFieldParameter() {
            return importedFieldParameter;
          }
        }

        public static final class MetaMixedJavaModelBuilderClass1 extends MetaClass<model.other.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

          private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

          private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

          private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

          private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

          private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

          private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

          private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

          private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

          private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<model.other.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.other.MixedJavaModel.InnerModel.class),false));

          private final MetaField<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class)),false));

          private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

          private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

          private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

          private final MetaField<model.other.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),false));

          private final MetaField<java.util.List<model.other.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<model.other.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))),false));

          private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))),false));

          private final MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))),false));

          private final MetaField<model.other.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

          private final MetaF1Method f1Method = register(new MetaF1Method());

          private final MetaF2Method f2Method = register(new MetaF2Method());

          private final MetaF3Method f3Method = register(new MetaF3Method());

          private final MetaF4Method f4Method = register(new MetaF4Method());

          private final MetaF5Method f5Method = register(new MetaF5Method());

          private final MetaF6Method f6Method = register(new MetaF6Method());

          private final MetaF7Method f7Method = register(new MetaF7Method());

          private final MetaF8Method f8Method = register(new MetaF8Method());

          private final MetaF9Method f9Method = register(new MetaF9Method());

          private final MetaF10Method f10Method = register(new MetaF10Method());

          private final MetaF11Method f11Method = register(new MetaF11Method());

          private final MetaF12Method f12Method = register(new MetaF12Method());

          private final MetaF13Method f13Method = register(new MetaF13Method());

          private final MetaF14Method f14Method = register(new MetaF14Method());

          private final MetaF15Method f15Method = register(new MetaF15Method());

          private final MetaF16Method f16Method = register(new MetaF16Method());

          private final MetaF17Method f17Method = register(new MetaF17Method());

          private final MetaF18Method f18Method = register(new MetaF18Method());

          private final MetaF19Method f19Method = register(new MetaF19Method());

          private final MetaF20Method f20Method = register(new MetaF20Method());

          private final MetaF21Method f21Method = register(new MetaF21Method());

          private final MetaF22Method f22Method = register(new MetaF22Method());

          private final MetaF23Method f23Method = register(new MetaF23Method());

          private final MetaF24Method f24Method = register(new MetaF24Method());

          private final MetaF25Method f25Method = register(new MetaF25Method());

          private final MetaF26Method f26Method = register(new MetaF26Method());

          private final MetaF27Method f27Method = register(new MetaF27Method());

          private final MetaF28Method f28Method = register(new MetaF28Method());

          private final MetaF29Method f29Method = register(new MetaF29Method());

          private final MetaF30Method f30Method = register(new MetaF30Method());

          private final MetaF31Method f31Method = register(new MetaF31Method());

          private final MetaF32Method f32Method = register(new MetaF32Method());

          private final MetaF33Method f33Method = register(new MetaF33Method());

          private final MetaF34Method f34Method = register(new MetaF34Method());

          private final MetaF35Method f35Method = register(new MetaF35Method());

          private final MetaF36Method f36Method = register(new MetaF36Method());

          private final MetaF37Method f37Method = register(new MetaF37Method());

          private final MetaF38Method f38Method = register(new MetaF38Method());

          private final MetaF39Method f39Method = register(new MetaF39Method());

          private final MetaF40Method f40Method = register(new MetaF40Method());

          private final MetaF41Method f41Method = register(new MetaF41Method());

          private final MetaGenericFieldMethod genericFieldMethod = register(new MetaGenericFieldMethod());

          private final MetaBuildMethod buildMethod = register(new MetaBuildMethod());

          private MetaMixedJavaModelBuilderClass1() {
            super(metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          public MetaField<java.lang.Boolean> f1Field() {
            return f1Field;
          }

          public MetaField<java.lang.String> f2Field() {
            return f2Field;
          }

          public MetaField<java.util.List<java.lang.String>> f3Field() {
            return f3Field;
          }

          public MetaField<java.util.List<?>> f4Field() {
            return f4Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
            return f5Field;
          }

          public MetaField<java.util.List<? super java.lang.String>> f6Field() {
            return f6Field;
          }

          public MetaField<boolean[]> f7Field() {
            return f7Field;
          }

          public MetaField<java.util.List<java.lang.String>[]> f8Field() {
            return f8Field;
          }

          public MetaField<java.util.List<?>[]> f9Field() {
            return f9Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
            return f10Field;
          }

          public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
            return f11Field;
          }

          public MetaField<boolean[][]> f12Field() {
            return f12Field;
          }

          public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
            return f13Field;
          }

          public MetaField<java.util.List<?>[][]> f14Field() {
            return f14Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
            return f15Field;
          }

          public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
            return f16Field;
          }

          public MetaField<java.util.List<boolean[]>> f17Field() {
            return f17Field;
          }

          public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
            return f18Field;
          }

          public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
            return f19Field;
          }

          public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
            return f20Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
            return f21Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
            return f22Field;
          }

          public MetaField<java.util.List<java.util.List<?>>> f23Field() {
            return f23Field;
          }

          public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
            return f24Field;
          }

          public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
            return f25Field;
          }

          public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
            return f26Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
            return f27Field;
          }

          public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field(
              ) {
            return f28Field;
          }

          public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
            return f29Field;
          }

          public MetaField<model.other.MixedJavaModel.InnerModel> f30Field() {
            return f30Field;
          }

          public MetaField<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Field() {
            return f31Field;
          }

          public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
            return f32Field;
          }

          public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
            return f33Field;
          }

          public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
            return f34Field;
          }

          public MetaField<model.other.MixedJavaModel.Enum> f35Field() {
            return f35Field;
          }

          public MetaField<java.util.List<model.other.MixedJavaModel.Enum>> f36Field() {
            return f36Field;
          }

          public MetaField<model.other.MixedJavaModel.Enum[]> f37Field() {
            return f37Field;
          }

          public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Field(
              ) {
            return f38Field;
          }

          public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Field(
              ) {
            return f39Field;
          }

          public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Field(
              ) {
            return f40Field;
          }

          public MetaField<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Field(
              ) {
            return f41Field;
          }

          public MetaField<model.other.MixedJavaModel.GenericClass<?>> genericFieldField() {
            return genericFieldField;
          }

          public MetaF1Method f1Method() {
            return f1Method;
          }

          public MetaF2Method f2Method() {
            return f2Method;
          }

          public MetaF3Method f3Method() {
            return f3Method;
          }

          public MetaF4Method f4Method() {
            return f4Method;
          }

          public MetaF5Method f5Method() {
            return f5Method;
          }

          public MetaF6Method f6Method() {
            return f6Method;
          }

          public MetaF7Method f7Method() {
            return f7Method;
          }

          public MetaF8Method f8Method() {
            return f8Method;
          }

          public MetaF9Method f9Method() {
            return f9Method;
          }

          public MetaF10Method f10Method() {
            return f10Method;
          }

          public MetaF11Method f11Method() {
            return f11Method;
          }

          public MetaF12Method f12Method() {
            return f12Method;
          }

          public MetaF13Method f13Method() {
            return f13Method;
          }

          public MetaF14Method f14Method() {
            return f14Method;
          }

          public MetaF15Method f15Method() {
            return f15Method;
          }

          public MetaF16Method f16Method() {
            return f16Method;
          }

          public MetaF17Method f17Method() {
            return f17Method;
          }

          public MetaF18Method f18Method() {
            return f18Method;
          }

          public MetaF19Method f19Method() {
            return f19Method;
          }

          public MetaF20Method f20Method() {
            return f20Method;
          }

          public MetaF21Method f21Method() {
            return f21Method;
          }

          public MetaF22Method f22Method() {
            return f22Method;
          }

          public MetaF23Method f23Method() {
            return f23Method;
          }

          public MetaF24Method f24Method() {
            return f24Method;
          }

          public MetaF25Method f25Method() {
            return f25Method;
          }

          public MetaF26Method f26Method() {
            return f26Method;
          }

          public MetaF27Method f27Method() {
            return f27Method;
          }

          public MetaF28Method f28Method() {
            return f28Method;
          }

          public MetaF29Method f29Method() {
            return f29Method;
          }

          public MetaF30Method f30Method() {
            return f30Method;
          }

          public MetaF31Method f31Method() {
            return f31Method;
          }

          public MetaF32Method f32Method() {
            return f32Method;
          }

          public MetaF33Method f33Method() {
            return f33Method;
          }

          public MetaF34Method f34Method() {
            return f34Method;
          }

          public MetaF35Method f35Method() {
            return f35Method;
          }

          public MetaF36Method f36Method() {
            return f36Method;
          }

          public MetaF37Method f37Method() {
            return f37Method;
          }

          public MetaF38Method f38Method() {
            return f38Method;
          }

          public MetaF39Method f39Method() {
            return f39Method;
          }

          public MetaF40Method f40Method() {
            return f40Method;
          }

          public MetaF41Method f41Method() {
            return f41Method;
          }

          public MetaGenericFieldMethod genericFieldMethod() {
            return genericFieldMethod;
          }

          public MetaBuildMethod buildMethod() {
            return buildMethod;
          }

          public static final class MetaF1Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

            private MetaF1Method() {
              super("f1",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f1((boolean)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f1((boolean)(argument));
            }

            public MetaParameter<java.lang.Boolean> f1Parameter() {
              return f1Parameter;
            }
          }

          public static final class MetaF2Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

            private MetaF2Method() {
              super("f2",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f2((java.lang.String)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f2((java.lang.String)(argument));
            }

            public MetaParameter<java.lang.String> f2Parameter() {
              return f2Parameter;
            }
          }

          public static final class MetaF3Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF3Method() {
              super("f3",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f3((java.util.List<java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f3((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
              return f3Parameter;
            }
          }

          public static final class MetaF4Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

            private MetaF4Method() {
              super("f4",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f4((java.util.List<?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f4((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<?>> f4Parameter() {
              return f4Parameter;
            }
          }

          public static final class MetaF5Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF5Method() {
              super("f5",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f5((java.util.List<? extends java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f5((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
              return f5Parameter;
            }
          }

          public static final class MetaF6Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF6Method() {
              super("f6",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f6((java.util.List<? super java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f6((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
              return f6Parameter;
            }
          }

          public static final class MetaF7Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

            private MetaF7Method() {
              super("f7",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f7((boolean[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f7((boolean[])(argument));
            }

            public MetaParameter<boolean[]> f7Parameter() {
              return f7Parameter;
            }
          }

          public static final class MetaF8Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF8Method() {
              super("f8",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f8((java.util.List<java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f8((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
              return f8Parameter;
            }
          }

          public static final class MetaF9Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

            private MetaF9Method() {
              super("f9",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f9((java.util.List<?>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f9((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<?>[]> f9Parameter() {
              return f9Parameter;
            }
          }

          public static final class MetaF10Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF10Method() {
              super("f10",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f10((java.util.List<? extends java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f10((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
              return f10Parameter;
            }
          }

          public static final class MetaF11Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF11Method() {
              super("f11",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f11((java.util.List<? super java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f11((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
              return f11Parameter;
            }
          }

          public static final class MetaF12Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

            private MetaF12Method() {
              super("f12",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f12((boolean[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f12((boolean[][])(argument));
            }

            public MetaParameter<boolean[][]> f12Parameter() {
              return f12Parameter;
            }
          }

          public static final class MetaF13Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF13Method() {
              super("f13",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f13((java.util.List<java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f13((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
              return f13Parameter;
            }
          }

          public static final class MetaF14Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

            private MetaF14Method() {
              super("f14",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f14((java.util.List<?>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f14((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<?>[][]> f14Parameter() {
              return f14Parameter;
            }
          }

          public static final class MetaF15Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF15Method() {
              super("f15",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f15((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
              return f15Parameter;
            }
          }

          public static final class MetaF16Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF16Method() {
              super("f16",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f16((java.util.List<? super java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f16((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
              return f16Parameter;
            }
          }

          public static final class MetaF17Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

            private MetaF17Method() {
              super("f17",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f17((java.util.List<boolean[]>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f17((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
              return f17Parameter;
            }
          }

          public static final class MetaF18Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF18Method() {
              super("f18",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f18((java.util.List<java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f18((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
              return f18Parameter;
            }
          }

          public static final class MetaF19Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF19Method() {
              super("f19",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f19((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
              return f19Parameter;
            }
          }

          public static final class MetaF20Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF20Method() {
              super("f20",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f20((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
              return f20Parameter;
            }
          }

          public static final class MetaF21Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

            private MetaF21Method() {
              super("f21",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f21((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
              return f21Parameter;
            }
          }

          public static final class MetaF22Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF22Method() {
              super("f22",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f22((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
              return f22Parameter;
            }
          }

          public static final class MetaF23Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

            private MetaF23Method() {
              super("f23",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f23((java.util.List<java.util.List<?>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f23((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
              return f23Parameter;
            }
          }

          public static final class MetaF24Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF24Method() {
              super("f24",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f24((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
                ) {
              return f24Parameter;
            }
          }

          public static final class MetaF25Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF25Method() {
              super("f25",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f25((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
                ) {
              return f25Parameter;
            }
          }

          public static final class MetaF26Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

            private MetaF26Method() {
              super("f26",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f26((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
              return f26Parameter;
            }
          }

          public static final class MetaF27Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF27Method() {
              super("f27",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f27((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter(
                ) {
              return f27Parameter;
            }
          }

          public static final class MetaF28Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF28Method() {
              super("f28",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f28((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
                ) {
              return f28Parameter;
            }
          }

          public static final class MetaF29Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF29Method() {
              super("f29",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f29((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
                ) {
              return f29Parameter;
            }
          }

          public static final class MetaF30Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.other.MixedJavaModel.InnerModel.class)));

            private MetaF30Method() {
              super("f30",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f30((model.other.MixedJavaModel.InnerModel)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f30((model.other.MixedJavaModel.InnerModel)(argument));
            }

            public MetaParameter<model.other.MixedJavaModel.InnerModel> f30Parameter() {
              return f30Parameter;
            }
          }

          public static final class MetaF31Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.other.MixedJavaModel.InnerModel.class))));

            private MetaF31Method() {
              super("f31",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f31((java.util.List<model.other.MixedJavaModel.InnerModel>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f31((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<model.other.MixedJavaModel.InnerModel>> f31Parameter(
                ) {
              return f31Parameter;
            }
          }

          public static final class MetaF32Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

            private MetaF32Method() {
              super("f32",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f32((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
              return f32Parameter;
            }
          }

          public static final class MetaF33Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

            private MetaF33Method() {
              super("f33",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f33((java.util.Map<?, java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f33((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
              return f33Parameter;
            }
          }

          public static final class MetaF34Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

            private MetaF34Method() {
              super("f34",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f34((java.util.Map<java.lang.String, ?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f34((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
              return f34Parameter;
            }
          }

          public static final class MetaF35Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)));

            private MetaF35Method() {
              super("f35",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f35((model.other.MixedJavaModel.Enum)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f35((model.other.MixedJavaModel.Enum)(argument));
            }

            public MetaParameter<model.other.MixedJavaModel.Enum> f35Parameter() {
              return f35Parameter;
            }
          }

          public static final class MetaF36Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

            private MetaF36Method() {
              super("f36",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f36((java.util.List<model.other.MixedJavaModel.Enum>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f36((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<model.other.MixedJavaModel.Enum>> f36Parameter() {
              return f36Parameter;
            }
          }

          public static final class MetaF37Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

            private MetaF37Method() {
              super("f37",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f37((model.other.MixedJavaModel.Enum[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f37((model.other.MixedJavaModel.Enum[])(argument));
            }

            public MetaParameter<model.other.MixedJavaModel.Enum[]> f37Parameter() {
              return f37Parameter;
            }
          }

          public static final class MetaF38Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))));

            private MetaF38Method() {
              super("f38",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f38((java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f38((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, model.other.MixedJavaModel.Enum>> f38Parameter(
                ) {
              return f38Parameter;
            }
          }

          public static final class MetaF39Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf)))));

            private MetaF39Method() {
              super("f39",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f39((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f39((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum>>> f39Parameter(
                ) {
              return f39Parameter;
            }
          }

          public static final class MetaF40Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

            private MetaF40Method() {
              super("f40",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f40((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f40((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<model.other.MixedJavaModel.Enum[]>>> f40Parameter(
                ) {
              return f40Parameter;
            }
          }

          public static final class MetaF41Method extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.other.MixedJavaModel.Enum[].class, model.other.MixedJavaModel.Enum[]::new, metaEnum(model.other.MixedJavaModel.Enum.class, model.other.MixedJavaModel.Enum::valueOf))))));

            private MetaF41Method() {
              super("f41",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f41((java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f41((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.other.MixedJavaModel.Enum, java.util.List<? extends model.other.MixedJavaModel.Enum[]>>> f41Parameter(
                ) {
              return f41Parameter;
            }
          }

          public static final class MetaGenericFieldMethod extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.other.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

            private MetaGenericFieldMethod() {
              super("genericField",metaType(model.other.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.genericField((model.other.MixedJavaModel.GenericClass<?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.genericField((model.other.MixedJavaModel.GenericClass)(argument));
            }

            public MetaParameter<model.other.MixedJavaModel.GenericClass<?>> genericFieldParameter(
                ) {
              return genericFieldParameter;
            }
          }

          public static final class MetaBuildMethod extends InstanceMetaMethod<model.other.MixedJavaModel.MixedJavaModelBuilder, model.other.MixedJavaModel> {
            private MetaBuildMethod() {
              super("build",metaType(model.other.MixedJavaModel.class));
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.build();
            }

            @Override
            public java.lang.Object invoke(
                model.other.MixedJavaModel.MixedJavaModelBuilder instance) throws Throwable {
              return instance.build();
            }
          }
        }

        public static final class MetaInnerModelClass1 extends MetaClass<model.other.MixedJavaModel.InnerModel> {
          private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

          private final MetaField<Long> serialVersionUIDField = register(new MetaField<>("serialVersionUID",metaType(long.class),true));

          private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

          private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

          private final MetaField<Integer> f3Field = register(new MetaField<>("f3",metaType(int.class),false));

          private final MetaField<model.other.MixedJavaModel> f5Field = register(new MetaField<>("f5",metaType(model.other.MixedJavaModel.class),false));

          private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

          private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

          private final MetaCompareMethod compareMethod = register(new MetaCompareMethod());

          private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

          private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

          private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

          private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

          private final MetaIntValueMethod intValueMethod = register(new MetaIntValueMethod());

          private final MetaLongValueMethod longValueMethod = register(new MetaLongValueMethod());

          private final MetaFloatValueMethod floatValueMethod = register(new MetaFloatValueMethod());

          private final MetaDoubleValueMethod doubleValueMethod = register(new MetaDoubleValueMethod());

          private final MetaByteValueMethod byteValueMethod = register(new MetaByteValueMethod());

          private final MetaShortValueMethod shortValueMethod = register(new MetaShortValueMethod());

          private MetaInnerModelClass1() {
            super(metaType(model.other.MixedJavaModel.InnerModel.class));
          }

          public MetaConstructorConstructor constructor() {
            return constructor;
          }

          public MetaField<Long> serialVersionUIDField() {
            return serialVersionUIDField;
          }

          public MetaField<java.lang.Boolean> f1Field() {
            return f1Field;
          }

          public MetaField<java.lang.String> f2Field() {
            return f2Field;
          }

          public MetaField<Integer> f3Field() {
            return f3Field;
          }

          public MetaField<model.other.MixedJavaModel> f5Field() {
            return f5Field;
          }

          public MetaStaticMethodMethod staticMethodMethod() {
            return staticMethodMethod;
          }

          public MetaInstanceMethodMethod instanceMethodMethod() {
            return instanceMethodMethod;
          }

          public MetaCompareMethod compareMethod() {
            return compareMethod;
          }

          public MetaIsF1Method isF1Method() {
            return isF1Method;
          }

          public MetaGetF2Method getF2Method() {
            return getF2Method;
          }

          public MetaGetF3Method getF3Method() {
            return getF3Method;
          }

          public MetaGetF5Method getF5Method() {
            return getF5Method;
          }

          public MetaIntValueMethod intValueMethod() {
            return intValueMethod;
          }

          public MetaLongValueMethod longValueMethod() {
            return longValueMethod;
          }

          public MetaFloatValueMethod floatValueMethod() {
            return floatValueMethod;
          }

          public MetaDoubleValueMethod doubleValueMethod() {
            return doubleValueMethod;
          }

          public MetaByteValueMethod byteValueMethod() {
            return byteValueMethod;
          }

          public MetaShortValueMethod shortValueMethod() {
            return shortValueMethod;
          }

          public static final class MetaConstructorConstructor extends MetaConstructor<model.other.MixedJavaModel.InnerModel> {
            private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

            private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

            private final MetaParameter<Integer> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(int.class)));

            private final MetaParameter<model.other.MixedJavaModel> f5Parameter = register(new MetaParameter<>(3, "f5",metaType(model.other.MixedJavaModel.class)));

            private MetaConstructorConstructor() {
              super(metaType(model.other.MixedJavaModel.InnerModel.class));
            }

            @Override
            public model.other.MixedJavaModel.InnerModel invoke(java.lang.Object[] arguments) throws
                Throwable {
              return new model.other.MixedJavaModel.InnerModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(int)(arguments[2]),(model.other.MixedJavaModel)(arguments[3]));
            }

            public MetaParameter<java.lang.Boolean> f1Parameter() {
              return f1Parameter;
            }

            public MetaParameter<java.lang.String> f2Parameter() {
              return f2Parameter;
            }

            public MetaParameter<Integer> f3Parameter() {
              return f3Parameter;
            }

            public MetaParameter<model.other.MixedJavaModel> f5Parameter() {
              return f5Parameter;
            }
          }

          public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
            private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

            private MetaStaticMethodMethod() {
              super("staticMethod",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
              return model.other.MixedJavaModel.InnerModel.staticMethod((int)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
              return model.other.MixedJavaModel.InnerModel.staticMethod((int)(argument));
            }

            public MetaParameter<Integer> argumentParameter() {
              return argumentParameter;
            }
          }

          public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Integer> {
            private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

            private MetaInstanceMethodMethod() {
              super("instanceMethod",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.instanceMethod((int)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object argument) throws Throwable {
              return instance.instanceMethod((int)(argument));
            }

            public MetaParameter<Integer> argumentParameter() {
              return argumentParameter;
            }
          }

          public static final class MetaCompareMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Integer> {
            private final MetaParameter<java.lang.String> o1Parameter = register(new MetaParameter<>(0, "o1",metaType(java.lang.String.class)));

            private final MetaParameter<java.lang.String> o2Parameter = register(new MetaParameter<>(1, "o2",metaType(java.lang.String.class)));

            private MetaCompareMethod() {
              super("compare",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.compare((java.lang.String)(arguments[0]),(java.lang.String)(arguments[1]));
            }

            public MetaParameter<java.lang.String> o1Parameter() {
              return o1Parameter;
            }

            public MetaParameter<java.lang.String> o2Parameter() {
              return o2Parameter;
            }
          }

          public static final class MetaIsF1Method extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, java.lang.Boolean> {
            private MetaIsF1Method() {
              super("isF1",metaType(boolean.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.isF1();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.isF1();
            }
          }

          public static final class MetaGetF2Method extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, java.lang.String> {
            private MetaGetF2Method() {
              super("getF2",metaType(java.lang.String.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF2();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF2();
            }
          }

          public static final class MetaGetF3Method extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Integer> {
            private MetaGetF3Method() {
              super("getF3",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF3();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF3();
            }
          }

          public static final class MetaGetF5Method extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, model.other.MixedJavaModel> {
            private MetaGetF5Method() {
              super("getF5",metaType(model.other.MixedJavaModel.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF5();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF5();
            }
          }

          public static final class MetaIntValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Integer> {
            private MetaIntValueMethod() {
              super("intValue",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.intValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.intValue();
            }
          }

          public static final class MetaLongValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Long> {
            private MetaLongValueMethod() {
              super("longValue",metaType(long.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.longValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.longValue();
            }
          }

          public static final class MetaFloatValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Float> {
            private MetaFloatValueMethod() {
              super("floatValue",metaType(float.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.floatValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.floatValue();
            }
          }

          public static final class MetaDoubleValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Double> {
            private MetaDoubleValueMethod() {
              super("doubleValue",metaType(double.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.doubleValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.doubleValue();
            }
          }

          public static final class MetaByteValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Byte> {
            private MetaByteValueMethod() {
              super("byteValue",metaType(byte.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.byteValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.byteValue();
            }
          }

          public static final class MetaShortValueMethod extends InstanceMetaMethod<model.other.MixedJavaModel.InnerModel, Short> {
            private MetaShortValueMethod() {
              super("shortValue",metaType(short.class));
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.shortValue();
            }

            @Override
            public java.lang.Object invoke(model.other.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.shortValue();
            }
          }
        }
      }
    }

    public static final class MetaModelPackage1 extends MetaPackage {
      private final MetaMixedJavaClassClass2 mixedJavaClassClass = register(new MetaMixedJavaClassClass2());

      private final MetaMixedJavaInterfaceClass2 mixedJavaInterfaceClass = register(new MetaMixedJavaInterfaceClass2());

      private final MetaMixedJavaModelClass2 mixedJavaModelClass = register(new MetaMixedJavaModelClass2());

      private MetaModelPackage1() {
        super("model");
      }

      public MetaMixedJavaClassClass2 mixedJavaClassClass() {
        return mixedJavaClassClass;
      }

      public MetaMixedJavaInterfaceClass2 mixedJavaInterfaceClass() {
        return mixedJavaInterfaceClass;
      }

      public MetaMixedJavaModelClass2 mixedJavaModelClass() {
        return mixedJavaModelClass;
      }

      public static final class MetaMixedJavaClassClass2 extends MetaClass<model.model.MixedJavaClass> {
        private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),false));

        private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),false));

        private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

        private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

        private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

        private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

        private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

        private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

        private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

        private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

        private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

        private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

        private MetaMixedJavaClassClass2() {
          super(metaType(model.model.MixedJavaClass.class));
        }

        public MetaField<java.lang.Boolean> privateFieldField() {
          return privateFieldField;
        }

        public MetaField<java.lang.Boolean> protectedFieldField() {
          return protectedFieldField;
        }

        public MetaField<java.lang.Boolean> publicFieldField() {
          return publicFieldField;
        }

        public MetaField<java.lang.Boolean> internalFieldField() {
          return internalFieldField;
        }

        public MetaPublicMethodMethod publicMethodMethod() {
          return publicMethodMethod;
        }

        public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
          return publicAbstractMethodMethod;
        }

        public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
          return isPrivateFieldMethod;
        }

        public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
          return isProtectedFieldMethod;
        }

        public MetaIsPublicFieldMethod isPublicFieldMethod() {
          return isPublicFieldMethod;
        }

        public MetaIsInternalFieldMethod isInternalFieldMethod() {
          return isInternalFieldMethod;
        }

        public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
          return setPrivateFieldMethod;
        }

        public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
          return setProtectedFieldMethod;
        }

        public MetaSetPublicFieldMethod setPublicFieldMethod() {
          return setPublicFieldMethod;
        }

        public MetaSetInternalFieldMethod setInternalFieldMethod() {
          return setInternalFieldMethod;
        }

        public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaPublicMethodMethod() {
            super("publicMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicMethod();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.publicMethod();
          }
        }

        public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaPublicAbstractMethodMethod() {
            super("publicAbstractMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicAbstractMethod();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.publicAbstractMethod();
          }
        }

        public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaIsPrivateFieldMethod() {
            super("isPrivateField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPrivateField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.isPrivateField();
          }
        }

        public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaIsProtectedFieldMethod() {
            super("isProtectedField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isProtectedField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.isProtectedField();
          }
        }

        public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaIsPublicFieldMethod() {
            super("isPublicField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPublicField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.isPublicField();
          }
        }

        public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, java.lang.Boolean> {
          private MetaIsInternalFieldMethod() {
            super("isInternalField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isInternalField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance) throws Throwable {
            return instance.isInternalField();
          }
        }

        public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

          private MetaSetPrivateFieldMethod() {
            super("setPrivateField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPrivateField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setPrivateField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> privateFieldParameter() {
            return privateFieldParameter;
          }
        }

        public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

          private MetaSetProtectedFieldMethod() {
            super("setProtectedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setProtectedField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setProtectedField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
            return protectedFieldParameter;
          }
        }

        public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

          private MetaSetPublicFieldMethod() {
            super("setPublicField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPublicField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setPublicField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> publicFieldParameter() {
            return publicFieldParameter;
          }
        }

        public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.model.MixedJavaClass, Void> {
          private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

          private MetaSetInternalFieldMethod() {
            super("setInternalField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setInternalField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaClass instance,
              java.lang.Object argument) throws Throwable {
            instance.setInternalField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> internalFieldParameter() {
            return internalFieldParameter;
          }
        }
      }

      public static final class MetaMixedJavaInterfaceClass2 extends MetaClass<model.model.MixedJavaInterface> {
        private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

        private final MetaDefaultMethodMethod defaultMethodMethod = register(new MetaDefaultMethodMethod());

        private MetaMixedJavaInterfaceClass2() {
          super(metaType(model.model.MixedJavaInterface.class));
        }

        public MetaImplementableMethodMethod implementableMethodMethod() {
          return implementableMethodMethod;
        }

        public MetaDefaultMethodMethod defaultMethodMethod() {
          return defaultMethodMethod;
        }

        @Override
        public MetaProxy proxy(
            java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
          return new MetaMixedJavaInterfaceProxy(invocations);
        }

        public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.model.MixedJavaInterface, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaImplementableMethodMethod() {
            super("implementableMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaInterface instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.implementableMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaInterface instance,
              java.lang.Object argument) throws Throwable {
            return instance.implementableMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaDefaultMethodMethod extends InstanceMetaMethod<model.model.MixedJavaInterface, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaDefaultMethodMethod() {
            super("defaultMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaInterface instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.defaultMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaInterface instance,
              java.lang.Object argument) throws Throwable {
            return instance.defaultMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public class MetaMixedJavaInterfaceProxy extends MetaProxy implements model.model.MixedJavaInterface {
          private final Function<java.lang.Object, java.lang.Object> implementableMethodInvocation;

          public MetaMixedJavaInterfaceProxy(
              java.util.Map<MetaMethod<?>, Function<java.lang.Object, java.lang.Object>> invocations) {
            super(invocations);
            implementableMethodInvocation = invocations.get(implementableMethodMethod);
          }

          @Override
          public java.lang.String implementableMethod(java.lang.String argument) {
            return (java.lang.String)(implementableMethodInvocation.apply(argument));
          }
        }
      }

      public static final class MetaMixedJavaModelClass2 extends MetaClass<model.model.MixedJavaModel> {
        private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

        private final MetaConstructor_1Constructor constructor_1 = register(new MetaConstructor_1Constructor());

        private final MetaField<java.lang.Boolean> privateFieldField = register(new MetaField<>("privateField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> protectedFieldField = register(new MetaField<>("protectedField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> publicFieldField = register(new MetaField<>("publicField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> internalFieldField = register(new MetaField<>("internalField",metaType(boolean.class),true));

        private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

        private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

        private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

        private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

        private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

        private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

        private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

        private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

        private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

        private final MetaField<model.model.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.model.MixedJavaModel.InnerModel.class),false));

        private final MetaField<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class)),false));

        private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

        private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

        private final MetaField<model.model.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),false));

        private final MetaField<java.util.List<model.model.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<model.model.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

        private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))),false));

        private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))),false));

        private final MetaField<model.model.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

        private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

        private final MetaStaticMethod_1Method staticMethod_1Method = register(new MetaStaticMethod_1Method());

        private final MetaStaticMethod_2Method staticMethod_2Method = register(new MetaStaticMethod_2Method());

        private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

        private final MetaInstanceMethod_1Method instanceMethod_1Method = register(new MetaInstanceMethod_1Method());

        private final MetaImplementableMethodMethod implementableMethodMethod = register(new MetaImplementableMethodMethod());

        private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

        private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

        private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

        private final MetaGetF4Method getF4Method = register(new MetaGetF4Method());

        private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

        private final MetaGetF6Method getF6Method = register(new MetaGetF6Method());

        private final MetaGetF7Method getF7Method = register(new MetaGetF7Method());

        private final MetaGetF8Method getF8Method = register(new MetaGetF8Method());

        private final MetaGetF9Method getF9Method = register(new MetaGetF9Method());

        private final MetaGetF10Method getF10Method = register(new MetaGetF10Method());

        private final MetaGetF11Method getF11Method = register(new MetaGetF11Method());

        private final MetaGetF12Method getF12Method = register(new MetaGetF12Method());

        private final MetaGetF13Method getF13Method = register(new MetaGetF13Method());

        private final MetaGetF14Method getF14Method = register(new MetaGetF14Method());

        private final MetaGetF15Method getF15Method = register(new MetaGetF15Method());

        private final MetaGetF16Method getF16Method = register(new MetaGetF16Method());

        private final MetaGetF17Method getF17Method = register(new MetaGetF17Method());

        private final MetaGetF18Method getF18Method = register(new MetaGetF18Method());

        private final MetaGetF19Method getF19Method = register(new MetaGetF19Method());

        private final MetaGetF20Method getF20Method = register(new MetaGetF20Method());

        private final MetaGetF21Method getF21Method = register(new MetaGetF21Method());

        private final MetaGetF22Method getF22Method = register(new MetaGetF22Method());

        private final MetaGetF23Method getF23Method = register(new MetaGetF23Method());

        private final MetaGetF24Method getF24Method = register(new MetaGetF24Method());

        private final MetaGetF25Method getF25Method = register(new MetaGetF25Method());

        private final MetaGetF26Method getF26Method = register(new MetaGetF26Method());

        private final MetaGetF27Method getF27Method = register(new MetaGetF27Method());

        private final MetaGetF28Method getF28Method = register(new MetaGetF28Method());

        private final MetaGetF29Method getF29Method = register(new MetaGetF29Method());

        private final MetaGetF30Method getF30Method = register(new MetaGetF30Method());

        private final MetaGetF31Method getF31Method = register(new MetaGetF31Method());

        private final MetaGetF32Method getF32Method = register(new MetaGetF32Method());

        private final MetaGetF33Method getF33Method = register(new MetaGetF33Method());

        private final MetaGetF34Method getF34Method = register(new MetaGetF34Method());

        private final MetaGetF35Method getF35Method = register(new MetaGetF35Method());

        private final MetaGetF36Method getF36Method = register(new MetaGetF36Method());

        private final MetaGetF37Method getF37Method = register(new MetaGetF37Method());

        private final MetaGetF38Method getF38Method = register(new MetaGetF38Method());

        private final MetaGetF39Method getF39Method = register(new MetaGetF39Method());

        private final MetaGetF40Method getF40Method = register(new MetaGetF40Method());

        private final MetaGetF41Method getF41Method = register(new MetaGetF41Method());

        private final MetaGetGenericFieldMethod getGenericFieldMethod = register(new MetaGetGenericFieldMethod());

        private final MetaSetF1Method setF1Method = register(new MetaSetF1Method());

        private final MetaSetF2Method setF2Method = register(new MetaSetF2Method());

        private final MetaSetF3Method setF3Method = register(new MetaSetF3Method());

        private final MetaSetF4Method setF4Method = register(new MetaSetF4Method());

        private final MetaSetF5Method setF5Method = register(new MetaSetF5Method());

        private final MetaSetF6Method setF6Method = register(new MetaSetF6Method());

        private final MetaSetF7Method setF7Method = register(new MetaSetF7Method());

        private final MetaSetF8Method setF8Method = register(new MetaSetF8Method());

        private final MetaSetF9Method setF9Method = register(new MetaSetF9Method());

        private final MetaSetF10Method setF10Method = register(new MetaSetF10Method());

        private final MetaSetF11Method setF11Method = register(new MetaSetF11Method());

        private final MetaSetF12Method setF12Method = register(new MetaSetF12Method());

        private final MetaSetF13Method setF13Method = register(new MetaSetF13Method());

        private final MetaSetF14Method setF14Method = register(new MetaSetF14Method());

        private final MetaSetF15Method setF15Method = register(new MetaSetF15Method());

        private final MetaSetF16Method setF16Method = register(new MetaSetF16Method());

        private final MetaSetF17Method setF17Method = register(new MetaSetF17Method());

        private final MetaSetF18Method setF18Method = register(new MetaSetF18Method());

        private final MetaSetF19Method setF19Method = register(new MetaSetF19Method());

        private final MetaSetF20Method setF20Method = register(new MetaSetF20Method());

        private final MetaSetF21Method setF21Method = register(new MetaSetF21Method());

        private final MetaSetF22Method setF22Method = register(new MetaSetF22Method());

        private final MetaSetF23Method setF23Method = register(new MetaSetF23Method());

        private final MetaSetF24Method setF24Method = register(new MetaSetF24Method());

        private final MetaSetF25Method setF25Method = register(new MetaSetF25Method());

        private final MetaSetF26Method setF26Method = register(new MetaSetF26Method());

        private final MetaSetF27Method setF27Method = register(new MetaSetF27Method());

        private final MetaSetF28Method setF28Method = register(new MetaSetF28Method());

        private final MetaSetF29Method setF29Method = register(new MetaSetF29Method());

        private final MetaSetF30Method setF30Method = register(new MetaSetF30Method());

        private final MetaSetF31Method setF31Method = register(new MetaSetF31Method());

        private final MetaSetF32Method setF32Method = register(new MetaSetF32Method());

        private final MetaSetF33Method setF33Method = register(new MetaSetF33Method());

        private final MetaSetF34Method setF34Method = register(new MetaSetF34Method());

        private final MetaSetF35Method setF35Method = register(new MetaSetF35Method());

        private final MetaSetF36Method setF36Method = register(new MetaSetF36Method());

        private final MetaSetF37Method setF37Method = register(new MetaSetF37Method());

        private final MetaSetF38Method setF38Method = register(new MetaSetF38Method());

        private final MetaSetF39Method setF39Method = register(new MetaSetF39Method());

        private final MetaSetF40Method setF40Method = register(new MetaSetF40Method());

        private final MetaSetF41Method setF41Method = register(new MetaSetF41Method());

        private final MetaSetGenericFieldMethod setGenericFieldMethod = register(new MetaSetGenericFieldMethod());

        private final MetaPublicMethodMethod publicMethodMethod = register(new MetaPublicMethodMethod());

        private final MetaPublicAbstractMethodMethod publicAbstractMethodMethod = register(new MetaPublicAbstractMethodMethod());

        private final MetaIsPrivateFieldMethod isPrivateFieldMethod = register(new MetaIsPrivateFieldMethod());

        private final MetaIsProtectedFieldMethod isProtectedFieldMethod = register(new MetaIsProtectedFieldMethod());

        private final MetaIsPublicFieldMethod isPublicFieldMethod = register(new MetaIsPublicFieldMethod());

        private final MetaIsInternalFieldMethod isInternalFieldMethod = register(new MetaIsInternalFieldMethod());

        private final MetaSetPrivateFieldMethod setPrivateFieldMethod = register(new MetaSetPrivateFieldMethod());

        private final MetaSetProtectedFieldMethod setProtectedFieldMethod = register(new MetaSetProtectedFieldMethod());

        private final MetaSetPublicFieldMethod setPublicFieldMethod = register(new MetaSetPublicFieldMethod());

        private final MetaSetInternalFieldMethod setInternalFieldMethod = register(new MetaSetInternalFieldMethod());

        private final MetaMixedJavaModelBuilderClass2 mixedJavaModelBuilderClass = register(new MetaMixedJavaModelBuilderClass2());

        private final MetaInnerModelClass2 innerModelClass = register(new MetaInnerModelClass2());

        private MetaMixedJavaModelClass2() {
          super(metaType(model.model.MixedJavaModel.class));
        }

        public MetaConstructorConstructor constructor() {
          return constructor;
        }

        public MetaConstructor_1Constructor constructor_1() {
          return constructor_1;
        }

        public MetaField<java.lang.Boolean> privateFieldField() {
          return privateFieldField;
        }

        public MetaField<java.lang.Boolean> protectedFieldField() {
          return protectedFieldField;
        }

        public MetaField<java.lang.Boolean> publicFieldField() {
          return publicFieldField;
        }

        public MetaField<java.lang.Boolean> internalFieldField() {
          return internalFieldField;
        }

        public MetaField<java.lang.Boolean> f1Field() {
          return f1Field;
        }

        public MetaField<java.lang.String> f2Field() {
          return f2Field;
        }

        public MetaField<java.util.List<java.lang.String>> f3Field() {
          return f3Field;
        }

        public MetaField<java.util.List<?>> f4Field() {
          return f4Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
          return f5Field;
        }

        public MetaField<java.util.List<? super java.lang.String>> f6Field() {
          return f6Field;
        }

        public MetaField<boolean[]> f7Field() {
          return f7Field;
        }

        public MetaField<java.util.List<java.lang.String>[]> f8Field() {
          return f8Field;
        }

        public MetaField<java.util.List<?>[]> f9Field() {
          return f9Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
          return f10Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
          return f11Field;
        }

        public MetaField<boolean[][]> f12Field() {
          return f12Field;
        }

        public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
          return f13Field;
        }

        public MetaField<java.util.List<?>[][]> f14Field() {
          return f14Field;
        }

        public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
          return f15Field;
        }

        public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
          return f16Field;
        }

        public MetaField<java.util.List<boolean[]>> f17Field() {
          return f17Field;
        }

        public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
          return f18Field;
        }

        public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
          return f19Field;
        }

        public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
          return f20Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
          return f21Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
          return f22Field;
        }

        public MetaField<java.util.List<java.util.List<?>>> f23Field() {
          return f23Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
          return f24Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
          return f25Field;
        }

        public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
          return f26Field;
        }

        public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
          return f27Field;
        }

        public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field() {
          return f28Field;
        }

        public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
          return f29Field;
        }

        public MetaField<model.model.MixedJavaModel.InnerModel> f30Field() {
          return f30Field;
        }

        public MetaField<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Field() {
          return f31Field;
        }

        public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
          return f32Field;
        }

        public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
          return f33Field;
        }

        public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
          return f34Field;
        }

        public MetaField<model.model.MixedJavaModel.Enum> f35Field() {
          return f35Field;
        }

        public MetaField<java.util.List<model.model.MixedJavaModel.Enum>> f36Field() {
          return f36Field;
        }

        public MetaField<model.model.MixedJavaModel.Enum[]> f37Field() {
          return f37Field;
        }

        public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Field(
            ) {
          return f38Field;
        }

        public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Field(
            ) {
          return f39Field;
        }

        public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Field(
            ) {
          return f40Field;
        }

        public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Field(
            ) {
          return f41Field;
        }

        public MetaField<model.model.MixedJavaModel.GenericClass<?>> genericFieldField() {
          return genericFieldField;
        }

        public MetaStaticMethodMethod staticMethodMethod() {
          return staticMethodMethod;
        }

        public MetaStaticMethod_1Method staticMethod_1Method() {
          return staticMethod_1Method;
        }

        public MetaStaticMethod_2Method staticMethod_2Method() {
          return staticMethod_2Method;
        }

        public MetaInstanceMethodMethod instanceMethodMethod() {
          return instanceMethodMethod;
        }

        public MetaInstanceMethod_1Method instanceMethod_1Method() {
          return instanceMethod_1Method;
        }

        public MetaImplementableMethodMethod implementableMethodMethod() {
          return implementableMethodMethod;
        }

        public MetaIsF1Method isF1Method() {
          return isF1Method;
        }

        public MetaGetF2Method getF2Method() {
          return getF2Method;
        }

        public MetaGetF3Method getF3Method() {
          return getF3Method;
        }

        public MetaGetF4Method getF4Method() {
          return getF4Method;
        }

        public MetaGetF5Method getF5Method() {
          return getF5Method;
        }

        public MetaGetF6Method getF6Method() {
          return getF6Method;
        }

        public MetaGetF7Method getF7Method() {
          return getF7Method;
        }

        public MetaGetF8Method getF8Method() {
          return getF8Method;
        }

        public MetaGetF9Method getF9Method() {
          return getF9Method;
        }

        public MetaGetF10Method getF10Method() {
          return getF10Method;
        }

        public MetaGetF11Method getF11Method() {
          return getF11Method;
        }

        public MetaGetF12Method getF12Method() {
          return getF12Method;
        }

        public MetaGetF13Method getF13Method() {
          return getF13Method;
        }

        public MetaGetF14Method getF14Method() {
          return getF14Method;
        }

        public MetaGetF15Method getF15Method() {
          return getF15Method;
        }

        public MetaGetF16Method getF16Method() {
          return getF16Method;
        }

        public MetaGetF17Method getF17Method() {
          return getF17Method;
        }

        public MetaGetF18Method getF18Method() {
          return getF18Method;
        }

        public MetaGetF19Method getF19Method() {
          return getF19Method;
        }

        public MetaGetF20Method getF20Method() {
          return getF20Method;
        }

        public MetaGetF21Method getF21Method() {
          return getF21Method;
        }

        public MetaGetF22Method getF22Method() {
          return getF22Method;
        }

        public MetaGetF23Method getF23Method() {
          return getF23Method;
        }

        public MetaGetF24Method getF24Method() {
          return getF24Method;
        }

        public MetaGetF25Method getF25Method() {
          return getF25Method;
        }

        public MetaGetF26Method getF26Method() {
          return getF26Method;
        }

        public MetaGetF27Method getF27Method() {
          return getF27Method;
        }

        public MetaGetF28Method getF28Method() {
          return getF28Method;
        }

        public MetaGetF29Method getF29Method() {
          return getF29Method;
        }

        public MetaGetF30Method getF30Method() {
          return getF30Method;
        }

        public MetaGetF31Method getF31Method() {
          return getF31Method;
        }

        public MetaGetF32Method getF32Method() {
          return getF32Method;
        }

        public MetaGetF33Method getF33Method() {
          return getF33Method;
        }

        public MetaGetF34Method getF34Method() {
          return getF34Method;
        }

        public MetaGetF35Method getF35Method() {
          return getF35Method;
        }

        public MetaGetF36Method getF36Method() {
          return getF36Method;
        }

        public MetaGetF37Method getF37Method() {
          return getF37Method;
        }

        public MetaGetF38Method getF38Method() {
          return getF38Method;
        }

        public MetaGetF39Method getF39Method() {
          return getF39Method;
        }

        public MetaGetF40Method getF40Method() {
          return getF40Method;
        }

        public MetaGetF41Method getF41Method() {
          return getF41Method;
        }

        public MetaGetGenericFieldMethod getGenericFieldMethod() {
          return getGenericFieldMethod;
        }

        public MetaSetF1Method setF1Method() {
          return setF1Method;
        }

        public MetaSetF2Method setF2Method() {
          return setF2Method;
        }

        public MetaSetF3Method setF3Method() {
          return setF3Method;
        }

        public MetaSetF4Method setF4Method() {
          return setF4Method;
        }

        public MetaSetF5Method setF5Method() {
          return setF5Method;
        }

        public MetaSetF6Method setF6Method() {
          return setF6Method;
        }

        public MetaSetF7Method setF7Method() {
          return setF7Method;
        }

        public MetaSetF8Method setF8Method() {
          return setF8Method;
        }

        public MetaSetF9Method setF9Method() {
          return setF9Method;
        }

        public MetaSetF10Method setF10Method() {
          return setF10Method;
        }

        public MetaSetF11Method setF11Method() {
          return setF11Method;
        }

        public MetaSetF12Method setF12Method() {
          return setF12Method;
        }

        public MetaSetF13Method setF13Method() {
          return setF13Method;
        }

        public MetaSetF14Method setF14Method() {
          return setF14Method;
        }

        public MetaSetF15Method setF15Method() {
          return setF15Method;
        }

        public MetaSetF16Method setF16Method() {
          return setF16Method;
        }

        public MetaSetF17Method setF17Method() {
          return setF17Method;
        }

        public MetaSetF18Method setF18Method() {
          return setF18Method;
        }

        public MetaSetF19Method setF19Method() {
          return setF19Method;
        }

        public MetaSetF20Method setF20Method() {
          return setF20Method;
        }

        public MetaSetF21Method setF21Method() {
          return setF21Method;
        }

        public MetaSetF22Method setF22Method() {
          return setF22Method;
        }

        public MetaSetF23Method setF23Method() {
          return setF23Method;
        }

        public MetaSetF24Method setF24Method() {
          return setF24Method;
        }

        public MetaSetF25Method setF25Method() {
          return setF25Method;
        }

        public MetaSetF26Method setF26Method() {
          return setF26Method;
        }

        public MetaSetF27Method setF27Method() {
          return setF27Method;
        }

        public MetaSetF28Method setF28Method() {
          return setF28Method;
        }

        public MetaSetF29Method setF29Method() {
          return setF29Method;
        }

        public MetaSetF30Method setF30Method() {
          return setF30Method;
        }

        public MetaSetF31Method setF31Method() {
          return setF31Method;
        }

        public MetaSetF32Method setF32Method() {
          return setF32Method;
        }

        public MetaSetF33Method setF33Method() {
          return setF33Method;
        }

        public MetaSetF34Method setF34Method() {
          return setF34Method;
        }

        public MetaSetF35Method setF35Method() {
          return setF35Method;
        }

        public MetaSetF36Method setF36Method() {
          return setF36Method;
        }

        public MetaSetF37Method setF37Method() {
          return setF37Method;
        }

        public MetaSetF38Method setF38Method() {
          return setF38Method;
        }

        public MetaSetF39Method setF39Method() {
          return setF39Method;
        }

        public MetaSetF40Method setF40Method() {
          return setF40Method;
        }

        public MetaSetF41Method setF41Method() {
          return setF41Method;
        }

        public MetaSetGenericFieldMethod setGenericFieldMethod() {
          return setGenericFieldMethod;
        }

        public MetaPublicMethodMethod publicMethodMethod() {
          return publicMethodMethod;
        }

        public MetaPublicAbstractMethodMethod publicAbstractMethodMethod() {
          return publicAbstractMethodMethod;
        }

        public MetaIsPrivateFieldMethod isPrivateFieldMethod() {
          return isPrivateFieldMethod;
        }

        public MetaIsProtectedFieldMethod isProtectedFieldMethod() {
          return isProtectedFieldMethod;
        }

        public MetaIsPublicFieldMethod isPublicFieldMethod() {
          return isPublicFieldMethod;
        }

        public MetaIsInternalFieldMethod isInternalFieldMethod() {
          return isInternalFieldMethod;
        }

        public MetaSetPrivateFieldMethod setPrivateFieldMethod() {
          return setPrivateFieldMethod;
        }

        public MetaSetProtectedFieldMethod setProtectedFieldMethod() {
          return setProtectedFieldMethod;
        }

        public MetaSetPublicFieldMethod setPublicFieldMethod() {
          return setPublicFieldMethod;
        }

        public MetaSetInternalFieldMethod setInternalFieldMethod() {
          return setInternalFieldMethod;
        }

        public MetaMixedJavaModelBuilderClass2 mixedJavaModelBuilderClass() {
          return mixedJavaModelBuilderClass;
        }

        public MetaInnerModelClass2 innerModelClass() {
          return innerModelClass;
        }

        public static final class MetaConstructorConstructor extends MetaConstructor<model.model.MixedJavaModel> {
          private MetaConstructorConstructor() {
            super(metaType(model.model.MixedJavaModel.class));
          }

          @Override
          public model.model.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
            return new model.model.MixedJavaModel();
          }

          @Override
          public model.model.MixedJavaModel invoke() throws Throwable {
            return new model.model.MixedJavaModel();
          }
        }

        public static final class MetaConstructor_1Constructor extends MetaConstructor<model.model.MixedJavaModel> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

          private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(3, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

          private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(4, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(5, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(6, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

          private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(7, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(8, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(9, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(10, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(11, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(12, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(13, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

          private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(14, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(15, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(16, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(17, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(18, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(19, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(20, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(21, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(22, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(23, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(24, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(25, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

          private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(26, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(27, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(28, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private final MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(29, "f30",metaType(model.model.MixedJavaModel.InnerModel.class)));

          private final MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(30, "f31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class))));

          private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(31, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

          private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(32, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

          private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(33, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

          private final MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(34, "f35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));

          private final MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(35, "f36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(36, "f37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(37, "f38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(38, "f39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))));

          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(39, "f40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(40, "f41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

          private final MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(41, "genericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

          private MetaConstructor_1Constructor() {
            super(metaType(model.model.MixedJavaModel.class));
          }

          @Override
          public model.model.MixedJavaModel invoke(java.lang.Object[] arguments) throws Throwable {
            return new model.model.MixedJavaModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(java.util.List<java.lang.String>)(arguments[2]),(java.util.List<?>)(arguments[3]),(java.util.List<? extends java.lang.String>)(arguments[4]),(java.util.List<? super java.lang.String>)(arguments[5]),(boolean[])(arguments[6]),(java.util.List<java.lang.String>[])(arguments[7]),(java.util.List<?>[])(arguments[8]),(java.util.List<? extends java.lang.String>[])(arguments[9]),(java.util.List<? super java.lang.String>[])(arguments[10]),(boolean[][])(arguments[11]),(java.util.List<java.lang.String>[][])(arguments[12]),(java.util.List<?>[][])(arguments[13]),(java.util.List<? extends java.lang.String>[][])(arguments[14]),(java.util.List<? super java.lang.String>[][])(arguments[15]),(java.util.List<boolean[]>)(arguments[16]),(java.util.List<java.lang.String[]>[])(arguments[17]),(java.util.List<? extends java.lang.String[]>[])(arguments[18]),(java.util.List<? super java.lang.String[]>[])(arguments[19]),(java.util.List<java.util.List<java.lang.Boolean>>)(arguments[20]),(java.util.List<java.util.List<java.lang.String>>)(arguments[21]),(java.util.List<java.util.List<?>>)(arguments[22]),(java.util.List<java.util.List<? extends java.lang.String>>)(arguments[23]),(java.util.List<java.util.List<? super java.lang.String>>)(arguments[24]),(java.util.List<java.util.List<boolean[]>>)(arguments[25]),(java.util.List<java.util.List<java.lang.String[]>>)(arguments[26]),(java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[27]),(java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[28]),(model.model.MixedJavaModel.InnerModel)(arguments[29]),(java.util.List<model.model.MixedJavaModel.InnerModel>)(arguments[30]),(java.util.Map<java.lang.String, java.lang.String>)(arguments[31]),(java.util.Map<?, java.lang.String>)(arguments[32]),(java.util.Map<java.lang.String, ?>)(arguments[33]),(model.model.MixedJavaModel.Enum)(arguments[34]),(java.util.List<model.model.MixedJavaModel.Enum>)(arguments[35]),(model.model.MixedJavaModel.Enum[])(arguments[36]),(java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>)(arguments[37]),(java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>)(arguments[38]),(java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>)(arguments[39]),(java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>)(arguments[40]),(model.model.MixedJavaModel.GenericClass<?>)(arguments[41]));
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
            return f3Parameter;
          }

          public MetaParameter<java.util.List<?>> f4Parameter() {
            return f4Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
            return f5Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
            return f6Parameter;
          }

          public MetaParameter<boolean[]> f7Parameter() {
            return f7Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
            return f8Parameter;
          }

          public MetaParameter<java.util.List<?>[]> f9Parameter() {
            return f9Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
            return f10Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
            return f11Parameter;
          }

          public MetaParameter<boolean[][]> f12Parameter() {
            return f12Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
            return f13Parameter;
          }

          public MetaParameter<java.util.List<?>[][]> f14Parameter() {
            return f14Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
            return f15Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
            return f16Parameter;
          }

          public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
            return f17Parameter;
          }

          public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
            return f18Parameter;
          }

          public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
            return f19Parameter;
          }

          public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
            return f20Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
            return f21Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
            return f22Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
            return f23Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
              ) {
            return f24Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
              ) {
            return f25Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
            return f26Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
            return f27Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
              ) {
            return f28Parameter;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
              ) {
            return f29Parameter;
          }

          public MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter() {
            return f30Parameter;
          }

          public MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter(
              ) {
            return f31Parameter;
          }

          public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
            return f32Parameter;
          }

          public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
            return f33Parameter;
          }

          public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
            return f34Parameter;
          }

          public MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter() {
            return f35Parameter;
          }

          public MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter() {
            return f36Parameter;
          }

          public MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter() {
            return f37Parameter;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter(
              ) {
            return f38Parameter;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter(
              ) {
            return f39Parameter;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter(
              ) {
            return f40Parameter;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter(
              ) {
            return f41Parameter;
          }

          public MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
            return genericFieldParameter;
          }
        }

        public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
          private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

          private MetaStaticMethodMethod() {
            super("staticMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.model.MixedJavaModel.staticMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
            return model.model.MixedJavaModel.staticMethod((int)(argument));
          }

          public MetaParameter<Integer> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaStaticMethod_1Method extends StaticMetaMethod<java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaStaticMethod_1Method() {
            super("staticMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.model.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
            return model.model.MixedJavaModel.staticMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaStaticMethod_2Method extends StaticMetaMethod<Integer> {
          private final MetaParameter<java.lang.String> argument1Parameter = register(new MetaParameter<>(0, "argument1",metaType(java.lang.String.class)));

          private final MetaParameter<int[]> argument2Parameter = register(new MetaParameter<>(1, "argument2",metaArray(int[].class, int[]::new, metaType(int.class))));

          private MetaStaticMethod_2Method() {
            super("staticMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
            return model.model.MixedJavaModel.staticMethod((java.lang.String)(arguments[0]),(int[])(arguments[1]));
          }

          public MetaParameter<java.lang.String> argument1Parameter() {
            return argument1Parameter;
          }

          public MetaParameter<int[]> argument2Parameter() {
            return argument2Parameter;
          }
        }

        public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Integer> {
          private final MetaParameter<Integer> p1Parameter = register(new MetaParameter<>(0, "p1",metaType(int.class)));

          private MetaInstanceMethodMethod() {
            super("instanceMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.instanceMethod((int)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.instanceMethod((int)(argument));
          }

          public MetaParameter<Integer> p1Parameter() {
            return p1Parameter;
          }
        }

        public static final class MetaInstanceMethod_1Method extends InstanceMetaMethod<model.model.MixedJavaModel, Integer> {
          private final MetaParameter<int[]> p1Parameter = register(new MetaParameter<>(0, "p1",metaArray(int[].class, int[]::new, metaType(int.class))));

          private MetaInstanceMethod_1Method() {
            super("instanceMethod",metaType(int.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.instanceMethod((int[])(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.instanceMethod((int[])(argument));
          }

          public MetaParameter<int[]> p1Parameter() {
            return p1Parameter;
          }
        }

        public static final class MetaImplementableMethodMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.String> {
          private final MetaParameter<java.lang.String> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(java.lang.String.class)));

          private MetaImplementableMethodMethod() {
            super("implementableMethod",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.implementableMethod((java.lang.String)(arguments[0]));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            return instance.implementableMethod((java.lang.String)(argument));
          }

          public MetaParameter<java.lang.String> argumentParameter() {
            return argumentParameter;
          }
        }

        public static final class MetaIsF1Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaIsF1Method() {
            super("isF1",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isF1();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.isF1();
          }
        }

        public static final class MetaGetF2Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.String> {
          private MetaGetF2Method() {
            super("getF2",metaType(java.lang.String.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF2();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF2();
          }
        }

        public static final class MetaGetF3Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.lang.String>> {
          private MetaGetF3Method() {
            super("getF3",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF3();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF3();
          }
        }

        public static final class MetaGetF4Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<?>> {
          private MetaGetF4Method() {
            super("getF4",metaType(java.util.List.class,metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF4();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF4();
          }
        }

        public static final class MetaGetF5Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? extends java.lang.String>> {
          private MetaGetF5Method() {
            super("getF5",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF5();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF5();
          }
        }

        public static final class MetaGetF6Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? super java.lang.String>> {
          private MetaGetF6Method() {
            super("getF6",metaType(java.util.List.class,metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF6();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF6();
          }
        }

        public static final class MetaGetF7Method extends InstanceMetaMethod<model.model.MixedJavaModel, boolean[]> {
          private MetaGetF7Method() {
            super("getF7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF7();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF7();
          }
        }

        public static final class MetaGetF8Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.lang.String>[]> {
          private MetaGetF8Method() {
            super("getF8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF8();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF8();
          }
        }

        public static final class MetaGetF9Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<?>[]> {
          private MetaGetF9Method() {
            super("getF9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF9();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF9();
          }
        }

        public static final class MetaGetF10Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? extends java.lang.String>[]> {
          private MetaGetF10Method() {
            super("getF10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF10();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF10();
          }
        }

        public static final class MetaGetF11Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? super java.lang.String>[]> {
          private MetaGetF11Method() {
            super("getF11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF11();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF11();
          }
        }

        public static final class MetaGetF12Method extends InstanceMetaMethod<model.model.MixedJavaModel, boolean[][]> {
          private MetaGetF12Method() {
            super("getF12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF12();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF12();
          }
        }

        public static final class MetaGetF13Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.lang.String>[][]> {
          private MetaGetF13Method() {
            super("getF13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF13();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF13();
          }
        }

        public static final class MetaGetF14Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<?>[][]> {
          private MetaGetF14Method() {
            super("getF14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF14();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF14();
          }
        }

        public static final class MetaGetF15Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? extends java.lang.String>[][]> {
          private MetaGetF15Method() {
            super("getF15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF15();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF15();
          }
        }

        public static final class MetaGetF16Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? super java.lang.String>[][]> {
          private MetaGetF16Method() {
            super("getF16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF16();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF16();
          }
        }

        public static final class MetaGetF17Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<boolean[]>> {
          private MetaGetF17Method() {
            super("getF17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF17();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF17();
          }
        }

        public static final class MetaGetF18Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.lang.String[]>[]> {
          private MetaGetF18Method() {
            super("getF18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF18();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF18();
          }
        }

        public static final class MetaGetF19Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? extends java.lang.String[]>[]> {
          private MetaGetF19Method() {
            super("getF19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF19();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF19();
          }
        }

        public static final class MetaGetF20Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<? super java.lang.String[]>[]> {
          private MetaGetF20Method() {
            super("getF20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF20();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF20();
          }
        }

        public static final class MetaGetF21Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<java.lang.Boolean>>> {
          private MetaGetF21Method() {
            super("getF21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF21();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF21();
          }
        }

        public static final class MetaGetF22Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<java.lang.String>>> {
          private MetaGetF22Method() {
            super("getF22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF22();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF22();
          }
        }

        public static final class MetaGetF23Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<?>>> {
          private MetaGetF23Method() {
            super("getF23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF23();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF23();
          }
        }

        public static final class MetaGetF24Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String>>> {
          private MetaGetF24Method() {
            super("getF24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF24();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF24();
          }
        }

        public static final class MetaGetF25Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String>>> {
          private MetaGetF25Method() {
            super("getF25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF25();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF25();
          }
        }

        public static final class MetaGetF26Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<boolean[]>>> {
          private MetaGetF26Method() {
            super("getF26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF26();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF26();
          }
        }

        public static final class MetaGetF27Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<java.lang.String[]>>> {
          private MetaGetF27Method() {
            super("getF27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF27();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF27();
          }
        }

        public static final class MetaGetF28Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<? extends java.lang.String[]>>> {
          private MetaGetF28Method() {
            super("getF28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF28();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF28();
          }
        }

        public static final class MetaGetF29Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<java.util.List<? super java.lang.String[]>>> {
          private MetaGetF29Method() {
            super("getF29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF29();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF29();
          }
        }

        public static final class MetaGetF30Method extends InstanceMetaMethod<model.model.MixedJavaModel, model.model.MixedJavaModel.InnerModel> {
          private MetaGetF30Method() {
            super("getF30",metaType(model.model.MixedJavaModel.InnerModel.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF30();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF30();
          }
        }

        public static final class MetaGetF31Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<model.model.MixedJavaModel.InnerModel>> {
          private MetaGetF31Method() {
            super("getF31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF31();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF31();
          }
        }

        public static final class MetaGetF32Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<java.lang.String, java.lang.String>> {
          private MetaGetF32Method() {
            super("getF32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF32();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF32();
          }
        }

        public static final class MetaGetF33Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<?, java.lang.String>> {
          private MetaGetF33Method() {
            super("getF33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF33();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF33();
          }
        }

        public static final class MetaGetF34Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<java.lang.String, ?>> {
          private MetaGetF34Method() {
            super("getF34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF34();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF34();
          }
        }

        public static final class MetaGetF35Method extends InstanceMetaMethod<model.model.MixedJavaModel, model.model.MixedJavaModel.Enum> {
          private MetaGetF35Method() {
            super("getF35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF35();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF35();
          }
        }

        public static final class MetaGetF36Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.List<model.model.MixedJavaModel.Enum>> {
          private MetaGetF36Method() {
            super("getF36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF36();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF36();
          }
        }

        public static final class MetaGetF37Method extends InstanceMetaMethod<model.model.MixedJavaModel, model.model.MixedJavaModel.Enum[]> {
          private MetaGetF37Method() {
            super("getF37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF37();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF37();
          }
        }

        public static final class MetaGetF38Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> {
          private MetaGetF38Method() {
            super("getF38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF38();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF38();
          }
        }

        public static final class MetaGetF39Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> {
          private MetaGetF39Method() {
            super("getF39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF39();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF39();
          }
        }

        public static final class MetaGetF40Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> {
          private MetaGetF40Method() {
            super("getF40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF40();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF40();
          }
        }

        public static final class MetaGetF41Method extends InstanceMetaMethod<model.model.MixedJavaModel, java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> {
          private MetaGetF41Method() {
            super("getF41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getF41();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getF41();
          }
        }

        public static final class MetaGetGenericFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, model.model.MixedJavaModel.GenericClass<?>> {
          private MetaGetGenericFieldMethod() {
            super("getGenericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.getGenericField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.getGenericField();
          }
        }

        public static final class MetaSetF1Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

          private MetaSetF1Method() {
            super("setF1",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF1((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF1((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> f1Parameter() {
            return f1Parameter;
          }
        }

        public static final class MetaSetF2Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

          private MetaSetF2Method() {
            super("setF2",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF2((java.lang.String)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF2((java.lang.String)(argument));
            return null;
          }

          public MetaParameter<java.lang.String> f2Parameter() {
            return f2Parameter;
          }
        }

        public static final class MetaSetF3Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF3Method() {
            super("setF3",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF3((java.util.List<java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF3((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
            return f3Parameter;
          }
        }

        public static final class MetaSetF4Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

          private MetaSetF4Method() {
            super("setF4",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF4((java.util.List<?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF4((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>> f4Parameter() {
            return f4Parameter;
          }
        }

        public static final class MetaSetF5Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF5Method() {
            super("setF5",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF5((java.util.List<? extends java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF5((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
            return f5Parameter;
          }
        }

        public static final class MetaSetF6Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

          private MetaSetF6Method() {
            super("setF6",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF6((java.util.List<? super java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF6((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
            return f6Parameter;
          }
        }

        public static final class MetaSetF7Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

          private MetaSetF7Method() {
            super("setF7",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF7((boolean[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF7((boolean[])(argument));
            return null;
          }

          public MetaParameter<boolean[]> f7Parameter() {
            return f7Parameter;
          }
        }

        public static final class MetaSetF8Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF8Method() {
            super("setF8",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF8((java.util.List<java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF8((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
            return f8Parameter;
          }
        }

        public static final class MetaSetF9Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaSetF9Method() {
            super("setF9",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF9((java.util.List<?>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF9((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>[]> f9Parameter() {
            return f9Parameter;
          }
        }

        public static final class MetaSetF10Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF10Method() {
            super("setF10",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF10((java.util.List<? extends java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF10((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
            return f10Parameter;
          }
        }

        public static final class MetaSetF11Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF11Method() {
            super("setF11",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF11((java.util.List<? super java.lang.String>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF11((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
            return f11Parameter;
          }
        }

        public static final class MetaSetF12Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaSetF12Method() {
            super("setF12",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF12((boolean[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF12((boolean[][])(argument));
            return null;
          }

          public MetaParameter<boolean[][]> f12Parameter() {
            return f12Parameter;
          }
        }

        public static final class MetaSetF13Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF13Method() {
            super("setF13",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF13((java.util.List<java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF13((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
            return f13Parameter;
          }
        }

        public static final class MetaSetF14Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

          private MetaSetF14Method() {
            super("setF14",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF14((java.util.List<?>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF14((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<?>[][]> f14Parameter() {
            return f14Parameter;
          }
        }

        public static final class MetaSetF15Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF15Method() {
            super("setF15",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF15((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
            return f15Parameter;
          }
        }

        public static final class MetaSetF16Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

          private MetaSetF16Method() {
            super("setF16",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF16((java.util.List<? super java.lang.String>[][])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF16((java.util.List[][])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
            return f16Parameter;
          }
        }

        public static final class MetaSetF17Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

          private MetaSetF17Method() {
            super("setF17",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF17((java.util.List<boolean[]>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF17((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
            return f17Parameter;
          }
        }

        public static final class MetaSetF18Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF18Method() {
            super("setF18",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF18((java.util.List<java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF18((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
            return f18Parameter;
          }
        }

        public static final class MetaSetF19Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF19Method() {
            super("setF19",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF19((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
            return f19Parameter;
          }
        }

        public static final class MetaSetF20Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF20Method() {
            super("setF20",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF20((java.util.List[])(argument));
            return null;
          }

          public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
            return f20Parameter;
          }
        }

        public static final class MetaSetF21Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

          private MetaSetF21Method() {
            super("setF21",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF21((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
            return f21Parameter;
          }
        }

        public static final class MetaSetF22Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF22Method() {
            super("setF22",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF22((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
            return f22Parameter;
          }
        }

        public static final class MetaSetF23Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

          private MetaSetF23Method() {
            super("setF23",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF23((java.util.List<java.util.List<?>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF23((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
            return f23Parameter;
          }
        }

        public static final class MetaSetF24Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF24Method() {
            super("setF24",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF24((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
              ) {
            return f24Parameter;
          }
        }

        public static final class MetaSetF25Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

          private MetaSetF25Method() {
            super("setF25",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF25((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
              ) {
            return f25Parameter;
          }
        }

        public static final class MetaSetF26Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

          private MetaSetF26Method() {
            super("setF26",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF26((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
            return f26Parameter;
          }
        }

        public static final class MetaSetF27Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF27Method() {
            super("setF27",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF27((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter() {
            return f27Parameter;
          }
        }

        public static final class MetaSetF28Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF28Method() {
            super("setF28",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF28((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
              ) {
            return f28Parameter;
          }
        }

        public static final class MetaSetF29Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

          private MetaSetF29Method() {
            super("setF29",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF29((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
              ) {
            return f29Parameter;
          }
        }

        public static final class MetaSetF30Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.model.MixedJavaModel.InnerModel.class)));

          private MetaSetF30Method() {
            super("setF30",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF30((model.model.MixedJavaModel.InnerModel)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF30((model.model.MixedJavaModel.InnerModel)(argument));
            return null;
          }

          public MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter() {
            return f30Parameter;
          }
        }

        public static final class MetaSetF31Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class))));

          private MetaSetF31Method() {
            super("setF31",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF31((java.util.List<model.model.MixedJavaModel.InnerModel>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF31((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter(
              ) {
            return f31Parameter;
          }
        }

        public static final class MetaSetF32Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

          private MetaSetF32Method() {
            super("setF32",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF32((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
            return f32Parameter;
          }
        }

        public static final class MetaSetF33Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

          private MetaSetF33Method() {
            super("setF33",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF33((java.util.Map<?, java.lang.String>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF33((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
            return f33Parameter;
          }
        }

        public static final class MetaSetF34Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

          private MetaSetF34Method() {
            super("setF34",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF34((java.util.Map<java.lang.String, ?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF34((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
            return f34Parameter;
          }
        }

        public static final class MetaSetF35Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));

          private MetaSetF35Method() {
            super("setF35",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF35((model.model.MixedJavaModel.Enum)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF35((model.model.MixedJavaModel.Enum)(argument));
            return null;
          }

          public MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter() {
            return f35Parameter;
          }
        }

        public static final class MetaSetF36Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private MetaSetF36Method() {
            super("setF36",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF36((java.util.List<model.model.MixedJavaModel.Enum>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF36((java.util.List)(argument));
            return null;
          }

          public MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter() {
            return f36Parameter;
          }
        }

        public static final class MetaSetF37Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private MetaSetF37Method() {
            super("setF37",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF37((model.model.MixedJavaModel.Enum[])(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF37((model.model.MixedJavaModel.Enum[])(argument));
            return null;
          }

          public MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter() {
            return f37Parameter;
          }
        }

        public static final class MetaSetF38Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

          private MetaSetF38Method() {
            super("setF38",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF38((java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF38((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter(
              ) {
            return f38Parameter;
          }
        }

        public static final class MetaSetF39Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))));

          private MetaSetF39Method() {
            super("setF39",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF39((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF39((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter(
              ) {
            return f39Parameter;
          }
        }

        public static final class MetaSetF40Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

          private MetaSetF40Method() {
            super("setF40",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF40((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF40((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter(
              ) {
            return f40Parameter;
          }
        }

        public static final class MetaSetF41Method extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

          private MetaSetF41Method() {
            super("setF41",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setF41((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setF41((java.util.Map)(argument));
            return null;
          }

          public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter(
              ) {
            return f41Parameter;
          }
        }

        public static final class MetaSetGenericFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

          private MetaSetGenericFieldMethod() {
            super("setGenericField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setGenericField((model.model.MixedJavaModel.GenericClass<?>)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setGenericField((model.model.MixedJavaModel.GenericClass)(argument));
            return null;
          }

          public MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter() {
            return genericFieldParameter;
          }
        }

        public static final class MetaPublicMethodMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaPublicMethodMethod() {
            super("publicMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicMethod();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.publicMethod();
          }
        }

        public static final class MetaPublicAbstractMethodMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaPublicAbstractMethodMethod() {
            super("publicAbstractMethod",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.publicAbstractMethod();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.publicAbstractMethod();
          }
        }

        public static final class MetaIsPrivateFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaIsPrivateFieldMethod() {
            super("isPrivateField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPrivateField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.isPrivateField();
          }
        }

        public static final class MetaIsProtectedFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaIsProtectedFieldMethod() {
            super("isProtectedField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isProtectedField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.isProtectedField();
          }
        }

        public static final class MetaIsPublicFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaIsPublicFieldMethod() {
            super("isPublicField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isPublicField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.isPublicField();
          }
        }

        public static final class MetaIsInternalFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, java.lang.Boolean> {
          private MetaIsInternalFieldMethod() {
            super("isInternalField",metaType(boolean.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            return instance.isInternalField();
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance) throws Throwable {
            return instance.isInternalField();
          }
        }

        public static final class MetaSetPrivateFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> privateFieldParameter = register(new MetaParameter<>(0, "privateField",metaType(boolean.class)));

          private MetaSetPrivateFieldMethod() {
            super("setPrivateField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPrivateField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setPrivateField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> privateFieldParameter() {
            return privateFieldParameter;
          }
        }

        public static final class MetaSetProtectedFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> protectedFieldParameter = register(new MetaParameter<>(0, "protectedField",metaType(boolean.class)));

          private MetaSetProtectedFieldMethod() {
            super("setProtectedField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setProtectedField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setProtectedField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> protectedFieldParameter() {
            return protectedFieldParameter;
          }
        }

        public static final class MetaSetPublicFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> publicFieldParameter = register(new MetaParameter<>(0, "publicField",metaType(boolean.class)));

          private MetaSetPublicFieldMethod() {
            super("setPublicField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setPublicField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setPublicField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> publicFieldParameter() {
            return publicFieldParameter;
          }
        }

        public static final class MetaSetInternalFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel, Void> {
          private final MetaParameter<java.lang.Boolean> internalFieldParameter = register(new MetaParameter<>(0, "internalField",metaType(boolean.class)));

          private MetaSetInternalFieldMethod() {
            super("setInternalField",metaType(Void.class));
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object[] arguments) throws Throwable {
            instance.setInternalField((boolean)(arguments[0]));
            return null;
          }

          @Override
          public java.lang.Object invoke(model.model.MixedJavaModel instance,
              java.lang.Object argument) throws Throwable {
            instance.setInternalField((boolean)(argument));
            return null;
          }

          public MetaParameter<java.lang.Boolean> internalFieldParameter() {
            return internalFieldParameter;
          }
        }

        public static final class MetaMixedJavaModelBuilderClass2 extends MetaClass<model.model.MixedJavaModel.MixedJavaModelBuilder> {
          private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

          private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

          private final MetaField<java.util.List<java.lang.String>> f3Field = register(new MetaField<>("f3",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<java.util.List<?>> f4Field = register(new MetaField<>("f4",metaType(java.util.List.class,metaType(java.lang.Object.class)),false));

          private final MetaField<java.util.List<? extends java.lang.String>> f5Field = register(new MetaField<>("f5",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<java.util.List<? super java.lang.String>> f6Field = register(new MetaField<>("f6",metaType(java.util.List.class,metaType(java.lang.String.class)),false));

          private final MetaField<boolean[]> f7Field = register(new MetaField<>("f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)),false));

          private final MetaField<java.util.List<java.lang.String>[]> f8Field = register(new MetaField<>("f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<?>[]> f9Field = register(new MetaField<>("f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

          private final MetaField<java.util.List<? extends java.lang.String>[]> f10Field = register(new MetaField<>("f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<? super java.lang.String>[]> f11Field = register(new MetaField<>("f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<boolean[][]> f12Field = register(new MetaField<>("f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

          private final MetaField<java.util.List<java.lang.String>[][]> f13Field = register(new MetaField<>("f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<?>[][]> f14Field = register(new MetaField<>("f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))),false));

          private final MetaField<java.util.List<? extends java.lang.String>[][]> f15Field = register(new MetaField<>("f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? super java.lang.String>[][]> f16Field = register(new MetaField<>("f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<boolean[]>> f17Field = register(new MetaField<>("f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))),false));

          private final MetaField<java.util.List<java.lang.String[]>[]> f18Field = register(new MetaField<>("f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field = register(new MetaField<>("f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<? super java.lang.String[]>[]> f20Field = register(new MetaField<>("f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field = register(new MetaField<>("f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field = register(new MetaField<>("f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<?>>> f23Field = register(new MetaField<>("f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class))),false));

          private final MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field = register(new MetaField<>("f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field = register(new MetaField<>("f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class))),false));

          private final MetaField<java.util.List<java.util.List<boolean[]>>> f26Field = register(new MetaField<>("f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))),false));

          private final MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field = register(new MetaField<>("f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field = register(new MetaField<>("f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field = register(new MetaField<>("f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class)))),false));

          private final MetaField<model.model.MixedJavaModel.InnerModel> f30Field = register(new MetaField<>("f30",metaType(model.model.MixedJavaModel.InnerModel.class),false));

          private final MetaField<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Field = register(new MetaField<>("f31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class)),false));

          private final MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field = register(new MetaField<>("f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class)),false));

          private final MetaField<java.util.Map<?, java.lang.String>> f33Field = register(new MetaField<>("f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class)),false));

          private final MetaField<java.util.Map<java.lang.String, ?>> f34Field = register(new MetaField<>("f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class)),false));

          private final MetaField<model.model.MixedJavaModel.Enum> f35Field = register(new MetaField<>("f35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),false));

          private final MetaField<java.util.List<model.model.MixedJavaModel.Enum>> f36Field = register(new MetaField<>("f36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<model.model.MixedJavaModel.Enum[]> f37Field = register(new MetaField<>("f37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Field = register(new MetaField<>("f38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)),false));

          private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Field = register(new MetaField<>("f39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))),false));

          private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Field = register(new MetaField<>("f40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))),false));

          private final MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Field = register(new MetaField<>("f41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))),false));

          private final MetaField<model.model.MixedJavaModel.GenericClass<?>> genericFieldField = register(new MetaField<>("genericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class)),false));

          private final MetaF1Method f1Method = register(new MetaF1Method());

          private final MetaF2Method f2Method = register(new MetaF2Method());

          private final MetaF3Method f3Method = register(new MetaF3Method());

          private final MetaF4Method f4Method = register(new MetaF4Method());

          private final MetaF5Method f5Method = register(new MetaF5Method());

          private final MetaF6Method f6Method = register(new MetaF6Method());

          private final MetaF7Method f7Method = register(new MetaF7Method());

          private final MetaF8Method f8Method = register(new MetaF8Method());

          private final MetaF9Method f9Method = register(new MetaF9Method());

          private final MetaF10Method f10Method = register(new MetaF10Method());

          private final MetaF11Method f11Method = register(new MetaF11Method());

          private final MetaF12Method f12Method = register(new MetaF12Method());

          private final MetaF13Method f13Method = register(new MetaF13Method());

          private final MetaF14Method f14Method = register(new MetaF14Method());

          private final MetaF15Method f15Method = register(new MetaF15Method());

          private final MetaF16Method f16Method = register(new MetaF16Method());

          private final MetaF17Method f17Method = register(new MetaF17Method());

          private final MetaF18Method f18Method = register(new MetaF18Method());

          private final MetaF19Method f19Method = register(new MetaF19Method());

          private final MetaF20Method f20Method = register(new MetaF20Method());

          private final MetaF21Method f21Method = register(new MetaF21Method());

          private final MetaF22Method f22Method = register(new MetaF22Method());

          private final MetaF23Method f23Method = register(new MetaF23Method());

          private final MetaF24Method f24Method = register(new MetaF24Method());

          private final MetaF25Method f25Method = register(new MetaF25Method());

          private final MetaF26Method f26Method = register(new MetaF26Method());

          private final MetaF27Method f27Method = register(new MetaF27Method());

          private final MetaF28Method f28Method = register(new MetaF28Method());

          private final MetaF29Method f29Method = register(new MetaF29Method());

          private final MetaF30Method f30Method = register(new MetaF30Method());

          private final MetaF31Method f31Method = register(new MetaF31Method());

          private final MetaF32Method f32Method = register(new MetaF32Method());

          private final MetaF33Method f33Method = register(new MetaF33Method());

          private final MetaF34Method f34Method = register(new MetaF34Method());

          private final MetaF35Method f35Method = register(new MetaF35Method());

          private final MetaF36Method f36Method = register(new MetaF36Method());

          private final MetaF37Method f37Method = register(new MetaF37Method());

          private final MetaF38Method f38Method = register(new MetaF38Method());

          private final MetaF39Method f39Method = register(new MetaF39Method());

          private final MetaF40Method f40Method = register(new MetaF40Method());

          private final MetaF41Method f41Method = register(new MetaF41Method());

          private final MetaGenericFieldMethod genericFieldMethod = register(new MetaGenericFieldMethod());

          private final MetaBuildMethod buildMethod = register(new MetaBuildMethod());

          private MetaMixedJavaModelBuilderClass2() {
            super(metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
          }

          public MetaField<java.lang.Boolean> f1Field() {
            return f1Field;
          }

          public MetaField<java.lang.String> f2Field() {
            return f2Field;
          }

          public MetaField<java.util.List<java.lang.String>> f3Field() {
            return f3Field;
          }

          public MetaField<java.util.List<?>> f4Field() {
            return f4Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>> f5Field() {
            return f5Field;
          }

          public MetaField<java.util.List<? super java.lang.String>> f6Field() {
            return f6Field;
          }

          public MetaField<boolean[]> f7Field() {
            return f7Field;
          }

          public MetaField<java.util.List<java.lang.String>[]> f8Field() {
            return f8Field;
          }

          public MetaField<java.util.List<?>[]> f9Field() {
            return f9Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>[]> f10Field() {
            return f10Field;
          }

          public MetaField<java.util.List<? super java.lang.String>[]> f11Field() {
            return f11Field;
          }

          public MetaField<boolean[][]> f12Field() {
            return f12Field;
          }

          public MetaField<java.util.List<java.lang.String>[][]> f13Field() {
            return f13Field;
          }

          public MetaField<java.util.List<?>[][]> f14Field() {
            return f14Field;
          }

          public MetaField<java.util.List<? extends java.lang.String>[][]> f15Field() {
            return f15Field;
          }

          public MetaField<java.util.List<? super java.lang.String>[][]> f16Field() {
            return f16Field;
          }

          public MetaField<java.util.List<boolean[]>> f17Field() {
            return f17Field;
          }

          public MetaField<java.util.List<java.lang.String[]>[]> f18Field() {
            return f18Field;
          }

          public MetaField<java.util.List<? extends java.lang.String[]>[]> f19Field() {
            return f19Field;
          }

          public MetaField<java.util.List<? super java.lang.String[]>[]> f20Field() {
            return f20Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.Boolean>>> f21Field() {
            return f21Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.String>>> f22Field() {
            return f22Field;
          }

          public MetaField<java.util.List<java.util.List<?>>> f23Field() {
            return f23Field;
          }

          public MetaField<java.util.List<java.util.List<? extends java.lang.String>>> f24Field() {
            return f24Field;
          }

          public MetaField<java.util.List<java.util.List<? super java.lang.String>>> f25Field() {
            return f25Field;
          }

          public MetaField<java.util.List<java.util.List<boolean[]>>> f26Field() {
            return f26Field;
          }

          public MetaField<java.util.List<java.util.List<java.lang.String[]>>> f27Field() {
            return f27Field;
          }

          public MetaField<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Field(
              ) {
            return f28Field;
          }

          public MetaField<java.util.List<java.util.List<? super java.lang.String[]>>> f29Field() {
            return f29Field;
          }

          public MetaField<model.model.MixedJavaModel.InnerModel> f30Field() {
            return f30Field;
          }

          public MetaField<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Field() {
            return f31Field;
          }

          public MetaField<java.util.Map<java.lang.String, java.lang.String>> f32Field() {
            return f32Field;
          }

          public MetaField<java.util.Map<?, java.lang.String>> f33Field() {
            return f33Field;
          }

          public MetaField<java.util.Map<java.lang.String, ?>> f34Field() {
            return f34Field;
          }

          public MetaField<model.model.MixedJavaModel.Enum> f35Field() {
            return f35Field;
          }

          public MetaField<java.util.List<model.model.MixedJavaModel.Enum>> f36Field() {
            return f36Field;
          }

          public MetaField<model.model.MixedJavaModel.Enum[]> f37Field() {
            return f37Field;
          }

          public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Field(
              ) {
            return f38Field;
          }

          public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Field(
              ) {
            return f39Field;
          }

          public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Field(
              ) {
            return f40Field;
          }

          public MetaField<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Field(
              ) {
            return f41Field;
          }

          public MetaField<model.model.MixedJavaModel.GenericClass<?>> genericFieldField() {
            return genericFieldField;
          }

          public MetaF1Method f1Method() {
            return f1Method;
          }

          public MetaF2Method f2Method() {
            return f2Method;
          }

          public MetaF3Method f3Method() {
            return f3Method;
          }

          public MetaF4Method f4Method() {
            return f4Method;
          }

          public MetaF5Method f5Method() {
            return f5Method;
          }

          public MetaF6Method f6Method() {
            return f6Method;
          }

          public MetaF7Method f7Method() {
            return f7Method;
          }

          public MetaF8Method f8Method() {
            return f8Method;
          }

          public MetaF9Method f9Method() {
            return f9Method;
          }

          public MetaF10Method f10Method() {
            return f10Method;
          }

          public MetaF11Method f11Method() {
            return f11Method;
          }

          public MetaF12Method f12Method() {
            return f12Method;
          }

          public MetaF13Method f13Method() {
            return f13Method;
          }

          public MetaF14Method f14Method() {
            return f14Method;
          }

          public MetaF15Method f15Method() {
            return f15Method;
          }

          public MetaF16Method f16Method() {
            return f16Method;
          }

          public MetaF17Method f17Method() {
            return f17Method;
          }

          public MetaF18Method f18Method() {
            return f18Method;
          }

          public MetaF19Method f19Method() {
            return f19Method;
          }

          public MetaF20Method f20Method() {
            return f20Method;
          }

          public MetaF21Method f21Method() {
            return f21Method;
          }

          public MetaF22Method f22Method() {
            return f22Method;
          }

          public MetaF23Method f23Method() {
            return f23Method;
          }

          public MetaF24Method f24Method() {
            return f24Method;
          }

          public MetaF25Method f25Method() {
            return f25Method;
          }

          public MetaF26Method f26Method() {
            return f26Method;
          }

          public MetaF27Method f27Method() {
            return f27Method;
          }

          public MetaF28Method f28Method() {
            return f28Method;
          }

          public MetaF29Method f29Method() {
            return f29Method;
          }

          public MetaF30Method f30Method() {
            return f30Method;
          }

          public MetaF31Method f31Method() {
            return f31Method;
          }

          public MetaF32Method f32Method() {
            return f32Method;
          }

          public MetaF33Method f33Method() {
            return f33Method;
          }

          public MetaF34Method f34Method() {
            return f34Method;
          }

          public MetaF35Method f35Method() {
            return f35Method;
          }

          public MetaF36Method f36Method() {
            return f36Method;
          }

          public MetaF37Method f37Method() {
            return f37Method;
          }

          public MetaF38Method f38Method() {
            return f38Method;
          }

          public MetaF39Method f39Method() {
            return f39Method;
          }

          public MetaF40Method f40Method() {
            return f40Method;
          }

          public MetaF41Method f41Method() {
            return f41Method;
          }

          public MetaGenericFieldMethod genericFieldMethod() {
            return genericFieldMethod;
          }

          public MetaBuildMethod buildMethod() {
            return buildMethod;
          }

          public static final class MetaF1Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

            private MetaF1Method() {
              super("f1",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f1((boolean)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f1((boolean)(argument));
            }

            public MetaParameter<java.lang.Boolean> f1Parameter() {
              return f1Parameter;
            }
          }

          public static final class MetaF2Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(0, "f2",metaType(java.lang.String.class)));

            private MetaF2Method() {
              super("f2",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f2((java.lang.String)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f2((java.lang.String)(argument));
            }

            public MetaParameter<java.lang.String> f2Parameter() {
              return f2Parameter;
            }
          }

          public static final class MetaF3Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>> f3Parameter = register(new MetaParameter<>(0, "f3",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF3Method() {
              super("f3",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f3((java.util.List<java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f3((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>> f3Parameter() {
              return f3Parameter;
            }
          }

          public static final class MetaF4Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>> f4Parameter = register(new MetaParameter<>(0, "f4",metaType(java.util.List.class,metaType(java.lang.Object.class))));

            private MetaF4Method() {
              super("f4",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f4((java.util.List<?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f4((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<?>> f4Parameter() {
              return f4Parameter;
            }
          }

          public static final class MetaF5Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter = register(new MetaParameter<>(0, "f5",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF5Method() {
              super("f5",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f5((java.util.List<? extends java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f5((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>> f5Parameter() {
              return f5Parameter;
            }
          }

          public static final class MetaF6Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>> f6Parameter = register(new MetaParameter<>(0, "f6",metaType(java.util.List.class,metaType(java.lang.String.class))));

            private MetaF6Method() {
              super("f6",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f6((java.util.List<? super java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f6((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>> f6Parameter() {
              return f6Parameter;
            }
          }

          public static final class MetaF7Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<boolean[]> f7Parameter = register(new MetaParameter<>(0, "f7",metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))));

            private MetaF7Method() {
              super("f7",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f7((boolean[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f7((boolean[])(argument));
            }

            public MetaParameter<boolean[]> f7Parameter() {
              return f7Parameter;
            }
          }

          public static final class MetaF8Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>[]> f8Parameter = register(new MetaParameter<>(0, "f8",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF8Method() {
              super("f8",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f8((java.util.List<java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f8((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>[]> f8Parameter() {
              return f8Parameter;
            }
          }

          public static final class MetaF9Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>[]> f9Parameter = register(new MetaParameter<>(0, "f9",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class)))));

            private MetaF9Method() {
              super("f9",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f9((java.util.List<?>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f9((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<?>[]> f9Parameter() {
              return f9Parameter;
            }
          }

          public static final class MetaF10Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter = register(new MetaParameter<>(0, "f10",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF10Method() {
              super("f10",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f10((java.util.List<? extends java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f10((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>[]> f10Parameter() {
              return f10Parameter;
            }
          }

          public static final class MetaF11Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter = register(new MetaParameter<>(0, "f11",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF11Method() {
              super("f11",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f11((java.util.List<? super java.lang.String>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f11((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>[]> f11Parameter() {
              return f11Parameter;
            }
          }

          public static final class MetaF12Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<boolean[][]> f12Parameter = register(new MetaParameter<>(0, "f12",metaArray(boolean[][].class, boolean[][]::new, metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

            private MetaF12Method() {
              super("f12",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f12((boolean[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f12((boolean[][])(argument));
            }

            public MetaParameter<boolean[][]> f12Parameter() {
              return f12Parameter;
            }
          }

          public static final class MetaF13Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter = register(new MetaParameter<>(0, "f13",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF13Method() {
              super("f13",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f13((java.util.List<java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f13((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String>[][]> f13Parameter() {
              return f13Parameter;
            }
          }

          public static final class MetaF14Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<?>[][]> f14Parameter = register(new MetaParameter<>(0, "f14",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.Object.class))))));

            private MetaF14Method() {
              super("f14",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f14((java.util.List<?>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f14((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<?>[][]> f14Parameter() {
              return f14Parameter;
            }
          }

          public static final class MetaF15Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter = register(new MetaParameter<>(0, "f15",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF15Method() {
              super("f15",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f15((java.util.List<? extends java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f15((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String>[][]> f15Parameter() {
              return f15Parameter;
            }
          }

          public static final class MetaF16Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter = register(new MetaParameter<>(0, "f16",metaArray(java.util.List[][].class, java.util.List[][]::new, metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaType(java.lang.String.class))))));

            private MetaF16Method() {
              super("f16",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f16((java.util.List<? super java.lang.String>[][])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f16((java.util.List[][])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String>[][]> f16Parameter() {
              return f16Parameter;
            }
          }

          public static final class MetaF17Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<boolean[]>> f17Parameter = register(new MetaParameter<>(0, "f17",metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class)))));

            private MetaF17Method() {
              super("f17",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f17((java.util.List<boolean[]>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f17((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<boolean[]>> f17Parameter() {
              return f17Parameter;
            }
          }

          public static final class MetaF18Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter = register(new MetaParameter<>(0, "f18",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF18Method() {
              super("f18",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f18((java.util.List<java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f18((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<java.lang.String[]>[]> f18Parameter() {
              return f18Parameter;
            }
          }

          public static final class MetaF19Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter = register(new MetaParameter<>(0, "f19",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF19Method() {
              super("f19",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f19((java.util.List<? extends java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f19((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? extends java.lang.String[]>[]> f19Parameter() {
              return f19Parameter;
            }
          }

          public static final class MetaF20Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter = register(new MetaParameter<>(0, "f20",metaArray(java.util.List[].class, java.util.List[]::new, metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF20Method() {
              super("f20",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f20((java.util.List<? super java.lang.String[]>[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f20((java.util.List[])(argument));
            }

            public MetaParameter<java.util.List<? super java.lang.String[]>[]> f20Parameter() {
              return f20Parameter;
            }
          }

          public static final class MetaF21Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter = register(new MetaParameter<>(0, "f21",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Boolean.class)))));

            private MetaF21Method() {
              super("f21",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f21((java.util.List<java.util.List<java.lang.Boolean>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f21((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.Boolean>>> f21Parameter() {
              return f21Parameter;
            }
          }

          public static final class MetaF22Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter = register(new MetaParameter<>(0, "f22",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF22Method() {
              super("f22",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f22((java.util.List<java.util.List<java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f22((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.String>>> f22Parameter() {
              return f22Parameter;
            }
          }

          public static final class MetaF23Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<?>>> f23Parameter = register(new MetaParameter<>(0, "f23",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.Object.class)))));

            private MetaF23Method() {
              super("f23",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f23((java.util.List<java.util.List<?>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f23((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<?>>> f23Parameter() {
              return f23Parameter;
            }
          }

          public static final class MetaF24Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter = register(new MetaParameter<>(0, "f24",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF24Method() {
              super("f24",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f24((java.util.List<java.util.List<? extends java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f24((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? extends java.lang.String>>> f24Parameter(
                ) {
              return f24Parameter;
            }
          }

          public static final class MetaF25Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter = register(new MetaParameter<>(0, "f25",metaType(java.util.List.class,metaType(java.util.List.class,metaType(java.lang.String.class)))));

            private MetaF25Method() {
              super("f25",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f25((java.util.List<java.util.List<? super java.lang.String>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f25((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? super java.lang.String>>> f25Parameter(
                ) {
              return f25Parameter;
            }
          }

          public static final class MetaF26Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter = register(new MetaParameter<>(0, "f26",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(boolean[].class, boolean[]::new, metaType(boolean.class))))));

            private MetaF26Method() {
              super("f26",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f26((java.util.List<java.util.List<boolean[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f26((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<boolean[]>>> f26Parameter() {
              return f26Parameter;
            }
          }

          public static final class MetaF27Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter = register(new MetaParameter<>(0, "f27",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF27Method() {
              super("f27",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f27((java.util.List<java.util.List<java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f27((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<java.lang.String[]>>> f27Parameter(
                ) {
              return f27Parameter;
            }
          }

          public static final class MetaF28Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter = register(new MetaParameter<>(0, "f28",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF28Method() {
              super("f28",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f28((java.util.List<java.util.List<? extends java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f28((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? extends java.lang.String[]>>> f28Parameter(
                ) {
              return f28Parameter;
            }
          }

          public static final class MetaF29Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter = register(new MetaParameter<>(0, "f29",metaType(java.util.List.class,metaType(java.util.List.class,metaArray(java.lang.String[].class, java.lang.String[]::new, metaType(java.lang.String.class))))));

            private MetaF29Method() {
              super("f29",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f29((java.util.List<java.util.List<? super java.lang.String[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f29((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<java.util.List<? super java.lang.String[]>>> f29Parameter(
                ) {
              return f29Parameter;
            }
          }

          public static final class MetaF30Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter = register(new MetaParameter<>(0, "f30",metaType(model.model.MixedJavaModel.InnerModel.class)));

            private MetaF30Method() {
              super("f30",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f30((model.model.MixedJavaModel.InnerModel)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f30((model.model.MixedJavaModel.InnerModel)(argument));
            }

            public MetaParameter<model.model.MixedJavaModel.InnerModel> f30Parameter() {
              return f30Parameter;
            }
          }

          public static final class MetaF31Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter = register(new MetaParameter<>(0, "f31",metaType(java.util.List.class,metaType(model.model.MixedJavaModel.InnerModel.class))));

            private MetaF31Method() {
              super("f31",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f31((java.util.List<model.model.MixedJavaModel.InnerModel>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f31((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<model.model.MixedJavaModel.InnerModel>> f31Parameter(
                ) {
              return f31Parameter;
            }
          }

          public static final class MetaF32Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter = register(new MetaParameter<>(0, "f32",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.String.class))));

            private MetaF32Method() {
              super("f32",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f32((java.util.Map<java.lang.String, java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f32((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<java.lang.String, java.lang.String>> f32Parameter() {
              return f32Parameter;
            }
          }

          public static final class MetaF33Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter = register(new MetaParameter<>(0, "f33",metaType(java.util.Map.class,metaType(java.lang.Object.class),metaType(java.lang.String.class))));

            private MetaF33Method() {
              super("f33",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f33((java.util.Map<?, java.lang.String>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f33((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<?, java.lang.String>> f33Parameter() {
              return f33Parameter;
            }
          }

          public static final class MetaF34Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter = register(new MetaParameter<>(0, "f34",metaType(java.util.Map.class,metaType(java.lang.String.class),metaType(java.lang.Object.class))));

            private MetaF34Method() {
              super("f34",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f34((java.util.Map<java.lang.String, ?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f34((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<java.lang.String, ?>> f34Parameter() {
              return f34Parameter;
            }
          }

          public static final class MetaF35Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter = register(new MetaParameter<>(0, "f35",metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)));

            private MetaF35Method() {
              super("f35",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f35((model.model.MixedJavaModel.Enum)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f35((model.model.MixedJavaModel.Enum)(argument));
            }

            public MetaParameter<model.model.MixedJavaModel.Enum> f35Parameter() {
              return f35Parameter;
            }
          }

          public static final class MetaF36Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter = register(new MetaParameter<>(0, "f36",metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

            private MetaF36Method() {
              super("f36",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f36((java.util.List<model.model.MixedJavaModel.Enum>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f36((java.util.List)(argument));
            }

            public MetaParameter<java.util.List<model.model.MixedJavaModel.Enum>> f36Parameter() {
              return f36Parameter;
            }
          }

          public static final class MetaF37Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter = register(new MetaParameter<>(0, "f37",metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

            private MetaF37Method() {
              super("f37",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f37((model.model.MixedJavaModel.Enum[])(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f37((model.model.MixedJavaModel.Enum[])(argument));
            }

            public MetaParameter<model.model.MixedJavaModel.Enum[]> f37Parameter() {
              return f37Parameter;
            }
          }

          public static final class MetaF38Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter = register(new MetaParameter<>(0, "f38",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))));

            private MetaF38Method() {
              super("f38",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f38((java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f38((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, model.model.MixedJavaModel.Enum>> f38Parameter(
                ) {
              return f38Parameter;
            }
          }

          public static final class MetaF39Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter = register(new MetaParameter<>(0, "f39",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf)))));

            private MetaF39Method() {
              super("f39",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f39((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f39((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum>>> f39Parameter(
                ) {
              return f39Parameter;
            }
          }

          public static final class MetaF40Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter = register(new MetaParameter<>(0, "f40",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

            private MetaF40Method() {
              super("f40",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f40((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f40((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<model.model.MixedJavaModel.Enum[]>>> f40Parameter(
                ) {
              return f40Parameter;
            }
          }

          public static final class MetaF41Method extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter = register(new MetaParameter<>(0, "f41",metaType(java.util.Map.class,metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf),metaType(java.util.List.class,metaArray(model.model.MixedJavaModel.Enum[].class, model.model.MixedJavaModel.Enum[]::new, metaEnum(model.model.MixedJavaModel.Enum.class, model.model.MixedJavaModel.Enum::valueOf))))));

            private MetaF41Method() {
              super("f41",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.f41((java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.f41((java.util.Map)(argument));
            }

            public MetaParameter<java.util.Map<model.model.MixedJavaModel.Enum, java.util.List<? extends model.model.MixedJavaModel.Enum[]>>> f41Parameter(
                ) {
              return f41Parameter;
            }
          }

          public static final class MetaGenericFieldMethod extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel.MixedJavaModelBuilder> {
            private final MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter = register(new MetaParameter<>(0, "genericField",metaType(model.model.MixedJavaModel.GenericClass.class,metaType(java.lang.Object.class))));

            private MetaGenericFieldMethod() {
              super("genericField",metaType(model.model.MixedJavaModel.MixedJavaModelBuilder.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.genericField((model.model.MixedJavaModel.GenericClass<?>)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object argument) throws Throwable {
              return instance.genericField((model.model.MixedJavaModel.GenericClass)(argument));
            }

            public MetaParameter<model.model.MixedJavaModel.GenericClass<?>> genericFieldParameter(
                ) {
              return genericFieldParameter;
            }
          }

          public static final class MetaBuildMethod extends InstanceMetaMethod<model.model.MixedJavaModel.MixedJavaModelBuilder, model.model.MixedJavaModel> {
            private MetaBuildMethod() {
              super("build",metaType(model.model.MixedJavaModel.class));
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.build();
            }

            @Override
            public java.lang.Object invoke(
                model.model.MixedJavaModel.MixedJavaModelBuilder instance) throws Throwable {
              return instance.build();
            }
          }
        }

        public static final class MetaInnerModelClass2 extends MetaClass<model.model.MixedJavaModel.InnerModel> {
          private final MetaConstructorConstructor constructor = register(new MetaConstructorConstructor());

          private final MetaField<Long> serialVersionUIDField = register(new MetaField<>("serialVersionUID",metaType(long.class),true));

          private final MetaField<java.lang.Boolean> f1Field = register(new MetaField<>("f1",metaType(boolean.class),false));

          private final MetaField<java.lang.String> f2Field = register(new MetaField<>("f2",metaType(java.lang.String.class),false));

          private final MetaField<Integer> f3Field = register(new MetaField<>("f3",metaType(int.class),false));

          private final MetaField<model.model.JavaModel.InnerModel> f5Field = register(new MetaField<>("f5",metaType(model.model.JavaModel.InnerModel.class),false));

          private final MetaStaticMethodMethod staticMethodMethod = register(new MetaStaticMethodMethod());

          private final MetaInstanceMethodMethod instanceMethodMethod = register(new MetaInstanceMethodMethod());

          private final MetaCompareMethod compareMethod = register(new MetaCompareMethod());

          private final MetaIsF1Method isF1Method = register(new MetaIsF1Method());

          private final MetaGetF2Method getF2Method = register(new MetaGetF2Method());

          private final MetaGetF3Method getF3Method = register(new MetaGetF3Method());

          private final MetaGetF5Method getF5Method = register(new MetaGetF5Method());

          private final MetaIntValueMethod intValueMethod = register(new MetaIntValueMethod());

          private final MetaLongValueMethod longValueMethod = register(new MetaLongValueMethod());

          private final MetaFloatValueMethod floatValueMethod = register(new MetaFloatValueMethod());

          private final MetaDoubleValueMethod doubleValueMethod = register(new MetaDoubleValueMethod());

          private final MetaByteValueMethod byteValueMethod = register(new MetaByteValueMethod());

          private final MetaShortValueMethod shortValueMethod = register(new MetaShortValueMethod());

          private MetaInnerModelClass2() {
            super(metaType(model.model.MixedJavaModel.InnerModel.class));
          }

          public MetaConstructorConstructor constructor() {
            return constructor;
          }

          public MetaField<Long> serialVersionUIDField() {
            return serialVersionUIDField;
          }

          public MetaField<java.lang.Boolean> f1Field() {
            return f1Field;
          }

          public MetaField<java.lang.String> f2Field() {
            return f2Field;
          }

          public MetaField<Integer> f3Field() {
            return f3Field;
          }

          public MetaField<model.model.JavaModel.InnerModel> f5Field() {
            return f5Field;
          }

          public MetaStaticMethodMethod staticMethodMethod() {
            return staticMethodMethod;
          }

          public MetaInstanceMethodMethod instanceMethodMethod() {
            return instanceMethodMethod;
          }

          public MetaCompareMethod compareMethod() {
            return compareMethod;
          }

          public MetaIsF1Method isF1Method() {
            return isF1Method;
          }

          public MetaGetF2Method getF2Method() {
            return getF2Method;
          }

          public MetaGetF3Method getF3Method() {
            return getF3Method;
          }

          public MetaGetF5Method getF5Method() {
            return getF5Method;
          }

          public MetaIntValueMethod intValueMethod() {
            return intValueMethod;
          }

          public MetaLongValueMethod longValueMethod() {
            return longValueMethod;
          }

          public MetaFloatValueMethod floatValueMethod() {
            return floatValueMethod;
          }

          public MetaDoubleValueMethod doubleValueMethod() {
            return doubleValueMethod;
          }

          public MetaByteValueMethod byteValueMethod() {
            return byteValueMethod;
          }

          public MetaShortValueMethod shortValueMethod() {
            return shortValueMethod;
          }

          public static final class MetaConstructorConstructor extends MetaConstructor<model.model.MixedJavaModel.InnerModel> {
            private final MetaParameter<java.lang.Boolean> f1Parameter = register(new MetaParameter<>(0, "f1",metaType(boolean.class)));

            private final MetaParameter<java.lang.String> f2Parameter = register(new MetaParameter<>(1, "f2",metaType(java.lang.String.class)));

            private final MetaParameter<Integer> f3Parameter = register(new MetaParameter<>(2, "f3",metaType(int.class)));

            private final MetaParameter<model.model.JavaModel.InnerModel> f5Parameter = register(new MetaParameter<>(3, "f5",metaType(model.model.JavaModel.InnerModel.class)));

            private MetaConstructorConstructor() {
              super(metaType(model.model.MixedJavaModel.InnerModel.class));
            }

            @Override
            public model.model.MixedJavaModel.InnerModel invoke(java.lang.Object[] arguments) throws
                Throwable {
              return new model.model.MixedJavaModel.InnerModel((boolean)(arguments[0]),(java.lang.String)(arguments[1]),(int)(arguments[2]),(model.model.JavaModel.InnerModel)(arguments[3]));
            }

            public MetaParameter<java.lang.Boolean> f1Parameter() {
              return f1Parameter;
            }

            public MetaParameter<java.lang.String> f2Parameter() {
              return f2Parameter;
            }

            public MetaParameter<Integer> f3Parameter() {
              return f3Parameter;
            }

            public MetaParameter<model.model.JavaModel.InnerModel> f5Parameter() {
              return f5Parameter;
            }
          }

          public static final class MetaStaticMethodMethod extends StaticMetaMethod<Integer> {
            private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

            private MetaStaticMethodMethod() {
              super("staticMethod",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(java.lang.Object[] arguments) throws Throwable {
              return model.model.MixedJavaModel.InnerModel.staticMethod((int)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(java.lang.Object argument) throws Throwable {
              return model.model.MixedJavaModel.InnerModel.staticMethod((int)(argument));
            }

            public MetaParameter<Integer> argumentParameter() {
              return argumentParameter;
            }
          }

          public static final class MetaInstanceMethodMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Integer> {
            private final MetaParameter<Integer> argumentParameter = register(new MetaParameter<>(0, "argument",metaType(int.class)));

            private MetaInstanceMethodMethod() {
              super("instanceMethod",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.instanceMethod((int)(arguments[0]));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object argument) throws Throwable {
              return instance.instanceMethod((int)(argument));
            }

            public MetaParameter<Integer> argumentParameter() {
              return argumentParameter;
            }
          }

          public static final class MetaCompareMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Integer> {
            private final MetaParameter<java.lang.String> o1Parameter = register(new MetaParameter<>(0, "o1",metaType(java.lang.String.class)));

            private final MetaParameter<java.lang.String> o2Parameter = register(new MetaParameter<>(1, "o2",metaType(java.lang.String.class)));

            private MetaCompareMethod() {
              super("compare",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.compare((java.lang.String)(arguments[0]),(java.lang.String)(arguments[1]));
            }

            public MetaParameter<java.lang.String> o1Parameter() {
              return o1Parameter;
            }

            public MetaParameter<java.lang.String> o2Parameter() {
              return o2Parameter;
            }
          }

          public static final class MetaIsF1Method extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, java.lang.Boolean> {
            private MetaIsF1Method() {
              super("isF1",metaType(boolean.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.isF1();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.isF1();
            }
          }

          public static final class MetaGetF2Method extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, java.lang.String> {
            private MetaGetF2Method() {
              super("getF2",metaType(java.lang.String.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF2();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF2();
            }
          }

          public static final class MetaGetF3Method extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Integer> {
            private MetaGetF3Method() {
              super("getF3",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF3();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF3();
            }
          }

          public static final class MetaGetF5Method extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, model.model.JavaModel.InnerModel> {
            private MetaGetF5Method() {
              super("getF5",metaType(model.model.JavaModel.InnerModel.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.getF5();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.getF5();
            }
          }

          public static final class MetaIntValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Integer> {
            private MetaIntValueMethod() {
              super("intValue",metaType(int.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.intValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.intValue();
            }
          }

          public static final class MetaLongValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Long> {
            private MetaLongValueMethod() {
              super("longValue",metaType(long.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.longValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.longValue();
            }
          }

          public static final class MetaFloatValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Float> {
            private MetaFloatValueMethod() {
              super("floatValue",metaType(float.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.floatValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.floatValue();
            }
          }

          public static final class MetaDoubleValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Double> {
            private MetaDoubleValueMethod() {
              super("doubleValue",metaType(double.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.doubleValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.doubleValue();
            }
          }

          public static final class MetaByteValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Byte> {
            private MetaByteValueMethod() {
              super("byteValue",metaType(byte.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.byteValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.byteValue();
            }
          }

          public static final class MetaShortValueMethod extends InstanceMetaMethod<model.model.MixedJavaModel.InnerModel, Short> {
            private MetaShortValueMethod() {
              super("shortValue",metaType(short.class));
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance,
                java.lang.Object[] arguments) throws Throwable {
              return instance.shortValue();
            }

            @Override
            public java.lang.Object invoke(model.model.MixedJavaModel.InnerModel instance) throws
                Throwable {
              return instance.shortValue();
            }
          }
        }
      }
    }
  }
}
