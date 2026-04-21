package mallmanagement2;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

// import javax.swing.*;
// import java.awt.*;
import java.sql.*;

public class MallGUI extends JFrame {

    ProductDAO dao = new ProductDAO();
    Billing billing = new Billing();
    Report report = new Report();
    User user;

    JTextArea display;

    public MallGUI() {

        setTitle("Mall Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ROLE
        String role = JOptionPane.showInputDialog("Enter role (admin/cashier):");
        user = new User(role);

        // PANEL
        JPanel panel = new JPanel(new GridLayout(6, 1));

        JButton addBtn = new JButton("Add Product");
        JButton showBtn = new JButton("Show Products");
        JButton buyBtn = new JButton("Buy Product");
        JButton reportBtn = new JButton("Daily Report");
        JButton exitBtn = new JButton("Exit");

        panel.add(addBtn);
        panel.add(showBtn);
        panel.add(buyBtn);
        panel.add(reportBtn);
        panel.add(exitBtn);

        add(panel, BorderLayout.WEST);

        // DISPLAY
        display = new JTextArea();
        add(new JScrollPane(display), BorderLayout.CENTER);

        // ================= ADD PRODUCT =================
        addBtn.addActionListener(e -> {

            if (!user.isAdmin()) {
                show("Access Denied!");
                return;
            }

            try {
                String name = JOptionPane.showInputDialog("Name:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
                int qty = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));

                dao.addProduct(name, price, qty);

                show("Product Added Successfully!");

            } catch (Exception ex) {
                show("Invalid Input!");
            }
        });

        // ================= SHOW PRODUCTS (FIXED) =================
        showBtn.addActionListener(e -> {

            display.setText("");

            try {
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM products");

                while (rs.next()) {

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int qty = rs.getInt("quantity");

                    display.append(
                            id + " | " +
                            name + " | " +
                            "Price: " + price + " | " +
                            "Qty: " + qty + "\n"
                    );

                    if (qty <= 5) {
                        display.append("⚠ LOW STOCK\n");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                display.setText("Error loading products");
            }
        });

        // ================= BUY PRODUCT =================
        buyBtn.addActionListener(e -> {

            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Product ID:"));
                int qty = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));

                Product p = dao.getProduct(id);

                if (p == null) {
                    show("Product not found!");
                    return;
                }

                if (qty > p.quantity) {
                    show("Only " + p.quantity + " available!");
                    return;
                }

                double cost = Double.parseDouble(JOptionPane.showInputDialog("Cost Price:"));

                billing.generateBill(p, qty, cost, report);

                dao.updateQuantity(id, p.quantity - qty);

                show("Purchase Successful!");

            } catch (Exception ex) {
                show("Error in purchase");
            }
        });

        // ================= REPORT =================
        reportBtn.addActionListener(e -> {

            display.setText("--- REPORT ---\n");

            for (String item : report.soldItems.keySet()) {
                display.append(item + ": " + report.soldItems.get(item) + "\n");
            }

            display.append("\nTotal Sales: " + billing.totalSales);
            display.append("\nTotal Profit: " + billing.totalProfit);
        });

        // ================= EXIT =================
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    void show(String msg) {
        display.setText(msg);
    }

    public static void main(String[] args) {
        new MallGUI();
    }
}