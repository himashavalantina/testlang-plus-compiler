package org.example;

public class StatusAssertion implements AssertionStatement {
    public enum Type { EQ, IN_RANGE }

    public final Type type;
    public final Object value; // Integer for EQ, Range for IN_RANGE
    public final Object expectedResponse;

    public StatusAssertion(Type type, Object value, Object expectedResponse) {
        this.type = type;
        this.value = value;
        this.expectedResponse = expectedResponse;
    }
}
