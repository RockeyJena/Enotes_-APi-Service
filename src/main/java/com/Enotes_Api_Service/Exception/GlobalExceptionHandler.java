package com.Enotes_Api_Service.Exception;

import com.Enotes_Api_Service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotUpdatableException.class)
    public ResponseEntity<Object> handleCategoryNotUpdatableException(CategoryNotUpdatableException ex) {
        // Custom response structure
        ErrorResponse errorResponse = new ErrorResponse("error", ex.getMessage(), "CATEGORY_DELETED");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
