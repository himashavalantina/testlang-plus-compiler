%%

// Configuration for JFlex and CUP
%class Lexer
%implements sym
%unicode
%line
%column
%cup

// Import CUP's symbol constants interface and define the package
%{
package com.testlang.compiler; // <-- FIX: Package declaration moved here
import java_cup.runtime.*;
 
 private java_cup.runtime.Symbol symbol(int type) {
  return new java_cup.runtime.Symbol(type, yyline, yycolumn);
 }

 private java_cup.runtime.Symbol symbol(int type, Object value) {
  // Line and column reporting should be adjusted for human readability (start at 1)
  return new java_cup.runtime.Symbol(type, yyline + 1, yycolumn + 1, value);
 }
%}

// Regular Expressions Definitions
EOL             = \r|\n|\r\n
WHITESPACE      = {EOL} | [ \t\f]
LETTER          = [a-zA-Z]
DIGIT           = [0-9]
IDENTIFIER      = {LETTER} ({LETTER}|{DIGIT}|_)*
// Standard string literal: enclosed in double quotes, handles escaped quotes.
STRING_LITERAL  = \" ([^\"\\] | \\.)* \"
// Triple-quoted string for multiline bodies
// Note: This pattern is complex and highly dependent on exact interpretation.
// The provided pattern is generally correct for handling escaped chars and embedded double quotes.
TRIPLE_STRING   = \"\"\" ([^\"\\] | \"[^\"] | \"\"[^\"] | \\.)* \"\"\"
NUMBER_LITERAL  = {DIGIT}+

// State for single-line comments
%x COMMENT

%%

// State: YYINITIAL (initial state)
<YYINITIAL> {
    // ---------------------- Keywords ----------------------
    "config"        { return symbol(sym.CONFIG); }
    "base_url"      { return symbol(sym.BASE_URL); }
    "header"        { return symbol(sym.HEADER); }
    "let"           { return symbol(sym.LET); }
    "test"          { return symbol(sym.TEST); }
    "expect"        { return symbol(sym.EXPECT); }
    "status"        { return symbol(sym.STATUS); }
    "body"          { return symbol(sym.BODY); }
    "contains"      { return symbol(sym.CONTAINS); }
    "capture"       { return symbol(sym.CAPTURE); }
    "from"          { return symbol(sym.FROM); }
    "GET"           { return symbol(sym.GET); }
    "POST"          { return symbol(sym.POST); }
    "PUT"           { return symbol(sym.PUT); }
    "DELETE"        { return symbol(sym.DELETE); }
    "in"            { return symbol(sym.IN); } 

    // ---------------------- Operators and Symbols ----------------------
    "="             { return symbol(sym.EQ); }
    ";"             { return symbol(sym.SEMICOLON); }
    "{"             { return symbol(sym.LBRACE); }
    "}"             { return symbol(sym.RBRACE); }
    "("             { return symbol(sym.LPAREN); }
    ")"             { return symbol(sym.RPAREN); }
    ".."            { return symbol(sym.RANGE); } 

    // ---------------------- Literals ----------------------
    {NUMBER_LITERAL}    { return symbol(sym.NUMBER, Integer.valueOf(yytext())); }
    {TRIPLE_STRING}     {
        // Remove the triple quotes (first 3 and last 3 characters)
        String text = yytext().substring(3, yytext().length() - 3);
        // Add logic here if you need to unescape internal characters (e.g., \" to ")
        return symbol(sym.STRING, text);
    }
    {STRING_LITERAL}    {
        // Remove the double quotes (first 1 and last 1 characters)
        String text = yytext().substring(1, yytext().length() - 1);
        // Add logic here to process escape sequences (e.g., \\n to newline)
        return symbol(sym.STRING, text);
    }

    // ---------------------- Identifiers and Variables ----------------------
    // This rule must come *after* all keywords to ensure keywords are matched first.
    {IDENTIFIER}        { return symbol(sym.IDENTIFIER, yytext()); }

    // ---------------------- Comments ----------------------
    "//"            { yybegin(COMMENT); } // Single-line comment transition

    // ---------------------- Whitespace ----------------------
    {WHITESPACE}    { /* ignore whitespace */ }

    // ---------------------- Error Handling ----------------------
    .               {
        System.err.println("Lexical Error at line " + (yyline+1) + ", column " + (yycolumn+1) +
                             ": Invalid token '" + yytext() + "'");
    }
}

// State: COMMENT (inside single-line comment)
<COMMENT> {
    {EOL}           { yybegin(YYINITIAL); } // Exit comment on EOL
    [^\r\n]         { /* ignore comment content */ }
}