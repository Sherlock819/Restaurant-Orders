package com.example.Restaurant_Orders.config.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Value("${spring.rabbitmq.host}")
    private String host;

    public static final String ORDER_EXCHANGE = "orderExchange";

    @Bean
    public Connection rabbitMQConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        log.info("RabbitMQ host: {}", host);
        factory.setHost(host);
        return factory.newConnection();
    }
}

