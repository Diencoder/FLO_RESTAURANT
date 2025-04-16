package com.example.florestaurant.controller;

import com.example.florestaurant.model.OrderManager;
import com.example.florestaurant.model.Review;
import com.example.florestaurant.service.OrderService;
import com.example.florestaurant.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vieworder")
public class UserReviewController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserReviewService userReviewService;

    // Hiển thị danh sách đơn hàng và đánh giá từ session
    @GetMapping("/vieworder")
    public String viewOrders(HttpSession session, Model model) {
        List<OrderManager> orders = (List<OrderManager>) session.getAttribute("orders");

        if (orders == null) {
            orders = orderService.getAllOrders();
            session.setAttribute("orders", orders);
        }

        for (OrderManager order : orders) {
            if (order.getItems() == null) {
                order.setItems(new ArrayList<>());
            }
        }

        model.addAttribute("orders", orders);
        return "layout/vieworder";
    }

    // Cập nhật hoặc thêm đánh giá
    @PostMapping("/addOrUpdateReview")
    public String addOrUpdateReview(@RequestParam("orderId") Long orderId,
                                    @RequestParam(value = "foodId", required = false) Long foodId,  // Cho phép foodId null
                                    @RequestParam("rating") int rating,
                                    @RequestParam("reviewText") String reviewText,
                                    HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<OrderManager> orders = (List<OrderManager>) session.getAttribute("orders");
            if (orders == null) {
                orders = orderService.getAllOrders();
                session.setAttribute("orders", orders);
            }

            OrderManager order = null;
            for (OrderManager ord : orders) {
                if (ord.getOrderId().equals(orderId)) {
                    order = ord;
                    break;
                }
            }

            if (order == null) {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy đơn hàng.");
                return "layout/vieworder";
            }

            // Kiểm tra đánh giá món ăn trong đơn hàng
            Review review = userReviewService.getReviewByOrderIdAndFoodId(orderId, foodId);
            if (review == null) {
                review = new Review();
                review.setOrderId(orderId);
                review.setFoodId(foodId);
            }

            // Cập nhật hoặc tạo mới đánh giá
            review.setRating(rating);
            review.setReviewText(reviewText);

            // Lưu đánh giá vào CSDL
            userReviewService.saveReview(review);

            redirectAttributes.addFlashAttribute("message", "Đánh giá đã được cập nhật thành công.");
            return "redirect:/vieworder";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi khi gửi đánh giá.");
            return "layout/vieworder";
        }
    }

    // Xóa đánh giá
    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("orderId") Long orderId,
                               @RequestParam("foodId") Long foodId,
                               HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            userReviewService.deleteReviewByOrderIdAndFoodId(orderId, foodId);

            redirectAttributes.addFlashAttribute("message", "Đánh giá đã được xóa thành công.");
            return "redirect:/vieworder";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi khi xóa đánh giá.");
            return "layout/vieworder";
        }
    }
}
