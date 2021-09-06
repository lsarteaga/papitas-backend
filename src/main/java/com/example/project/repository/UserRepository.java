package com.example.project.repository;

import com.example.project.model.URole;
import com.example.project.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
