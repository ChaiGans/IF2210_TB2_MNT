package entity;

import java.util.*;

public class Store {
    private Map<ProductCard, Integer> item_list;

    public Store() {
        this.item_list = new HashMap<>();
    }

    public void addItem(ProductCard newItem, int quantity) {
        Optional<ProductCard> existingKey = this.item_list.keySet().stream()
                .filter(p -> p.getClass().equals(newItem.getClass()))
                .findFirst();

        if (existingKey.isPresent()) {
            this.item_list.merge(existingKey.get(), quantity, Integer::sum);
        } else {
            this.item_list.put(newItem, quantity);
        }
    }

    public void deductItem(ProductCard targetItem, int quantity) throws Exception {
        Optional<ProductCard> existingKey = this.item_list.keySet().stream()
                .filter(p -> p.getName().equals(targetItem.getName()))
                .findFirst();

        if (existingKey.isEmpty()) {
            throw new Exception("You can't deduct the stock, because no item of this type exists");
        }

        Integer currentQuantity = this.item_list.get(existingKey.get());
        if (currentQuantity == 0) {
            throw new Exception("You can't deduct the stock, because item is currently sold out");
        }
        if (currentQuantity < quantity) {
            throw new Exception("Insufficient stock to deduct: requested " + quantity + ", available " + currentQuantity);
        }

        this.item_list.put(existingKey.get(), currentQuantity - quantity);
    }

    public void purchaseItem(ProductCard item, Player player, int quantity) throws Exception {
        printStoreInformation();
        ProductCard productInStore = findProductByName(item.getName());
        if (productInStore == null || item_list.get(productInStore) == 0) {
            throw new Exception("Item is out of stock.");
        }
        int totalCost = item.getPrice() * quantity;
        if (player.getCash() < totalCost) {
            throw new Exception("Insufficient funds. Required: $" + totalCost + ", Available: $" + player.getCash());
        }
        player.deductCash(totalCost);
        player.AddHand(productInStore);
        deductItem(productInStore, quantity);
//        printStoreInformation();
//        Integer stock = item_list.get(item);
//        if (stock == null || stock == 0) {
//            throw new Exception("Item is out of stock.");
//        }
//        int totalCost = item.getPrice() * quantity;
//        if (player.getCash() < totalCost) {
//            throw new Exception("Insufficient funds. Required: $" + totalCost + ", Available: $" + player.getCash());
//        }
//        player.deductCash(totalCost);
//        player.AddHand(item);
//        deductItem(item, quantity);
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
        System.out.println("Searching for product: " + name);
        for (ProductCard product : item_list.keySet()) {
            System.out.println("Product in store: " + product.getName());
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

//    public ProductCard findProductByName(String name) {
//        return item_list.keySet().stream()
//                .filter(product -> product.getName().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//    }
}
