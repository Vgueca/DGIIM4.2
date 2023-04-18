package compiler.Parser.Nodes;

import compiler.Lexer.Token;
import compiler.Parser.ASTNode;

import java.text.ParseException;

public class TypeNode extends ASTNode {
    public static class Base extends TypeNode {
        Token token;

        @Override
        public String toString() {
            return token.toString();
        }

        public Base(Token token) throws ParseException {
            switch (token) {
                case INTTYPE, REALTYPE, STRINGTYPE, BOOLTYPE -> this.token = token;
                default ->
                    throw new ParseException("Basic TypeNode created but not with int, real, string or bool.", 0);
            }

        }
    }

    public static class Identifier extends TypeNode {
        String identifier;

        @Override
        public String toString() {
            return "Identifier{" + identifier + "}";
        }

        public Identifier(String identifier) {
            this.identifier = identifier;
        }
    }

    public static class Array extends TypeNode {
        TypeNode.Base baseType;

        @Override
        public String toString() {
            return baseType.toString() + "[]";
        }

        public Array(TypeNode.Base baseType) {
            this.baseType = baseType;
        }
    }
}
