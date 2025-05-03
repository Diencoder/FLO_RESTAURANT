package com.example.florestaurant.controller;

import com.example.florestaurant.model.Reservation;
import com.example.florestaurant.model.RestaurantTable;
import com.example.florestaurant.model.User;
import com.example.florestaurant.service.ReservationService;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    // Hiển thị danh sách các bàn có sẵn
    @GetMapping("/reserve")
    public String showAvailableTables(Model model) {
        List<RestaurantTable> tables = reservationService.getAvailableTables();
        System.out.println("Available tables: " + tables.size());
        model.addAttribute("tables", tables);
        return "reservation/list";
    }

    // Hiển thị form đặt bàn
    @GetMapping("/reserve/{tableNumber}")
    public String showReservationForm(@PathVariable("tableNumber") int tableNumber, Model model) {
        Reservation reservation = new Reservation();
        reservation.setTableNumber(tableNumber);
        model.addAttribute("reservation", reservation);
        return "reservation/form";
    }

    // Xử lý yêu cầu đặt bàn từ form
    @PostMapping("/reserve")
    public String makeReservation(@ModelAttribute("reservation") Reservation reservation, Model model) {
        // Kiểm tra thông tin người dùng trong tbl_users
        User user = userService.findUserByDetails(
                reservation.getCustomerName(),
                reservation.getCustomerEmail(),
                reservation.getCustomerPhone()
        );

        // Gán user_id nếu tìm thấy người dùng
        if (user != null) {
            reservation.setUser(user);
        }

        // Thiết lập thời gian đặt bàn
        reservation.setReservationTime(new Date());

        // Lấy thông tin bàn để gán area
        RestaurantTable table = reservationService.getTableByNumber(reservation.getTableNumber());
        if (table != null) {
            reservation.setArea(table.getArea());
        }

        // Lưu đặt bàn
        Reservation createdReservation = reservationService.createReservation(reservation);

        if (createdReservation != null) {
            model.addAttribute("message", "Đặt bàn thành công!");
            return "reservation/confirmation";
        } else {
            model.addAttribute("message", "Bàn đã được đặt hoặc không còn sẵn!");
            return "reservation/list";
        }
    }
}