package com.example.florestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

    // Xử lý lỗi 403 (Access Denied)
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError() {
        // Trả về trang lỗi 403
        return "layout/error";  // Trang lỗi 403
    }

    // Xử lý lỗi 404 (Page Not Found)
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String handleNotFoundError() {
        return "layout/404";  // Trang lỗi 404
    }
}
