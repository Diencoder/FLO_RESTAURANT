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

    @GetMapping("/admin/manageadmin")
    public String showManageAdmin(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Admin");
        return "admin/manageadmin";
    }

    @GetMapping("/admin/manageonlineorder")
    public String showManageOnlineOrder(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Đơn Hàng Online");
        return "admin/manageonlineorder";
    }

    @GetMapping("/admin/manageeiorder")
    public String showManageEiOrder(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Đơn Hàng Tại Quán");
        return "admin/manageeiorder";
    }

    @GetMapping("/admin/managecategory")
    public String showManageCategory(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Danh Mục");
        return "admin/managecategory";
    }

    @GetMapping("/admin/managefood")
    public String showManageFood(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Thực Đơn");
        model.addAttribute("foods", foodService.getActiveFoods());
        return "admin/managefood";
    }

    @GetMapping("/admin/inventory")
    public String showInventory(Model model) {
        model.addAttribute("breadcrumb", "Kho Hàng");
        model.addAttribute("foods", foodService.getActiveFoods());
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
