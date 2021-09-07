package com.example.project.service;

import com.example.project.model.UserModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private UserService userService;

    public String generateUsersReport() throws FileSystemNotFoundException, JRException, FileNotFoundException {
        String path = "c:\\report";
        List<UserModel> users = userService.getAllUsers();
        File file = ResourceUtils.getFile("classpath:users_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "ADMIN");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(print, path + "\\users-" + LocalDate.now().toString() + ".pdf");
        return "REPORT GENERATED";
    }
}
