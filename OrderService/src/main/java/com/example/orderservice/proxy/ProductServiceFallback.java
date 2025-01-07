package com.example.orderservice.proxy;

import com.example.orderservice.DTO.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallback implements ProductServiceProxy{
    @Override
    public ProductDTO getProductDetails(Long id) {
        return null;
    }
}
