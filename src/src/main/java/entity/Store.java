package entity;

import java.util.*;

public class Store {
    // {ProductCard: quantity}
    private Map<ProductCard, Integer> item_list;

    public Store() {
        this.item_list = new HashMap<>();
    }

    public void addItem(ProductCard newItem, int quantity) {
        // Retrieve current quantity, update it, or initialize it if not present
        this.item_list.merge(newItem, quantity, Integer::sum);
    }

    public void deductItem(ProductCard targetItem, int quantity) throws Exception {
        // Check if item exists and has sufficient quantity
        Integer currentQuantity = this.item_list.get(targetItem);
        if (currentQuantity == null || currentQuantity == 0) {
            throw new Exception("You can't deduct the stock, because item is currently sold out");
        }
        if (currentQuantity < quantity) {
            throw new Exception("Insufficient stock to deduct: requested " + quantity + ", available " + currentQuantity);
        }
        // Update the store's inventory by reducing the quantity
        this.item_list.put(targetItem, currentQuantity - quantity);
    }

    public void purchaseItem(ProductCard item, Player player, int quantity) throws Exception {
        printStoreInformation();
        Integer stock = item_list.get(item);
        if (stock == null || stock == 0) {
            throw new Exception("Item is out of stock.");
        }
        int totalCost = item.getPrice() * quantity;
        if (player.getCash() < totalCost) {
            throw new Exception("Insufficient funds. Required: $" + totalCost + ", Available: $" + player.getCash());
        }
        player.deductCash(totalCost);
        player.AddHand(item);
        deductItem(item, quantity);
    }

    public void sellingItem(ProductCard item, Player player, int quantity) throws Exception {
        this.addItem(item, quantity);
        int totalProfit = item.getPrice() * quantity;
        player.addCash(totalProfit);
    }

    public Map<ProductCard, Integer> getStoreInformation() {
        return Collections.unmodifiableMap(item_list);
    }

    public void printStoreInformation() {
        System.out.println("Store Inventory:");
        item_list.forEach((product, quantity) ->
                System.out.println(product + " - Quantity: " + quantity));
    }

    public ProductCard findProductByName(String name) {
        return item_list.keySet().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
