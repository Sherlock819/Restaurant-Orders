package com.example.Restaurant_Orders.service.feign;

import com.example.Restaurant_Orders.config.feign.FeignConfig;
import com.example.Restaurant_Orders.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "RESTAURANT", url = "${restaurant.service.url}", configuration = FeignConfig.class)
public interface OrderServiceClient {
    @GetMapping("/restaurant-management/restaurant/all")
    List<RestaurantDTO> getAllRestaurant();

    @GetMapping("/restaurant-management/restaurant")
    RestaurantDTO getRestaurant();
}
