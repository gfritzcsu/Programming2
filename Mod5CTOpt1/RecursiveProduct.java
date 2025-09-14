import java.util.Scanner;

public class RecursiveProduct {

    // Recursive method to read n numbers and return their product
    public static double productOfNumbers(Scanner sc, int count) {
        if (count == 0) {
            return 1.0;
        }

        double num = getValidDouble(sc);  // read a validated double
        return num * productOfNumbers(sc, count - 1);
    }

    // Method that keeps asking until a valid double is entered
    private static double getValidDouble(Scanner sc) {
        while (true) {
            System.out.print("Enter a number (positive, negative, or decimals numbers accepted): ");
            String input = sc.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalNumbers = 5;
        System.out.println("Please enter five number that you want to compute the product of recursively.");

        // Start the recursive process and get final product
        double product = productOfNumbers(sc, totalNumbers);
        System.out.println("The product of the 5 numbers given is: " + product);

        sc.close();
    }
}