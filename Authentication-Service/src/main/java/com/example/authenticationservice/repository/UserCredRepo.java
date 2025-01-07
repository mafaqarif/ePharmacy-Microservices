package com.example.authenticationservice.repository;

import com.example.authenticationservice.model.UserCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCredRepo extends JpaRepository<UserCred , Long> {
    Optional<UserCred> findByName(String username);
    List<UserCred> findByRolesContaining(String role);
}
