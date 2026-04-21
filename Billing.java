package mallmanagement2;

class Billing {
    double totalSales = 0;
    double totalProfit = 0;

    public void generateBill(Product p, int qty, double costPrice, Report report) {
        if (p.quantity < qty) {
            System.out.println("Not enough stock!");
            return;
        }

        double total = p.price * qty;
        double profit = (p.price - costPrice) * qty;

        p.quantity -= qty;
        totalSales += total;
        totalProfit += profit;

        report.recordSale(p.name, qty);

        System.out.println("\n--- BILL ---");
        System.out.println("Item: " + p.name);
        System.out.println("Qty: " + qty);
        System.out.println("Total: " + total);
    }
}