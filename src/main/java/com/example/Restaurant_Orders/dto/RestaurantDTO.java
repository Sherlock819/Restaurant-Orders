package com.example.Restaurant_Orders.dto;

import com.example.Restaurant_Orders.constant.RestaurantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String mobile;
    private String email;
    private Double rating;
    private Double latitude;
    private Double longitude;
    private RestaurantStatus status;

    // Getters and Setters
}

