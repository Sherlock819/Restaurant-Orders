package com.example.Restaurant_Orders.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public TokenRequestInterceptor tokenRequestInterceptor() {
        return new TokenRequestInterceptor();
    }
}
