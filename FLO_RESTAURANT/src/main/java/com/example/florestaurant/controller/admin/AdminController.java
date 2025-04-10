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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping("/admin/editfood/{id}")
    public String showEditFood(@PathVariable Long id, Model model) {
        // Lấy thông tin món ăn cần chỉnh sửa
        Food food = foodService.getFoodById(id);

        // Lấy tất cả các danh mục
        List<Category> categories = categoryService.getAllCategories();

        // Truyền dữ liệu vào model
        model.addAttribute("food", food);
        model.addAttribute("categories", categories);

        return "admin/editfood";  // Trả về trang chỉnh sửa món ăn
    }

    @PostMapping("/admin/editfood/{id}")
    public String updateFood(@PathVariable Long id,
                             @ModelAttribute Food food,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("category_id") Long categoryId) {
        Food existingFood = foodService.getFoodById(id);

        if (existingFood != null) {
            // Cập nhật các trường của món ăn
            existingFood.setTitle(food.getTitle());
            existingFood.setPrice(food.getPrice());
            existingFood.setStock(food.getStock());
            existingFood.setDescription(food.getDescription());
            existingFood.setCategoryId(categoryId);  // Cập nhật danh mục

            // Nếu người dùng thay đổi ảnh, lưu ảnh mới
            if (!image.isEmpty()) {
                String imageName = image.getOriginalFilename();
                existingFood.setImageName(imageName);
                // Lưu ảnh vào thư mục uploads
                try {
                    Path path = Paths.get("uploads/" + imageName);
                    Files.write(path, image.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            existingFood.setActive(food.getActive()); // Cập nhật trạng thái (có sẵn hay không)
            foodService.updateFood(existingFood); // Cập nhật món ăn trong cơ sở dữ liệu
        }

        return "redirect:/admin/inventory"; // Chuyển hướng về trang quản lý món ăn
    }

    @GetMapping("/admin/deletefood/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return "redirect:/admin/inventory";
    }
    @GetMapping("/admin/addfood")
    public String showAddFoodForm(Model model) {
        model.addAttribute("food", new Food());  // Tạo đối tượng Food rỗng để binding dữ liệu
        model.addAttribute("categories", categoryService.getAllCategories());  // Lấy tất cả danh mục
        return "admin/addfood";  // Trả về trang addfood.html
    }

    // Xử lý thêm món ăn vào cơ sở dữ liệu
    @PostMapping("/admin/addfood")
    public String addFood(@RequestParam("title") String title,
                          @RequestParam("price") double price,
                          @RequestParam("stock") int stock,
                          @RequestParam("description") String description,
                          @RequestParam("categoryId") Long categoryId,
                          @RequestParam("active") String active,
                          @RequestParam("featured") String featured,
                          @RequestParam("image") MultipartFile image) {
        // Kiểm tra nếu hình ảnh không được tải lên, gán hình ảnh mặc định
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

        try {
            // Nếu có hình ảnh được tải lên, lưu vào thư mục static/images
            if (!image.isEmpty()) {
                Path path = Paths.get("src/main/resources/static/images/" + imageName);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            // Tạo đối tượng Food và lưu vào database
            Food food = new Food();
            food.setTitle(title);
            food.setPrice(price);
            food.setStock(stock);
            food.setDescription(description);
            food.setCategoryId(categoryId);
            food.setActive(active);
            food.setFeatured(featured);
            food.setImageName(imageName); // Gán tên hình ảnh vào Food
            foodService.addFood(food);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/inventory";
    }


}
