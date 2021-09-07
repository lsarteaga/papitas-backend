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
import java.util.Date;
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
        order.setOrderStatus(OrderStatus.COMPLETE);
        order.setUser(userModel);
        order.setCreated_at(new Date());
        order.setUpdated_at(new Date());
        return orderRepository.save(order);
    }

    public Order getOrder(Long user_id, Long id) {
        return orderRepository.findOrderByUserModelIdAndId(user_id, id);
    }

    public List<Order> getOrders(Long user_id) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
        return orderRepository.findOrdersByUserModelId(user_id);
    }

    // obtener todas las ordenes sin filtros
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
