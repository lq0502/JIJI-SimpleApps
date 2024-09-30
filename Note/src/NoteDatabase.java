import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDatabase {

    // 构造函数，创建表
    public NoteDatabase() {
        createTable();
    }

    // 连接数据库的方法
    private Connection connect() {
        String url = "jdbc:sqlite:notes.db"; // SQLite 数据库文件
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // 创建表的方法
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS notes ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " content TEXT NOT NULL"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // 执行创建表的SQL语句
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 获取所有记事本条目
    public List<String> getAllNotes() {
        List<String> notes = new ArrayList<>();
        String sql = "SELECT content FROM notes";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 遍历结果集并将内容添加到列表中
            while (rs.next()) {
                notes.add(rs.getString("content"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return notes;
    }

    // 添加新记事本条目
    public void addNote(String note) {
        String sql = "INSERT INTO notes(content) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);  // 设置SQL语句中的第一个参数
            pstmt.executeUpdate();     // 执行更新操作
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 更新现有记事本条目
    public void updateNote(String oldNote, String newNote) {
        String sql = "UPDATE notes SET content = ? WHERE content = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNote); // 设置新的条目内容
            pstmt.setString(2, oldNote); // 根据旧的条目内容进行匹配
            pstmt.executeUpdate();       // 执行更新操作
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 删除记事本条目
    public void deleteNote(String note) {
        String sql = "DELETE FROM notes WHERE content = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);  // 根据条目内容进行删除
            pstmt.executeUpdate();     // 执行删除操作
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
