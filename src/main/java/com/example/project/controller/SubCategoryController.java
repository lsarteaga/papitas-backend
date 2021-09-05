package com.example.project.controller;

import com.example.project.model.SubCategory;
import com.example.project.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/categories/{category_id}/subcategories")
    public ResponseEntity<SubCategory> saveSubCategory(@Valid @RequestBody SubCategory subCategory,
                                                       @PathVariable(name = "category_id") Long category_id) {
        return new ResponseEntity<>(subCategoryService.saveSubCategory(subCategory, category_id), HttpStatus.CREATED);
    }

    @GetMapping("/api/categories/{category_id}/subcategories")
    public List<SubCategory> findByCategoryId(@PathVariable(name = "category_id") Long category_id) {
        return subCategoryService.findByCategoryId(category_id);
    }

    @GetMapping("/api/categories/{category_id}/subcategories/{id}")
    public ResponseEntity<SubCategory> findByIdAndCategoryId(@PathVariable(name = "category_id") Long category_id,
                                                             @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.findByIdAndCategoryId(id, category_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/categories/{category_id}/subcategories/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@Valid @RequestBody SubCategory subCategory,
                                                         @PathVariable(name = "category_id") Long category_id,
                                                         @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.updateSubCategory(subCategory, category_id, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/categories/{category_id}/subcategories/{id}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable(name = "category_id") Long category_id,
                                                    @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.deleteSubCategory(category_id, id), HttpStatus.OK);
    }
}
