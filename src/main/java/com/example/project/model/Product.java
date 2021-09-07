package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "slug"), @UniqueConstraint(columnNames = "name")
        })
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;
    @NotNull
    private String slug;
    @NotNull
    private String description;
    @NotNull
    private float price;
    @NotNull
    private int quantity;

    private String image;

    @Enumerated
    private ProductStatus productStatus;

    @Enumerated
    private ProductExpired productExpired;

    @NotNull
    private int sold;

    @NotNull
    private int available;

    @NotNull
    private Date expired_at;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SubCategory subCategory;

    public Product() {
    }

    public Product(String name, String slug, String description,
                   float price, int quantity, String image, ProductStatus productStatus,
                   ProductExpired productExpired, int sold, int available, Date expired_at) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.productStatus = productStatus;
        this.productExpired = productExpired;
        this.sold = sold;
        this.available = available;
        this.expired_at = expired_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public ProductExpired getProductExpired() {
        return productExpired;
    }

    public void setProductExpired(ProductExpired productExpired) {
        this.productExpired = productExpired;
    }

    public Date getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Date expired_at) {
        this.expired_at = expired_at;
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
