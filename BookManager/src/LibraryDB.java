import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LibraryDB {

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:library.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "available INTEGER NOT NULL DEFAULT 1);";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created or exists already.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}