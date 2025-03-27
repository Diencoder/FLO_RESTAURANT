package com.example.florestaurant.controller;

import com.example.florestaurant.model.CartItem;
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
import com.example.florestaurant.model.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PurchaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

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
        if (session.getAttribute("user") == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập để thanh toán!");
            return "redirect:/login";  // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        // Lấy thông tin người dùng từ session
        String username = ((User) session.getAttribute("user")).getUsername();

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("mycart");
        if (cart == null || cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Giỏ hàng trống!");
            return "redirect:/menu";  // Nếu giỏ hàng trống, chuyển hướng về menu
        }

        // Chuyển đổi giỏ hàng thành List<Map<String, Object>> để gửi vào processOrder
        List<Map<String, Object>> cartItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            Map<String, Object> item = new HashMap<>();
            item.put("Item_Name", cartItem.getFood().getTitle());
            item.put("Price", cartItem.getFood().getPrice());
            item.put("Quantity", cartItem.getQuantity());
            cartItems.add(item);
        }

        // Tính tổng tiền và áp dụng giảm giá (nếu có)
        double totalAmount = cart.getTotalPrice();
        double discountAmount = session.getAttribute("discount_amount") != null ? (double) session.getAttribute("discount_amount") : 0.0;
        double totalAfterDiscount = session.getAttribute("total_after_discount") != null ? (double) session.getAttribute("total_after_discount") : totalAmount;

        // Xử lý thanh toán và lưu đơn hàng vào cơ sở dữ liệu
        orderService.processOrder(tran_id, username, cus_name, cus_email, cus_add1, cus_city, cus_phone, totalAfterDiscount, discountAmount, cartItems);

        // Xóa giỏ hàng sau khi thanh toán thành công
        session.removeAttribute("mycart");
        session.removeAttribute("discount_amount");
        session.removeAttribute("total_after_discount");

        // Thêm thông báo thanh toán thành công
        redirectAttributes.addFlashAttribute("message", "✅ Thanh toán thành công!");

        // Chuyển hướng đến trang xác nhận hoặc thông báo thành công
        return "layout/confirmation";  // Chuyển đến trang xác nhận
    }
}
