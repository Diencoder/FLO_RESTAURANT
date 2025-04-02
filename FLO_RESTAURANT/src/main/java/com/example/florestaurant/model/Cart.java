//package com.example.florestaurant.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Cart {
//    private List<CartItem> items = new ArrayList<>();
//
//    // Thêm món ăn vào giỏ hàng
//    public void addItem(Food food, int quantity) {
//        for (CartItem item : items) {
//            if (item.getFood().getId().equals(food.getId())) {
//                item.setQuantity(item.getQuantity() + quantity); // Cập nhật số lượng nếu món ăn đã có
//                return;
//            }
//        }
//        items.add(new CartItem(food, quantity)); // Nếu món ăn chưa có, thêm mới vào giỏ
//    }
//    // Cập nhật số lượng món ăn trong giỏ hàng
//    public void updateItemQuantity(Long foodId, int quantity) {
//        for (CartItem item : items) {
//            if (item.getFood().getId().equals(foodId)) {
//                item.setQuantity(quantity);  // Cập nhật số lượng
//                break;
//            }
//        }
//    }
//
//    public List<CartItem> getItems() {
//        return items;
//    }
//
//    public double getTotalPrice() {
//        double total = 0;
//        for (CartItem item : items) {
//            total += item.getTotalPrice();
//        }
//        return total;
//    }
//
//    public void removeItem(Long foodId) {
//        items.removeIf(item -> item.getFood().getId().equals(foodId)); // Xóa món ăn theo foodId
//    }
//}
