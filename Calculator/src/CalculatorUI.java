import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CalculatorUI extends JFrame {
    private JTextField displayField;
    private JPanel buttonPanel;
    private HistoryUI historyUI;
    private StringBuilder currentInput = new StringBuilder();
    private String operator = "";
    private double num1 = 0;
    private boolean isOperatorPressed = false;

    private String[] emoticons = {"(•‿•)", "(◕‿◕)", "(¬‿¬)"};
    private Timer emoticonTimer;
    private Timer inactivityTimer;
    private Random random = new Random();

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
        displayField.setPreferredSize(new Dimension(380, 80));  // 控制显示屏的尺寸
        add(displayField, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));  // 控制按钮之间的间距
        addButtonsToPanel();

        add(buttonPanel, BorderLayout.CENTER);

        startEmoticonTimer();

        // 设置用户操作超时后的屏幕保护
        startInactivityTimer();

        setVisible(true);
    }

    // 表情滚动的计时器
    private void startEmoticonTimer() {
        emoticonTimer = new Timer(500, e -> {
            String emoticon = emoticons[random.nextInt(emoticons.length)];
            displayField.setText(emoticon);
        });
        emoticonTimer.start();
    }

    // 用户操作后的超时计时器
    private void startInactivityTimer() {
        inactivityTimer = new Timer(30000, e -> startEmoticonTimer()); // 30秒无操作后启动颜文字滚动
        inactivityTimer.setRepeats(false);
    }

    private void resetInactivityTimer() {
        inactivityTimer.restart();
        emoticonTimer.stop(); // 停止滚动表情
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
            button.setFont(new Font("MS Gothic", Font.BOLD, 20));  // 调整字体大小
            button.setPreferredSize(new Dimension(50, 50));  // 控制按钮的尺寸
            button.setFont(new Font("MS Gothic", Font.PLAIN, 14));// 使用支持日语的字体
            button.addActionListener(new ButtonAction());
            buttonPanel.add(button);
        }
    }

    private class ButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // 每次用户操作时重置计时器并停止表情滚动
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
                        // 在显示区域显示数字和运算符
                        displayField.setText(currentInput.toString() + " " + operator);
                        currentInput.setLength(0);  // 重置输入，准备输入第二个数字
                    }
                    break;
                case "=":
                    if (currentInput.length() > 0 && !operator.isEmpty()) {
                        double num2 = Double.parseDouble(currentInput.toString());
                        double result = calculateResult(num1, num2, operator);
                        // 显示完整表达式和结果
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
                        Database.clearHistory();  // 调用删除历史的数据库方法
                    }
                    break;
                default:
                    if (isOperatorPressed) {
                        currentInput.setLength(0);
                        isOperatorPressed = false;
                    }
                    currentInput.append(command);
                    // 在每次输入数字或小数点时，更新显示区域
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
