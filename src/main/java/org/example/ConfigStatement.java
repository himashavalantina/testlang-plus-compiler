package org.example;

public class ConfigStatement {
    public enum Type { BASE_URL, DEFAULT_HEADER }

    public final Type type;
    public final String key;
    public final String value;

    public ConfigStatement(Type type, String value) {
        this.type = type;
        this.key = null;
        this.value = value;
    }

    public ConfigStatement(Type type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
}
