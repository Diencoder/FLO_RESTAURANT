package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return "layout/about"; // Trả về view "about.html" từ thư mục templates
    }
}
