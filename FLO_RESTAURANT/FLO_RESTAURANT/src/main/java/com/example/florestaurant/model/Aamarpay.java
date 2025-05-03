package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "aamarpay")
@Getter
@Setter
public class Aamarpay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cus_name")
    private String cusName;  // Tên khách hàng

    @Column(name = "amount")
    private Double amount;   // Số tiền thanh toán

    @Column(name = "status")
    private String status;   // Trạng thái thanh toán

    @Column(name = "card_type")
    private String cardType; // Loại thẻ thanh toán

    @Column(name = "transaction_id")
    private String transactionId;  // Mã giao dịch

    @OneToMany(mappedBy = "aamarpay")
    private List<OrderManager> orderManagers;  // Liên kết với bảng order_manager

    public Aamarpay() {
    }

    public Aamarpay(String cusName, Double amount, String status, String cardType, String transactionId) {
        this.cusName = cusName;
        this.amount = amount;
        this.status = status;
        this.cardType = cardType;
        this.transactionId = transactionId;
    }
}
