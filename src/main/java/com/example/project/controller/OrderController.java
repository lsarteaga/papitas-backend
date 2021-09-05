package com.example.project.controller;

import com.example.project.model.Order;
import com.example.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{user_id}")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order,
                                           @PathVariable(name = "user_id") Long user_id) {
        return new ResponseEntity<>(orderService.saveOrder(order, user_id), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order,
                                             @PathVariable(name = "user_id") Long user_id,
                                             @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.updateOrder(order, user_id, id), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "user_id") Long user_id,
                                          @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.getOrder(user_id, id), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<Order> getOrders(@PathVariable(name = "user_id") Long user_id) {
        return orderService.getOrders(user_id);
    }
}
