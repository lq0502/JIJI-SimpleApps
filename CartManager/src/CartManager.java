import java.util.*;

public class CartManager {
    private final List<Product> cart;

    public CartManager() {
        cart = new ArrayList<>();
    }

    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        cart.add(product);
    }

    public String viewCart() {
        if (cart.isEmpty()) {
            return "カートは空です！";
        }

        StringBuilder cartDetails = new StringBuilder();
        for (Product product : cart) {
            cartDetails.append(product.getName()).append(" - ¥").append(product.getPrice()).append("\n");
        }
        return cartDetails.toString();
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : cart) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void clearCart() {
        cart.clear();
    }
}
