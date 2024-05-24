package entity;

import java.util.*;

public class Store {
    // {ProductCard: quantity}
    private Map<ProductCard, Integer> item_list;

    public Store() {
        this.item_list = new HashMap<>();
    }

    public void addItem(ProductCard newItem, int quantity) {
        // Jika kartu produk belum pernah ada di toko
        if (!this.item_list.containsKey(newItem)) {
            this.item_list.put(newItem, quantity);
            return;
        }

        // Jika produk sudah ada di toko maka +1 quantity
        this.item_list.put(newItem, this.item_list.get(newItem) + quantity);
    }

    public void deductItem(ProductCard targetItem, int quantity) throws Exception {
        // Jika produk sedang habis stoknya
        if (!this.item_list.containsKey(targetItem) || this.item_list.get(targetItem) == 0) {
            throw new Exception("You can't deduct the stock, because item is currently sold out");
        }

        // Kurangi stok produk sebanyak 1 buah
        if (this.item_list.get(targetItem) < quantity) {
            throw new Exception("You can't buy that much, because item stock is unsufficient");
        }

        this.item_list.put(targetItem, this.item_list.get(targetItem) - quantity);
    }

    public void purchaseItem(ProductCard item, Player player, int quantity) throws Exception {
        printStoreInformation();
        if (!item_list.containsKey(item) || item_list.get(item) == 0) {
            throw new Exception("Item is out of stock.");
        }
        int totalCost = item.getPrice() * quantity;
        if (player.getCash() < totalCost) {
            throw new Exception("Insufficient funds.");
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
        return this.item_list;
    }

    public void printStoreInformation() {
        System.out.println("Store Inventory:");
        for (Map.Entry<ProductCard, Integer> entry : item_list.entrySet()) {
            ProductCard product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.toString() + " - Quantity: " + quantity);
        }
    }

    public ProductCard findProductByName(String name) {
        for (ProductCard product : item_list.keySet()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
