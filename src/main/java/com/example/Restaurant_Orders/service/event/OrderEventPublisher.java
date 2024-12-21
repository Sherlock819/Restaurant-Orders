//package com.example.Restaurant_Orders.service.event;
//
//import com.example.Restaurant.dto.OrderDTO;
//import com.example.Restaurant.dto.OrderUpdateEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OrderEventPublisher {
//    private final ApplicationEventPublisher publisher;
//
//    public OrderEventPublisher(ApplicationEventPublisher publisher) {
//        this.publisher = publisher;
//    }
//
//    public void publishOrderUpdate(OrderDTO order, String restaurantId) {
//        publisher.publishEvent(new OrderUpdateEvent(order, restaurantId));
//    }
//}
