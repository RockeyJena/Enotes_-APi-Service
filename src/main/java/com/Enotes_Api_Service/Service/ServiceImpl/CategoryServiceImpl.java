package com.Enotes_Api_Service.Service.ServiceImpl;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
import com.Enotes_Api_Service.Exception.CategoryNotUpdatableException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            return updateExistingCategory(categoryDto);
        } else {
            return createNewCategory(categoryDto);
        }
    }

    private Boolean createNewCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        category.setIsDeleted(false); // New categories should not be deleted
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());

        return saveCategoryToDB(category);
    }

    private Boolean updateExistingCategory(CategoryDto categoryDto) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryDto.getId());
        if (categoryOpt.isPresent()) {
            Category existingCategory = categoryOpt.get();

            // 🚫 Prevent update if category is deleted
            if (existingCategory.getIsDeleted()) {
                log.warn("This category is deleted and cannot be updated. ID: {}", categoryDto.getId());
                // Throw custom exception with a specific message
                throw new CategoryNotUpdatableException("Deleted category cannot be updated.");
            }

            updateCategoryFields(existingCategory, categoryDto);
            existingCategory.setUpdatedBy(1);
            existingCategory.setUpdatedOn(new Date());

            return saveCategoryToDB(existingCategory);
        } else {
            log.error("Category not found with ID: {}", categoryDto.getId());
            return false;
        }
    }

    private void updateCategoryFields(Category category, CategoryDto categoryDto) {
        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }
        if (categoryDto.getIsActive() != null) {
            category.setIsActive(categoryDto.getIsActive());
        }
    }

    private Boolean saveCategoryToDB(Category category) {
        Category savedCategory = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(savedCategory)) {
            log.error("Failed to save category: {}", category);
            return false;
        }

        log.info("Category saved successfully: {}", savedCategory);
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categorys = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categorys.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        log.info("getAllCategories successfully", categoryDtoList);
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {
        List<Category> categorys = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponseList = categorys.stream().map(c -> mapper.map(c, CategoryResponse.class)).toList();
        return categoryResponseList;
    }

    @Override
    public CategoryDto getCateogryById(Integer id) {
        Optional<Category> categoryRepositoryById = categoryRepository.findByIdAndIsDeletedFalse(id);
        if (categoryRepositoryById.isPresent()) {
            Category category = categoryRepositoryById.get();
            CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
            log.info("getCategoryById successfully ,{}", categoryDto);
            return categoryDto;
        }
        return null;
    }

    @Override
    public Boolean deleteCateogryById(Integer id) {
        Optional<Category> categoryRepositoryById = categoryRepository.findById(id);
        if (categoryRepositoryById.isPresent()) {
            Category category = categoryRepositoryById.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            log.info("deleteCategoryById successfully {}", id);
            return true;
        }
        return false;
    }
}