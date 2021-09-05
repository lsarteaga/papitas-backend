package com.example.project.controller;

import com.example.project.model.Detail;
import com.example.project.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders/{order_id}/products/{product_id}")
public class DetailController {
    @Autowired
    private DetailService detailService;

    @PostMapping("/details")
    public ResponseEntity<Detail> saveDetail(@Valid @RequestBody Detail detail,
                                             @PathVariable(name = "order_id") Long order_id,
                                             @PathVariable(name = "product_id") Long product_id) {
        return new ResponseEntity<>(detailService.saveDetail(detail, order_id, product_id), HttpStatus.CREATED);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<Detail> updateDetail(@Valid @RequestBody Detail detail,
                                               @PathVariable(name = "order_id") Long order_id,
                                               @PathVariable(name = "product_id") Long product_id,
                                               @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(detailService.updateDetail(detail, order_id, product_id, id), HttpStatus.OK);
    }

    @GetMapping("/details")
    public List<Detail> getDetails(@PathVariable(name = "order_id") Long order_id) {
        return detailService.getDetails(order_id);
    }
}
