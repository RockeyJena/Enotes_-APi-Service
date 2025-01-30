package com.Enotes_Api_Service.Service.ServiceImpl;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Service.CategoryService;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;
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
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setIsDeleted(false);
//        category.setDescription(categoryDto.getDescription());
//        category.setUpdatedBy(categoryDto.getUpdatedBy());
//        category.setIsActive(categoryDto.getIsActive());
        Category category = mapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category save = categoryRepository.save(category);
        log.info("Category saved successfully {}", save);
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