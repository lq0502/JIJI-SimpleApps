import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private List<Product> cart;

    public CartManager() {
        cart = new ArrayList<>();
    }

    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        cart.add(product);
    }

    public String viewCart() {
        if (cart.isEmpty()) {
            return "Cart is empty!";
        }

        StringBuilder cartDetails = new StringBuilder();
        for (Product product : cart) {
            cartDetails.append(product.getName()).append(" - $").append(product.getPrice()).append("\n");
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
