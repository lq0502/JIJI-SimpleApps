import javax.swing.*;

public class LibraryUI extends JFrame {
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField bookIdField;
    private final JTextArea outputArea;

    public LibraryUI() {
        setTitle("図書館管理システム");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("タイトル:");
        titleLabel.setBounds(10, 10, 80, 25);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(100, 10, 165, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("著者:");
        authorLabel.setBounds(10, 40, 80, 25);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(100, 40, 165, 25);
        add(authorField);

        JButton addButton = new JButton("本を追加");
        addButton.setBounds(10, 80, 120, 25);
        add(addButton);

        JLabel bookIdLabel = new JLabel("本のID:");
        bookIdLabel.setBounds(10, 120, 80, 25);
        add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(100, 120, 165, 25);
        add(bookIdField);

        JButton borrowButton = new JButton("本を借りる");
        borrowButton.setBounds(10, 160, 120, 25);
        add(borrowButton);

        JButton returnButton = new JButton("本を返す");
        returnButton.setBounds(140, 160, 120, 25);
        add(returnButton);

        JButton deleteButton = new JButton("本を削除");
        deleteButton.setBounds(10, 200, 120, 25);
        add(deleteButton);

        JButton updateButton = new JButton("本を更新");
        updateButton.setBounds(140, 200, 120, 25);
        add(updateButton);

        JButton viewButton = new JButton("本を表示");
        viewButton.setBounds(10, 240, 120, 25);
        add(viewButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 280, 450, 150);
        outputArea.setEditable(false);
        add(outputArea);

        // Add action listeners for buttons
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            if (title.isEmpty() || author.isEmpty()) {
                outputArea.setText("タイトルと著者名を入力してください。");
            } else {
                String result = BookManager.addBook(title, author);
                outputArea.setText(result);
            }
        });

        borrowButton.addActionListener(e -> {
            String bookIdText = bookIdField.getText();
            if (bookIdText.isEmpty()) {
                outputArea.setText("本のIDを入力してください。");
            } else {
                try {
                    int bookId = Integer.parseInt(bookIdText);
                    String result = BookManager.borrowBook(bookId);
                    outputArea.setText(result);
                } catch (NumberFormatException ex) {
                    outputArea.setText("本のIDは数字でなければなりません。");
                }
            }
        });

        returnButton.addActionListener(e -> {
            String bookIdText = bookIdField.getText();
            if (bookIdText.isEmpty()) {
                outputArea.setText("本のIDを入力してください。");
            } else {
                try {
                    int bookId = Integer.parseInt(bookIdText);
                    String result = BookManager.returnBook(bookId);
                    outputArea.setText(result);
                } catch (NumberFormatException ex) {
                    outputArea.setText("本のIDは数字でなければなりません。");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String bookIdText = bookIdField.getText();
            if (bookIdText.isEmpty()) {
                outputArea.setText("本のIDを入力してください。");
            } else {
                try {
                    int bookId = Integer.parseInt(bookIdText);
                    String result = BookManager.deleteBook(bookId);
                    outputArea.setText(result);
                } catch (NumberFormatException ex) {
                    outputArea.setText("本のIDは数字でなければなりません。");
                }
            }
        });

        updateButton.addActionListener(e -> {
            String bookIdText = bookIdField.getText();
            String newTitle = titleField.getText();
            String newAuthor = authorField.getText();
            if (bookIdText.isEmpty() || newTitle.isEmpty() || newAuthor.isEmpty()) {
                outputArea.setText("本のID、タイトル、著者名を入力してください。");
            } else {
                try {
                    int bookId = Integer.parseInt(bookIdText);
                    String result = BookManager.updateBook(bookId, newTitle, newAuthor);
                    outputArea.setText(result);
                } catch (NumberFormatException ex) {
                    outputArea.setText("本のIDは数字でなければなりません。");
                }
            }
        });

        viewButton.addActionListener(e -> {
            String result = BookManager.viewBooks();
            outputArea.setText(result);
        });
    }

    public static void main(String[] args) {
        LibraryDB.initializeDatabase(); // Initialize the database
        LibraryUI libraryUI = new LibraryUI();
        libraryUI.setVisible(true);
    }
}
