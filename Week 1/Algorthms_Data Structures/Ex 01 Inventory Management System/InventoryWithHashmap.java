import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryWithHashmap {
    private Map<Integer, Product> productsMap;

    public InventoryWithHashmap() {
        productsMap = new HashMap<>();
    }

    // Method to add a product
    public boolean addProduct(Product product) {
        if(productsMap.containsKey(product.getProductId())){
            System.out.println("Product Ids whould be unique! ");
            return false;
        }
        productsMap.put(product.getProductId(), product);
        return true;
    }

    // Method to update a product
    public boolean updateProduct(int productId, String productName, int quantity, double price) {
        if(!productsMap.containsKey(productId)) //no product of given ID exists
        {   
            System.out.println("Invalid Product ID : " + productId);
            return false;
        }
        Product product = productsMap.get(productId);
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setPrice(price);
        System.out.println("Product with ID : " + productId + " Updated");
        return true;
    }

    // Method to delete a product
    public boolean deleteProduct(int productId) {
        if (productsMap.remove(productId) != null) {
            System.out.println("Product with ID : " + productId + " Removed");
            return true;
        } else {
            System.out.println("Invalid Product ID : " + productId);
            return false;
        }
    }

    // Method to display all products
    public void displayProducts() {
        for (Product product : productsMap.values()) {  //for each over map's values
            System.out.println(product);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        InventoryWithHashmap inventory = new InventoryWithHashmap();

        int productId;
        String productName;
        int quantity;
        double price;

        // Adding Product
        char addMore = 'y';
        while (addMore == 'y') {
            System.out.println("Enter the Product Details");
            System.out.print("Product ID: ");
            productId = sc.nextInt();
            sc.nextLine();
            System.out.print("Product Name: ");
            productName = sc.nextLine();
            System.out.print("Quantity: ");
            quantity = sc.nextInt();
            System.out.print("Price: ");
            price = sc.nextDouble();
            Product p = new Product(productId, productName, quantity, price);
            inventory.addProduct(p);

            System.out.print("Want to add another product [y/n]: ");
            addMore = sc.next().charAt(0);
            System.out.println();
        }

        // Displaying products
        System.out.println("Products in inventory:");
        inventory.displayProducts();

        // Updating a product
        System.out.print("Enter Product ID to update: ");
        productId = sc.nextInt();
        inventory.updateProduct(productId, "Gaming Laptop", 5, 95000.00);

        // Displaying products after update
        System.out.println("Products in inventory after update:");
        inventory.displayProducts();

        // Deleting a product
        System.out.print("Enter Product ID to delete: ");
        productId = sc.nextInt();
        inventory.deleteProduct(productId);

        // Displaying products after deletion
        System.out.println("Products in inventory after deletion:");
        inventory.displayProducts();

        sc.close();
    }
}