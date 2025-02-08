package com.Enotes_Api_Service.Service.ServiceImpl;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Exception.ResourceNotFoundException;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        try {
            // Check if category name already exists (only for new categories)
            if (categoryDto.getId() == null && categoryRepository.existsByName(categoryDto.getName())) {
                log.warn("Category name '{}' already exists", categoryDto.getName());
                throw new ResourceNotFoundException("Category name '" + categoryDto.getName() + "' already exists.");
            }

            if (categoryDto.getId() != null) {
                return updateExistingCategory(categoryDto);
            } else {
                return createNewCategory(categoryDto);
            }
        } catch (ResourceNotFoundException e) {
            throw e; // Propagate specific exception
        } catch (Exception e) {
            log.error("Error while saving category: {}", e.getMessage(), e);
            return false;
        }
    }

    private Boolean createNewCategory(CategoryDto categoryDto) {
        try {
            Category category = mapper.map(categoryDto, Category.class);
            category.setIsDeleted(false); // New categories should not be deleted
            return saveCategoryToDB(category);
        } catch (Exception e) {
            log.error("Error while creating new category: {}", e.getMessage(), e);
            return false;
        }
    }

    private Boolean updateExistingCategory(CategoryDto categoryDto) {
        try {
            Optional<Category> categoryOpt = categoryRepository.findById(categoryDto.getId());
            if (categoryOpt.isPresent()) {
                Category existingCategory = categoryOpt.get();

                // Prevent update if category is deleted
                if (existingCategory.getIsDeleted()) {
                    log.warn("This category is deleted and cannot be updated. ID: {}", categoryDto.getId());
                    throw new ResourceNotFoundException("Deleted category cannot be updated.");
                }

                updateCategoryFields(existingCategory, categoryDto);
                return saveCategoryToDB(existingCategory);
            } else {
                log.error("Category not found with ID: {}", categoryDto.getId());
                return false;
            }
        } catch (Exception e) {
            log.error("Error while updating category with ID {}: {}", categoryDto.getId(), e.getMessage(), e);
            return false;
        }
    }

    private void updateCategoryFields(Category category, CategoryDto categoryDto) {
        try {
            if (categoryDto.getName() != null) {
                category.setName(categoryDto.getName());
            }
            if (categoryDto.getDescription() != null) {
                category.setDescription(categoryDto.getDescription());
            }
            if (categoryDto.getIsActive() != null) {
                category.setIsActive(categoryDto.getIsActive());
            }
        } catch (Exception e) {
            log.error("Error while updating category fields: {}", e.getMessage(), e);
        }
    }

    private Boolean saveCategoryToDB(Category category) {
        try {
            Category savedCategory = categoryRepository.save(category);
            if (ObjectUtils.isEmpty(savedCategory)) {
                log.error("Failed to save category: {}", category);
                return false;
            }

            log.info("Category saved successfully: {}", savedCategory);
            return true;
        } catch (Exception e) {
            log.error("Error while saving category to database: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findByIsDeletedFalse();
            return categories.stream()
                    .map(category -> mapper.map(category, CategoryDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all categories: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {
        try {
            List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
            return categories.stream()
                    .map(c -> mapper.map(c, CategoryResponse.class))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching active categories: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public CategoryDto getCateogryById(Integer id) {
        return categoryRepository.findById(id)
                .filter(category -> !category.getIsDeleted())
                .map(category -> mapper.map(category, CategoryDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Category ID " + id + " not found or deleted."));
    }

    @Override
    public Boolean deleteCateogryById(Integer id) {
        try {
            Optional<Category> categoryOpt = categoryRepository.findById(id);
            if (categoryOpt.isPresent()) {
                Category category = categoryOpt.get();
                category.setIsDeleted(true);
                categoryRepository.save(category);
                log.info("Deleted category by ID successfully: {}", id);
                return true;
            }
            log.warn("Category not found for deletion, ID: {}", id);
            return false;
        } catch (Exception e) {
            log.error("Error while deleting category by ID {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}
