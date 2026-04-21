package mallmanagement2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mall_db";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql@0904";

    public static Connection getConnection() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("DB Connection Failed");
            e.printStackTrace();
            return null;
        }
    }
}
