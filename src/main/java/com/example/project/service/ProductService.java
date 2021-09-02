package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.SubCategory;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.SubCategoryRepository;
import com.example.project.utility.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setSubCategory(subCategory);
        if (product.getImage().isEmpty()) {
            product.setImage("https://www.pexels.com/photo/two-glasses-with-beverage-and-straws-104509/");
        }
        return productRepository.save(product);
    }

    public List<Product> findBySubCategoryId(Long subcategory_id) {
        return productRepository.findBySubCategoryId(subcategory_id);
    }

    public Product findByIdAndSubCategoryId(Long id, Long subcategory_id) {
        subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subcategory_id));
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productRepository.findByIdAndSubCategoryId(id, subcategory_id);
    }

    public Product updateProduct(Product product, Long subcategory_id, Long id) {
        SubCategory subCategory = subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subcategory_id));
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        existingProduct.setSlug(Slug.makeSlug(product.getName()));
        existingProduct.setUpdatedAt(LocalDateTime.now());
        existingProduct.setSubCategory(subCategory);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        if (!product.getImage().isEmpty()) {
            existingProduct.setImage(product.getImage());
        }
        return productRepository.save(existingProduct);
    }

    public String deleteProduct(Long subcategory_id, Long id) {
        SubCategory subCategory = subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with subcategory_id: " + subcategory_id));
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.deleteById(id);
        return "Product deleted";
    }
}
