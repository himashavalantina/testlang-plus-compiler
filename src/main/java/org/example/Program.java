package org.example;

import java.util.List;

public class Program {
    public final ConfigBlock config;
    public final List<VarDecl> variables;
    public final List<TestBlock> tests;

    public Program(ConfigBlock config, List<VarDecl> variables, List<TestBlock> tests) {
        this.config = config;
        this.variables = variables;
        this.tests = tests;
    }
}
