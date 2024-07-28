import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Order {
    int orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderID: " + orderId + ", Customer: " + customerName + ", Total Price: $" + totalPrice;
    }
}

public class OrderSorting {
     public static void main(String[] args) {
        // Providing sample test data

        Order[] orders = new Order[5];
        orders[0] = new Order(1, "Alice", 250.50);
        orders[1] = new Order(2, "Bob", 150.75);
        orders[2] = new Order(3, "Charlie", 350.00);
        orders[3] = new Order(4, "David", 200.25);
        orders[4] = new Order(5, "Eve", 300.10);

        // Display unsorted orders
        System.out.println("Unsorted Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }
        Random random=new Random();
        // Bubble Sort
        testSortingAlgorithm("Bubble Sort", orders, OrderSorting::bubbleSort);

        // Quick Sort
        testSortingAlgorithm("Quick Sort", orders, (Order[] arr) -> quickSort(arr, 0, arr.length - 1,random));

        // Insertion Sort
        testSortingAlgorithm("Insertion Sort", orders, OrderSorting::insertionSort);

        // Merge Sort
        testSortingAlgorithm("Merge Sort", orders, (Order[] arr) -> mergeSort(arr, 0, arr.length - 1));
    }

    // Method to test and display sorted orders
    public static void testSortingAlgorithm(String algorithmName, Order[] orders, SortingAlgorithm algorithm) {
        List<Order> orderList = Arrays.asList(orders);
        Collections.shuffle(orderList);
        Order[] shuffledOrders = orderList.toArray(new Order[0]);
        algorithm.sort(shuffledOrders);
        System.out.println("\nOrders Sorted by " + algorithmName + ":");
        for (Order order : shuffledOrders) {
            System.out.println(order);
        }
    }

    @FunctionalInterface
    interface SortingAlgorithm {
        void sort(Order[] orders);
    }


    public static void swap(Order[] orders, int i, int j) {
        Order temp = orders[i];
        orders[i] = orders[j];
        orders[j] = temp;
    }

    // Bubble Sort
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    // Swap orders[j] and orders[j + 1]
                    swap(orders,j,j+1);
                }
            }
        }
    }


    // Quick Sort
    public static void quickSort(Order[] orders, int l, int r,Random random) {
        if (l >= r)
            return;
       
        int position=partition(orders,l,r,random);
        // Recursively sort the partitions
        quickSort(orders, l, position - 1,random);
        quickSort(orders, position + 1, r,random);
    }

    public static int partition(Order[] orders,int l,int r,Random random){
        int i=l-1;
        int size=r-l+1;
        int pivotPosition=l+random.nextInt(size);   //get random pivot
        swap(orders, r, pivotPosition);             // send it to the rightmost boundery
        double pivot=orders[r].totalPrice;
        for(int j=l;j<r;j++){
            if(orders[j].totalPrice<pivot){ 
                swap(orders, ++i, j);
            }
        }
        ++i;
        swap(orders, r,i);  //set the pivot element to its corect place
        return i;
    }

    // Insertion Sort
    public static void insertionSort(Order[] orders) {
        int n = orders.length;
        for (int i = 1; i < n; ++i) {
            Order key = orders[i];
            int j = i - 1;

            // Move elements of orders[0..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= 0 && orders[j].totalPrice > key.totalPrice) {
                orders[j + 1] = orders[j];
                j = j - 1;
            }
            orders[j + 1] = key;
        }
    }

    // Merge Sort
    public static void mergeSort(Order[] orders, int l, int r) {
        if (l < r) {
            int m = l+(r-l)/2;

            // Sort first and second halves
            mergeSort(orders, l, m);
            mergeSort(orders, m + 1, r);

            // Merge the sorted halves
            merge(orders, l, m, r);
        }
    }

    public static void merge(Order[] orders, int l, int m, int r) {
        int size=r-l+1;
        // Create temp array
        Order[] temp=new Order[size];

        // Merge the temp arrays
        // Initial indexes of first and second subarrays
        int i = l, j = m+1;

        int k=0;    //points to current free position in the temp array
        for(;i<=m && j<=r;k++){
            if(orders[i].totalPrice<orders[j].totalPrice )
                temp[k]=orders[i++];
            else
                temp[k]=orders[j++];
        }

        for(;i<=m;i++){                 //copy left over
            temp[k++]=orders[i];
        }
        for(;j<=r;j++){                 //copy left over
            temp[k++]=orders[j];
        }

        for(i=0;i<size;i++)
            orders[l++]=temp[i];
    }
}
