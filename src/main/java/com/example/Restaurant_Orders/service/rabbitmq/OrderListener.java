package com.example.Restaurant_Orders.service.rabbitmq;

import com.example.Restaurant_Orders.config.rabbitmq.RabbitMQConfig;
import com.example.Restaurant_Orders.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.example.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class OrderListener {

    @Autowired
    private ObjectMapper objectMapper;

    public void startListening(Channel channel, String requestQueueName, String responseRoutingKey) throws Exception {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            // Simulate order processing
            OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);


            System.out.println("Received order: " + orderDTO.getStatus());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            // Update the order status
            orderDTO.setStatus(OrderStatus.PREPARING);
            String updatedOrderJson = objectMapper.writeValueAsString(orderDTO);
            System.out.println("Updated order - : "+orderDTO.getOrderId()+" - " + orderDTO.getStatus());


            // Send the update to the response queue
            channel.basicPublish(RabbitMQConfig.ORDER_EXCHANGE, responseRoutingKey, null, updatedOrderJson.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent update - : "+orderDTO.getOrderId()+" - " + orderDTO.getStatus());
        };

        channel.basicConsume(requestQueueName, true, deliverCallback, consumerTag -> {
        });
    }
}
