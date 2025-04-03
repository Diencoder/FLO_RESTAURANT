package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_reservations")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;  // Liên kết với người dùng (null nếu khách vãng lai)

    @Column(name = "table_number", nullable = false)
    private int tableNumber;

    @Column(name = "area", nullable = false)
    private String area;  // Khu vực (Tầng 1, Tầng 2, VIP,...)

    @Column(name = "reservation_time", nullable = false)
    private Date reservationTime;  // Thời gian đặt bàn

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending'")
    private ReservationStatus status = ReservationStatus.Pending;  // Trạng thái đặt bàn

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    // Constructor và các phương thức cần thiết
    public enum ReservationStatus {
        Pending, Confirmed, Cancelled, Completed
    }
}
