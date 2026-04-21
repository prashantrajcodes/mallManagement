package mallmanagement2;

import java.sql.*;


public class ProductDAO {

    public void addProduct(String name, double price, int quantity) {
        
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);

            ps.executeUpdate();

            System.out.println("Product added to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void viewProducts() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM products";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Product List ---");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getDouble("price") + " | " +
                    rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
    }
    
}
    public Product getProduct(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    0
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateQuantity(int id, int newQty) {
    try {
        Connection con = DBConnection.getConnection();

        String query = "UPDATE products SET quantity = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, newQty);
        ps.setInt(2, id);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}