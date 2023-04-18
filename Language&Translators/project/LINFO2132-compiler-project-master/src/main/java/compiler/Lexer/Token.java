package compiler.Lexer;

public enum Token {
    SEPARATOR, // " ", "\t", "\n"
    COMMENT, // "// bla bla bla"
    IDENTIFIER, // function and variable names
    CONST, // const
    RECORD, // record
    VAR, // var
    VAL, // val
    PROC, // proc
    FOR, // for
    TO, // to
    BY, // by
    WHILE, // while
    IF, // if
    ELSE, // else
    RETURN, // return
    AND, // and
    OR, // or
    INTTYPE, // int
    REALTYPE, // real
    STRINGTYPE, // string
    BOOLTYPE, // bool
    INT, // integer value (19)
    REAL, // real value (3.14)
    STRING, // string value ("yo!")
    BOOL, // boolean value (true)
    ASSIGNMENT, // =
    ADDITION, // +
    SUBTRACTION, // -
    MULTIPLICATION, // *
    DIVISION, // /
    MODULO, // %
    EQUAL, // =
    DIFFERENT, // <>
    SMALLER, // <
    GREATER, // >
    SEQ, // <=
    GEQ, // >=
    OPENPARENTHESIS, // (
    CLOSEPARENTHESIS, // )
    OPENCURLYBRACKETS, // {
    CLOSECURLYBRACKETS, // }
    OPENBRACKETS, // [
    CLOSEBRACKETS, // ]
    DOT, // .
    SEMICOLON, // ;
    COMMA // ,
}
