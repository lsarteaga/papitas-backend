package com.example.project.repository;

import com.example.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndUserId(Long id, Long user_id);
    List<Order> findByUserId(Long user_id);
}
