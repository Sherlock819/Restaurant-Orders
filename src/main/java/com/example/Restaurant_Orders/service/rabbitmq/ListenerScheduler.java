package com.example.Restaurant_Orders.service.rabbitmq;

import com.example.Restaurant_Orders.dto.RestaurantDTO;
import com.example.Restaurant_Orders.service.feign.OrderServiceClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.scheduling.annotation.Scheduled.CRON_DISABLED;

@Component
public class ListenerScheduler {

    @Autowired
    private OrderListenerManager listenerManager;

    @Autowired
    private OrderServiceClient orderServiceClient;

    // Runs every 5 minutes
    @Scheduled(fixedRate = 300000, cron = CRON_DISABLED)
    public void scheduleListenerUpdate() {
        try {
            // Fetch updated restaurant list from Restaurant Management Service
            List<RestaurantDTO> restaurants = fetchActiveRestaurants();
            listenerManager.setupListeners(restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<RestaurantDTO> fetchActiveRestaurants() {
        // Dummy data or fetch from actual DB
        return orderServiceClient.getAllRestaurant();
    }
}
