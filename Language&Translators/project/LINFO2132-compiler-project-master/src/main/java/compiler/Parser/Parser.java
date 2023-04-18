package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.Token;
import compiler.Parser.Nodes.*;

import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public final LB lb; // TODO make private

    public Parser(Lexer lexer) {
        this.lb = new LB(lexer);
    }

    public ASTNode getAST() throws ParseException {
        List<ASTNode> statements = new ArrayList<>();
        while (lb.peek() != null)
            statements.add(parseStatement());

        return new ProgramNode(statements);
    }

    private ASTNode parseStatement() throws ParseException {
        Symbol symbol = lb.peek();
        // TODO their can be multiple chained const declarations, etc.
        switch (symbol.getToken()) {
            case CONST:
                return parseConstDeclaration();
            case RECORD:
                return parseRecordDeclaration();
        }
        throw new ParseException("Unexpected token: " + symbol.getToken(), 0);
    }

    private ASTNode parseConstDeclaration() throws ParseException {
        lb.consume(Token.CONST);
        String identifier = lb.match(Token.IDENTIFIER).getContent();
        TypeNode.Base baseType = parseBaseType();
        lb.consume(Token.ASSIGNMENT);
        String expression = lb.get().getContent(); // TODO parseExpression
        lb.consume(Token.SEMICOLON);
        return new CVVNode.Const(identifier, baseType, expression);
    }

    private CVVNode parseValVarDeclaration() throws ParseException {
        Token token = lb.peek().getToken();
        if (token == Token.VAL)
            return parseValue();
        else if (token == Token.VAR)
            return parseVariable();

        throw new ParseException("Unexpected val/var initialization.", 0);
    }

    private CVVNode.Val parseValue() throws ParseException {
        lb.consume(Token.VAL);
        String identifier = lb.match(Token.IDENTIFIER).getContent();
        TypeNode.Base baseType = parseBaseType();
        lb.consume(Token.ASSIGNMENT);
        String expression = lb.get().getContent();
        lb.consume(Token.SEMICOLON);
        return new CVVNode.Val(identifier, baseType, expression);
    }

    private CVVNode.Var parseVariable() throws ParseException {
        lb.consume(Token.VAR);
        String identifier = lb.match(Token.IDENTIFIER).getContent();
        TypeNode.Base baseType = parseBaseType();
        lb.consume(Token.ASSIGNMENT);
        String expression = lb.get().getContent(); // TODO parseExpression
        lb.consume(Token.SEMICOLON);
        return new CVVNode.Var(identifier, baseType, expression);
    }

    private ASTNode parseRecordDeclaration() throws ParseException {
        lb.consume(Token.RECORD);
        String identifier = lb.match(Token.IDENTIFIER).getContent();
        lb.consume(Token.OPENCURLYBRACKETS);
        FieldDeclarationNode field = parseFieldDeclaration(); // TODO field declaration
        lb.consume(Token.CLOSECURLYBRACKETS);
        return new RecordDeclarationNode(identifier, field);
    }

    private FieldDeclarationNode parseFieldDeclaration() throws ParseException {
        String identifier = lb.match(Token.IDENTIFIER).getContent();
        TypeNode type = parseType();
        lb.consume(Token.SEMICOLON);
        return new FieldDeclarationNode(identifier, type);
    }

    private TypeNode parseType() throws ParseException { // TODO type tests still ok after <base-type>[] arrays?
        Token token = lb.peek().getToken();

        switch (token) {
            case INTTYPE, REALTYPE, STRINGTYPE, BOOLTYPE -> {
                TypeNode.Base baseType = parseBaseType();
                if (lb.peek().getToken() == Token.OPENBRACKETS) {
                    lb.consume(Token.OPENBRACKETS);
                    lb.consume(Token.CLOSEBRACKETS);
                    return new TypeNode.Array(baseType);
                } else
                    return baseType;
            }
            case IDENTIFIER -> new TypeNode.Identifier(lb.get().getContent());
        }
        throw new ParseException("Unexpected type.", 0);
    }

    private TypeNode.Base parseBaseType() throws ParseException {
        Token token = lb.get().getToken();

        if (token == Token.INTTYPE || token == Token.REALTYPE || token == Token.STRINGTYPE || token == Token.BOOLTYPE)
            return new TypeNode.Base(token);

        throw new ParseException("Expected base type (int | real | string | bool) but got " + token + ".", 0);
    }

    // TODO remove if useless
    // private TypeNode.Array parseArrayType() throws ParseException {
    // Symbol symbol = lb.get();
    // Token token = symbol.getToken();
    //
    // if (lb.peek().getToken() == Token.OPENBRACKETS) {
    // lb.consume(Token.OPENBRACKETS);
    // lb.consume(Token.CLOSEBRACKETS);
    // return new TypeNode.Array(new TypeNode.Base(token));
    // }
    //
    // throw new ParseException("Expected array type (int | real | string | bool)
    // but got " + token + ".", 0);
    // }
}
