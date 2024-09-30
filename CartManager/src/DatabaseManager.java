import java.sql.*;
import java.util.logging.*;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(CartManager.class.getName());
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:shopping_cart.db");
            createOrdersTable();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add book", e);
        }
    }

    private void createOrdersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "items TEXT NOT NULL,"
                + "total_price REAL NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add book", e);
        }
    }

    public void saveOrder(String items, double totalPrice) {
        String insertOrderSQL = "INSERT INTO orders(items, total_price) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertOrderSQL)) {
            pstmt.setString(1, items);
            pstmt.setDouble(2, totalPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add book", e);
        }
    }

    public String viewOrderHistory() {
        StringBuilder history = new StringBuilder();
        String selectOrdersSQL = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectOrdersSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String items = rs.getString("items");
                double totalPrice = rs.getDouble("total_price");
                history.append("注文ID: ").append(id)
                        .append(", 商品: ").append(items)
                        .append(", 合計金額: ¥").append(totalPrice).append("\n");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add book", e);
        }
        return history.toString();
    }
}
