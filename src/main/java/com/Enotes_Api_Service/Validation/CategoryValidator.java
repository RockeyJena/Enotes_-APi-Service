package com.Enotes_Api_Service.Validation;


import com.Enotes_Api_Service.dto.CategoryDto;

import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    public void validate(CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getName().trim().isEmpty()) {
            throw new ValidationException("❌ Category name is required and cannot be empty.");
        }

        if (categoryDto.getName().length() < 3 || categoryDto.getName().length() > 100) {
            throw new ValidationException("⚠️ Category name must be between 3 and 100 characters.");
        }

        if (categoryDto.getDescription() == null || categoryDto.getDescription().trim().isEmpty()) {
            throw new ValidationException("❌ Category description is required and cannot be empty.");
        }

        if (categoryDto.getDescription().length() < 5 || categoryDto.getDescription().length() > 100) {
            throw new ValidationException("⚠️ Category description must be between 5 and 100 characters.");
        }

        // Only check if isActive is null
        if (categoryDto.getIsActive() == null) {
            throw new ValidationException("❌ isActive field is required. Please provide true or false its Boolean Type value.");
        }

//        if (categoryDto.getIsActive() == null|| categoryDto.getIsActive() == Boolean.TRUE || categoryDto.getIsActive() == Boolean.FALSE) {
//            throw new ValidationException("❌ isActive field is required. Please provide true or false its Boolean Type value .");
//        }
    }
}
