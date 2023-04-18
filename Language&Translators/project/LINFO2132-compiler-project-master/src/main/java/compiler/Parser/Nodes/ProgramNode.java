package compiler.Parser.Nodes;

import compiler.Parser.ASTNode;

import java.util.List;

public class ProgramNode extends ASTNode {
    List<ASTNode> statements;

    public ProgramNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "statements=" + statements +
                '}';
    }
}
