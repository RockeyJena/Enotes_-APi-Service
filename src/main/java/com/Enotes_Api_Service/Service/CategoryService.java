package com.Enotes_Api_Service.Service;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Handler.GenericResponse;
import com.Enotes_Api_Service.dto.CategoryDto;
import com.Enotes_Api_Service.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    Boolean saveCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    List<CategoryResponse> getActiveCategories();

    CategoryDto getCateogryById(Integer id);

    Boolean deleteCateogryById(Integer id);
}
