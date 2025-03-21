package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class CartService {

    // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
    public List<Map<String, Object>> getCart(HttpSession session) {
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }

    // Thêm món ăn vào giỏ hàng
    public void addItemToCart(List<Map<String, Object>> cart, String itemName, double price, long id) {
        boolean itemExists = false;

        // Kiểm tra xem món ăn đã có trong giỏ chưa
        for (Map<String, Object> item : cart) {
            if (item.get("Item_Name").equals(itemName)) {
                // Nếu món đã có trong giỏ hàng, tăng số lượng lên
                item.put("Quantity", (int) item.get("Quantity") + 1);
                itemExists = true;
                break;
            }
        }

        // Nếu món chưa có trong giỏ, thêm mới vào danh sách
        if (!itemExists) {
            Map<String, Object> newItem = new HashMap<>();
            newItem.put("Item_Name", itemName);
            newItem.put("Price", price);
            newItem.put("Id", id);
            newItem.put("Quantity", 1);  // Số lượng mặc định là 1
            cart.add(newItem);
        }
    }

    // Xóa món ăn khỏi giỏ hàng
    public void removeItemFromCart(List<Map<String, Object>> cart, String itemName) {
        cart.removeIf(item -> item.get("Item_Name").equals(itemName));
    }

    // Cập nhật số lượng món ăn trong giỏ
    public void updateItemQuantity(List<Map<String, Object>> cart, String itemName, int quantity) {
        for (Map<String, Object> item : cart) {
            if (item.get("Item_Name").equals(itemName)) {
                item.put("Quantity", quantity);
                break;
            }
        }
    }

    // Tính tổng giá trị của giỏ hàng
    public double calculateTotalAmount(List<Map<String, Object>> cart) {
        double totalAmount = 0;

        for (Map<String, Object> item : cart) {
            double itemPrice = (double) item.get("Price") * (int) item.get("Quantity");
            totalAmount += itemPrice;
        }

        return totalAmount;
    }
}
