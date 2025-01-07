package com.example.orderservice.proxy;

import com.example.orderservice.DTO.ProductDTO;
import com.example.orderservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service" , fallback = ProductServiceFallback.class)
public interface ProductServiceProxy {
    @GetMapping("/products/{id}")
    ProductDTO getProductDetails(@PathVariable("id") Long id);
}
