package com.example.florestaurant.service;

import com.example.florestaurant.model.RestaurantTable;

import java.util.List;

public interface RestaurantTableService {

    List<RestaurantTable> getAllTables();

    RestaurantTable getTableByNumber(int tableNumber);

    RestaurantTable saveTable(RestaurantTable table);

    void deleteTable(int tableNumber);

    void cancelReservation(int tableNumber);
}