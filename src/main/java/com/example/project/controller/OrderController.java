package com.example.project.controller;

import com.example.project.model.Order;
import com.example.project.service.OrderService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(authentication.getName()).getId();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PostMapping("/orders")
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(orderService.saveOrder(order, getUserID()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PutMapping("/orders/{id}/update")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order,
                                             @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.updateOrder(order, getUserID(), id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.getOrder(getUserID(), id), HttpStatus.OK);
    }

    // user orders report
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/orders/user/{user_id}")
    public List<Order> getOrders(@PathVariable(name = "user_id") Long user_id) {
        return orderService.getOrders(user_id);
    }

    // user orders
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders(getUserID());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/orders/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
