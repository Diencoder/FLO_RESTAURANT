package com.example.florestaurant.service;

import com.example.florestaurant.model.Reservation;
import com.example.florestaurant.model.RestaurantTable;

import java.util.List;

public interface ReservationService {

    List<RestaurantTable> getAvailableTables();

    Reservation createReservation(Reservation reservation);

    RestaurantTable getTableByNumber(int tableNumber);

    // Thêm phương thức lấy tất cả các đặt bàn
    List<Reservation> getAllReservations();

    // Thêm phương thức hủy lịch đặt bàn
    void cancelReservation(Long reservationId);
}