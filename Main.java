package mallmanagement2;
import java.sql.Connection;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Connection con = DBConnection.getConnection();
        

        // if (con != null) {

        //     System.out.println("Connected to database!");

        // } else {

        //     System.out.println("Connection failed!");

        // }

        Scanner sc = new Scanner(System.in);
        //Inventory inventory = new Inventory();
        Billing billing = new Billing();
        Report report = new Report();
        ProductDAO dao = new ProductDAO();

        //FileHandler.loadInventory(inventory);

        System.out.println("Enter role (admin/cashier): ");
        User user = new User(sc.next());

        while (true) {
            System.out.println("\n1.Add Product 2.Show Products 3.Buy 4.Report 5.Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (!user.isAdmin()){
                        System.out.println("Access Denied! ");
                        break;
                    }
                    System.out.print("Name | Price | Quantity |  ");
                    String name = sc.next();
                    double price = sc.nextDouble();
                    int quantity = sc.nextInt();

                    dao.addProduct(name,price, quantity);
                    break;

                case 2:
                    dao.viewProducts();
                    break;

                case 3:
                    System.out.print("Enter Product ID & Qty: ");
                    int id = sc.nextInt();
                    int qty = sc.nextInt();

                    Product p = dao.getProduct(id);

                    if (p != null) {
                        if (p.quantity <= 0) {
                            System.out.println("Not enough stock!");
                            break;
                        }
                        if (qty > p.quantity){
                            System.out.println("Only " + p.quantity + " available!");
                            break;
                        }

                        System.out.print("Enter cost price: ");
                        double cost = sc.nextDouble();

                        billing.generateBill(p, qty, cost, report);

                        //  UPDATE DB
                        int newQty = p.quantity - qty;
                        dao.updateQuantity(id, newQty);

                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 4:
                    report.showReport();
                    System.out.println("Total Sales: " + billing.totalSales);
                    System.out.println("Total Profit: " + billing.totalProfit);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;



                // case 5:
                //     FileHandler.saveInventory(inventory);
                //     break;

                // case 6:
                //     FileHandler.saveInventory(inventory);
                //     System.out.println("Exiting...");
                //     return;
            }
        }
 
    }
}