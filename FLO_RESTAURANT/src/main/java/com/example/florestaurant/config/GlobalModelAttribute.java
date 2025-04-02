//package com.example.florestaurant.config;
//
//import com.example.florestaurant.model.Cart;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalModelAttribute {
//
//    // Thêm thuộc tính cartSize vào tất cả các model được trả về từ các controller
//    @ModelAttribute("cartSize")
//    public int cartSize(HttpSession session) {
//        // Lấy giỏ hàng từ session
//        Cart cart = (Cart) session.getAttribute("mycart");
//
//        // Kiểm tra giỏ hàng có tồn tại không và trả về số lượng các món trong giỏ
//        return (cart != null && cart.getItems() != null) ? cart.getItems().size() : 0;
//    }
//}
