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

    // obtener el id del usuario actualmente logeado
    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(authentication.getName()).getId();
    }
    //http://localhost:8080/orders
    // los administradores y usuarios pueden crear ordenes
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PostMapping("/orders")
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(orderService.saveOrder(order, getUserID()), HttpStatus.CREATED);
    }

    // ruta para obtener una orden del usuario actualmente logeado
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.getOrder(getUserID(), id), HttpStatus.OK);
    }

    // ruta para obtener todas las ordenes de un usuario
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/orders/user/{user_id}")
    public List<Order> getOrders(@PathVariable(name = "user_id") Long user_id) {
        return orderService.getOrders(user_id);
    }

    // ruta para obtener todas las ordenes del usuario actualmente logeado
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders(getUserID());
    }

    // ruta para obtener todas las ordenes
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/orders/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
