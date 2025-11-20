package org.example;

public class BodyAssertion implements AssertionStatement {
    public enum Type { CONTAINS }

    public final Type type;
    public final String value;
    public final Object expectedResponse;

    public BodyAssertion(Type type, String value, Object expectedResponse) {
        this.type = type;
        this.value = value;
        this.expectedResponse = expectedResponse;
    }
}
