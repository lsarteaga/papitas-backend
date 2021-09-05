package com.example.project.repository;

import com.example.project.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Query(value = "SELECT * FROM details d LEFT JOIN orders o ON d.order_id = o.id WHERE d.order_id = ?1", nativeQuery = true)
    List<Detail> getDetails(Long order_id);
}
