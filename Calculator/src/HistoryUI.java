import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoryUI extends JFrame {
    private final JTextArea historyArea;

    public HistoryUI() {
        setTitle("計算履歴");
        setSize(400, 300);
        setLocationRelativeTo(null);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void showHistory() {
        List<String> history = Database.getHistory();
        historyArea.setText("");
        for (String record : history) {
            historyArea.append(record + "\n");
        }
        setVisible(true);
    }
}
