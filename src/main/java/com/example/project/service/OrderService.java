package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Order;
import com.example.project.model.OrderStatus;
import com.example.project.model.UserModel;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Order saveOrder(Order order, Long user_id) {
        UserModel userModel = userRepository.findById(user_id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
        order.setUser(userModel);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order, Long user_id, Long id) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        existingOrder.setUpdatedAt(LocalDateTime.now());
        existingOrder.setTotal(order.getTotal());
        existingOrder.setOrderStatus(order.getOrderStatus());
        return orderRepository.save(existingOrder);
    }

    public Order getOrder(Long user_id, Long id) {
        return orderRepository.findByIdAndUserId(id, user_id);
    }

    public List<Order> getOrders(Long user_id) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
        return orderRepository.findByUserId(user_id);
    }
}
