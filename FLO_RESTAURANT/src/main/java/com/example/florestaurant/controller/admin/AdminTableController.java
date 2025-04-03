package com.example.florestaurant.controller.admin;

import com.example.florestaurant.model.RestaurantTable;
import com.example.florestaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    // Hiển thị danh sách bàn
    @GetMapping("/tables")
    public String viewTables(Model model) {
        List<RestaurantTable> tables = restaurantTableService.getAllTables();
        System.out.println("Tables: " + tables);
        for (RestaurantTable table : tables) {
            System.out.println("Table Number: " + table.getTableNumber() + ", Status: " + table.getStatus());
        }
        model.addAttribute("tables", tables);
        return "admin/manage-tables";
    }

    // Thêm bàn mới
    @GetMapping("/tables/add_table")
    public String addTableForm(Model model) {
        model.addAttribute("restaurantTable", new RestaurantTable());
        return "admin/add_table";
    }

    @PostMapping("/tables/add")
    public String addTable(@ModelAttribute RestaurantTable table, Model model) {
        try {
            System.out.println("Saving table: " + table);
            restaurantTableService.saveTable(table);
            return "redirect:/admin/tables";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm bàn: " + e.getMessage());
            model.addAttribute("restaurantTable", table);
            return "admin/add_table";
        }
    }

    // Hủy đặt bàn
    @GetMapping("/tables/cancel/{tableNumber}")
    public String cancelReservation(@PathVariable int tableNumber, Model model) {
        try {
            restaurantTableService.cancelReservation(tableNumber);
            return "redirect:/admin/tables";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi hủy đặt bàn: " + e.getMessage());
            model.addAttribute("tables", restaurantTableService.getAllTables());
            return "admin/manage-tables";
        }
    }

    // Xóa bàn
    @GetMapping("/tables/delete/{tableNumber}")
    public String deleteTable(@PathVariable int tableNumber, Model model) {
        try {
            restaurantTableService.deleteTable(tableNumber);
            return "redirect:/admin/tables";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi xóa bàn: " + e.getMessage());
            model.addAttribute("tables", restaurantTableService.getAllTables());
            return "admin/manage-tables";
        }
    }
}