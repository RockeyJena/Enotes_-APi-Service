package com.Enotes_Api_Service.Exception;

public class CategoryNotUpdatableException extends RuntimeException {
    public CategoryNotUpdatableException() {
        super("Category is not updatable");
    }
    public CategoryNotUpdatableException(String message) {
        super(message);
    }
}
