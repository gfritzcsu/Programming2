package Programming2.Mod2CTOpt1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
 * A simple Bank Balance GUI application using Swing.
 * Components used: JPanel, JButton, ActionListener
 *  - Prompts the user for an initial balance
 *  - Displays current balance inside a JPanel
 *  - Allows Deposit and Withdraw via text input + buttons
 *  - Validates input (non-negative numbers; prevents overdraft)
 *  - Shows the remaining balance in a dialog before exiting (both via Exit button and window close)
 */
public class BankBalanceApp extends JFrame {
    private final BankAccount account;
    private final JLabel balanceLabel;     // Displayed inside a JPanel
    private final JTextField amountField;  // For entering deposit/withdraw amounts
    private final DecimalFormat moneyFmt = new DecimalFormat("$#,##0.00");

    public BankBalanceApp(double initialBalance) {
        super("Bank Balance App");
        this.account = new BankAccount(initialBalance);

        // Balance display panel (JPanel)
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        balancePanel.setBorder(BorderFactory.createTitledBorder("Current Balance"));
        balanceLabel = new JLabel(moneyFmt.format(account.getBalance()));
        balanceLabel.setFont(balanceLabel.getFont().deriveFont(Font.BOLD, 18f));
        balancePanel.add(balanceLabel);

        // Middle: Controls panel
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(12);
        amountField.setToolTipText("Enter a non-negative number (e.g., 25 or 25.50)");

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton exitBtn = new JButton("Exit");

        // Layout controls
        gbc.gridx = 0; gbc.gridy = 0; controlsPanel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; controlsPanel.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; controlsPanel.add(depositBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; controlsPanel.add(withdrawBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; controlsPanel.add(exitBtn, gbc);

        // Add panels to frame
        setLayout(new BorderLayout(10, 10));
        add(balancePanel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.CENTER);

        // ActionListeners
        depositBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { onDeposit(); }
        });
        withdrawBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { onWithdraw(); }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { onExit(); }
        });

        // Show remaining balance before closing via window
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                showRemainingAndExit();
            }
        });

        // Window setup
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
    }

    private void onDeposit() {
        Double amount = parseAmount();
        if (amount == null) return; // parse error already shown
        if (amount < 0) {
            showError("Amount must be non-negative.");
            return;
        }
        account.deposit(amount);
        updateBalance();
        amountField.setText("");
    }

    private void onWithdraw() {
        Double amount = parseAmount();
        if (amount == null) return;
        if (amount < 0) {
            showError("Amount must be non-negative.");
            return;
        }
        if (!account.canWithdraw(amount)) {
            showError("Insufficient funds. You cannot withdraw more than the current balance.");
            return;
        }
        account.withdraw(amount);
        updateBalance();
        amountField.setText("");
    }

    private void onExit() {
        showRemainingAndExit();
    }

    private void showRemainingAndExit() {
        String msg = "Remaining balance: " + moneyFmt.format(account.getBalance());
        JOptionPane.showMessageDialog(this, msg, "Goodbye", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        System.exit(0);
    }

    private void updateBalance() {
        balanceLabel.setText(moneyFmt.format(account.getBalance()));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private Double parseAmount() {
        String text = amountField.getText().trim();
        if (text.isEmpty()) {
            showError("Please enter an amount.");
            return null;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            showError("Invalid number. Use digits only, e.g., 25 or 25.50.");
            return null;
        }
    }

    // Simple BankAccount model
    private static class BankAccount {
        private double balance;
        public BankAccount(double initialBalance) { this.balance = Math.max(0, initialBalance); }
        public double getBalance() { return balance; }
        public void deposit(double amount) { balance += amount; }
        public boolean canWithdraw(double amount) { return amount <= balance; }
        public void withdraw(double amount) { balance -= amount; }
    }

    // App entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            double initialBalance = promptForInitialBalance();
            new BankBalanceApp(initialBalance).setVisible(true);
        });
    }

    // Prompt the user for an initial balance before showing the frame
    private static double promptForInitialBalance() {
        while (true) {
            String input = JOptionPane.showInputDialog(null,
                    "Enter initial account balance:",
                    "Starting Balance",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) { // user cancelled — default to 0
                return 0.0;
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    JOptionPane.showMessageDialog(null, "Balance cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number. Use digits only, e.g., 100 or 100.50.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

