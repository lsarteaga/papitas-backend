package com.example.project.mapper;

import com.example.project.model.URole;
import com.example.project.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsMapper {

    public static UserDetails build(UserModel userModel) {
        return new User(userModel.getUsername(), userModel.getPassword(), getAuthorities(userModel));
    }

    private static Set<? extends GrantedAuthority> getAuthorities(UserModel userModel) {
        Set<URole> roles = userModel.getRoles();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }
}
