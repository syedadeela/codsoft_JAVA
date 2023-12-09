import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true; // Successful withdrawal
        } else {
            return false; // Insufficient funds
        }
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return bankAccount.withdraw(amount);
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }
}

class ATMGUI extends JFrame {
    private JTextField amountField;
    private JTextArea outputArea;
    private ATM atm;

    public ATMGUI(ATM atm) {
        this.atm = atm;

        setTitle("ATM Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        amountField = new JTextField(10);
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(createButton("Deposit", e -> deposit()));
        inputPanel.add(createButton("Withdraw", e -> withdraw()));
        inputPanel.add(createButton("Check Balance", e -> checkBalance()));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    private void deposit() {
        double amount = getAmount();
        atm.deposit(amount);
        displayMessage("Deposited: $" + amount);
    }

    private void withdraw() {
        double amount = getAmount();
        boolean success = atm.withdraw(amount);

        if (success) {
            displayMessage("Withdrawn: $" + amount);
        } else {
            displayMessage("Insufficient funds for withdrawal.");
        }
    }

    private void checkBalance() {
        double balance = atm.checkBalance();
        displayMessage("Current Balance: $" + balance);
    }

    private double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
            return 0;
        }
    }

    private void displayMessage(String message) {
        outputArea.append(message + "\n");
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000);
        ATM atm = new ATM(bankAccount);
        SwingUtilities.invokeLater(() -> new ATMGUI(atm));
    }
}
