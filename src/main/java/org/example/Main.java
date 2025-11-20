package org.example;

import com.testlang.compiler.Lexer;
import com.testlang.compiler.LexerFactory;
import com.testlang.compiler.Parser;
import java_cup.runtime.ComplexSymbolFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java_cup.runtime.Symbol;

/**
 * CLI entrypoint for the TestLang++ compiler.
 * Usage: java -cp <jar> org.example.Main <path/to/file.test>
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -cp <jar> org.example.Main <path/to/file.test>");
            System.exit(1);
        }
        String inputPath = args[0];
        try (Reader reader = new BufferedReader(new FileReader(inputPath))) {
            Lexer lexer = LexerFactory.create(reader);
            Parser parser = new Parser(lexer, new ComplexSymbolFactory());
            Symbol result = parser.parse();
            Program program = (Program) result.value;

            CodeGenerator generator = new CodeGenerator();
            generator.generate(program);
            System.out.println("✅ Compilation succeeded: Generated tests written.");
        } catch (Exception ex) {
            // Parser already prints detailed syntax errors via report_error/report_fatal_error
            System.err.println("Compilation failed: " + ex.getMessage());
            System.exit(1);
        }
    }
}
