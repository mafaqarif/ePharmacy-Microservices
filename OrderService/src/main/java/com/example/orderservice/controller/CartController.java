package com.example.orderservice.controller;

import com.example.orderservice.model.Cart;
import com.example.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping
    public List<Cart> getAll(){
        return cartService.getAllCart();
    }

    @PostMapping("/{productId}")
    public void addProduct(@PathVariable Long productId ,@RequestHeader("Authorization") String authorizationHeader){
        cartService.addProduct(productId , authorizationHeader);
    }
    /*
    @PostMapping("/remove/{productId}")
    public void removeProduct(@PathVariable Long productId ,@RequestHeader("Authorization") String authorizationHeader){
        cartService.removeProduct(productId , authorizationHeader);
    }
    *
    * */

}
