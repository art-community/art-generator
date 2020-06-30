package ru.art.generator.javac.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import java.util.List;
import java.util.*;

public class MethodScanner extends TreePathScanner<Object, Trees> {
    private final Map<String, ClassMethod> classMethods = new LinkedHashMap<>();
    private JCMethodDecl mainMethodDeclaration;
    private JCClassDecl mainClassDeclaration;
    private final TreeMaker maker;
    private final JavacElements elements;

    public MethodScanner(TreeMaker maker, JavacElements elements) {
        this.maker = maker;
        this.elements = elements;
    }

    @Override
    public Object scan(TreePath path, Trees trees) {
        Object scanResult = super.scan(path, trees);
        if (isNull(mainMethodDeclaration)) {
            return scanResult;
        }
        JCBlock mainBody = mainMethodDeclaration.getBody();
        ListBuffer<JCStatement> mainStatements = new ListBuffer<>();

        List<String> rsocketFunctions = mainBody.stats
                .stream()
                .map(statement -> ((JCExpressionStatement) statement).getExpression())
                .filter(expression -> expression.getKind() == METHOD_INVOCATION)
                .map(invocation -> ((JCMethodInvocation) invocation))
                .filter(invocation -> ((JCIdent) invocation.getMethodSelect()).name.toString().equals("rsocket"))
                .map(invocation -> ((JCIdent) invocation.getArguments().head).name.toString())
                .collect(toList());


        rsocketFunctions.forEach(function -> {
            JCMethodDecl methodDeclaration = classMethods.get(function).methodDeclaration;
            JCIdent requestType = (JCIdent) methodDeclaration.getParameters().head.getType();
            JCIdent responseType = (JCIdent) methodDeclaration.getReturnType();

            JCClassDecl requestClass = (JCClassDecl) elements.getTree(requestType.sym);
            JCClassDecl responseClass = (JCClassDecl) elements.getTree(responseType.sym);

            ListBuffer<JCTree> requestFields = new ListBuffer<>();
            requestFields.addAll(requestClass.defs);
            requestFields.add(maker.VarDef(
                    maker.Modifiers(InterfaceVarFlags),
                    elements.getName("to" + requestType.getName().toString()),
                    maker.TypeApply(maker.Ident(elements.getName("ValueToModelMapper")), of(requestType, maker.Ident(elements.getName("Entity")))),
                    maker.Lambda(of(maker.VarDef(new Symbol.VarSymbol(PARAMETER, elements.getName("entity"), elements.getTypeElement("ru.art.entity.Entity").type, requestClass.sym), null)),
                            maker.Apply(
                                    nil(),
                                    maker.Select(
                                            maker.Apply(
                                                    nil(),
                                                    maker.Select(
                                                            maker.Apply(nil(), maker.Select(requestType, elements.getName("builder")), nil()),
                                                            elements.getName("input")),
                                                    of(maker.Apply(nil(), maker.Select(maker.Ident(elements.getName("entity")), elements.getName("getString")), of(maker.Literal("input"))))),
                                            elements.getName("build")),
                                    nil())
                            )
                    )
            );

            requestClass.defs = requestFields.toList();

            JCMethodInvocation rsocketApply = maker.Apply(
                    nil(),
                    maker.Select(maker.Ident(elements.getName("RsocketServiceFunction")), elements.getName("rsocket")),
                    of(maker.Literal(function))
            );

            JCMethodInvocation rsocketRequestMapperApply = maker.Apply(
                    nil(),
                    maker.Select(rsocketApply, elements.getName("requestMapper")),
                    of(maker.Select(
                            requestType,
                            elements.getName("to" + requestType.getName().toString())))
            );

            JCMethodInvocation rsocketResponseMapperApply = maker.Apply(
                    nil(),
                    maker.Select(rsocketRequestMapperApply, elements.getName("responseMapper")),
                    of(maker.Select(
                            responseType,
                            elements.getName("from" + responseType.getName().toString())))
            );

            JCMethodInvocation rsocketHandleApply = maker.Apply(
                    nil(),
                    maker.Select(rsocketResponseMapperApply, elements.getName("handle")),
                    of(maker.Reference(
                            INVOKE,
                            elements.getName(function),
                            maker.Ident(elements.getName(classMethods.get(function).classDeclaration.name.toString())),
                            null)
                    )
            );

            mainStatements.add(maker.Exec(rsocketHandleApply));
        });
        mainStatements.add(maker.Exec(
                maker.Apply(
                        nil(),
                        maker.Select(maker.Apply(
                                nil(),
                                maker.Select(maker.Ident(elements.getName("RsocketServer")), elements.getName("rsocketTcpServer")),
                                nil()
                        ), elements.getName("await")),
                        nil()
                ))
        );
        mainBody.stats = mainStatements.toList();
        JCCompilationUnit mainPackage = elements.getTreeAndTopLevel(mainClassDeclaration.sym, null, null).snd;
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.add(maker.Import(maker.Select(maker.Ident(elements.getName("ru.art.rsocket.function")), elements.getName("RsocketServiceFunction")), false));
        definitions.add(maker.Import(maker.Select(maker.Ident(elements.getName("ru.art.rsocket.server")), elements.getName("RsocketServer")), false));
        definitions.add(maker.Import(maker.Select(maker.Ident(elements.getName("ru.art.entity.mapper")), elements.getName("ValueToModelMapper")), false));
        definitions.add(maker.Import(maker.Select(maker.Ident(elements.getName("ru.art.entity.mapper")), elements.getName("ValueFromModelMapper")), false));
        definitions.add(maker.Import(maker.Select(maker.Ident(elements.getName("ru.art.entity")), elements.getName("Entity")), false));
        definitions.addAll(mainPackage.defs);

        mainPackage.defs = definitions.toList();
        System.out.println(mainPackage);
        return scanResult;
    }

    @Override
    public Object visitMethod(MethodTree node, Trees trees) {
        if (node.getName().toString().equals("main") && isNull(mainMethodDeclaration)) {
            mainMethodDeclaration = (JCMethodDecl) node;
            return super.visitMethod(node, trees);
        }
        return super.visitMethod(node, trees);
    }

    @Override
    public Object visitClass(ClassTree node, Trees trees) {
        JCClassDecl classDeclaration = (JCClassDecl) node;
        if (node.getMembers().stream().anyMatch(member -> member.getKind() == VARIABLE)) {
            return super.visitClass(node, trees);
        }

        if (node.getMembers().stream().anyMatch(member -> member.getKind() == METHOD && ((JCMethodDecl) member).name.toString().equals("main"))) {
            mainClassDeclaration = (JCClassDecl) node;
        }

        node.getMembers()
                .stream()
                .filter(member -> member.getKind() == METHOD)
                .map(member -> (JCMethodDecl) member)
                .filter(method -> method.getModifiers().getFlags().contains(PUBLIC))
                .filter(method -> method.getModifiers().getFlags().contains(STATIC))
                .filter(method -> !method.getName().toString().equals("<init>"))
                .filter(method -> !method.getName().toString().equals("main"))
                .forEach(method -> classMethods.put(method.getName().toString(), new ClassMethod(classDeclaration, method)));
        return super.visitClass(node, trees);
    }


    @AllArgsConstructor
    private class ClassMethod {
        JCClassDecl classDeclaration;
        JCMethodDecl methodDeclaration;
    }
}
