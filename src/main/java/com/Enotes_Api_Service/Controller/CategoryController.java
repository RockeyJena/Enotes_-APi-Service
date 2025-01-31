package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            Boolean saveData = categoryService.saveCategory(categoryDto);
            if (Boolean.TRUE.equals(saveData)) {
                log.info("Category saved successfully: {}", categoryDto);
                return new ResponseEntity<>("Category stored successfully", HttpStatus.CREATED);
            } else {
                log.error("Failed to save category: {}", categoryDto);
                return new ResponseEntity<>("Failed to save category", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception occurred while saving category: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<CategoryDto> categoryList = categoryService.getAllCategories();
            if (CollectionUtils.isEmpty(categoryList)) {
                log.warn("No categories found");
                return ResponseEntity.noContent().build();
            }
            log.info("Fetched all categories successfully");
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while fetching categories: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ActiveCategory")
    public ResponseEntity<?> getActiveCategories() {
        try {
            List<CategoryResponse> categoryList = categoryService.getActiveCategories();
            if (CollectionUtils.isEmpty(categoryList)) {
                log.warn("No active categories found");
                return ResponseEntity.noContent().build();
            }
            log.info("Fetched active categories successfully");
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while fetching active categories: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable("id") Integer id) {
        try {
            CategoryDto categoryDto = categoryService.getCateogryById(id);
            if (ObjectUtils.isEmpty(categoryDto)) {
                log.warn("Category not found for ID: {}", id);
                return new ResponseEntity<>("Category not found for ID: " + id, HttpStatus.NOT_FOUND);
            }
            log.info("Fetched category details successfully for ID: {}", id);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while fetching category by ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) {
        try {
            Boolean deleted = categoryService.deleteCateogryById(id);
            if (Boolean.TRUE.equals(deleted)) {
                log.info("Category deleted successfully for ID: {}", id);
                return new ResponseEntity<>("Category deleted successfully for ID: " + id, HttpStatus.OK);
            } else {
                log.warn("Failed to delete category. ID not found: {}", id);
                return new ResponseEntity<>("Category deletion failed. ID not found: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception occurred while deleting category by ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
