public class Main {
    public static void main(String[] args) {
        // 设置UI风格并启动主界面
        javax.swing.SwingUtilities.invokeLater(() -> {
            NoteUI noteUI = new NoteUI();
            noteUI.setVisible(true);
        });
    }
}