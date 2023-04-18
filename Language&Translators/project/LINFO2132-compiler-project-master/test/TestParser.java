import compiler.Lexer.Lexer;
import compiler.Lexer.Token;
import compiler.Parser.ASTNode;
import compiler.Parser.Nodes.*;
import compiler.Parser.Parser;
import org.junit.Test;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestParser {
    /**
     * Create a lexer and a parser working on the given input.
     * 
     * @param input the code string the parser should read.
     * @return a new Parser of the input.
     */
    private Parser initParser(String input) {
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        return new Parser(lexer);
    }

    @Test
    public void testConstDeclaration_int() throws ParseException {
        Parser parser = initParser("const life int = 42;");

        List<ASTNode> statements = new ArrayList<>();
        statements.add(new CVVNode.Const("life", new TypeNode.Base(Token.INTTYPE), "42"));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    @Test
    public void testConstDeclaration_real() throws ParseException {
        Parser parser = initParser("const PI real = 3.1415;");

        List<ASTNode> statements = new ArrayList<>();
        statements.add(new CVVNode.Const("PI", new TypeNode.Base(Token.REALTYPE), "3.1415"));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    @Test
    public void testConstDeclaration_string() throws ParseException {
        Parser parser = initParser("const hello_world string = \"¡Hola mundo!\";");

        List<ASTNode> statements = new ArrayList<>();
        statements
                .add(new CVVNode.Const("hello_world", new TypeNode.Base(Token.STRINGTYPE), "\"¡Hola mundo!\""));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    @Test
    public void testConstDeclaration_bool() throws ParseException {
        Parser parser = initParser("const isGood2Go bool = true;");

        List<ASTNode> statements = new ArrayList<>();
        statements.add(new CVVNode.Const("isGood2Go", new TypeNode.Base(Token.BOOLTYPE), "true"));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    // TODO reuse for val/var tests because const should be base type
    // @Test
    // public void testConstDeclaration_identifier() throws ParseException {
    // Parser parser = initParser("const ben Person = \"Person('Ben', 20)\";"); //
    // TODO remove string + "Ben" to 'Ben'
    //
    // List<ASTNode> statements = new ArrayList<>();
    // statements.add(new ConstDeclarationNode("ben", new
    // TypeNode.Identifier("Person"), "\"Person('Ben', 20)\""));
    // // TODO no toString() but equals functions
    // assertEquals(new ProgramNode(statements).toString(),
    // parser.getAST().toString());
    // }
    // @Test
    // public void testConstDeclaration_realArray() throws ParseException {
    // Parser parser = initParser("const myScores real[] = \"real[](10)\";"); //
    // TODO remove string -> real assignment
    //
    // List<ASTNode> statements = new ArrayList<>();
    // statements.add(new ConstDeclarationNode("myScores", new TypeNode.Array(new
    // TypeNode.Base(Token.REALTYPE)),
    // "\"real[](10)\""));
    // // TODO no toString() but equals functions
    // assertEquals(new ProgramNode(statements).toString(),
    // parser.getAST().toString());
    // }

    @Test
    public void testRecordDeclaration_string() throws ParseException {
        Parser parser = initParser("record Person { name string; }"); // TODO multiple fields

        List<ASTNode> statements = new ArrayList<>();
        statements.add(new RecordDeclarationNode("Person",
                new FieldDeclarationNode("name", new TypeNode.Base(Token.STRINGTYPE))));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    @Test
    public void testRecordDeclaration_intArray() throws ParseException {
        Parser parser = initParser("record Person { age int[]; }"); // TODO multiple fields

        List<ASTNode> statements = new ArrayList<>();
        statements.add(new RecordDeclarationNode("Person",
                new FieldDeclarationNode("age", new TypeNode.Array(new TypeNode.Base(Token.INTTYPE)))));
        // TODO no toString() but equals functions
        assertEquals(new ProgramNode(statements).toString(), parser.getAST().toString());
    }

    @Test
    public void testBaseType()
            throws ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Parser.class.getDeclaredMethod("parseBaseType");
        method.setAccessible(true);

        Parser int_parser = initParser("int");
        Parser real_parser = initParser("real");
        Parser string_parser = initParser("string");
        Parser bool_parser = initParser("bool");

        TypeNode.Base int_result = (TypeNode.Base) method.invoke(int_parser);
        TypeNode.Base real_result = (TypeNode.Base) method.invoke(real_parser);
        TypeNode.Base string_result = (TypeNode.Base) method.invoke(string_parser);
        TypeNode.Base bool_result = (TypeNode.Base) method.invoke(bool_parser);

        assertEquals(new TypeNode.Base(Token.INTTYPE).toString(), int_result.toString());
        assertEquals(new TypeNode.Base(Token.REALTYPE).toString(), real_result.toString());
        assertEquals(new TypeNode.Base(Token.STRINGTYPE).toString(), string_result.toString());
        assertEquals(new TypeNode.Base(Token.BOOLTYPE).toString(), bool_result.toString());
    }
}
