package com.example.project.repository;

import com.example.project.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long> {

    List<Detail> getDetailsByOrderId(Long order_id);
    Detail findByOrderIdAndId(Long order_id, Long id);
}
