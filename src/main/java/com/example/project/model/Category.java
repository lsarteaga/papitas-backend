package com.example.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "slug"), @UniqueConstraint(columnNames = "name")
})
public class Category extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @NotNull
    private String slug;

    private String image;

    public Category() {
    }

    public Category(String name, String slug, String image) {
        this.name = name;
        this.slug = slug;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
