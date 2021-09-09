package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Category;
import com.example.project.repository.CategoryRepository;
import com.example.project.utility.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        category.setSlug(Slug.makeSlug(category.getName()));
        category.setCreated_at(new Date());
        category.setUpdated_at(new Date());
        if (category.getImage().isEmpty()) {
            category.setImage("https://www.pexels.com/photo/abstract-art-cooking-cutlery-262896/");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        existingCategory.setName(category.getName());
        existingCategory.setSlug(Slug.makeSlug(category.getName()));
        if (!category.getImage().isEmpty()) {
            existingCategory.setImage(category.getImage());
        }
        existingCategory.setUpdated_at(new Date());
        return categoryRepository.save(existingCategory);
    }

    public String deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.deleteById(id);
        return "Category deleted";
    }
}
