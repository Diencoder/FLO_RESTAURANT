package com.example.florestaurant.repository;

import com.example.florestaurant.model.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {
    List<OrderManager> findByUsername(String username);
}
