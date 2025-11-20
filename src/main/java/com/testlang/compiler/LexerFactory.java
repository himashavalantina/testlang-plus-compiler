package com.testlang.compiler;

import java.io.Reader;

/**
 * Package-local factory to construct the JFlex-generated Lexer while keeping
 * access friendly for callers in other packages.
 */
public final class LexerFactory {
    private LexerFactory() {}

    public static Lexer create(Reader in) {
        return new Lexer(in); // constructor is package-private in regenerated code
    }
}
