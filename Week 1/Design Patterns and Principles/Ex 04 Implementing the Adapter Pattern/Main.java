// Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println();

        PaymentProcessor payPalProcessor = new PayPalAdapter(new PayPalGateway());
        PaymentProcessor stripeProcessor = new StripeAdapter(new StripeGateway());

        payPalProcessor.processPayment(150.0); // Should use PayPal to process payment
        stripeProcessor.processPayment(370.50); // Should use Stripe to process payment
        System.out.println();
    }
}
