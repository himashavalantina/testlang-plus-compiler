package org.example;

public class CaptureStatement implements TestStatement {
    public enum Type { BODY, HEADER }

    public final Type type;
    public final String key;
    public final String varName;

    public CaptureStatement(Type type, String key, String varName) {
        this.type = type;
        this.key = key;
        this.varName = varName;
    }
}
