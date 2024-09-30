import java.sql.*;
import java.util.logging.*;

public class LibraryDB {

    private static final Logger logger = Logger.getLogger(LibraryDB.class.getName());

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:library.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
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
            logger.log(Level.SEVERE, "Failed to initialize the database", e);
        }
    }
}
