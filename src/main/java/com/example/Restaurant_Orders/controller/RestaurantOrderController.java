package com.example.Restaurant_Orders.controller;

import com.example.Restaurant_Orders.dto.RestaurantDTO;
import com.example.Restaurant_Orders.service.feign.OrderServiceClient;
import com.example.Restaurant_Orders.config.feign.TokenRequestInterceptor;
import com.example.Restaurant_Orders.service.rabbitmq.OrderListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant-order/restaurant")
public class RestaurantOrderController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantOrderController.class);

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private TokenRequestInterceptor tokenRequestInterceptor;

    @Autowired
    private OrderListenerManager listenerManager;

    @GetMapping("/all")
    public List<RestaurantDTO> getAllRestaurant(@RequestHeader("Authorization") String token) {
        tokenRequestInterceptor.setToken(token.split(" ")[1]);
        return orderServiceClient.getAllRestaurant();
    }

    @GetMapping
    public RestaurantDTO getRestaurant(@RequestHeader("Authorization") String token) throws Exception {
        tokenRequestInterceptor.setToken(token.split(" ")[1]);
        RestaurantDTO restaurant = orderServiceClient.getRestaurant();
        if(restaurant != null && restaurant.getId() != null){
            listenerManager.setupListeners(List.of(restaurant));
        }

        return restaurant;
    }
}
