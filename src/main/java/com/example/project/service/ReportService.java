package com.example.project.service;

import com.example.project.model.Detail;
import com.example.project.model.Order;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.model.pojo.DetailReport;
import com.example.project.model.pojo.UserOrderReport;
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
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DetailService detailService;

    final String path = "c:\\report";
    final String usersPath = "classpath:users_report.jrxml";
    final String usersOrdersPath = "classpath:user_orders_report.jrxml";
    final String productsPath = "classpath:products_report.jrxml";
    final String detailsPath = "classpath:details_report.jrxml";
    final String expiredProductsPath = "classpath:expired_products_report.jrxml";

    public String generateUsersReport() throws FileSystemNotFoundException, JRException, FileNotFoundException {
        List<UserModel> users = userService.getAllUsers();
        return generateReport(usersPath, users, "users-");
    }

    public String generateUserOrdersReport(Long user_id) throws FileSystemNotFoundException,
            JRException, FileNotFoundException {
        List<Order> orders = orderService.getOrders(user_id);
        List<UserOrderReport> userOrderReports = orders
                .stream()
                .map(order -> {
                    UserOrderReport userOrderReport = new UserOrderReport();
                    userOrderReport.setId(order.getId());
                    userOrderReport.setName(order.getUser().getName());
                    userOrderReport.setTotal(order.getTotal());
                    userOrderReport.setCreated_at(order.getCreated_at());
                    return userOrderReport;
                })
                .collect(Collectors.toList());

        return generateReport(usersOrdersPath, userOrderReports, "user-orders-");
    }

    public String generateProductsReport() throws JRException, FileNotFoundException {
        List<Product> products = productService.getAllProducts();
        return generateReport(productsPath, products, "products-");
    }

    public String generateExpiredProductsReport() throws JRException, FileNotFoundException {
        List<Product> products = productService.getExpiredProducts();
        return generateReport(expiredProductsPath, products, "expired-products-");
    }

    public String generateDetailsReport() throws JRException, FileNotFoundException {
        List<Detail> details = detailService.getAllDetails();
        List<DetailReport> detailReportList = details
                .stream()
                .map(detail -> {
                    DetailReport detailReport = new DetailReport();
                    detailReport.setId(detail.getId());
                    detailReport.setProduct_name(detail.getProductName());
                    detailReport.setQuantity(detail.getQuantity());
                    detailReport.setCreated_at(detail.getCreated_at());
                    detailReport.setUnit_price(detail.getUnitPrice());
                    detailReport.setTotal(detail.getTotal());
                    return detailReport;
                })
                .collect(Collectors.toList());
        return generateReport(detailsPath, detailReportList, "details-");
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
