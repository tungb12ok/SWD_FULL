package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String, Map<String, Integer>> items; // Change the key type to String (userId)

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(String userId, String productId) {
        items.computeIfAbsent(userId, k -> new HashMap<>());

        Map<String, Integer> userCart = items.get(userId);
        String mobileId = productId;

        userCart.put(mobileId, userCart.getOrDefault(mobileId, 0) + 1);
    }

    public Map<String, Map<String, Integer>> getItems() {
        return items;
    }

    public void removeItem(String userId, String productId) {
        Map<String, Integer> userCart = items.get(userId);
        if (userCart != null) {
            userCart.remove(productId);
        }
    }
}
