package com.Enotes_Api_Service.Service;

import com.Enotes_Api_Service.Entity.Category;

import java.util.List;

public interface CategoryService {

    Boolean saveCategory(Category category);

    List<Category> getAllCategories();
}
