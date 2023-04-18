package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
    Reader input;
    String content;

    public Lexer(Reader input) {
        this.input = input;
        content = "";
    }

    public Symbol getNextSymbol() {
        boolean alreadyRead = false;
        Symbol bestMatch = null;
        int bestLength = 0;

        try {
            while (input.ready()) {
                if (alreadyRead || content.length() == 0)
                    content += (char) input.read();

                Token found = Symbol.findMatch(content);
                if (found == null) {
                    content = content.substring(bestLength);
                    break; // longest match
                }
                alreadyRead = true;
                bestMatch = new Symbol(found, content);
                bestLength++;
            }
        } catch (IOException e) {
            return bestMatch;
        }

        // if the match is a separator, get next token
        if (bestMatch != null && bestMatch.getToken() == Token.SEPARATOR)
            return getNextSymbol();

        return bestMatch;
    }
}
