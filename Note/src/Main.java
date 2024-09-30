public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            NoteUI noteUI = new NoteUI();
            noteUI.setVisible(true);
        });
    }
}