package com.example.project.repository;

import com.example.project.model.Order;
import com.example.project.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /*@Query(value = "SELECT * FROM orders o INNER JOIN users u ON o.user_id = u.id WHERE o.user_id = ?1 AND u.id = ?2", nativeQuery = true)
    Order getOrder(Long user_id, Long id);*/
    /*@Query(value = "SELECT * FROM orders o INNER JOIN users u ON o.user_id = u.id WHERE o.user_id = ?1 AND o.order_status = ?2", nativeQuery = true)
    List<Order> getUserOrders(Long user_id, OrderStatus orderStatus);
*/
    Order findOrderByUserModelIdAndId(Long user_id, Long id);
    List<Order> findOrdersByUserModelIdAndOrderStatus(Long user_id, OrderStatus orderStatus);
}
