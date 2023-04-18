import compiler.Lexer.Token;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;

import static org.junit.Assert.*;

public class TestLexer {

    @Test
    public void testAssignment() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertEquals(new Symbol(Token.VAR, "var"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "x"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INTTYPE, "int"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "2"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testMath() {
        String input = "x += 10 * 1 / (2 - (3+4))";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertEquals(new Symbol(Token.IDENTIFIER, "x"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ADDITION, "+"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "10"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.MULTIPLICATION, "*"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "1"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.DIVISION, "/"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.OPENPARENTHESIS, "("), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "2"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SUBTRACTION, "-"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.OPENPARENTHESIS, "("), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "3"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ADDITION, "+"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "4"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.CLOSEPARENTHESIS, ")"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.CLOSEPARENTHESIS, ")"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testLOOP() {
        String input = "for i=1 to 100 by 2"; // from code_example.lang
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.FOR, "for"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "i"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "1"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.TO, "to"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "100"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.BY, "by"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "2"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testBRACKETS() {
        String input = "var c int[] = int[](5);"; // from code_example.lang
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.VAR, "var"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "c"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INTTYPE, "int"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.OPENBRACKETS, "["), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.CLOSEBRACKETS, "]"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INTTYPE, "int"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.OPENBRACKETS, "["), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.CLOSEBRACKETS, "]"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.OPENPARENTHESIS, "("), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "5"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.CLOSEPARENTHESIS, ")"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testCOMMENT() {
        String input = "//This is a comment.\n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.COMMENT, "//This is a comment."), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testMultiline() {
        String input = "var a int = 3;\n" +
                "val e int = a*2;"; // from code_example.lang
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.VAR, "var"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "a"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INTTYPE, "int"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "3"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.VAL, "val"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "e"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INTTYPE, "int"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "a"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.MULTIPLICATION, "*"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.INT, "2"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testReal() {
        String input = "var x real = 10.2432;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.VAR, "var"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "x"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.REALTYPE, "real"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.REAL, "10.2432"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testString() {
        String input = "var s string = \"var this_is_a_string\"";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.VAR, "var"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "s"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.STRINGTYPE, "string"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.STRING, "\"var this_is_a_string\""), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void testBool() {
        String input = "val x bool = true;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(new Symbol(Token.VAL, "val"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.IDENTIFIER, "x"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.BOOLTYPE, "bool"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.ASSIGNMENT, "="), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.BOOL, "true"), lexer.getNextSymbol());
        assertEquals(new Symbol(Token.SEMICOLON, ";"), lexer.getNextSymbol());
        assertNull(lexer.getNextSymbol());
    }

}
