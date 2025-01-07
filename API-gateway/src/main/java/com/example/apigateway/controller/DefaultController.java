package com.example.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fallback")
public class DefaultController {

    @GetMapping("/productservice")
    public String defaultservice(){
        return "Product service Not available";
    }

    @RequestMapping("/fallback/authenticate")
    public ResponseEntity<String> identityServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Authentication service is currently unavailable. Please try again later.");
    }

    @RequestMapping("/fallback/orderservice")
    public ResponseEntity<String> oredrServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Order service is currently unavailable. Please try again later.");
    }
}
