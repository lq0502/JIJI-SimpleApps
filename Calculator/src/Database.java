import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:sqlite:calculations.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS history (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "num1 REAL," +
                        "operator TEXT," +
                        "num2 REAL," +
                        "result REAL)";
                Statement stmt = conn.createStatement();
                stmt.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveCalculation(double num1, String operator, double num2, double result) {
        String insertSQL = "INSERT INTO history(num1, operator, num2, result) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setDouble(1, num1);
            pstmt.setString(2, operator);
            pstmt.setDouble(3, num2);
            pstmt.setDouble(4, result);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getHistory() {
        List<String> history = new ArrayList<>();
        String selectSQL = "SELECT num1, operator, num2, result FROM history";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                double num1 = rs.getDouble("num1");
                String operator = rs.getString("operator");
                double num2 = rs.getDouble("num2");
                double result = rs.getDouble("result");
                history.add(num1 + " " + operator + " " + num2 + " = " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }
    public static void clearHistory() {
        String deleteSQL = "DELETE FROM history";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
