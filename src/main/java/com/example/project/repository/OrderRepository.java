package com.example.project.repository;

import com.example.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // obtener order por user_id e id
    Order findOrderByUserModelIdAndId(Long user_id, Long id);
    List<Order> findOrdersByUserModelId(Long user_id);
}
