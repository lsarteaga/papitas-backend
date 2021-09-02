package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.SubCategory;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.SubCategoryRepository;
import com.example.project.utility.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Product saveProduct(Product product, Long subcategory_id) {
        SubCategory subCategory = subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subcategory_id));
        product.setSlug(Slug.makeSlug(product.getName()));
        //product.setCreatedAt(new T);
        return productRepository.save(product);
    }
}
