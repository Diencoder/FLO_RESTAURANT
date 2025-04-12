package com.example.florestaurant.controller.admin;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.model.Category;
import com.example.florestaurant.service.FoodService;
import com.example.florestaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;


    // Hiển thị form thêm món ăn
    @GetMapping("/addfood")
    public String showAddFoodForm(Model model) {
        model.addAttribute("food", new Food());  // Tạo đối tượng Food rỗng để binding dữ liệu
        model.addAttribute("categories", categoryService.getAllCategories());  // Lấy tất cả danh mục
        model.addAttribute("breadcrumb", "Thêm Món Ăn");
        return "admin/addfood";  // Trả về trang addfood.html
    }

    // Xử lý thêm món ăn vào cơ sở dữ liệu
    @PostMapping("/addfood")
    public String addFood(@RequestParam("title") String title,
                          @RequestParam("price") double price,
                          @RequestParam("stock") int stock,
                          @RequestParam("description") String description,
                          @RequestParam("categoryId") Long categoryId,
                          @RequestParam("active") String active,
                          @RequestParam("featured") String featured,
                          @RequestParam("image") MultipartFile image) {

        // Tạo đối tượng món ăn và gán thông tin
        Food food = new Food();
        food.setTitle(title);
        food.setPrice(price);
        food.setStock(stock);
        food.setDescription(description);
        food.setCategoryId(categoryId);
        food.setActive(active);
        food.setFeatured(featured);

        // Gọi dịch vụ để thêm món ăn và xử lý ảnh
        foodService.addFood(food, image);

        return "redirect:/admin/inventory"; // Quay lại trang quản lý kho
    }
    // Hiển thị form chỉnh sửa món ăn
    @GetMapping("/editfood/{id}")
    public String showEditFoodForm(@PathVariable Long id, Model model) {
        // Lấy thông tin món ăn cần chỉnh sửa
        Food food = foodService.getFoodById(id);

        // Lấy tất cả các danh mục
        List<Category> categories = categoryService.getAllCategories();

        // Truyền dữ liệu vào model
        model.addAttribute("food", food);
        model.addAttribute("categories", categories);
        model.addAttribute("breadcrumb", "Chỉnh Sửa Món Ăn");

        return "admin/editfood";  // Trả về trang chỉnh sửa món ăn
    }

    // Xử lý việc cập nhật món ăn
    @PostMapping("/editfood/{id}")
    public String updateFood(@PathVariable Long id,
                             @ModelAttribute Food food,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("category_id") Long categoryId) {

        // Call the service to update the food with the provided data
        foodService.updateFood(id, food, image, categoryId);

        return "redirect:/admin/inventory"; // Redirect to inventory management page
    }

    // Xóa món ăn
    @GetMapping("/deletefood/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return "redirect:/admin/inventory";  // Quay lại trang kho hàng
    }
}
