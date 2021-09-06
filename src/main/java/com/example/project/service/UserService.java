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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private URoleRepository uRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found with: " + username);
        }
        return UserDetailsMapper.build(userModel);
    }

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getByUsername(authentication.getName()).getId();
    }

    public UserModel getUser() {
        return userRepository.findById(getUserID())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + getUserID()));
    }

    public UserModel saveUser(UserModel userModel) {
        URole role = uRoleRepository.findByName("ROLE_USER");
        Set<URole> roles = new HashSet<>();
        roles.add(role);
        userModel.setRoles(roles);
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userModel);
    }

    public UserModel saveUserAdmin(UserModel userModel) {
        URole role = uRoleRepository.findByName("ROLE_ADMIN");
        Set<URole> roles = new HashSet<>();
        roles.add(role);
        role = uRoleRepository.findByName("ROLE_USER");
        roles.add(role);
        userModel.setRoles(roles);
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userModel);
    }

    // users list
    public List<UserModel> getAllUsers() {
        List<UserModel> totalUsers = userRepository.findAll();
        URole role = uRoleRepository.findByName("ROLE_ADMIN");
        List<UserModel> filteredUsers = totalUsers
                .stream()
                .filter(userModel -> !userModel.getRoles().contains(role))
                .collect(Collectors.toList());
        return filteredUsers;
    }

    public UserModel getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
