package Programming2.Mod1CTOpt1;

import java.text.NumberFormat;

/**
 * checkingAccount (Subclass)
 * - Adds interestRate (percent, e.g., 1.5 for 1.5%)
 * - Allows overdraft and applies a $30 fee
 */
public class checkingAccount extends bankAccount {
    private double interestRate; // percent (0.1 to 5.0 inclusive)
    private static final double OVERDRAFT_FEE = 30.0;
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

    /** Constructor: names pass to BankAccount; ID auto-assigned by superclass. */
    public checkingAccount(String firstName, String lastName, double interestRate) {
        super(firstName, lastName);
        this.interestRate = interestRate;
    }

    /** Overdraft-enabled withdrawal with $30 fee if amount exceeds balance. */
    public void processWithdrawal(double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount must be positive or equal to zero if nothing is to be withdrawn at this time.");
            return;
        }
        double current = getBalance();
        if (amount <= current) {
            super.withdrawal(amount);
        } else {
            double newBalance = current - amount - OVERDRAFT_FEE;
            setBalance(newBalance);
            System.out.println("Overdraft! A $" + OVERDRAFT_FEE + " fee has been applied.");
            System.out.println("Withdrew: " + CURRENCY.format(amount)
                    + " | New Balance (overdraft): " + CURRENCY.format(getBalance()));
        }
    }

    /** Display superclass attributes + interest rate. */
    public void displayAccount() {
        super.accountSummary();
        System.out.printf("Interest Rate: %.2f%%%n", interestRate);
    }

    // Optional getters/setters for interest if needed elsewhere
    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
}