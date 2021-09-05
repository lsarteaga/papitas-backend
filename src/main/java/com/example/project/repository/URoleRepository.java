package com.example.project.repository;

import com.example.project.model.URole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URoleRepository extends JpaRepository<URole, Long> {
    URole findByName(String name);
    URole getURoleById(Long id);
}
