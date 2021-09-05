package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.*;
import com.example.project.repository.DetailRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        detail.setCreatedAt(LocalDateTime.now());
        detail.setUpdatedAt(LocalDateTime.now());
        detail.setProduct(product);
        detail.setOrder(order);
        detail.setProductName(product.getName());
        detail.setUnitPrice(product.getPrice());
        // updating stock
        product.setSold(product.getSold() + detail.getQuantity());
        product.setAvailable(product.getAvailable() - detail.getQuantity());

        if (product.getAvailable() == 0) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }
        return  detailRepository.save(detail);
    }

    public Detail updateDetail(Detail detail, Long order_id, Long product_id, Long id) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + order_id));
        if (order.getOrderStatus() == OrderStatus.PROGRESS) {
            throw new ResourceNotFoundException("Order cannot be updated");
        }
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + order_id));
        Detail existingDetail = detailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail not found with id: " + order_id));
        existingDetail.setQuantity(detail.getQuantity());
        existingDetail.setUpdatedAt(LocalDateTime.now());
        existingDetail.setProductName(product.getName());
        existingDetail.setTotal(detail.getTotal());
        existingDetail.setUnitPrice(product.getPrice());
        // updating stock
        product.setSold(product.getSold() + detail.getQuantity());
        product.setAvailable(product.getAvailable() - detail.getQuantity());

        if (product.getAvailable() == 0) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }

        return detailRepository.save(existingDetail);
    }

    public List<Detail> getDetails(Long order_id) {
        return detailRepository.getDetails(order_id);
    }

    public String deleteDetail(Long order_id, Long id) {
        orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + order_id));
        detailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail not found with id: " + order_id));
        detailRepository.deleteById(id);
        return "Detail deleted";
    }
}
