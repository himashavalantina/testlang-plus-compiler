package org.example;

public class RequestStatement implements TestStatement {
    public enum Method { GET, POST, PUT, DELETE }

    public final Method method;
    public final String path;
    public final RequestDetails details;

    public RequestStatement(Method method, String path, RequestDetails details) {
        this.method = method;
        this.path = path;
        this.details = details;
    }
}
