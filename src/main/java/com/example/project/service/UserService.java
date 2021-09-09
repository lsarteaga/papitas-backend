package com.example.project.service;

import com.example.project.exception.ResourceNotFoundException;
import com.example.project.mapper.UserDetailsMapper;
import com.example.project.model.URole;
import com.example.project.model.UserModel;
import com.example.project.repository.URoleRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private URoleRepository uRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // encuentra el usuario en la base de datos
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found with: " + username);
        }
        return UserDetailsMapper.build(userModel);
    }
    // obtener el ID usuario actualmente logeado
    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getByUsername(authentication.getName()).getId();
    }
    // obtener al usuario actualmente logeado
    public UserModel getUser() {
        return userRepository.findById(getUserID())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + getUserID()));
    }

    // guardar un usuario
    public UserModel saveUser(UserModel userModel) {
        URole role = uRoleRepository.findByName("ROLE_USER");
        Set<URole> roles = new HashSet<>();
        roles.add(role);
        userModel.setRoles(roles);
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userRepository.save(userModel);
    }
    // guardar un usuario admin
    public UserModel saveUserAdmin(UserModel userModel) {
        URole role = uRoleRepository.findByName("ROLE_ADMIN");
        Set<URole> roles = new HashSet<>();
        roles.add(role);
        role = uRoleRepository.findByName("ROLE_USER");
        roles.add(role);
        userModel.setRoles(roles);
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userModel.setCreated_at(new Date());
        userModel.setUpdated_at(new Date());
        return userRepository.save(userModel);
    }

    // obtener la lista de usuarios
    public List<UserModel> getAllUsers() {
        List<UserModel> totalUsers = userRepository.findAll();
        URole role = uRoleRepository.findByName("ROLE_ADMIN");
        return totalUsers
                .stream()
                .filter(userModel -> !userModel.getRoles().contains(role))
                .collect(Collectors.toList());
    }

    // obtener un usuario por su 'username'
    public UserModel getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
