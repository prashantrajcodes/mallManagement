package mallmanagement2;

import java.io.Serializable;

class Product implements Serializable {
    int id;
    String name;
    double price;
    int quantity;
    int threshold;

    public Product(int id, String name, double price, int quantity, int threshold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public boolean isLowStock() {
        return quantity <= threshold;
    }
}