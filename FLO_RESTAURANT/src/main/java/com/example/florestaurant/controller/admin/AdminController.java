package com.example.florestaurant.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Trang Dashboard
    @GetMapping("/admin")
    public String showDashboard() {
        return "/admin/admin"; // Trả về trang admin.html
    }

    // Trang Quản lý Admin
    @GetMapping("/manageadmin")
    public String showManageAdmin() {
        return "/admin/manageadmin"; // Trả về trang manageadmin.html
    }

    // Trang Quản lý Đơn hàng Online
    @GetMapping("/manageonlineorder")
    public String showManageOnlineOrder() {
        return "/admin/manageonlineorder"; // Trả về trang manageonlineorder.html
    }

    // Trang Quản lý Đơn hàng Tại Quán
    @GetMapping("/manageeiorder")
    public String showManageEiOrder() {
        return "/admin/manageeiorder"; // Trả về trang manageeiorder.html
    }

    // Trang Quản lý Danh mục
    @GetMapping("/admin/managecategory")
    public String showManageCategory() {
        return "/admin/managecategory"; // Trả về trang managecategory.html
    }

    // Trang Quản lý Thực đơn
    @GetMapping("/managefood")
    public String showManageFood() {
        return "/admin/managefood"; // Trả về trang managefood.html
    }

    // Trang Kho Hàng
    @GetMapping("/inventory")
    public String showInventory() {
        return "/admin/inventory"; // Trả về trang inventory.html
    }

    // Trang Đăng xuất
    @GetMapping("/logout")
    public String logout() {
        return "/layout/login"; // Trả về trang login.html (hoặc trang khác nếu cần)
    }
}
