package com.example.Restaurant_Orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestaurantOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantOrdersApplication.class, args);
	}

}
