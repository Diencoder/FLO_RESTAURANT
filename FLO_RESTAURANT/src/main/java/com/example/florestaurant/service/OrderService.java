package com.example.florestaurant.service;

import com.example.florestaurant.model.Order;
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
}
