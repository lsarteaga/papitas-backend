package com.example.project.controller;

import com.example.project.model.Detail;
import com.example.project.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class DetailController {

    @Autowired
    private DetailService detailService;

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PostMapping("/orders/{order_id}/products/{product_id}/details")
    public ResponseEntity<Detail> saveDetail(@Valid @RequestBody Detail detail,
                                             @PathVariable(name = "order_id") Long order_id,
                                             @PathVariable(name = "product_id") Long product_id) {
        return new ResponseEntity<>(detailService.saveDetail(detail, order_id, product_id), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders/{order_id}/details")
    public List<Detail> getDetails(@PathVariable(name = "order_id") Long order_id) {
        return detailService.getDetails(order_id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/orders/{order_id}/details/{id}")
    public Detail getDetail(@PathVariable(name = "order_id") Long order_id,
                            @PathVariable(name = "id") Long id) {
        return detailService.getDetail(order_id, id);
    }
}
