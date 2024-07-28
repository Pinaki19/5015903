public class Main {
    public static void main(String[] args) {

        System.out.println();

        // Create a StockMarket instance
        StockMarket stockMarket = new StockMarket("Nvidia ", 100.0);

        // Create observer instances
        MobileApp mobileApp = new MobileApp("MobileApp");
        WebApp webApp = new WebApp("WebApp");

        // Register observers
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        // Change stock price and notify observers
        System.out.println("Updating stock price...");
        stockMarket.setStockPrice(178.0);

        // Deregister an observer
        stockMarket.deregisterObserver(webApp);

        // Change stock price and notify observers
        System.out.println("Updating stock price again...");
        stockMarket.setStockPrice(240.0);

        System.out.println();
    }
}
