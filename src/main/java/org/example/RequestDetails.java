package org.example;

import java.util.Collections;
import java.util.List;

public class RequestDetails {
    public final List<HeaderStatement> headers;
    public final String body;

    public RequestDetails(List<HeaderStatement> headers, String body) {
        this.headers = headers != null ? headers : Collections.emptyList();
        this.body = body;
    }
}
