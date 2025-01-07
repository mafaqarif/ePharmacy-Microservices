package com.example.authenticationservice.service;

import com.example.authenticationservice.model.UserCred;
import com.example.authenticationservice.repository.UserCredRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserCredRepo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCred credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        if (credential.getRoles() == null || credential.getRoles().isEmpty()) {
            credential.setRoles(List.of("ROLE_USER"));
        }
        repository.save(credential);
        return "User Registered Successfully";
    }

    public String generateToken(String username) {
        Optional<UserCred> optionalUser= repository.findByName(username);
        UserCred user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return jwtService.generateToken(username , user.getId() , user.getRoles());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
