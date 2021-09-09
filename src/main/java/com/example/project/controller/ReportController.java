package com.example.project.controller;

import com.example.project.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<String> getUsersReport() throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateUsersReport(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/{user_id}/orders")
    public ResponseEntity<String> getUserOrdersReport(@PathVariable(name = "user_id") Long user_id)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateUserOrdersReport(user_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/products")
    public ResponseEntity<String> getProductsReport()
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateProductsReport(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/products/expired")
    public ResponseEntity<String> getExpiredProductsReport()
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateExpiredProductsReport(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/details")
    public ResponseEntity<String> getDetailsReport()
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        return new ResponseEntity<>(reportService.generateDetailsReport(), HttpStatus.OK);
    }
}
