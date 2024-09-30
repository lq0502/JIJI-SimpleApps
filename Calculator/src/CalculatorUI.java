import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CalculatorUI extends JFrame {
    private JTextField displayField;
    private JPanel buttonPanel;
    private final HistoryUI historyUI;
    private final StringBuilder currentInput = new StringBuilder();
    private String operator = "";
    private double num1 = 0;
    private boolean isOperatorPressed = false;

    private final String[] emoticons = {"(•‿•)", "(◕‿◕)", "(¬‿¬)"};
    private Timer emoticonTimer;
    private Timer inactivityTimer;
    private final Random random = new Random();

    public CalculatorUI() {
        historyUI = new HistoryUI();
    }

    public void createAndShowUI() {
        setTitle("電卓");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(new Font("MS Gothic", Font.PLAIN, 30));
        displayField.setPreferredSize(new Dimension(380, 80));
        add(displayField, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        addButtonsToPanel();

        add(buttonPanel, BorderLayout.CENTER);

        startEmoticonTimer();

        startInactivityTimer();

        setVisible(true);
    }

    private void startEmoticonTimer() {
        emoticonTimer = new Timer(500, e -> {
            String emoticon = emoticons[random.nextInt(emoticons.length)];
            displayField.setText(emoticon);
        });
        emoticonTimer.start();
    }

    private void startInactivityTimer() {
        inactivityTimer = new Timer(30000, e -> startEmoticonTimer());
        inactivityTimer.setRepeats(false);
    }

    private void resetInactivityTimer() {
        inactivityTimer.restart();
        emoticonTimer.stop();
    }

    private void addButtonsToPanel() {
        String[] buttons = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "−",
                "0", ".", "=", "+",
                "C", "履歴", "削除履歴"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("MS Gothic", Font.BOLD, 20));
            button.setPreferredSize(new Dimension(50, 50));
            button.setFont(new Font("MS Gothic", Font.PLAIN, 14));
            button.addActionListener(new ButtonAction());
            buttonPanel.add(button);
        }
    }

    private class ButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            resetInactivityTimer();

            switch (command) {
                case "C":
                    currentInput.setLength(0);
                    displayField.setText("");
                    num1 = 0;
                    operator = "";
                    isOperatorPressed = false;
                    break;
                case "÷":
                case "×":
                case "−":
                case "+":
                    if (currentInput.length() > 0) {
                        num1 = Double.parseDouble(currentInput.toString());
                        operator = command;
                        isOperatorPressed = true;
                        displayField.setText(currentInput + " " + operator);
                        currentInput.setLength(0);
                    }
                    break;
                case "=":
                    if (currentInput.length() > 0 && !operator.isEmpty()) {
                        double num2 = Double.parseDouble(currentInput.toString());
                        double result = calculateResult(num1, num2, operator);
                        displayField.setText(num1 + " " + operator + " " + num2 + " = " + result);
                        Database.saveCalculation(num1, operator, num2, result);
                        currentInput.setLength(0);
                    }
                    break;
                case "履歴":
                    historyUI.showHistory();
                    break;
                case "削除履歴":
                    int confirmation = JOptionPane.showConfirmDialog(
                            CalculatorUI.this,
                            "履歴を削除してもよろしいですか？",
                            "削除確認",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        Database.clearHistory();
                    }
                    break;
                default:
                    if (isOperatorPressed) {
                        currentInput.setLength(0);
                        isOperatorPressed = false;
                    }
                    currentInput.append(command);
                    displayField.setText(displayField.getText() + command);
            }
        }
    }


    private double calculateResult(double num1, double num2, String operator) {
        switch (operator) {
            case "÷":
                return num1 / num2;
            case "×":
                return num1 * num2;
            case "−":
                return num1 - num2;
            case "+":
                return num1 + num2;
            default:
                return 0;
        }
    }
}
