// PayPalPayment.java
public class PayPalPayment implements PaymentStrategy {
    @SuppressWarnings("unused")
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        // add payment processing and verification
        System.out.println("Paid " + amount + " using PayPal.");
    }
}
