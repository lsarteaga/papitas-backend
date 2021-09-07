package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Category;
import com.example.project.model.SubCategory;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.SubCategoryRepository;
import com.example.project.utility.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public SubCategory saveSubCategory(SubCategory subCategory, Long category_id) {
        Category category = categoryRepository.findById(category_id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category_id));
        subCategory.setSlug(Slug.makeSlug(subCategory.getName()));
        subCategory.setCreated_at(new Date());
        subCategory.setUpdated_at(new Date());
        subCategory.setCategory(category);
        if (subCategory.getImage().isEmpty()) {
            subCategory.setImage("https://www.pexels.com/photo/two-glasses-with-beverage-and-straws-104509/");
        }
        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> findByCategoryId(Long category_id) {
        return subCategoryRepository.findByCategoryId(category_id);
    }

    public SubCategory findByIdAndCategoryId(Long id, Long category_id) {
        categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category_id));
        subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + id));
        return subCategoryRepository.findByIdAndCategoryId(id, category_id);
    }

    public SubCategory updateSubCategory(SubCategory subCategory, Long category_id, Long id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category_id));
        SubCategory existingSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));
        existingSubCategory.setName(subCategory.getName());
        existingSubCategory.setSlug(Slug.makeSlug(subCategory.getName()));
        if (!subCategory.getImage().isEmpty()) {
            existingSubCategory.setImage(subCategory.getImage());
        }
        existingSubCategory.setUpdated_at(new Date());
        existingSubCategory.setCategory(category);
        return subCategoryRepository.save(existingSubCategory);
    }

    public String deleteSubCategory(Long category_id, Long id) {
        categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category_id));
        subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));
        subCategoryRepository.deleteById(id);
        return "SubCategory deleted";
    }
}
