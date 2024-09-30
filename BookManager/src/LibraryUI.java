import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryUI extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField bookIdField;

    public LibraryUI() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(10, 10, 80, 25);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(100, 10, 165, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(10, 40, 80, 25);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(100, 40, 165, 25);
        add(authorField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(10, 80, 120, 25);
        add(addButton);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(10, 120, 80, 25);
        add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(100, 120, 165, 25);
        add(bookIdField);

        JButton borrowButton = new JButton("Borrow Book");
        borrowButton.setBounds(10, 160, 120, 25);
        add(borrowButton);

        JButton returnButton = new JButton("Return Book");
        returnButton.setBounds(140, 160, 120, 25);
        add(returnButton);

        JButton viewButton = new JButton("View Books");
        viewButton.setBounds(10, 200, 120, 25);
        add(viewButton);

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                BookManager.addBook(title, author);
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bookId = Integer.parseInt(bookIdField.getText());
                BookManager.borrowBook(bookId);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bookId = Integer.parseInt(bookIdField.getText());
                BookManager.returnBook(bookId);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookManager.viewBooks();
            }
        });
    }

    public static void main(String[] args) {
        LibraryDB.initializeDatabase(); // Initialize the database
        LibraryUI libraryUI = new LibraryUI();
        libraryUI.setVisible(true);
    }
}
