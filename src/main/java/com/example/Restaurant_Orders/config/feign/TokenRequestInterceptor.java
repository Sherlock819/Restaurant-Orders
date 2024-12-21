package com.example.Restaurant_Orders.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TokenRequestInterceptor implements RequestInterceptor {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + token);
    }
}
