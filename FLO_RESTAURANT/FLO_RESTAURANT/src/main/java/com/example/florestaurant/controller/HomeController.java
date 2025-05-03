package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String home() {
        return "layout/index";  // Trả về layout/index.html
    }
}
