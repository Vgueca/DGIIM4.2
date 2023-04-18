package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.Token;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Lexer Buffer with symbol preview.
 */
public class LB {
    Lexer lexer;
    Queue<Symbol> symbols;

    public LB(Lexer lexer) {
        this.lexer = lexer;
        symbols = new LinkedList<>();
    }

    /**
     * Get the next symbol.
     * 
     * @return the next Symbol.
     */
    public Symbol get() {
        if (!symbols.isEmpty())
            return symbols.remove();
        return lexer.getNextSymbol();
    }

    /**
     * Get the next symbol and check if the token type is right.
     * 
     * @param token the token type of the next symbol.
     * @return the next symbol.
     * @throws ParseException if the types don't match.
     */
    public Symbol match(Token token) throws ParseException {
        return matchAny(new Token[] { token });
    }

    /**
     * Get the next symbol and check if the token type matches any one of tokens.
     * 
     * @param tokens an array of token types that the next symbol could be.
     * @return the next symbol.
     * @throws ParseException if the type don't match with any of the array.
     */
    public Symbol matchAny(Token[] tokens) throws ParseException {
        Symbol symbol = get();

        if (!Arrays.asList(tokens).contains(symbol.getToken()))
            throw new ParseException("Expected a " + Arrays.toString(tokens) + " token but got a " + symbol.getToken(),
                    0);

        return symbol;
    }

    /**
     * Consume the next symbol while checking its token type.
     * 
     * @param token the token type of the next symbol.
     * @throws ParseException if the types don't match.
     */
    public void consume(Token token) throws ParseException {
        match(token);
    }

    /**
     * Get the next symbol without consuming it.
     * 
     * @return the next symbol.
     */
    public Symbol peek() {
        if (symbols.isEmpty())
            symbols.add(lexer.getNextSymbol());
        return symbols.peek();
    }
}
