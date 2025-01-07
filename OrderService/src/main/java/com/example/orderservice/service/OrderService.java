package com.example.orderservice.service;

import com.example.orderservice.Util.JwtService;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.CartRepository;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private String topic = "notification-topic";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String , Order> kafkaTemplate;

    public void sendNotification( String authenticationHeader) throws Exception {
        authenticationHeader = authenticationHeader.substring(7);
        Long id = jwtService.getUserId(authenticationHeader);
        Optional<Cart> cartTemp = cartRepository.findCartByUserId(id);

        if (!cartTemp.isPresent()){
            throw new Exception("No cart found");
        }
        Cart cart = cartTemp.get();

        Order order = new Order();
        order.setTotal(cart.getTotal());
        order.setProductList(cart.getProductList());

        orderRepository.save(order);

        kafkaTemplate.send(topic , order);
    }
}
