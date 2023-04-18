package compiler.Parser.Nodes;

import compiler.Parser.ASTNode;

public class FieldDeclarationNode extends ASTNode {
    String identifier;
    TypeNode type;

    public FieldDeclarationNode(String identifier, TypeNode type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(field " + identifier + " " + type + ")";
    }
}
