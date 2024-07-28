import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class InventoryWithList 
{
    private List<Product> products;
    private Set<Integer> productIds;   
    // ProductIds set will be used to track the id's that have been used,
    // it is useful in preventing use of same id, with a different product.
    // we can also check the procuts list to check if Id already exists, 
    // but that will be O(n) in worst case, unless products is sorted by id,
    // which would make product insertion bit more time consuming


    public InventoryWithList() 
    {
        products = new ArrayList<>();
        productIds=new HashSet<>();
    }

    // Method to add a product
    public boolean addProduct(Product product) 
    {
        if(productIds.contains(product.getProductId())){
            System.out.println("Product with specified id exists! id should be unique!");
            return false;
        }else{
            productIds.add(product.getProductId());
            products.add(product);
            System.out.println("Product with Id: "+product.getProductId()+" added.");
            return true;
        }
    }

    // Method to update a product
    public boolean updateProduct(int productId, String productName, int quantity, double price) 
    {
        for (Product product : products)    // using for-each loop
        {
            if (product.getProductId() == productId) 
            {
                product.setProductName(productName);
                product.setQuantity(quantity);
                product.setPrice(price);
                System.out.println("Product with ID : " + productId + " Updated");
                return true;
            }
        }
        //product was not found
        System.out.println("Invalid Product ID : " + productId);
        return false;
    }

    // Method to delete a product
    public boolean deleteProduct(int productId) 
    {
        for (Product product : products)    // using for-each loop
        {
            if (product.getProductId() == productId)
            {
                products.remove(product);
                System.out.println("Product with ID : " + productId + " Removed");
                return true;
            }
        }
        System.out.println("Invalid Product ID : " + productId);
        return false;
    }

    // Method to display all products
    public void displayProducts() 
    {
        for (Product product : products) 
        {
            System.out.println(product);    //calls the product's toString() method
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryWithList inventory = new InventoryWithList();
        
        int productId;
        String productName;
        int quantity;
        double price;

        // Adding Product
        char addMore = 'y';
        while (addMore == 'y')
        {
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

            inventory.addProduct(p);    // we can check return value to see if it was added

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
        // provide new product details, or take inputs
        inventory.updateProduct(productId, "Realme narzo", 5, 25000.00);

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
