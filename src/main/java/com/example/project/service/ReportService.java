package com.example.project.service;

import com.example.project.model.Order;
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

    @Autowired
    private OrderService orderService;

    final String path = "c:\\report";
    final String usersPath = "classpath:users_report.jrxml";
    final String usersOrdersPath = "classpath:userss_orders_report.jrxml";
    final String productsPath = "classpath:products_report.jrxml";
    final String ordersPath = "classpath:orders_report.jrxml";
    final String expiredProductsPath = "classpath:products_expired_report.jrxml";

    public String generateUsersReport() throws FileSystemNotFoundException, JRException, FileNotFoundException {
        List<UserModel> users = userService.getAllUsers();
        return generateReport(usersPath, users, "users-");
    }

    public String generateUserOrdersReport(Long user_id) throws FileSystemNotFoundException,
            JRException, FileNotFoundException {
        List<Order> orders = orderService.getOrders(user_id);
        return generateReport(usersOrdersPath, orders, "user-orders-");
    }

    public String generateReport(String jrxmlPath, List<?> models, String prefix) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(jrxmlPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(models);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "ADMIN");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(print, path + "\\" + prefix + LocalDate.now().toString() + ".pdf");
        return "SUCCESS";
    }
}
