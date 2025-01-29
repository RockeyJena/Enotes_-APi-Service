package com.Enotes_Api_Service.Service.ServiceImpl;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedOn(new Date());
        Category save = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(save)) {
            log.error("Failed to save category {}", category);
            return false;
        }
        log.info("Category saved successfully {}", category);
        return true;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Getting all categories");
        return categoryRepository.findAll();
    }
}