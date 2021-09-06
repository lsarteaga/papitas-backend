package com.example.project.repository;

import com.example.project.model.Order;
import com.example.project.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByUserModelIdAndId(Long user_id, Long id);
    List<Order> findOrdersByUserModelIdAndOrderStatus(Long user_id, OrderStatus orderStatus);
}
