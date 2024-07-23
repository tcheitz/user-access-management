package com.example.user_access_management.service;

import com.example.user_access_management.model.Role;
import com.example.user_access_management.model.User;
import com.example.user_access_management.repository.RoleRepository;
import com.example.user_access_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
