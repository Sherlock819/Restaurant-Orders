package com.example.Restaurant_Orders.service.rabbitmq;

import com.example.Restaurant_Orders.config.rabbitmq.RabbitMQConfig;
import com.example.Restaurant_Orders.dto.RestaurantDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderListenerManager {

    private static final Logger log = LoggerFactory.getLogger(OrderListenerManager.class);
    private final Connection connection;
    private final Map<String, Channel> restaurantChannels = new HashMap<>();
    private final OrderListener orderListener;  // Injected OrderListener bean

    @Autowired
    public OrderListenerManager(Connection connection, OrderListener orderListener) {
        this.connection = connection;
        this.orderListener = orderListener;
    }

    public void setupListeners(List<RestaurantDTO> restaurants) throws Exception {
        for (RestaurantDTO restaurant : restaurants) {
            String restaurantId = restaurant.getId().toString();
            createRestaurantQueues(restaurantId);
        }
    }

    private void createRestaurantQueues(String restaurantId) throws Exception {
        String requestQueueName = getRequestQueueName(restaurantId);
        String requestRoutingKey = getRequestRoutingKey(restaurantId);
        String responseQueueName = getResponseQueueName(restaurantId);
        String responseRoutingKey = getResponseRoutingKey(restaurantId);

        Channel channel = connection.createChannel();

        // Declare request and response queues
        channel.queueDeclare(requestQueueName, true, false, false, null);
        channel.queueDeclare(responseQueueName, true, false, false, null);

        // Bind the request queue to the exchange
        channel.queueBind(requestQueueName, RabbitMQConfig.ORDER_EXCHANGE, requestRoutingKey);
        // Bind the response queue to the exchange
        channel.queueBind(responseQueueName, RabbitMQConfig.ORDER_EXCHANGE, responseRoutingKey);

        restaurantChannels.put(restaurantId, channel);

        // Use the injected OrderListener bean to start listening
        log.info("Starting order listener for queue: {}, routing key: {}", requestQueueName, requestRoutingKey);
        orderListener.startListening(channel, requestQueueName, responseRoutingKey);
    }

    public void sendOrder(String restaurantId, String orderMessage) throws Exception {
        Channel channel = restaurantChannels.get(restaurantId);
        if (channel == null) {
            throw new IllegalStateException("No channel found for restaurant: " + restaurantId);
        }

        String requestRoutingKey = getRequestRoutingKey(restaurantId);
        // Publish the order message to the exchange with the request routing key
        channel.basicPublish(RabbitMQConfig.ORDER_EXCHANGE, requestRoutingKey, null, orderMessage.getBytes(StandardCharsets.UTF_8));
        log.info("Sending order for routing key: {}", requestRoutingKey);
        System.out.println("Sent order message: " + orderMessage + " to routing key: " + requestRoutingKey);
    }

    private static String getResponseRoutingKey(String restaurantId) {
        return "restaurant." + restaurantId + ".response";
    }

    private static String getResponseQueueName(String restaurantId) {
        return "restaurant_" + restaurantId + "_responseQueue";
    }

    private static String getRequestRoutingKey(String restaurantId) {
        return "restaurant." + restaurantId + ".request";
    }

    private static String getRequestQueueName(String restaurantId) {
        return "restaurant_" + restaurantId + "_requestQueue";
    }
}
