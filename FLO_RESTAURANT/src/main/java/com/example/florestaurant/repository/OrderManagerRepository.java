package com.example.florestaurant.repository;

import com.example.florestaurant.model.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {
}
