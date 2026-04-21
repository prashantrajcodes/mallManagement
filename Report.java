package mallmanagement2;

import java.util.*;

class Report {
    Map<String, Integer> soldItems = new HashMap<>();

    public void recordSale(String name, int qty) {
        soldItems.put(name, soldItems.getOrDefault(name, 0) + qty);
    }

    public void showReport() {
        System.out.println("\n--- DAILY REPORT ---");
        for (String item : soldItems.keySet()) {
            System.out.println(item + ": " + soldItems.get(item));
        }
    }
}