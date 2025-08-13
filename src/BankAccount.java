public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String phoneNumber;
    private String email;
    private String city;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.phoneNumber = "";
        this.email = "";
        this.city = "";
    }
    
    // Constructor with all fields
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, 
                      String phoneNumber, String email, String city) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
        this.email = email != null ? email : "";
        this.city = city != null ? city : "";
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
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getCity() {
        return city;
    }
    
    // Setters
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
    }
    
    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }
    
    public void setCity(String city) {
        this.city = city != null ? city : "";
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
        return "Account: " + accountNumber + " | Name: " + accountHolderName + 
               " | Phone: " + phoneNumber + " | Email: " + email + 
               " | City: " + city + " | Balance: ₹" + String.format("%.2f", balance);
    }
}
