package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String about(Model model) {
        model.addAttribute("pageTitle", "Liên hệ"); // Truyền pageTitle vào model
        return "layout/contact"; // Trả về view "about.html" từ thư mục templates
    }
}
