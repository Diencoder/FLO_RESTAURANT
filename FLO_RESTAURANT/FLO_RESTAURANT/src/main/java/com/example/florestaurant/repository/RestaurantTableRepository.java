package com.example.florestaurant.repository;

import com.example.florestaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findByStatus(RestaurantTable.TableStatus status);

    Optional<RestaurantTable> findByTableNumber(int tableNumber);
}