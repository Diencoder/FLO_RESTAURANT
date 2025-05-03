package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_tables")
@Getter
@Setter
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private int tableNumber;  // Số bàn

    @Column(name = "area", nullable = false)
    private String area;  // Khu vực (Tầng 1, Tầng 2,...)

    @Column(name = "capacity", nullable = false)
    private int capacity;  // Số người tối đa

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('Available', 'Reserved', 'Occupied', 'OutOfService') DEFAULT 'Available'")
    private TableStatus status = TableStatus.Available;  // Trạng thái bàn

    public enum TableStatus {
        Available, Reserved, Occupied, OutOfService
    }
}
