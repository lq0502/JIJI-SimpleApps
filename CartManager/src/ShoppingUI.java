import javax.swing.*;

public class ShoppingUI {
    private final JFrame frame;
    private final CartManager cartManager;
    private final DatabaseManager databaseManager;

    public ShoppingUI() {
        cartManager = new CartManager();
        databaseManager = new DatabaseManager();
        frame = new JFrame("簡単なショッピングカート");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("利用可能な商品:");
        label.setBounds(20, 20, 200, 20);
        frame.add(label);

        JButton addButton = new JButton("商品1を追加 (価格: 10)");
        addButton.setBounds(20, 60, 200, 30);
        frame.add(addButton);

        JButton cartButton = new JButton("カートを見る");
        cartButton.setBounds(20, 100, 200, 30);
        frame.add(cartButton);

        JButton saveOrderButton = new JButton("注文を確認して保存");
        saveOrderButton.setBounds(20, 140, 200, 30);
        frame.add(saveOrderButton);

        JButton historyButton = new JButton("注文履歴を見る");
        historyButton.setBounds(20, 180, 200, 30);
        frame.add(historyButton);

        addButton.addActionListener(e -> {
            cartManager.addProduct("商品1", 10.0);
            JOptionPane.showMessageDialog(frame, "商品1がカートに追加されました！");
        });

        cartButton.addActionListener(e -> {
            String cartDetails = cartManager.viewCart();
            JOptionPane.showMessageDialog(frame, cartDetails);
        });

        saveOrderButton.addActionListener(e -> {
            String cartDetails = cartManager.viewCart();
            double totalPrice = cartManager.calculateTotalPrice();
            databaseManager.saveOrder(cartDetails, totalPrice);
            JOptionPane.showMessageDialog(frame, "注文が正常に保存されました！");
            cartManager.clearCart();
        });

        historyButton.addActionListener(e -> {
            String orderHistory = databaseManager.viewOrderHistory();
            JOptionPane.showMessageDialog(frame, orderHistory);
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}
