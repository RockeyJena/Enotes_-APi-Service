package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Exception.ResourceNotFoundException;
import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.Validation.CategoryValidator;
import com.Enotes_Api_Service.Validation.ValidationException;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
import com.Enotes_Api_Service.Handler.GenericResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryValidator.validate(categoryDto);
            Boolean saveData = categoryService.saveCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Category stored successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Internal server error");
        }
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse> getAllCategories() {
        try {
            List<CategoryDto> categoryList = categoryService.getAllCategories();
            if (CollectionUtils.isEmpty(categoryList)) {
                log.warn("No categories found");
                return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "No categories found", List.of()));
            }
            log.info("Fetched all categories successfully");
            return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "Categories fetched successfully", categoryList));
        } catch (Exception e) {
            log.error("Exception occurred while fetching categories: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
        }
    }

    @GetMapping("/ActiveCategory")
    public ResponseEntity<GenericResponse> getActiveCategories() {
        try {
            List<CategoryResponse> categoryList = categoryService.getActiveCategories();
            if (CollectionUtils.isEmpty(categoryList)) {
                log.warn("No active categories found");
                return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "No active categories found", List.of()));
            }
            log.info("Fetched active categories successfully");
            return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "Active categories fetched successfully", categoryList));
        } catch (Exception e) {
            log.error("Exception occurred while fetching active categories: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getCategoryDetailsById(@PathVariable("id") Integer id) {
        try {
            CategoryDto categoryDto = categoryService.getCateogryById(id);
            log.info("Fetched category details successfully for ID: {}", id);
            return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "Category details fetched successfully", categoryDto));
        } catch (ResourceNotFoundException e) {
            log.warn("Category retrieval failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericResponse.failed(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error while fetching category by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while fetching the category."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) {
        try {
            Boolean deleted = categoryService.deleteCateogryById(id);
            if (Boolean.TRUE.equals(deleted)) {
                log.info("Category deleted successfully for ID: {}", id);
                return ResponseEntity.ok("Category deleted successfully for ID: " + id);
            } else {
                log.warn("Failed to delete category. ID not found: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category deletion failed. ID not found: " + id);
            }
        } catch (Exception e) {
            log.error("Exception occurred while deleting category by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
