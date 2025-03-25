package com.example.florestaurant.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Trang Dashboard
    @GetMapping("/admin")
    public String showDashboard(Model model) {
        model.addAttribute("breadcrumb", "Dashboard"); // Thêm breadcrumb cho trang Dashboard
        return "admin/admin"; // Trả về trang admin.html
    }

    // Trang Quản lý Admin
    @GetMapping("/manageadmin")
    public String showManageAdmin(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Admin");
        return "admin/manageadmin"; // Trả về trang manageadmin.html
    }

    // Trang Quản lý Đơn hàng Online
    @GetMapping("/manageonlineorder")
    public String showManageOnlineOrder(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Đơn Hàng Online");
        return "admin/manageonlineorder"; // Trả về trang manageonlineorder.html
    }

    // Trang Quản lý Đơn hàng Tại Quán
    @GetMapping("/manageeiorder")
    public String showManageEiOrder(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Đơn Hàng Tại Quán");
        return "admin/manageeiorder"; // Trả về trang manageeiorder.html
    }

    // Trang Quản lý Danh mục
    @GetMapping("/admin/managecategory")
    public String showManageCategory(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Danh Mục");
        return "admin/managecategory"; // Trả về trang managecategory.html
    }

    // Trang Quản lý Thực đơn
    @GetMapping("/managefood")
    public String showManageFood(Model model) {
        model.addAttribute("breadcrumb", "Quản Lý Thực Đơn");
        return "admin/managefood"; // Trả về trang managefood.html
    }

    // Trang Kho Hàng
    @GetMapping("/inventory")
    public String showInventory(Model model) {
        model.addAttribute("breadcrumb", "Kho Hàng");
        return "admin/inventory"; // Trả về trang inventory.html
    }

    // Trang Đăng xuất
    @GetMapping("/admin/logout")
    public String logout() {
        return "layout/login"; // Trả về trang login.html (hoặc trang khác nếu cần)
    }
}
