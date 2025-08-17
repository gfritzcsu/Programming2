package Programming2.Mod1CTOpt1;

import java.util.Scanner;

/**
 * bankTest
 * - Step 1: Create a BankAccount by entering first/last name (validated).
 *  Account ID is auto-assigned by BankAccount class.
 * - Step 2: Create a CheckingAccount using the SAME name as the BankAccount.
 *  User only enters the interest rate, deposit, and withdrawal amounts.
 *  Deposit must be a positive integer.
 *  Withdrawal allows overdraft and applies a $30 fee if insufficient funds.
 * 
 */
public class bankTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------------- Step 1: Open a BankAccount ----------------
        System.out.println("Open a BankAccount");

        // Prompt user for first/last name with validation
        String baFirst = readName(sc, "Enter first name: ");
        String baLast  = readName(sc, "Enter last name: ");

        // Create BankAccount (ID auto-assigned, balance = $0.00 initially)
        bankAccount bankAccount = new bankAccount(baFirst, baLast);

        // Print account summary (shows name and assigned ID)
        System.out.println();
        bankAccount.accountSummary();

        // ---------------- Step 2: Open a CheckingAccount ----------------
        System.out.println("\nOpen a CheckingAccount");

        // Reuse the SAME names from BankAccount (no second name prompt)
        String caFirst = bankAccount.getFirstName();
        String caLast  = bankAccount.getLastName();

        // Ask user for interest rate (0.1% to 5.0%, decimals allowed)
        double rate = readPercentInRange(sc,
                "Enter interest rate (0.1% to 5.0%): ",
                0.1, 5.0);

        // Create CheckingAccount using same owner name
        checkingAccount checking = new checkingAccount(caFirst, caLast, rate);

        // Prompt for deposit (must be a positive integer)
        int caDeposit = readPositiveInt(sc, "Enter deposit amount for CheckingAccount: ");
        checking.deposit(caDeposit);

        // Prompt for withdrawal (positive double, overdraft allowed with $30 fee warning)
        double caWithdraw = readPositiveDouble(sc,
                "Enter withdrawal amount for CheckingAccount (overdraft allowed, $30 fee if insufficient funds): ");
        checking.processWithdrawal(caWithdraw);

        // Show explicit getter usage (rubric requires getter demonstration)
        System.out.println("\nChecking Account Holder (via getters): "
                + checking.getFirstName() + " " + checking.getLastName());
        System.out.println("Checking Account ID (via getter): " + checking.getAccountID());
        System.out.println("Checking Account Balance (via getter): " + checking.getBalance());

        // Print full CheckingAccount summary (includes interest rate)
        System.out.println();
        checking.displayAccount();

        sc.close();
    }

    // ---------- Helper input methods with validation ----------

    /**
     * Reads a name string that must contain only letters, hyphens, or spaces.
     * Rejects numbers, punctuation, and empty input.
     */
    private static String readName(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty() && s.matches("[a-zA-Z\\- ]+")) {
                return s;
            }
            System.out.println("Names can only contain letters, hyphens, and spaces. Please try again.");
        }
    }

    /**
     * Reads a positive decimal value (e.g., 125.50).
     */
    private static double readPositiveDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= 0) return v;
                System.out.println("Value must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    /**
     * Reads a positive integer value (e.g., 50).
     * Used for deposit amounts.
     */
    private static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= 0) return v;
                System.out.println("Value must be a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    /**
     * Reads a percentage value between min and max (inclusive).
     * Example: min=0.1, max=5.0 and will accept 0.1, 1.5, 5.0 etc.
     */
    private static double readPercentInRange(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
                System.out.printf("Please enter a value between %.1f and %.1f%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

}