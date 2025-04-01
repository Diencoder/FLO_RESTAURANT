package com.example.florestaurant.service;

import com.example.florestaurant.model.Aamarpay;
import com.example.florestaurant.model.OrderManager;

import java.util.List;
import java.util.Map;

public interface OrderService {
    // Phương thức để xử lý đơn hàng
    void processOrder(String tranId, String username, String cusName, String cusEmail,
                      String cusAdd1, String cusCity, String cusPhone,
                      double totalAmount, double discountAmount,
                      List<Map<String, Object>> cartItems);

    // Lấy tất cả đơn hàng
    List<OrderManager> getAllOrders();

    // Lấy đơn hàng theo id
    OrderManager getOrderById(Long id);

    // Lưu đơn hàng
    OrderManager saveOrder(OrderManager order);

    // Xóa đơn hàng
    void deleteOrder(Long id);

    // Phương thức để lưu thanh toán
    Aamarpay savePayment(String tranId, String cusName, double totalAmount);

    // Phương thức để lưu đơn hàng
    OrderManager saveOrderDetails(String username, String cusName, String cusEmail,
                                  String cusAdd1, String cusCity, String cusPhone,
                                  double totalAmount, Aamarpay payment);

    // Phương thức để lưu các mục trong giỏ hàng
    void saveOrderItems(List<Map<String, Object>> cartItems, OrderManager order, double discountAmount);

    // Phương thức để lưu lịch sử đơn hàng
    void saveOrderHistory(String username, List<Map<String, Object>> cartItems);
}
