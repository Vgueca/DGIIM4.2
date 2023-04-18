# Progress in the Backus-Naur form of the custom language

```
🔴 Not started.
🟠 Started.
🟡 Nearly finished.
🟢 Done.
```

```go
🟠 <program> ::= <const>* <record-declaration>* <global-variable>* <procedure>*

🟡 <const> ::= "const" <IDENTIFIER> <base-type> "=" <expression> ";"
🟡 <value> ::= "val" <IDENTIFIER> <base-type> "=" <expression> ";"
🟡 <variable> ::= "var" <IDENTIFIER> <type> "=" <expression> ";"
🟢 <val-var> ::= <variable> | <value>
🟢 <record-declaration> ::= "record" <IDENTIFIER> "{" <field-declaration>* "}"
🟢 <field-declaration> ::= <IDENTIFIER> <type> ";"

🟢 <type> ::= <base-type> | <IDENTIFIER> | <array-type>
🟢 <base-type> ::= "int" | "real" | "bool" | "string"
🟢 <array-type> ::= <base-type> "[]"

🔴 <expression> ::= <bool-term> ( ( "or" | "and" ) <bool-term> )*
🔴 <bool-term> ::= <bool-factor> ( ( "<" | "<=" | ">" | ">=" | "==" | "<>" ) <bool-factor> )*
🔴 <bool-factor> ::= <arith-term> ( ( "+" | "-" ) <arith-term> )*
🔴 <arith-term> ::= <arith-factor> ( ( "*" | "/" | "%" ) <arith-factor> )*
🔴 <arith-factor> ::= ( "+" | "-" )? <primary>
🔴 <primary> ::= <literal> | <identifier> | <function-call> | "(" <expression> ")" | <array-element> | <record-field>

🔴 <literal> ::= <INT> | <REAL> | <BOOL> | <STRING>
🔴 <function-call> ::= <IDENTIFIER> "(" (<expression> ("," <expression>)*)? ")"
🔴 <procedure> ::= "proc" <IDENTIFIER> "(" <parameter-list> ")" <return-type> <block-statement>
🔴 <parameter-list> ::= (<parameter> ("," <parameter>)*)*
🔴 <parameter> ::= <IDENTIFIER> <type>
🔴 <return-type> ::= ":" <type> | ":void"
🔴 <block> ::= "{" <statement>* "}"
🔴 <statement> ::= <expression> ";" | <val-var> | <if-statement> | <while-statement> | <for-statement> | <return-statement> | <delete-statement>
🔴 <if-statement> ::= "if" "(" <expression> ")" <block-statement> ("else" <block-statement>)
```
