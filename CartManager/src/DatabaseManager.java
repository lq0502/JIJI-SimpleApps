import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            // 创建与SQLite数据库的连接
            connection = DriverManager.getConnection("jdbc:sqlite:shopping_cart.db");
            createOrdersTable(); // 创建订单表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 创建订单表，存储订单ID、商品列表和总价
    private void createOrdersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "items TEXT NOT NULL,"
                + "total_price REAL NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 将订单保存到数据库
    public void saveOrder(String items, double totalPrice) {
        String insertOrderSQL = "INSERT INTO orders(items, total_price) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertOrderSQL)) {
            pstmt.setString(1, items);
            pstmt.setDouble(2, totalPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查看订单历史
    public String viewOrderHistory() {
        StringBuilder history = new StringBuilder();
        String selectOrdersSQL = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectOrdersSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String items = rs.getString("items");
                double totalPrice = rs.getDouble("total_price");
                history.append("Order ID: ").append(id)
                        .append(", Items: ").append(items)
                        .append(", Total Price: $").append(totalPrice).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}
