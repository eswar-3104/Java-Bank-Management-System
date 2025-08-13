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
        return createAccount(accountHolderName, initialDeposit, "", "", "");
    }
    
    // Create new account with all details
    public String createAccount(String accountHolderName, double initialDeposit, 
                               String phoneNumber, String email, String city) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            return null; // Invalid name
        }
        
        if (initialDeposit < 0) {
            return null; // Invalid initial deposit
        }
        
        String accountNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(accountNumber, accountHolderName.trim(), 
                                               initialDeposit, phoneNumber, email, city);
        
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
    
    // Delete account
    public boolean deleteAccount(String accountNumber) {
        BankAccount account = dbManager.getAccount(accountNumber);
        if (account != null) {
            return dbManager.deleteAccount(accountNumber);
        }
        return false;
    }
    
    // Transfer money between accounts
    public boolean transferMoney(String fromAccountNumber, String toAccountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        if (fromAccountNumber.equals(toAccountNumber)) {
            return false; // Cannot transfer to same account
        }
        
        BankAccount fromAccount = dbManager.getAccount(fromAccountNumber);
        BankAccount toAccount = dbManager.getAccount(toAccountNumber);
        
        if (fromAccount != null && toAccount != null && fromAccount.hasSufficientBalance(amount)) {
            // Withdraw from source account
            fromAccount.withdraw(amount);
            // Deposit to destination account
            toAccount.deposit(amount);
            
            // Update both accounts in database
            boolean updateFrom = dbManager.updateBalance(fromAccountNumber, fromAccount.getBalance());
            boolean updateTo = dbManager.updateBalance(toAccountNumber, toAccount.getBalance());
            
            return updateFrom && updateTo;
        }
        
        return false;
    }
    
    // Close service (cleanup)
    public void close() {
        dbManager.closeConnection();
    }
}
