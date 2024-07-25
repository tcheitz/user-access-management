package com.example.user_access_management;

import com.example.user_access_management.model.Role;
import com.example.user_access_management.model.User;
import com.example.user_access_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class UserAccessManagementApplication implements CommandLineRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserAccessManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userService.findByEmail("admin@email.com").isEmpty()) {
            var _user = User.builder()
                    .firstname("admin")
                    .email("admin@email.com")
                    .password("admin123")
                    .roles(Set.of(Role.ADMIN))
                    .build();
            userService.createUser(_user);
        }
    }
}
