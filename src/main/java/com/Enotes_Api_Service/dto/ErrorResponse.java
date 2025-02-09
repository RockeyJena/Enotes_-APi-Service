package com.Enotes_Api_Service.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus httpStatus;
    private String status;
    private String message;
    private Object data; // Ensure it's `null` in case of errors

    public ErrorResponse(HttpStatus httpStatus, String status, String message, Object data) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
