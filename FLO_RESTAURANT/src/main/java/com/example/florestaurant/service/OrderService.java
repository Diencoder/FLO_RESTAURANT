package com.example.florestaurant.service;

import com.example.florestaurant.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();  // Lấy tất cả đơn hàng
    Order getOrderById(Long id);  // Lấy đơn hàng theo id
    Order saveOrder(Order order);  // Lưu đơn hàng (cập nhật hoặc tạo mới)
    void deleteOrder(Long id);  // Xóa đơn hàng
}
