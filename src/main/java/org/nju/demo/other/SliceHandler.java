package org.nju.demo.other;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.SwitchEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class SliceHandler {

    public static String sliceFile(File file, String methodName, List<Integer> lines) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            List<MethodDeclaration> methodList = cu.findAll(MethodDeclaration.class);

            for (MethodDeclaration m : methodList) {
                if (methodName.equals(m.getNameAsString())) {
                    // 方法体存在
                    if (m.getBody().isPresent()) {
                        sliceMethod(m, lines);
                    }
                } else {
                    m.remove();
                }
            }
            cu.removePackageDeclaration();
            removeComments(cu);
            return cu.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.toString() + " " + file.getAbsolutePath());
        }
        System.out.println(String.format("%s method not in %s", methodName, file.getAbsolutePath()));
        return "";
    }

    private static void removeComments(CompilationUnit cu) {
        // 获得语法树中所有的注释节点
        List<Comment> comments = cu.getAllComments();
        comments.forEach(Node::remove);
    }

    public static String sliceFile(File file, List<Integer> lines) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            List<MethodDeclaration> methodList = cu.findAll(MethodDeclaration.class);
            for (MethodDeclaration m : methodList) {
                m.getRange().ifPresent(r -> {
                    if (r.contains(new Position(lines.get(0), 100))) {
                        sliceMethod(m, lines);
                    } else {
                        m.remove();
                    }
                });
            }
            return cu.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.toString() + " " + file.getAbsolutePath());
        }
        return "";
    }

    private static void sliceMethod(MethodDeclaration method, List<Integer> lines) {
//        walk函数对AST上的节点做层次遍历。
        method.getBody().get().walk(node -> {
            if (node.getRange().isPresent()) {
//                System.out.println(node.getClass() + "_" + node.toString() + "_" + node.getBegin().get().line + "_" + node.getEnd().get().line);
                if (node.getParentNode().isPresent() && node instanceof CatchClause) {
                    return;
                }
                int start = node.getRange().get().begin.line;
                int end = node.getRange().get().end.line;
                boolean flag = false;
                for (int line : lines) {
                    if (line >= start && line <= end) {
                        flag = true;
                        break;
                    }
                }
                /**
                 * 如果当前节点的父节点是SwitchEntry类型,并且是父节点的第一个子节点，则不删除。
                 * 父节点:case Const.MULTIANEWARRAY:
                 * 当前节点:Const.MULTIANEWARRAY
                 *
                 * 假设lines的范围只包含case语句下面的一条语句，由于当前节点的范围不在lines的范围中，就会误删该节点
                 * 最终父节点下的第一个子节点为空，结果就会显示为null
                 */
                if (node.getParentNode().isPresent() && node.getParentNode().get() instanceof SwitchEntry
                        && node == node.getParentNode().get().getChildNodes().get(0)) {
                    flag = true;
                }

                /**
                 * 如果当前节点是catch语句中的非blockstmt节点，例如参数类型节点等，需要和catchclause节点一起保留
                 * 递归查找父节点，以防误删。
                 */
                Node temp = node;
                while(temp.getParentNode().isPresent()) {
                    temp = temp.getParentNode().get();
                    if (temp instanceof Parameter && temp.getParentNode().isPresent()
                            && temp.getParentNode().get() instanceof CatchClause) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    node.remove();
                }
            }
        });
    }
}
