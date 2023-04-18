package compiler.Parser.Nodes;

import compiler.Parser.ASTNode;

public class RecordDeclarationNode extends ASTNode {
    String identifier;
    FieldDeclarationNode field; // TODO allow multiple declarations

    public RecordDeclarationNode(String identifier, FieldDeclarationNode field) {
        this.identifier = identifier;
        this.field = field;
    }

    @Override
    public String toString() {
        return "(record " + identifier + " " + field + ")";
    }
}
