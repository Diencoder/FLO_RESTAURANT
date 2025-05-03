package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Reservation;
import com.example.florestaurant.model.RestaurantTable;
import com.example.florestaurant.repository.ReservationRepository;
import com.example.florestaurant.repository.RestaurantTableRepository;
import com.example.florestaurant.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Override
    public List<RestaurantTable> getAvailableTables() {
        return restaurantTableRepository.findByStatus(RestaurantTable.TableStatus.Available);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        RestaurantTable table = restaurantTableRepository.findById((long) reservation.getTableNumber()).orElse(null);

        if (table != null && table.getStatus() == RestaurantTable.TableStatus.Available) {
            table.setStatus(RestaurantTable.TableStatus.Reserved);
            restaurantTableRepository.save(table);

            reservation.setStatus(Reservation.ReservationStatus.Pending);
            return reservationRepository.save(reservation);
        }
        return null;
    }

    @Override
    public RestaurantTable getTableByNumber(int tableNumber) {
        return restaurantTableRepository.findByTableNumber(tableNumber).orElse(null);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return List.of();
    }

    @Override
    public void cancelReservation(Long reservationId) {

    }
}