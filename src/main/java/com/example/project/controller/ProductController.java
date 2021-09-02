package com.example.project.controller;

import com.example.project.model.Product;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subcategories")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/{subcategory_id}/products")
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product,
                                               @PathVariable(name = "subcategory_id") Long subcategory_id) {
        return new ResponseEntity<>(productService.saveProduct(product, subcategory_id), HttpStatus.CREATED);
    }

    @GetMapping("/{subcategory_id}/products")
    public List<Product> findBySubCategoryId(@PathVariable(name = "subcategory_id") Long subcategory_id) {
        return productService.findBySubCategoryId(subcategory_id);
    }

    @GetMapping("/{subcategory_id}/products/{id}")
    public ResponseEntity<Product> findByIdAndSubCategoryId(@PathVariable(name = "subcategory_id") Long subcategory_id,
                                                  @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.findByIdAndSubCategoryId(id, subcategory_id), HttpStatus.OK);
    }

    @PutMapping("/{subcategory_id}/products/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product,
                                                 @PathVariable(name = "subcategory_id") Long subcategory_id,
                                                 @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.updateProduct(product, subcategory_id, id), HttpStatus.OK);
    }

    @DeleteMapping("/{subcategory_id}/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "subcategory_id") Long subcategory_id,
                                                @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.deleteProduct(subcategory_id, id), HttpStatus.OK);
    }
}
