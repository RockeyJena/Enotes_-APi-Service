package com.Enotes_Api_Service.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Category is not updatable");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
