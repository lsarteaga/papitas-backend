package com.example.project.controller;

import com.example.project.model.SubCategory;
import com.example.project.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping("/{category_id}/subcategories")
    public ResponseEntity<SubCategory> saveSubCategory(@Valid @RequestBody SubCategory subCategory,
                                                       @PathVariable(name = "category_id") Long category_id) {
        return new ResponseEntity<>(subCategoryService.saveSubCategory(subCategory, category_id), HttpStatus.CREATED);
    }

    @GetMapping("/{category_id}/subcategories")
    public List<SubCategory> findByCategoryId(@PathVariable(name = "category_id") Long id) {
        return subCategoryService.findByCategoryId(id);
    }

    @GetMapping("/{category_id}/subcategories/{id}")
    public ResponseEntity<SubCategory> findByIdAndCategoryId(@PathVariable(name = "category_id") Long category_id,
                                                             @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.findByIdAndCategoryId(id, category_id), HttpStatus.OK);
    }

    @PutMapping("/{category_id}/subcategories/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@Valid @RequestBody SubCategory subCategory,
                                                         @PathVariable(name = "category_id") Long category_id,
                                                         @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.updateSubCategory(subCategory, category_id, id), HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}/subcategories/{id}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable(name = "category_id") Long category_id,
                                                    @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(subCategoryService.deleteSubCategory(category_id, id), HttpStatus.OK);
    }
}
