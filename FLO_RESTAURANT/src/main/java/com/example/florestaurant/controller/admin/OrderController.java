package com.example.florestaurant.controller.admin;

import com.example.florestaurant.model.Order;
import com.example.florestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/manageorders";  // Chuyển đến trang manageorders.html
    }

    // Hiển thị đơn hàng theo id để chỉnh sửa
    @GetMapping("/orders/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);  // Truyền đối tượng 'order' vào model
        return "admin/editorder";  // Chuyển đến trang editorder.html
    }

    // Cập nhật hoặc tạo mới đơn hàng
    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute Order order) {
        // Nếu order_date chưa được thiết lập, đặt giá trị mặc định là thời gian hiện tại
        if (order.getOrderDate() == null) {
            order.setOrderDate(new Date());  // Đặt giá trị cho order_date bằng ngày hiện tại
        }

        orderService.saveOrder(order);
        return "redirect:/admin/orders";  // Quay lại trang danh sách đơn hàng
    }

    // Xóa đơn hàng
    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";  // Quay lại trang danh sách đơn hàng
    }
}
