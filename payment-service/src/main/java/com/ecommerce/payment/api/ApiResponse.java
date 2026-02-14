package com.ecommerce.payment.api;

import java.time.Instant;

public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Instant timestamp;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static final class ApiMessages {
        public static final String ORDER_CREATED = "Order created successfully";
        public static final String ORDER_FETCHED = "Order fetched successfully";
        public static final String ORDERS_FETCHED = "Orders fetched successfully";

        public static final String VALIDATION_FAILED = "Validation failed";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String FORBIDDEN = "Forbidden";
        public static final String NOT_FOUND = "Resource not found";
        public static final String BAD_REQUEST = "Bad request";
        public static final String INTERNAL_ERROR = "Internal server error";
        private ApiMessages() {
        }
    }
}

