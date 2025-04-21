package com.example.florestaurant.controller.admin;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import com.example.florestaurant.model.Category;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CategoryService;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping({"/admin/admin","/admin"})
    public String showDashboard(Model model) {
        model.addAttribute("breadcrumb", "Dashboard");
        return "admin/admin";
    }

    @GetMapping("/admin/inventory")
    public String showInventory(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Page<Food> foodPage = foodService.getActiveFoods(PageRequest.of(page, size));

        model.addAttribute("breadcrumb", "Kho Hàng");
        model.addAttribute("foods", foodPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());

        return "admin/inventory";
    }


    @GetMapping("/admin/logout")
    public String logout() {
        return "layout/login";
    }



    @GetMapping("/layout/403")
    public String error403() {
        return "layout/403"; // trả về file templates/error/403.html
    }

}