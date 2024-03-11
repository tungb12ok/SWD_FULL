package model;

import dao.ProductDAO;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, Map<Integer, Integer>> items;
    ProductDAO dao = new ProductDAO();

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(int userId, int productId) {
        items.computeIfAbsent(userId, k -> new HashMap<>());

        Map<Integer, Integer> userCart = items.get(userId);
        int mobileId = productId;

        userCart.put(mobileId, userCart.getOrDefault(mobileId, 0) + 1);
    }

    public void addItem(int userId, int productId, int quantity) {
        items.computeIfAbsent(userId, k -> new HashMap<>());

        Map<Integer, Integer> userCart = items.get(userId);
        int mobileId = productId;

        int currentQuantity = userCart.getOrDefault(mobileId, 0);
        userCart.put(mobileId, currentQuantity + quantity);
    }

    public Map<Integer, Map<Integer, Integer>> getItems() {
        return items;
    }

    public void decreaseItemQuantity(int userId, int productId, int quantityToDecrease) {
        if (quantityToDecrease <= 0) {
            // If the quantity to decrease is zero or negative, do nothing
            return;
        }

        Map<Integer, Integer> userCart = items.get(userId);

        if (userCart != null) {
            int currentQuantity = userCart.getOrDefault(productId, 0);
            int newQuantity = Math.max(0, currentQuantity - quantityToDecrease);

            if (newQuantity > 0) {
                userCart.put(productId, newQuantity);
            } else {
                userCart.remove(productId);

                // Remove the user entry if the cart is empty after removing the item
                if (userCart.isEmpty()) {
                    items.remove(userId);
                }
            }
        }
    }

    public double calculateTotalAmount() {
        double totalAmount = 0.0;

        for (Map<Integer, Integer> userCart : items.values()) {
            for (Map.Entry<Integer, Integer> entry : userCart.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Get the product price from your ProductDAO or another source
                double productPrice = dao.getProductById(productId).getProductPrice();

                // Calculate the product total amount and add it to the overall total
                totalAmount += quantity * productPrice;
            }
        }

        return totalAmount;
    }

    public void updateItemQuantity(int userId, int productId, int newQuantity) {
        if (newQuantity <= 0) {
            // If the new quantity is zero or negative, remove the item from the cart
            removeItem(userId, productId);
        } else {
            // Otherwise, update the quantity in the cart
            items.computeIfAbsent(userId, k -> new HashMap<>());

            Map<Integer, Integer> userCart = items.get(userId);
            userCart.put(productId, newQuantity);
        }
    }

    public boolean containsProduct(int userId, int productId) {
        if (items.containsKey(userId)) { // Kiểm tra xem giỏ hàng có chứa userId
            Map<Integer, Integer> userCart = items.get(userId); // Lấy giỏ hàng của userId
            return userCart.containsKey(productId); // Kiểm tra xem giỏ hàng của người dùng có chứa productId
        }
        return false; // Nếu userId không tồn tại trong giỏ hàng, trả về false
    }

    public void removeItem(int userId, int productId) {
        Map<Integer, Integer> userCart = items.get(userId);
        if (userCart != null) {
            userCart.remove(productId);

            // Remove the user entry if the cart is empty after removing the item
            if (userCart.isEmpty()) {
                items.remove(userId);
            }
        }
    }
}
