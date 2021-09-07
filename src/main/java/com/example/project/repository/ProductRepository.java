package com.example.project.repository;

import com.example.project.model.Product;
import com.example.project.model.ProductExpired;
import com.example.project.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySubCategoryId(Long subcategory_id);
    Product findByIdAndSubCategoryId(Long id, Long subcategory_id);
    // productos agotados
    List<Product> findProductsByProductStatus(ProductStatus productStatus);

    // productos caducados
    List<Product> findProductsByProductExpired(ProductExpired productExpired);

    // productos por rango de precios
    List<Product> findProductsByPriceBetweenAndSubCategoryId(float minPrice, float maxPrice, Long subcategory_id);
}
