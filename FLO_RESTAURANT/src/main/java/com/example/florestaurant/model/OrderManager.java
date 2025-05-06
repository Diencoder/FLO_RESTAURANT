package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_manager")
@Getter
@Setter
public class OrderManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "username")
    private String username;

    @Column(name = "cus_name")
    private String cusName;

    @Column(name = "cus_email")
    private String cusEmail;

    @Column(name = "cus_add1")
    private String cusAdd1;

    @Column(name = "cus_city")
    private String cusCity;

    @Column(name = "cus_phone")
    private Long cusPhone;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id", nullable = true)
    private Aamarpay aamarpay;  // Liên kết với bảng aamarpay qua transaction_id

    @Column(name = "order_status")
    private String orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;  // Thêm thuộc tính items liên kết với OrderItem

    public OrderManager() {
    }

    public OrderManager(String username, String cusName, String cusEmail, String cusAdd1, String cusCity,
                        Long cusPhone, String paymentStatus, Date orderDate, Double totalAmount,
                        Aamarpay aamarpay, String orderStatus) {
        this.username = username;
        this.cusName = cusName;
        this.cusEmail = cusEmail;
        this.cusAdd1 = cusAdd1;
        this.cusCity = cusCity;
        this.cusPhone = cusPhone;
        this.paymentStatus = paymentStatus;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.aamarpay = aamarpay;  // Liên kết đến đối tượng Aamarpay
        this.orderStatus = orderStatus;
    }
}
