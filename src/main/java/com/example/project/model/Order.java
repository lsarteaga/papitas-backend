package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private OrderStatus orderStatus;

    @NotNull
    private float total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private UserModel userModel;

    public Order() {
    }

    public Order(OrderStatus orderStatus, float total) {
        this.orderStatus = orderStatus;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public UserModel getUser() {
        return userModel;
    }

    public void setUser(UserModel userModel) {
        this.userModel = userModel;
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
