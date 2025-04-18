package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "Giới thiệu"); // Truyền pageTitle vào model
        return "layout/about"; // Trả về view "about.html" từ thư mục templates
    }
}
