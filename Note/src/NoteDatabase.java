import java.sql.*;
import java.util.*;

public class NoteDatabase {

    public NoteDatabase() {
        createTable();
    }

    private Connection connect() {
        String url = "jdbc:sqlite:notes.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS notes ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " content TEXT NOT NULL"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getAllNotes() {
        List<String> notes = new ArrayList<>();
        String sql = "SELECT content FROM notes";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                notes.add(rs.getString("content"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return notes;
    }

    public void addNote(String note) {
        String sql = "INSERT INTO notes(content) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateNote(String oldNote, String newNote) {
        String sql = "UPDATE notes SET content = ? WHERE content = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNote);
            pstmt.setString(2, oldNote);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteNote(String note) {
        String sql = "DELETE FROM notes WHERE content = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
