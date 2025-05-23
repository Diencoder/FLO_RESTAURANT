package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.*;
import com.example.florestaurant.repository.*;
import com.example.florestaurant.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AamarpayRepository aamarpayRepository;
    @Autowired
    private OrderManagerRepository orderManagerRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void processOrder(String tranId, String username, String cusName, String cusEmail,
                             String cusAdd1, String cusCity, String cusPhone,
                             double totalAmount, double discountAmount,
                             List<Map<String, Object>> cartItems) {

        // Lưu thanh toán
        Aamarpay payment = savePayment(tranId, cusName, totalAmount);

        // Lưu thông tin đơn hàng
        OrderManager order = saveOrderDetails(username, cusName, cusEmail, cusAdd1, cusCity, cusPhone, totalAmount, payment);

        // Lưu các mục trong giỏ hàng và cập nhật tồn kho
        saveOrderItems(cartItems, order, discountAmount);

        // Lưu lịch sử đơn hàng
        saveOrderHistory(username, cartItems);
    }

    @Override
    public List<OrderManager> getOrdersByUsername(String username) {
        return orderManagerRepository.findByUsername(username);  // Truy vấn đơn hàng theo username
    }

    @Override
    public Aamarpay savePayment(String tranId, String cusName, double totalAmount) {
        Aamarpay payment = new Aamarpay();
        payment.setCusName(cusName);
        payment.setAmount(totalAmount);
        payment.setStatus("Pending");
        payment.setTransactionId(tranId);
        payment.setCardType("Card");
        aamarpayRepository.save(payment);  // Lưu vào bảng aamarpay
        return payment;
    }

    @Override
    public OrderManager saveOrderDetails(String username, String cusName, String cusEmail,
                                         String cusAdd1, String cusCity, String cusPhone,
                                         double totalAmount, Aamarpay payment) {
        OrderManager order = new OrderManager();
        order.setUsername(username);  // Đảm bảo gán username vào đối tượng order
        order.setCusName(cusName);
        order.setCusEmail(cusEmail);
        order.setCusAdd1(cusAdd1);
        order.setCusCity(cusCity);
        order.setCusPhone(Long.valueOf(cusPhone));  // Chuyển đổi số điện thoại sang Long
        order.setPaymentStatus("Pending");
        order.setOrderStatus("Pending");
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setAamarpay(payment);  // Liên kết với transaction_id trong bảng aamarpay
        return orderManagerRepository.save(order);  // Lưu vào bảng order_manager
    }


    @Override
    public void saveOrderItems(List<Map<String, Object>> cartItems, OrderManager order, double discountAmount) {
        for (Map<String, Object> item : cartItems) {
            String itemName = (String) item.get("Item_Name");
            double price = (double) item.get("Price");
            int quantity = (int) item.get("Quantity");

            if (discountAmount > 0) {
                price -= (discountAmount / cartItems.size());
            }

            OrderItem orderItem = new OrderItem(order, itemName, price, quantity);
            orderItemRepository.save(orderItem);

            // Cập nhật số lượng tồn kho của món ăn
            foodRepository.reduceStockByTitle(itemName, quantity);
        }
    }

    @Override
    public void saveOrderHistory(String username, List<Map<String, Object>> cartItems) {
        Long userId = userRepository.findIdByUsername(username);
        for (Map<String, Object> item : cartItems) {
            String itemName = (String) item.get("Item_Name");
            Long foodId = foodRepository.findIdByTitle(itemName);
            int quantity = (int) item.get("Quantity");
            OrderHistory history = new OrderHistory(userId, foodId, quantity, LocalDateTime.now());
            orderHistoryRepository.save(history);
        }
    }

    @Override
    public List<OrderManager> getAllOrders() {
        return orderManagerRepository.findAll();  // Lấy tất cả đơn hàng từ database
    }

    @Override
    public OrderManager getOrderById(Long id) {
        return orderManagerRepository.findById(id).orElse(null);  // Nếu không tìm thấy đơn hàng trả về null
    }

    @Override
    public OrderManager saveOrder(OrderManager orderManager) {
        // Kiểm tra nếu đơn hàng đã tồn tại
        if (orderManager.getOrderId() != null) {
            // Lấy thông tin đơn hàng từ CSDL
            OrderManager existingOrder = orderManagerRepository.findById(orderManager.getOrderId()).orElse(null);

            if (existingOrder != null) {
                // Cập nhật thông tin đơn hàng
                existingOrder.setUsername(orderManager.getUsername());  // Đảm bảo username được gán
                existingOrder.setCusName(orderManager.getCusName());
                existingOrder.setCusEmail(orderManager.getCusEmail());
                existingOrder.setCusAdd1(orderManager.getCusAdd1());
                existingOrder.setCusCity(orderManager.getCusCity());
                existingOrder.setCusPhone(orderManager.getCusPhone());
                existingOrder.setPaymentStatus(orderManager.getPaymentStatus());
                existingOrder.setOrderStatus(orderManager.getOrderStatus());
                existingOrder.setTotalAmount(orderManager.getTotalAmount());

                // Kiểm tra và giữ nguyên order_date nếu không thay đổi
                if (orderManager.getOrderDate() != null) {
                    existingOrder.setOrderDate(orderManager.getOrderDate());
                } else {
                    existingOrder.setOrderDate(existingOrder.getOrderDate());  // Giữ nguyên nếu order_date không thay đổi
                }



                // Lưu thông tin cập nhật vào cơ sở dữ liệu
                return orderManagerRepository.save(existingOrder);
            } else {
                throw new IllegalArgumentException("Đơn hàng không tồn tại.");
            }
        } else {
            // Nếu đơn hàng mới, gán order_date và username, rồi lưu vào cơ sở dữ liệu
            orderManager.setOrderDate(new Date());  // Gán thời gian hiện tại nếu đơn hàng mới
            if (orderManager.getUsername() == null || orderManager.getUsername().isEmpty()) {
                throw new IllegalArgumentException("Username không thể trống.");
            }
            return orderManagerRepository.save(orderManager);  // Lưu đơn hàng mới
        }
    }



    @Override
    public void deleteOrder(Long id) {
        orderManagerRepository.deleteById(id);  // Xóa đơn hàng theo ID từ database
    }
}
