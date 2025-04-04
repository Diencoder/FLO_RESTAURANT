package com.example.florestaurant.controller.admin;

import com.example.florestaurant.model.OrderManager;
import com.example.florestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping("/orders")
    public String listOrders(Model model) {
        // Truyền danh sách đơn hàng từ OrderManager
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/manageorders";  // Chuyển đến trang manageorders.html
    }

    // Hiển thị đơn hàng theo id để chỉnh sửa
    @GetMapping("/orders/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        OrderManager order = orderService.getOrderById(id);  // Lấy OrderManager để chỉnh sửa thông tin đơn hàng
        model.addAttribute("order", order);  // Truyền đối tượng 'order' vào model
        return "admin/editorder";  // Chuyển đến trang editorder.html
    }

    // Cập nhật hoặc tạo mới đơn hàng
    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute OrderManager orderManager) {


        // Lưu OrderManager
        orderService.saveOrder(orderManager);
        return "redirect:/admin/orders";  // Quay lại trang danh sách đơn hàng
    }

    // Xóa đơn hàng
    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        // Xóa OrderManager theo id
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";  // Quay lại trang danh sách đơn hàng
    }
}
