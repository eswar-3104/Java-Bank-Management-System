import java.util.ArrayList;
import java.util.List;

public class SimpleBankService {
    private List<SimpleBankAccount> accounts;
    
    public SimpleBankService() {
        accounts = new ArrayList<>();
        initializeAccounts();
    }
    
    // Initialize with 5 predefined accounts
    private void initializeAccounts() {
        accounts.add(new SimpleBankAccount("12345678", "John Smith", 15000.00));
        accounts.add(new SimpleBankAccount("87654321", "Mary Johnson", 23000.50));
        accounts.add(new SimpleBankAccount("11111111", "David Brown", 8000.75));
        accounts.add(new SimpleBankAccount("22222222", "Sarah Wilson", 32000.00));
        accounts.add(new SimpleBankAccount("33333333", "Michael Davis", 9500.25));
    }
    
    // Find account by account number
    public SimpleBankAccount findAccount(String accountNumber) {
        for (SimpleBankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    // Get all accounts
    public List<SimpleBankAccount> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
    
    // Deposit money
    public boolean deposit(String accountNumber, double amount) {
        SimpleBankAccount account = findAccount(accountNumber);
        if (account != null && amount > 0) {
            return account.deposit(amount);
        }
        return false;
    }
    
    // Withdraw money
    public boolean withdraw(String accountNumber, double amount) {
        SimpleBankAccount account = findAccount(accountNumber);
        if (account != null && amount > 0 && account.hasSufficientBalance(amount)) {
            return account.withdraw(amount);
        }
        return false;
    }
    
    // Check balance
    public double checkBalance(String accountNumber) {
        SimpleBankAccount account = findAccount(accountNumber);
        return account != null ? account.getBalance() : -1;
    }
    
    // Check if account exists
    public boolean accountExists(String accountNumber) {
        return findAccount(accountNumber) != null;
    }
}
