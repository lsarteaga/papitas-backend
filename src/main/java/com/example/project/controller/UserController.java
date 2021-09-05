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
public class UserController {
    @Autowired
    private UserService userService;

    // register
    @PostMapping("/register")
    public ResponseEntity<UserModel> saveUser(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUser(userModel), HttpStatus.CREATED);
    }

    // new admin users
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("admin/user-admin")
    public ResponseEntity<UserModel> saveUserAdmin(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUserAdmin(userModel), HttpStatus.CREATED);
    }

    @PostMapping("/testing")
    public ResponseEntity<UserModel> saveUserTesting(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.saveUserAdmin(userModel), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/users")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/users/{username}/find")
    public ResponseEntity<UserModel> getUser(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(userService.getByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
}
