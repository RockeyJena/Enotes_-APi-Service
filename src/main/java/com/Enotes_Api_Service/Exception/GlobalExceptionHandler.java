package com.Enotes_Api_Service.Exception;

import com.Enotes_Api_Service.Handler.GenericResponse;
import com.Enotes_Api_Service.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Resource Not Found Exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("Resource Not Found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.failed(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Handles NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        log.error("Null Pointer Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error",
                "Unexpected null value encountered.",
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal Argument Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Error",
                ex.getMessage(),
                null
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HTTP Request Method Not Supported Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        log.error("Method Not Allowed: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Error",
                "HTTP method not supported for this request.",
                null
        ), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles Missing Servlet Request Parameter Exception
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error("Missing Request Parameter: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Error",
                "Required parameter is missing: " + ex.getParameterName(),
                null
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Method Argument Not Valid Exception (Validation Errors)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation Error: {}", ex.getMessage(), ex);

        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Error");
        errors.put("timestamp", LocalDateTime.now());

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        errors.put("errors", validationErrors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles No Handler Found Exception (Invalid URLs)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.error("No Handler Found: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Error",
                "Invalid URL or endpoint does not exist.",
                null
        ), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles JSON Parsing Errors like Invalid Boolean values ("Tru fge")
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        log.error("JSON Parsing Error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Error",
                "Invalid data format in request body. Ensure all values are correctly typed.",
                null
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Generic RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error",
                "An unexpected error occurred.",
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Generic Exception (Fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleGlobalException(Exception ex) {
        log.error("Unhandled Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + ex.getMessage()));
    }
}
