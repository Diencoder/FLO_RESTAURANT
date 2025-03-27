package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.CartService;
import com.example.florestaurant.service.OrderService;
import com.example.florestaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class PurchaseController {

    @Autowired private OrderService orderService;
    @Autowired private CartService cartService;
    @Autowired private UserService userService;

    @PostMapping("/purchase")
    public String processPurchase(@RequestParam String tran_id,
                                  @RequestParam String cus_name,
                                  @RequestParam String cus_email,
                                  @RequestParam String cus_add1,
                                  @RequestParam String cus_city,
                                  @RequestParam String cus_phone,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        // Kiểm tra người dùng đã đăng nhập chưa
        if (session.getAttribute("user") == null) {  // Kiểm tra xem "user" có trong session không
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập để thanh toán!");
            return "redirect:/login";  // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        // Lấy thông tin người dùng từ session
        String username = ((User) session.getAttribute("user")).getUsername();  // Lấy tên đăng nhập từ session

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = cartService.getCart(session);
        if (cart == null || cart.isEmpty()) {
            // Giỏ hàng trống, chuyển về trang menu và thông báo
            redirectAttributes.addFlashAttribute("message", "Giỏ hàng trống!");
            return "redirect:/menu";
        }

        // Tính tổng tiền và áp dụng giảm giá (nếu có)
        double totalAmount = cartService.calculateTotalAmount(cart);
        double discountAmount = session.getAttribute("discount_amount") != null ? (double) session.getAttribute("discount_amount") : 0.0;
        double totalAfterDiscount = session.getAttribute("total_after_discount") != null ? (double) session.getAttribute("total_after_discount") : totalAmount;

        // Xử lý thanh toán và lưu đơn hàng vào cơ sở dữ liệu
        orderService.processOrder(tran_id, username, cus_name, cus_email, cus_add1, cus_city, cus_phone, totalAfterDiscount, discountAmount, cart);

        // Xóa giỏ hàng sau khi thanh toán thành công
        session.removeAttribute("cart");
        session.removeAttribute("discount_amount");
        session.removeAttribute("total_after_discount");

        // Thêm thông báo thanh toán thành công
        redirectAttributes.addFlashAttribute("message", "✅ Thanh toán thành công!");

        // Chuyển hướng đến trang xác nhận hoặc thông báo thành công
        return "redirect:/order/confirmation";  // Thay đổi thành trang thông báo đơn hàng nếu muốn
    }
}
