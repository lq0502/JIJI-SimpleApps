import java.sql.*;

public class BookManager {

    public static void addBook(String title, String author) {
        String sql = "INSERT INTO books(title, author) VALUES(?,?)";

        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.executeUpdate();
            System.out.println("Book added: " + title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrowBook(int bookId) {
        String sql = "UPDATE books SET available = 0 WHERE id = ? AND available = 1";

        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book borrowed.");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(int bookId) {
        String sql = "UPDATE books SET available = 1 WHERE id = ?";

        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            System.out.println("Book returned.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewBooks() {
        String sql = "SELECT * FROM books";

        try (Connection conn = LibraryDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Library Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                        rs.getString("title") + " by " +
                        rs.getString("author") + " - " +
                        (rs.getInt("available") == 1 ? "Available" : "Borrowed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
