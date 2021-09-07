package com.example.project.controller;

import com.example.project.model.UserModel;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    // registro de usuarios
    @PostMapping("/register")
    public ResponseEntity<UserModel> saveUser(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUser(userModel), HttpStatus.CREATED);
    }

    // crear administradores
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/user-admin")
    public ResponseEntity<UserModel> saveUserAdmin(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUserAdmin(userModel), HttpStatus.CREATED);
    }

    // crear usuario admin (base de datos vacia)
    @PostMapping("/testing")
    public ResponseEntity<UserModel> saveUserTesting(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUserAdmin(userModel), HttpStatus.CREATED);
    }

    // obtener lista de usuarios registrados
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/users")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    // obtener un usuario en especifico
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/users/{username}/find")
    public ResponseEntity<UserModel> getUser(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(userService.getByUsername(username), HttpStatus.OK);
    }

    // obtener datos del usuario actualmente logeado
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/users/profile")
    public ResponseEntity<UserModel> getUserProfile() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }
}
