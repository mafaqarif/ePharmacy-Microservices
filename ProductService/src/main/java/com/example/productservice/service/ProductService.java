package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getProductByName(String name){
        return productRepository.getProductsByNameContaining(name);
    }

    public Product saveProduct(Product product){
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product update(Product product , Long id) throws Exception {
        Optional<Product> temp = productRepository.findById(id);
        if (temp.isEmpty()){
            throw new Exception("No Product found");
        }
        Product p1 = temp.get();
        p1.setName(product.getName());
        p1.setDescription(product.getDescription());
        productRepository.save(p1);
        return p1;
    }

}
