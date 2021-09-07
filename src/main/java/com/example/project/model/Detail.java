package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "details")
public class Detail extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int quantity;
    @NotNull
    private float total;
    @NotNull
    private float unitPrice;
    @NotNull
    private String productName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Product product;

    public Detail() {
    }

    public Detail(int quantity, float total, float unitPrice, String productName) {
        this.quantity = quantity;
        this.total = total;
        this.unitPrice = unitPrice;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Date getCreated_at() {
        return super.getCreated_at();
    }

    @Override
    public void setCreated_at(Date created_at) {
        super.setCreated_at(created_at);
    }

    @Override
    public Date getUpdated_at() {
        return super.getUpdated_at();
    }

    @Override
    public void setUpdated_at(Date updated_at) {
        super.setUpdated_at(updated_at);
    }
}
