package com.example.orderservice.service;

import com.example.orderservice.DTO.ProductDTO;
import com.example.orderservice.Util.JwtService;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Product;
import com.example.orderservice.proxy.ProductServiceProxy;
import com.example.orderservice.repository.CartRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Qualifier("com.example.orderservice.proxy.ProductServiceProxy")
    @Autowired
    private ProductServiceProxy productServiceProxy;

    @Autowired
    private JwtService jwtService;
    public Cart createCart(){
        Cart cart=new Cart();
        cartRepository.save(cart);
        return cart;
    }

    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }

    public Optional<Cart> getCart(Long id){
        return cartRepository.findById(id);
    }
    public Optional<Cart> getCartByUserId(Long id){
        return cartRepository.findCartByUserId(id);
    }


    @CircuitBreaker(name = "productService" ,fallbackMethod ="addProductFallBack" )
    public Cart addProduct(Long productid , String authorizationHeader){

        authorizationHeader = authorizationHeader.substring(7);

        Long id = jwtService.getUserId(authorizationHeader);
        Optional<Cart> cartTemp = cartRepository.findCartByUserId(id);
        Cart cart;
        if(!cartTemp.isPresent()){
            cart = new Cart();
            cart.setUserId(id);
        }
        else {
            cart = cartTemp.get();
        }
        ProductDTO productDto= productServiceProxy.getProductDetails(productid);
        Product product = new Product(productDto.getId() , productDto.getName() , productDto.getDescription() , productDto.getPrice());

        cart.getProductList().add(product);
        cart.setTotal(cart.getTotal()+ product.getPrice());

        cartRepository.save(cart);

        return cart;

    }

    public void addProductFallBack(){
        throw new RuntimeException("Product service down");
    }
}
