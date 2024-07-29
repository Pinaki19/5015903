import java.util.Map;
import java.util.HashMap;

public class FinancialForecasting {

    private static Map<Integer, Double> memo = new HashMap<>();

    // Recursive method with memoization
    public static double calculateMultiplierMemoized(double rate, int years) {
        if (memo.containsKey(years)) {
            return memo.get(years);
        }

        // Base case: No more years to calculate
        if (years == 0) {
            return 1.0;
        }

        // Recursive case with memoization
        double result = (1 + rate) * calculateMultiplierMemoized(rate, years - 1);
        memo.put(years, result);
        return result;
    }

    // Separate function to calculate future value
    public static double calculatefutureValueLogn(double principal, double rate, int years) {
        double multiplier = calculateMultiplierMemoized(rate, years);
        return principal * multiplier;
    }

    // Recursive function to calculate (1 + rate)^years in O(log n) time
    public static double calculateMultiplierLogN(double rate, int years) {
        if (years == 0) {
            return 1.0;
        }
        
        double half = calculateMultiplierLogN(rate, years / 2);
        
        if (years % 2 == 0) {
            return half * half;
        } else {
            return (1 + rate) * half * half;
        }
    }

    // Separate function to calculate future value
    public static double calculateFutureValueLogN(double principal, double rate, int years) {
        double multiplier = calculateMultiplierLogN(rate, years);
        return principal * multiplier;
    }

    public static double calculateFutureValue(double principal, double rate, int years) {
        if(years==0){
            return principal;
        }
        return calculateFutureValue(principal*(1+rate),rate,years-1);
    }


    public static void main(String[] args) {
        // Hardcoded test values
        double principal = 1000.0;
        double rate = 5.0/100;
        int years = 10;

        System.out.println("Initial Amount: " + principal);
        System.out.println("Annual Growth Rate: " + rate);
        System.out.println("Number of Years: " + years);

        // Calculate the future value using the separate function
        double futureValueRecursive = calculateFutureValue(principal, rate, years);
        double futureValueLogn = calculateFutureValueLogN(principal, rate, years);
       
        System.out.printf("Future Value for %.2f is: \nlinear Recursion: %.2f%nExponential squaring: %.2f%n",principal, futureValueRecursive,futureValueLogn);
        System.out.println();
    }
}

 
