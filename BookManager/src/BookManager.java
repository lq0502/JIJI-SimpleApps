import java.sql.*;
import java.util.logging.*;

public class BookManager {

    private static final Logger logger = Logger.getLogger(BookManager.class.getName());

    public static String addBook(String title, String author) {
        String sql = "INSERT INTO books(title, author) VALUES(?,?)";
        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.executeUpdate();
            return "本が追加されました: " + title;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add book", e);
            return "本の追加に失敗しました。";
        }
    }

    public static String deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return "本が削除されました。";
            } else {
                return "本が見つかりませんでした。";
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to delete book", e);
            return "本の削除に失敗しました。";
        }
    }

    public static String updateBook(int bookId, String newTitle, String newAuthor) {
        String sql = "UPDATE books SET title = ?, author = ? WHERE id = ?";
        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newAuthor);
            pstmt.setInt(3, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return "本が更新されました。";
            } else {
                return "本が見つかりませんでした。";
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update book", e);
            return "本の更新に失敗しました。";
        }
    }

    public static String borrowBook(int bookId) {
        String sql = "UPDATE books SET available = 0 WHERE id = ? AND available = 1";
        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return "本が借りられました。";
            } else {
                return "本は利用できません。";
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to borrow book", e);
            return "本の借りる操作に失敗しました。";
        }
    }

    public static String returnBook(int bookId) {
        String sql = "UPDATE books SET available = 1 WHERE id = ?";
        try (Connection conn = LibraryDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            return "本が返されました。";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to return book", e);
            return "本の返却操作に失敗しました。";
        }
    }

    public static String viewBooks() {
        StringBuilder result = new StringBuilder("図書館の本:\n");
        String sql = "SELECT * FROM books";
        try (Connection conn = LibraryDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.append(rs.getInt("id"))
                        .append(". ")
                        .append(rs.getString("title"))
                        .append(" - ")
                        .append(rs.getString("author"))
                        .append(" - ")
                        .append(rs.getInt("available") == 1 ? "利用可能" : "借りられています")
                        .append("\n");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve books", e);
            return "本の表示に失敗しました。";
        }
        return result.toString();
    }
}
