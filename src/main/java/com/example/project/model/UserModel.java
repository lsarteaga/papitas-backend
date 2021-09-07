package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")
})
public class UserModel extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(min = 5)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<URole> roles;

    public UserModel() {
    }

    public UserModel(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<URole> getRoles() {
        return roles;
    }

    public void setRoles(Set<URole> roles) {
        this.roles = roles;
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
