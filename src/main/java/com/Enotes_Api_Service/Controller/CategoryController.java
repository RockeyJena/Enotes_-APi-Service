package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

        Boolean saveData = categoryService.saveCategory(categoryDto);
        if (ObjectUtils.isEmpty(saveData)) {
            log.error("Failed to saveData : {}", categoryDto);
            return new ResponseEntity<>("Failed to save", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info(" saveData successfully: {}", categoryDto);
            return new ResponseEntity<>("saveData store Successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDto> categoryList = categoryService.getAllCategories();
        if (CollectionUtils.isEmpty(categoryList)) {
            log.error("Failed to get all data");
            return ResponseEntity.noContent().build();
        } else {
            log.info("getAllCategories successfully");
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }
        @GetMapping("/Active-Category")
     public ResponseEntity<?> ActiveCategory() {
        List<CategoryResponse> categoryList = categoryService.getActiveCategories();
        if (CollectionUtils.isEmpty(categoryList)) {
            log.error("Failed to get all data");
            return  ResponseEntity.noContent().build();
        } else {
            log.info("getAllCategories successfully");
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }



    }
}