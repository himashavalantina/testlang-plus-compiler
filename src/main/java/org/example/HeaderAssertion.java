package org.example;

public class HeaderAssertion implements AssertionStatement {
    public enum Type { EQ, CONTAINS }

    public final Type type;
    public final String key;
    public final String value;
    public final Object expectedResponse;

    public HeaderAssertion(Type type, String key, String value, Object expectedResponse) {
        this.type = type;
        this.key = key;
        this.value = value;
        this.expectedResponse = expectedResponse;
    }
}
