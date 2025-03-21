package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;  // ID giao dịch
    private Double total;  // Tổng tiền đơn hàng
    private Date orderDate;  // Ngày đặt hàng
    private String status;  // Trạng thái đơn hàng
    private String customerName;  // Tên khách hàng
    private String customerContact;  // Số điện thoại khách hàng
    private String customerEmail;  // Email khách hàng
    private String customerAddress;  // Địa chỉ khách hàng

    // Constructor mặc định
    public Order() {}

    // Constructor với tham số
    public Order(String transactionId, Double total, Date orderDate, String status, String customerName, String customerContact, String customerEmail, String customerAddress) {
        this.transactionId = transactionId;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
    }
}
