package com.ecommerce.auth.api;



import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        return buildError(ApiMessages.VALIDATION_FAILED, HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {
        return buildError(ApiMessages.VALIDATION_FAILED, HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAccessDenied(
            AccessDeniedException ex,
            WebRequest request) {
        return buildError(ApiMessages.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleRuntime(
            RuntimeException ex,
            WebRequest request) {
        return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGlobal(
            Exception ex,
            WebRequest request) {
        return buildError(ApiMessages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    private ResponseEntity<ApiResponse<ErrorResponse>> buildError(
            String message,
            HttpStatus status,
            WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse error = new ErrorResponse(message, path);
        return ResponseEntity.status(status).body(ApiResponse.error(message, error));
    }
}
