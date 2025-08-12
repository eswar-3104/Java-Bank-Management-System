import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankdb";
    private static final String USER = "root";
    private static final String PASS = "Somu**7898";
    private Connection connection;
    
    // Constructor - Initialize database
    public DatabaseManager() {
        initializeDatabase();
    }
    
    // Initialize database and create table if not exists
    private void initializeDatabase() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Create accounts table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts ("
                + "account_number VARCHAR(20) PRIMARY KEY,"
                + "account_holder_name VARCHAR(100),"
                + "balance DOUBLE)";
            
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            statement.close();
            
            System.out.println("Database initialized successfully!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    // Create a new account
    public boolean createAccount(BankAccount account) {
        String insertSQL = "INSERT INTO accounts (account_number, account_holder_name, balance) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setString(2, account.getAccountHolderName());
            pstmt.setDouble(3, account.getBalance());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }
    
    // Get account by account number
    public BankAccount getAccount(String accountNumber) {
        String selectSQL = "SELECT * FROM accounts WHERE account_number = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new BankAccount(
                    rs.getString("account_number"),
                    rs.getString("account_holder_name"),
                    rs.getDouble("balance")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving account: " + e.getMessage());
        }
        
        return null;
    }
    
    // Update account balance
    public boolean updateBalance(String accountNumber, double newBalance) {
        String updateSQL = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }
    
    // Get all accounts
    public List<BankAccount> getAllAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        String selectSQL = "SELECT * FROM accounts ORDER BY account_number";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                BankAccount account = new BankAccount(
                    rs.getString("account_number"),
                    rs.getString("account_holder_name"),
                    rs.getDouble("balance")
                );
                accounts.add(account);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving all accounts: " + e.getMessage());
        }
        
        return accounts;
    }
    
    // Check if account exists
    public boolean accountExists(String accountNumber) {
        String selectSQL = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking account existence: " + e.getMessage());
        }
        
        return false;
    }
    
    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
