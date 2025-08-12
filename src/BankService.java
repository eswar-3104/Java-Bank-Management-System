import java.util.List;
import java.util.Random;

public class BankService {
    private final DatabaseManager dbManager;
    private final Random random;
    
    public BankService() {
        this.dbManager = new DatabaseManager();
        this.random = new Random();
    }
    
    // Generate unique account number
    private String generateAccountNumber() {
        String accountNumber;
        do {
            // Generate 8-digit account number
            accountNumber = String.format("%08d", random.nextInt(100000000));
        } while (dbManager.accountExists(accountNumber));
        
        return accountNumber;
    }
    
    // Create new account
    public String createAccount(String accountHolderName, double initialDeposit) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            return null; // Invalid name
        }
        
        if (initialDeposit < 0) {
            return null; // Invalid initial deposit
        }
        
        String accountNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(accountNumber, accountHolderName.trim(), initialDeposit);
        
        if (dbManager.createAccount(newAccount)) {
            return accountNumber;
        }
        
        return null;
    }
    
    // Get account information
    public BankAccount getAccountInfo(String accountNumber) {
        return dbManager.getAccount(accountNumber);
    }
    
    // Deposit money
    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        BankAccount account = dbManager.getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            return dbManager.updateBalance(accountNumber, account.getBalance());
        }
        
        return false;
    }
    
    // Withdraw money
    public boolean withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        BankAccount account = dbManager.getAccount(accountNumber);
        if (account != null && account.hasSufficientBalance(amount)) {
            account.withdraw(amount);
            return dbManager.updateBalance(accountNumber, account.getBalance());
        }
        
        return false;
    }
    
    // Check balance
    public double checkBalance(String accountNumber) {
        BankAccount account = dbManager.getAccount(accountNumber);
        return account != null ? account.getBalance() : -1;
    }
    
    // Get all accounts
    public List<BankAccount> getAllAccounts() {
        return dbManager.getAllAccounts();
    }
    
    // Check if account exists
    public boolean accountExists(String accountNumber) {
        return dbManager.accountExists(accountNumber);
    }
    
    // Close service (cleanup)
    public void close() {
        dbManager.closeConnection();
    }
}
