package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("myaccount")
public class MyAccountController {

    @Autowired
    private UserService userService;

    // Hiển thị thông tin người dùng
    @GetMapping("/myaccount")
    public String myAccount(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ session
        if (user == null) {
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không có thông tin người dùng
        }
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model để hiển thị trên trang
        return "layout/myaccount"; // Trả về view myaccount.html
    }

    // Hiển thị form chỉnh sửa thông tin người dùng
    @GetMapping("/editaccount")
    public String editUserProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ session
        if (user == null) {
            return "redirect:/login"; // Nếu không có người dùng trong session, chuyển hướng về trang đăng nhập
        }
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model
        return "layout/userupdate"; // Trả về form chỉnh sửa hồ sơ người dùng
    }

    // Cập nhật thông tin người dùng
    @PostMapping("/updateaccount")
    public String updateAccount( HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");

            // Cập nhật thông tin người dùng
            user.setName(user.getName());
            user.setEmail(user.getEmail());
            user.setPhone(user.getPhone());
            user.setUsername(user.getUsername());
            user.setAdd1(user.getAdd1());
            user.setCity(user.getCity());

            // Lưu thông tin người dùng đã cập nhật
            userService.save(user);

            // Cập nhật lại session để đồng bộ thông tin người dùng
            session.setAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật thông tin người dùng: " + e.getMessage());
            return "layout/userupdate";  // Trả về form nếu có lỗi
        }
        return "redirect:/myaccount";  // Chuyển hướng về trang tài khoản sau khi cập nhật
    }

    // Hiển thị form thay đổi mật khẩu
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "layout/changepassword";  // Trả về form thay đổi mật khẩu
    }

    // Cập nhật mật khẩu (không sử dụng PasswordEncoder)
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                 @RequestParam String confirmPassword, HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");

            // Kiểm tra mật khẩu cũ
            if (!oldPassword.equals(user.getPassword())) {
                model.addAttribute("error", "Mật khẩu cũ không đúng.");
                return "layout/changepassword";  // Trả về form nếu mật khẩu cũ sai
            }

            // Kiểm tra mật khẩu mới và xác nhận mật khẩu
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
                return "layout/changepassword";  // Trả về form nếu mật khẩu không khớp
            }

            // Cập nhật mật khẩu mới
            user.setPassword(newPassword);
            userService.save(user);  // Lưu mật khẩu mới vào cơ sở dữ liệu

            // Cập nhật lại session để đồng bộ mật khẩu mới
            session.setAttribute("user", user);

        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thay đổi mật khẩu: " + e.getMessage());
            return "layout/changepassword";  // Trả về form nếu có lỗi
        }
        return "redirect:/myaccount";  // Chuyển hướng về trang tài khoản sau khi đổi mật khẩu thành công
    }
}
