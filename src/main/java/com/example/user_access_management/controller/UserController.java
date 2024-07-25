package com.example.user_access_management.controller;

import com.example.user_access_management.auth.AuthenticationRequest;
import com.example.user_access_management.auth.AuthenticationResponse;
import com.example.user_access_management.auth.AuthenticationService;
import com.example.user_access_management.dto.UserDTO;
import com.example.user_access_management.model.Role;
import com.example.user_access_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> listUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
        List<UserDTO> users;
        if (isAdmin) {
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersByRole(Set.of(Role.USER));
        }

        return ResponseEntity.ok(users);
    }
}
