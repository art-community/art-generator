package ru.art.generator.javac.model;

import lombok.*;
import ru.art.generator.javac.exception.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import java.util.*;

@Getter
public class ModuleModel {
    private final List<ExistedClass> services = new LinkedList<>();

    //Stub method
    public ModuleModel service(Class<?> service) {
        return this;
    }

    public static ModuleModel module() {
        return new ModuleModel();
    }

    public static ModuleModel parse(JCExpressionStatement statement) {
        ModuleModel moduleModel = new ModuleModel();
        switch (statement.expr.getKind()) {
            case METHOD_INVOCATION:
                JCMethodInvocation resultMethodInvocation = (JCMethodInvocation) statement.expr;
                JCFieldAccess moduleMethodSelect = (JCFieldAccess) resultMethodInvocation.getMethodSelect();
                switch (moduleMethodSelect.name.toString()) {
                    case "service":
                        JCFieldAccess serviceClass = (JCFieldAccess) resultMethodInvocation.getArguments().head;
                        switch (serviceClass.name.toString()) {
                            case "class":
                                moduleModel.services.add(getExistedClass(serviceClass.selected.toString()));
                                break;
                        }
                        break;
                }
                break;
            default:
                throw new GenerationException("Unable to parse module model");
        }
        return moduleModel;
    }
}
