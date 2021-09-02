package com.example.project.repository;

import com.example.project.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByCategoryId(Long category_id);
    SubCategory findByIdAndCategoryId(Long id, Long category_id);
}
