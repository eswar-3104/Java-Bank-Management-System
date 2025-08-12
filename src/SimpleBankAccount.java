public class SimpleBankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    
    // Constructor
    public SimpleBankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // Deposit method
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
    
    // Withdraw method
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    // Check if sufficient balance for withdrawal
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }
    
    @Override
    public String toString() {
        return "Account: " + accountNumber + " | Name: " + accountHolderName + " | Balance: ₹" + String.format("%.2f", balance);
    }
}
