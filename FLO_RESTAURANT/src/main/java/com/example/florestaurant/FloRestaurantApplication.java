package com.example.florestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.florestaurant.repository")
@EntityScan("com.example.florestaurant.model") // Đảm bảo quét đúng package model
public class FloRestaurantApplication {
    public static void main(String[] args) {
        SpringApplication.run(FloRestaurantApplication.class, args);
    }
}
