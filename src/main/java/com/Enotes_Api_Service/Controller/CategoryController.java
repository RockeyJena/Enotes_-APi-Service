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

    @GetMapping("/")
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
        @GetMapping("/ActiveCategory")
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
    @GetMapping("/{id}")
    public  ResponseEntity<?>getCategoryDetailsById(@PathVariable("id") Integer id ){
       CategoryDto categoryDto= categoryService.getCateogryById(id);
       if (ObjectUtils.isEmpty(categoryDto)){
           log.warn("Category data fetch UnsuccessfulFul  by Id, {} :- "+id);
           return new ResponseEntity<>("Category not found id:- " + id,HttpStatus.NOT_FOUND);
       }
       log.info("Category data fetch success by Id ,{}:- ",categoryDto);
       return new ResponseEntity<>(categoryDto,HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?>deleteCategoryByID(@PathVariable("id") Integer id ){
       Boolean deleted= categoryService.deleteCateogryById(id);
       if (deleted){
           log.info("Category delete  successfully  by Id  {} :- "+id);
           return new ResponseEntity<>(" Category  delete  successfully  by Id :- " + id,HttpStatus.OK);
       }
       log.warn("Category data fetch success by Id ,{}:- ",id);
       return new ResponseEntity<>(" delete  Unsuccessfully  Because Id not there  :-   "+ id ,HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

}