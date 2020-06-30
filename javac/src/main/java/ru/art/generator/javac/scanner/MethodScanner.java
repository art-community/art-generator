package ru.art.generator.javac.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static com.sun.tools.javac.util.List.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.*;
import java.util.List;
import java.util.*;

public class MethodScanner extends TreePathScanner<Object, Trees> {
    private final Map<String, ClassMethod> classMethods = new LinkedHashMap<>();
    private JCMethodDecl mainDeclaration;
    private final TreeMaker maker;
    private final JavacElements elements;

    public MethodScanner(TreeMaker maker, JavacElements elements) {
        this.maker = maker;
        this.elements = elements;
    }

    @Override
    public Object scan(TreePath path, Trees trees) {
        Object scanResult = super.scan(path, trees);
        if (isNull(mainDeclaration)) {
            return scanResult;
        }
        JCBlock mainBody = mainDeclaration.getBody();
        ListBuffer<JCStatement> mainStatements = new ListBuffer<>();

        List<String> rsocketFunctions = mainBody.stats
                .stream()
                .map(JCTree::getTree)
                .filter(expression -> expression.getKind() == METHOD_INVOCATION)
                .map(invocation -> ((JCMethodInvocation) invocation))
                .filter(invocation -> ((JCIdent) invocation.getMethodSelect()).name.toString().equals("rsocket"))
                .map(invocation -> ((JCIdent) invocation.getArguments().head).name.toString())
                .collect(toList());


        rsocketFunctions.forEach(function -> {
            JCMethodDecl methodDeclaration = classMethods.get(function).methodDeclaration;
            JCIdent requestType = (JCIdent) methodDeclaration.getParameters().head.getType();
            JCIdent responseType = (JCIdent) methodDeclaration.getReturnType();

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
                            ((JCIdent) classMethods.get(function).classDeclaration.getTree()),
                            nil())
                    )
            );

            mainStatements.add(maker.Exec(rsocketHandleApply));
        });
        mainStatements.add(maker.Exec(
                maker.Apply(
                        nil(),
                        maker.Select(maker.Apply(
                                nil(),
                                maker.Select(maker.Ident(elements.getName("ru.art.rsocket.server.RsocketServer")), elements.getName("rsocketTcpServer")),
                                nil()
                        ), elements.getName("await")),
                        nil()
                ))
        );
        mainBody.stats = mainStatements.toList();
        System.out.println(mainBody);
        return scanResult;
    }

    @Override
    public Object visitMethod(MethodTree node, Trees trees) {
        if (node.getName().toString().equals("main") && isNull(mainDeclaration)) {
            mainDeclaration = (JCMethodDecl) node;
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
