package com.Enotes_Api_Service.Repository;

import com.Enotes_Api_Service.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByIsActiveTrue();
}
