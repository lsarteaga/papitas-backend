package com.example.project.controller;

import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.model.pojo.DetailReport;
import com.example.project.model.pojo.UserOrderReport;
import com.example.project.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // REPORTE DE USUARIOS REGISTRADOS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserModel>> getUsersReport(
            @RequestParam(name = "value", required = false) Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateUsersReport(value), HttpStatus.OK);
    }
    // REPORTE DE COMPRAS POR USUARIO
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/{user_id}/orders")
    public ResponseEntity<List<UserOrderReport>> getUserOrdersReport(
            @PathVariable(name = "user_id") Long user_id,
            @RequestParam(name = "value", required = false) Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateUserOrdersReport(user_id, value), HttpStatus.OK);
    }
    // REPORTE DE INVENTARIO
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getProductsReport(
            @RequestParam(name = "value", required = false) Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateProductsReport(value), HttpStatus.OK);
    }
    // REPORTE DE PRODUCTOS EXPIRADOS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/products/expired")
    public ResponseEntity<List<Product>> getExpiredProductsReport(
            @RequestParam(name = "value", required = false) Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateExpiredProductsReport(value), HttpStatus.OK);
    }
    // REPORTE DE VENTAS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/details")
    public ResponseEntity<List<DetailReport>> getDetailsReport(
            @RequestParam(name = "value", required = false) Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateDetailsReport(value), HttpStatus.OK);
    }
}
