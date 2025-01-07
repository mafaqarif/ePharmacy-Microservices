package com.example.authenticationservice.controller;

import com.example.authenticationservice.dto.AuthenticationRequest;
import com.example.authenticationservice.model.UserCred;
import com.example.authenticationservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCred user) {
        return authenticationService.saveUser(user);
    }
    @PostMapping("/registersuper")
    public String addNewSuperUser(@RequestBody UserCred user) {
        user.getRoles().add("ROLE_ADMIN");
        return authenticationService.saveUser(user);
    }


    @PostMapping("/token")
    public String getToken(@RequestBody AuthenticationRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authenticationService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authenticationService.validateToken(token);
        return "Token is Valid";
    }
}
