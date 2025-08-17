package Programming2.Mod1CTOpt1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * bankAccount (Superclass)
 * - Make account with name of person
 * - Account ID is auto-assigned
 */
public class bankAccount {
    private String firstName;
    private String lastName;
    private int accountID;
    private double balance;

    private static final AtomicInteger NEXT_ID = new AtomicInteger(1000);

    /** Constructor: user provides names; bank assigns account ID; balance starts at 0. */
    public bankAccount(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        this.accountID = NEXT_ID.getAndIncrement();
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Deposit amount must be positiveor equal to zero if nothing is to be deposited at this time.");
            return;
        }
        setBalance(getBalance() + amount);
    }

    public void withdrawal(double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount must be positiveor equal to zero if nothing is to be withdrawn at this time.");
            return;
        }
        if (amount > getBalance()) {
            System.out.println("Insufficient funds for this withdrawal.");
            return;
        }
        setBalance(getBalance() - amount);
    }

    // ----- Setters / Getters -----
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName  = lastName; }
    public void setAccountID(int accountID)    { this.accountID = accountID; }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int getAccountID()    { return accountID; }
    public double getBalance()   { return balance; }

    protected void setBalance(double newBalance) { this.balance = newBalance; }

    /** Print account details (excluding balance at open). */
    public void accountSummary() {
        System.out.println("Account Summary:");
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Account ID: " + getAccountID());
    }
}