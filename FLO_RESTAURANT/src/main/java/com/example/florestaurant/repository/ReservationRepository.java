package com.example.florestaurant.repository;

import com.example.florestaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTableNumber(int tableNumber);
    // Phương thức này đã có sẵn trong JpaRepository
    // Bạn có thể thêm các phương thức truy vấn khác nếu cần

}
