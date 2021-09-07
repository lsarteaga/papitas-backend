package com.example.project.controller;

import com.example.project.model.Product;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/subcategories/{subcategory_id}/products")
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product,
                                               @PathVariable(name = "subcategory_id") Long subcategory_id) {
        return new ResponseEntity<>(productService.saveProduct(product, subcategory_id), HttpStatus.CREATED);
    }

    @GetMapping("/api/subcategories/{subcategory_id}/products")
    public List<Product> findBySubCategoryId(@PathVariable(name = "subcategory_id") Long subcategory_id) {
        return productService.findBySubCategoryId(subcategory_id);
    }

    @GetMapping("/api/subcategories/{subcategory_id}/products/{id}")
    public ResponseEntity<Product> findByIdAndSubCategoryId(@PathVariable(name = "subcategory_id") Long subcategory_id,
                                                  @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.findByIdAndSubCategoryId(id, subcategory_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/subcategories/{subcategory_id}/products/{id}/update")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product,
                                                 @PathVariable(name = "subcategory_id") Long subcategory_id,
                                                 @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.updateProduct(product, subcategory_id, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/subcategories/{subcategory_id}/products/{id}/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "subcategory_id") Long subcategory_id,
                                                @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.deleteProduct(subcategory_id, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/products/sold-out")
    public List<Product> getSoldOutProducts() {
        return productService.getSoldOutProducts();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/products/expired")
    public List<Product> getExpiredProducts() {
        return productService.getExpiredProducts();
    }

    @GetMapping("/api/subcategories/{subcategory_id}/products/{min_price}/{max_price}/price")
    public List<Product> getProductsByPrice(@PathVariable(name = "subcategory_id") Long subcategory_id,
                                            @PathVariable(name = "min_price") Long minPrice,
                                            @PathVariable(name = "max_price") Long maxPrice) {
        return productService.getProductsByPrice(subcategory_id, minPrice, maxPrice);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/products/{id}")
    public Product getProduct(@PathVariable(name = "id") Long id) {
        return productService.getProduct(id);
    }
}
