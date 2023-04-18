package compiler.Parser.Nodes;

import compiler.Parser.ASTNode;

/**
 * CVVNode is a Constant-Value-Variable node that represents any of a const, val, or var.
 */
public class CVVNode extends ASTNode {
    String identifier;
    TypeNode type;
    String expression; // TODO make expression object

    public CVVNode(String identifier, TypeNode type, String expression) {
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
    }

    public static class Const extends CVVNode {
        public Const(String identifier, TypeNode.Base baseType, String expression) {
            super(identifier, baseType, expression);
        }

        @Override
        public String toString() {
            return "(const " + identifier + " " + type + " " + expression + ")";
        }
    }

    public static class Val extends CVVNode {
        public Val(String identifier, TypeNode.Base baseType, String expression) {
            super(identifier, baseType, expression);
        }

        @Override
        public String toString() {
            return "(val " + identifier + " " + type + " " + expression + ")";
        }
    }

    public static class Var extends CVVNode {
        public Var(String identifier, TypeNode type, String expression) {
            super(identifier, type, expression);
        }

        @Override
        public String toString() {
            return "(var " + identifier + " " + type + " " + expression + ")";
        }
    }
}
