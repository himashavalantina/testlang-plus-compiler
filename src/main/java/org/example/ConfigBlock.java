package org.example;

import java.util.ArrayList;
import java.util.List;

public class ConfigBlock {
    public List<ConfigStatement> statements = new ArrayList<>();

    public ConfigBlock() {}

    public ConfigBlock(List<ConfigStatement> stmts) {
        this.statements = stmts;
    }

    public void addStatement(ConfigStatement stmt) {
        this.statements.add(stmt);
    }
}
