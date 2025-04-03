package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
        return "error";
    }
}