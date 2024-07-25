package com.example.user_access_management.service;

import com.example.user_access_management.dto.UserDTO;
import com.example.user_access_management.exception.UserAlreadyExistsException;
import com.example.user_access_management.model.Role;
import com.example.user_access_management.model.User;
import com.example.user_access_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
        }

        Set<Role> roles = Optional.ofNullable(user.getRoles())
                .orElseGet(HashSet::new);

        var _user = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(_user);
        return user.getEmail() + " account created";
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public String deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("User with id: " + id + " doesn't exist.");
        }
        userRepository.deleteById(id);
        return "user has been deleted";
    }


    public List<UserDTO> getUsersByRole(Set<Role> roles) {
        List<Role> roleList = new ArrayList<>(roles); // Convert Set to List
        List<User> users = userRepository.findAllByRoles(roleList);
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<User> findByEmail(String email) {
        return (userRepository.findByEmail(email));
    }

    private UserDTO convertToDto(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet());
        return new UserDTO(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                roleNames);
    }
}
