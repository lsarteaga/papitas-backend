package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.ProductExpired;
import com.example.project.model.ProductStatus;
import com.example.project.model.SubCategory;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.SubCategoryRepository;
import com.example.project.utility.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    // INYECTANDO DEPENDENCIAS
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // METODOS USUARIO

    // obtener los productos mediante la subcategoria a la cual pertenecen
    public List<Product> findBySubCategoryId(Long subcategory_id) {
        List<Product> products = productRepository.findBySubCategoryId(subcategory_id);
        return filteringProducts(products);
    }
    // obteniendo productos por precio
    public List<Product> getProductsByPrice(Long subcategory_id, Long minPrice, Long maxPrice) {
        subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with subcategory_id: " + subcategory_id));
        return filteringProducts(productRepository.findProductsByPriceBetweenAndSubCategoryId(minPrice, maxPrice, subcategory_id));
    }
    // obteniendo un producto en especifico
    public Product findByIdAndSubCategoryId(Long id, Long subcategory_id) {
        subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subcategory_id));
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productRepository.findByIdAndSubCategoryId(id, subcategory_id);
    }

    // METODOS DEL ADMIN

    public Product saveProduct(Product product, Long subcategory_id) {
        SubCategory subCategory = subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subcategory_id));
        product.setSlug(Slug.makeSlug(product.getName()));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setSubCategory(subCategory);
        product.setProductStatus(ProductStatus.AVAILABLE);
        product.setProductExpired(ProductExpired.VALID);
        product.setSold(0);
        product.setAvailable(product.getQuantity());
        if (product.getImage().isEmpty()) {
            product.setImage("https://www.pexels.com/photo/two-glasses-with-beverage-and-straws-104509/");
        }
        return productRepository.save(product);
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
        existingProduct.setSold(0);
        existingProduct.setAvailable(product.getQuantity());
        existingProduct.setExpiredAt(product.getExpiredAt());
        existingProduct.setProductStatus(ProductStatus.AVAILABLE);
        existingProduct.setProductExpired(ProductExpired.VALID);
        if (!product.getImage().isEmpty()) {
            existingProduct.setImage(product.getImage());
        }
        return productRepository.save(existingProduct);
    }
    public String deleteProduct(Long subcategory_id, Long id) {
        subCategoryRepository.findById(subcategory_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with subcategory_id: " + subcategory_id));
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.deleteById(id);
        return "Product deleted";
    }
    // obtener los productos agotados
    public List<Product> getSoldOutProducts() {
        return productRepository.findProductsByProductStatus(ProductStatus.SOLD_OUT);
    }
    // obtener los productos expirados
    public List<Product> getExpiredProducts() {
        return productRepository.findProductsByProductExpired(ProductExpired.EXPIRED);
    }
    // obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // obtener un producto sin filtros
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // METODOS PARA FILTRAR

    // filtrando para no mostrar los productos expirados y verificar el stock
    public List<Product> filteringProducts(List<Product> products) {
        products
                .stream()
                .map(product -> {
                    productRepository.save(editProduct(product));
                    return product;
                }).collect(Collectors.toList());
        return products
                .stream()
                .filter(product -> product.getProductExpired() != ProductExpired.EXPIRED)
                .collect(Collectors.toList());
    }

    // cambiando estado del producto
    public Product editProduct(Product product) {
        if (product.getAvailable() == 0) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
            product.setProductExpired(ProductExpired.NON_DATE);
        } else if (product.getExpiredAt().isBefore(LocalDate.now())) {
            product.setProductExpired(ProductExpired.EXPIRED);
        }
        return productRepository.save(product);
    }
}
