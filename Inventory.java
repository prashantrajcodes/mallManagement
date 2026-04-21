package mallmanagement2;

import java.util.*;

class Inventory {
    Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product p) {
        products.put(p.id, p);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public void showProducts() {
        for (Product p : products.values()) {
            System.out.println(p.id + " " + p.name + " Price:" + p.price + " Qty:" + p.quantity);
        }
    }

    public void checkLowStock() {
        for (Product p : products.values()) {
            if (p.isLowStock()) {
                System.out.println("⚠ Low stock: " + p.name);
            }
        }
    }
}