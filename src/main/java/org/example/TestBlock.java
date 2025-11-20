package org.example;

import java.util.List;

public class TestBlock {
    public final String name;
    public final List<TestStatement> statements;

    public TestBlock(String name, List<TestStatement> statements) {
        this.name = name.replaceAll("[^a-zA-Z0-9_]", "_");
        this.statements = statements;
    }
}
