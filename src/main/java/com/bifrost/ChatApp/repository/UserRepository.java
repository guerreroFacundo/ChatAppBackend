package com.bifrost.ChatApp.repository;

import com.bifrost.ChatApp.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Buscar por nombre de usuario
    Optional<User> findByEmail(String email); // Buscar por email
    @Query("SELECT m.username FROM User m WHERE m.id = :userId")
    String findUsernameById(@Param("userId") Long userId);
}
