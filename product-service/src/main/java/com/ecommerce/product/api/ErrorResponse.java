package com.ecommerce.product.api;

import java.time.Instant;

public class ErrorResponse {
    private final String error;
    private final String path;
    private final Instant timestamp;

    public ErrorResponse(String error, String path) {
        this.error = error;
        this.path = path;
        this.timestamp = Instant.now();
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

