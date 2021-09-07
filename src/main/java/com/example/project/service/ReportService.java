package com.example.project.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystemNotFoundException;

@Service
public class ReportService {

    public String generateReport() throws FileSystemNotFoundException, JRException {
        return "jaj";
    }
}
