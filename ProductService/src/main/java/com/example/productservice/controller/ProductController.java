package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id)
    {
        Optional<Product> temp = productService.getProductById(id);
        if (temp.isEmpty()){
            return new ResponseEntity<>("No Product found",HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Product>(temp.get() , HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity searchProduct(@PathVariable String name){
        List<Product> products = productService.getProductByName(name);
        if (products.size() == 0){
            return  new ResponseEntity("No products" , HttpStatus.OK);
        }
        return  new ResponseEntity(products , HttpStatus.OK);

    }

    @PostMapping()
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@RequestBody Product product , @PathVariable Long id) throws Exception {
        return productService.update(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id ){
        productService.deleteProduct(id);
    }
}
