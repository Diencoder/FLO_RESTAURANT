package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Reservation;
import com.example.florestaurant.model.RestaurantTable;
import com.example.florestaurant.repository.ReservationRepository;
import com.example.florestaurant.repository.RestaurantTableRepository;
import com.example.florestaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<RestaurantTable> getAllTables() {
        return restaurantTableRepository.findAll();
    }

    @Override
    public RestaurantTable getTableByNumber(int tableNumber) {
        Optional<RestaurantTable> table = restaurantTableRepository.findByTableNumber(tableNumber);
        return table.orElse(null);
    }

    @Override
    public RestaurantTable saveTable(RestaurantTable table) {
        return restaurantTableRepository.save(table);
    }

    @Override
    public void deleteTable(int tableNumber) {
        RestaurantTable table = restaurantTableRepository.findByTableNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Bàn không tồn tại: " + tableNumber));

        if (table.getStatus() != RestaurantTable.TableStatus.Available) {
            throw new IllegalStateException("Chỉ có thể xóa bàn khi trạng thái là Available");
        }

        List<Reservation> reservations = reservationRepository.findByTableNumber(tableNumber);
        if (!reservations.isEmpty()) {
            reservationRepository.deleteAll(reservations);
        }

        restaurantTableRepository.delete(table);
    }

    @Override
    public void cancelReservation(int tableNumber) {
        RestaurantTable table = restaurantTableRepository.findByTableNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Bàn không tồn tại"));

        // Kiểm tra nếu bàn đang ở trạng thái Reserved
        if (table.getStatus() != RestaurantTable.TableStatus.Reserved) {
            throw new IllegalStateException("Chỉ có thể hủy đặt bàn khi trạng thái là Reserved");
        }

        // Cập nhật trạng thái bàn thành Available
        table.setStatus(RestaurantTable.TableStatus.Available);
        restaurantTableRepository.save(table);

        // Tìm và xóa các bản ghi Reservation liên quan (chỉ xóa nếu Pending hoặc Confirmed)
        List<Reservation> reservations = reservationRepository.findByTableNumber(tableNumber);
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == Reservation.ReservationStatus.Pending ||
                    reservation.getStatus() == Reservation.ReservationStatus.Confirmed) {
                reservationRepository.delete(reservation);
            }
        }
    }
}