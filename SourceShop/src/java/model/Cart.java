package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, Map<Integer, Integer>> items; // Change the key type to String (userId)

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(int userId, int productId) {
        items.computeIfAbsent(userId, k -> new HashMap<>());

        Map<Integer, Integer> userCart = items.get(userId);
        int mobileId = productId;

        userCart.put(mobileId, userCart.getOrDefault(mobileId, 0) + 1);
    }

    public Map<Integer, Map<Integer, Integer>> getItems() {
        return items;
    }

    public void removeItem(int userId, int productId) {
        Map<Integer, Integer> userCart = items.get(userId);
        if (userCart != null) {
            userCart.remove(productId);
        }
    }
}
