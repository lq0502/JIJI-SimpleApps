import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingUI {
    private JFrame frame;
    private CartManager cartManager;
    private DatabaseManager databaseManager;

    public ShoppingUI() {
        cartManager = new CartManager(); // 初始化购物车管理器
        databaseManager = new DatabaseManager(); // 初始化数据库管理器
        frame = new JFrame("Simple Shopping Cart");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 商品标签和按钮
        JLabel label = new JLabel("Available Products:");
        label.setBounds(20, 20, 200, 20);
        frame.add(label);

        JButton addButton = new JButton("Add Product 1 (Price: 10)");
        addButton.setBounds(20, 60, 200, 30);
        frame.add(addButton);

        JButton cartButton = new JButton("View Cart");
        cartButton.setBounds(20, 100, 200, 30);
        frame.add(cartButton);

        JButton saveOrderButton = new JButton("Checkout and Save Order");
        saveOrderButton.setBounds(20, 140, 200, 30);
        frame.add(saveOrderButton);

        JButton historyButton = new JButton("View Order History");
        historyButton.setBounds(20, 180, 200, 30);
        frame.add(historyButton);

        // 添加商品到购物车
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cartManager.addProduct("Product 1", 10.0);
                JOptionPane.showMessageDialog(frame, "Product 1 added to cart!");
            }
        });

        // 查看购物车内容
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cartDetails = cartManager.viewCart();
                JOptionPane.showMessageDialog(frame, cartDetails);
            }
        });

        // 保存订单到数据库
        saveOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cartDetails = cartManager.viewCart();
                double totalPrice = cartManager.calculateTotalPrice();
                databaseManager.saveOrder(cartDetails, totalPrice);
                JOptionPane.showMessageDialog(frame, "Order saved successfully!");
                cartManager.clearCart(); // 清空购物车
            }
        });

        // 查看订单历史
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderHistory = databaseManager.viewOrderHistory();
                JOptionPane.showMessageDialog(frame, orderHistory);
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}
