package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.*;
import com.example.project.repository.DetailRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DetailService {

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public Detail saveDetail(Detail detail, Long order_id, Long product_id) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + order_id));
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + order_id));
        detail.setCreated_at(new Date());
        detail.setUpdated_at(new Date());
        detail.setOrder(order);
        detail.setProductName(product.getName());
        detail.setUnitPrice(product.getPrice());
        if (detail.getQuantity() > product.getAvailable()) {
            throw new RuntimeException("la cantidad de producto supera al stock disponible");
        }
        detail.setTotal(product.getPrice() * detail.getQuantity());
        // actualizando stock
        product.setSold(product.getSold() + detail.getQuantity());
        product.setAvailable(product.getAvailable() - detail.getQuantity());

        if (product.getAvailable() == 0) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }
        productRepository.save(product);
        return  detailRepository.save(detail);
    }

    public List<Detail> getDetails(Long order_id) {
        return detailRepository.getDetailsByOrderId(order_id);
    }

    public Detail getDetail(Long order_id, Long id) {
        return detailRepository.findByOrderIdAndId(order_id, id);
    }
}
