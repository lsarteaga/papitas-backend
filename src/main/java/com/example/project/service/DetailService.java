package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Detail;
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
        orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + order_id));
        productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + order_id));
        return  detailRepository.save(detail);
    }

    public Detail updateDetail(Detail detail, Long order_id, Long product_id, Long id) {
        orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + order_id));
        productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + order_id));
        Detail existingDetail = detailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail not found with id: " + order_id));
        existingDetail.setQuantity(detail.getQuantity());
        existingDetail.setUpdatedAt(LocalDateTime.now());
        existingDetail.setProductName(detail.getProductName());
        existingDetail.setTotal(detail.getTotal());
        existingDetail.setUnitPrice(detail.getUnitPrice());
        return detailRepository.save(existingDetail);
    }

    public List<Detail> getDetails(Long order_id) {
        return detailRepository.findByOrderId(order_id);
    }
}
