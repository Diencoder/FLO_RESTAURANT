package com.example.florestaurant.config;

import com.example.florestaurant.model.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @ModelAttribute("cartSize")
    public int cartSize(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("mycart");
        return (cart != null) ? cart.getItems().size() : 0;
    }
}
