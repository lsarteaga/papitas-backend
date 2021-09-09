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
import java.util.Optional;
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
    // PATHS USADOS PARA ENCONTRAR EL ARCHIVO DE JASPER REPORTS
    final String path = "c:\\report";
    final String usersPath = "classpath:users_report.jrxml";
    final String usersOrdersPath = "classpath:user_orders_report.jrxml";
    final String productsPath = "classpath:products_report.jrxml";
    final String detailsPath = "classpath:details_report.jrxml";
    final String expiredProductsPath = "classpath:expired_products_report.jrxml";

    // TODOS LOS USUARIOS REGISTRADOS, SI RECIBE EL PARAMETRO OPCIONAL SE GENERA EL REPORTE
    public List<UserModel> generateUsersReport(Optional<Long> value)
            throws FileSystemNotFoundException, JRException, FileNotFoundException {
        List<UserModel> users = userService.getAllUsers();
        if (value.isPresent())
            generateReport(usersPath, users, "users-");
        return users;
    }
    // TODOS LAS ORDENES DE UN USUARIO, SI RECIBE EL PARAMETRO OPCIONAL SE GENERA EL REPORTE
    public List<UserOrderReport> generateUserOrdersReport(Long user_id, Optional<Long> value) throws FileSystemNotFoundException,
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
        if (value.isPresent())
            generateReport(usersOrdersPath, userOrderReports, "user-orders-");
        return userOrderReports;
    }
    // TODOS LOS PRODUCTOS (INVENTARIO), SI RECIBE EL PARAMETRO OPCIONAL SE GENERA EL REPORTE
    public List<Product> generateProductsReport(Optional<Long> value)
            throws JRException, FileNotFoundException {
        List<Product> products = productService.getAllProducts();
        if (value.isPresent())
            generateReport(productsPath, products, "products-");
        return products;
    }
    // TODOS LOS PRODUCTOS EXPIRADOS, SI RECIBE EL PARAMETRO OPCIONAL SE GENERA EL REPORTE
    public List<Product> generateExpiredProductsReport(Optional<Long> value)
            throws JRException, FileNotFoundException {
        List<Product> products = productService.getExpiredProducts();
        if (value.isPresent())
            generateReport(expiredProductsPath, products, "expired-products-");
        return products;
    }
    // TODOS LAS VENTAS REALIZADAS, SI RECIBE EL PARAMETRO OPCIONAL SE GENERA EL REPORTE
    public List<DetailReport> generateDetailsReport(Optional<Long> value)
            throws JRException, FileNotFoundException {
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
        if (value.isPresent())
            generateReport(detailsPath, detailReportList, "details-");
        return detailReportList;
    }
    /*
    Funcion para generar un reporte, recibe el path del archivo jrxml, la lista de modelos
    y el prefijo de como se va a guardar el pdf,
    1) encuentra el archivo jrml
    2) genera el datasource a partir de los modelos provistos
    3) se pone un MAP<,> para identificar quien creo el pdf
    4) se llenan los datos al reporte, segun el formato del archivo jrxml
    5) se guarda el pdf generado en el path especificado + prefijo + fecha generacion + .pdf
     */
    public void generateReport(String jrxmlPath, List<?> models, String prefix)
            throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(jrxmlPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(models);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "ADMIN");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(print, path + "\\" + prefix + LocalDate.now().toString() + ".pdf");
    }
}
