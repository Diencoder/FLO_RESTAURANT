package com.example.florestaurant.controller;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        // Truy vấn các món ăn có trạng thái 'Yes'
        List<Food> foods = foodRepository.findByActive("Yes");
        // Thêm dữ liệu món ăn vào model để truyền cho view
        model.addAttribute("foods", foods);
        model.addAttribute("pageTitle", "Thực đơn"); // Truyền dữ liệu vào model
        return "layout/menu";  // Trả về tên template "menu.html"
    }
}
