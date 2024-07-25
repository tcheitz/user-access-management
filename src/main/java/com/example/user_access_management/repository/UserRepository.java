package com.example.user_access_management.repository;

import com.example.user_access_management.model.Role;
import com.example.user_access_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r IN :roles")
    List<User> findAllByRoles(@Param("roles") List<Role> roles);


}
