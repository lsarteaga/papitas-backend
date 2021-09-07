package com.example.project.controller;

import net.sf.jasperreports.engine.JRException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.FileSystemNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/reports")
public class ReportController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String getUsersReport() throws FileSystemNotFoundException, JRException {
        return "ajja";
    }
}
