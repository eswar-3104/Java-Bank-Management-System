import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SimpleBankGUI extends JFrame {
    private SimpleBankService bankService;
    private JTabbedPane tabbedPane;
    
    // Transaction Panel Components
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextArea transactionResultArea;
    
    // View Accounts Panel Components
    private JTextArea accountsListArea;
    
    public SimpleBankGUI() {
        bankService = new SimpleBankService();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Simple Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add tabs
        tabbedPane.addTab("Transactions", createTransactionPanel());
        tabbedPane.addTab("View All Accounts", createViewAccountsPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Title
        JLabel titleLabel = new JLabel("Account Transactions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // Input form panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
        
        // Account number row
        JPanel accountRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        accountLabel.setPreferredSize(new Dimension(120, 25));
        accountNumberField = new JTextField(15);
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        accountRow.add(accountLabel);
        accountRow.add(accountNumberField);
        
        // Amount row
        JPanel amountRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setPreferredSize(new Dimension(120, 25));
        amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountRow.add(amountLabel);
        amountRow.add(amountField);
        
        inputPanel.add(accountRow);
        inputPanel.add(amountRow);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        depositButton.setPreferredSize(new Dimension(120, 35));
        depositButton.setBackground(new Color(76, 175, 80));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.addActionListener(new DepositListener());
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        withdrawButton.setPreferredSize(new Dimension(120, 35));
        withdrawButton.setBackground(new Color(244, 67, 54));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        withdrawButton.addActionListener(new WithdrawListener());
        
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setFont(new Font("Arial", Font.BOLD, 14));
        balanceButton.setPreferredSize(new Dimension(120, 35));
        balanceButton.setBackground(new Color(33, 150, 243));
        balanceButton.setForeground(Color.WHITE);
        balanceButton.setFocusPainted(false);
        balanceButton.addActionListener(new BalanceListener());
        
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(balanceButton);
        
        // Available accounts info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Available Account Numbers"));
        
        JLabel infoLabel = new JLabel("<html>" +
                "<table style='font-family: Arial; font-size: 12px;'>" +
                "<tr><td><b>Account No.</b></td><td><b>Account Holder</b></td></tr>" +
                "<tr><td>12345678</td><td>John Smith</td></tr>" +
                "<tr><td>87654321</td><td>Mary Johnson</td></tr>" +
                "<tr><td>11111111</td><td>David Brown</td></tr>" +
                "<tr><td>22222222</td><td>Sarah Wilson</td></tr>" +
                "<tr><td>33333333</td><td>Michael Davis</td></tr>" +
                "</table></html>");
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        // Top panel combining input and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(infoPanel, BorderLayout.SOUTH);
        
        contentPanel.add(topPanel, BorderLayout.NORTH);
        
        // Result area
        transactionResultArea = new JTextArea(10, 50);
        transactionResultArea.setEditable(false);
        transactionResultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        transactionResultArea.setText("Welcome to Simple Bank Management System!\n\n" +
                "Instructions:\n" +
                "1. Enter an account number from the list above\n" +
                "2. Enter amount for deposit/withdrawal\n" +
                "3. Click the desired operation button\n\n" +
                "Ready for transactions...");
        
        JScrollPane scrollPane = new JScrollPane(transactionResultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction Results"));
        scrollPane.setPreferredSize(new Dimension(0, 200));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createViewAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("All Bank Accounts", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh Account List");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(156, 39, 176));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(new RefreshAccountsListener());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        
        // Accounts list area
        accountsListArea = new JTextArea(18, 60);
        accountsListArea.setEditable(false);
        accountsListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(accountsListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Accounts List"));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        // Load accounts initially
        refreshAccountsList();
        
        return panel;
    }
    
    // Event Listeners
    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String accountNumber = accountNumberField.getText().trim();
                String amountText = amountField.getText().trim();
                
                if (accountNumber.isEmpty() || amountText.isEmpty()) {
                    transactionResultArea.setText("Error: Please enter both account number and amount.");
                    return;
                }
                
                if (!bankService.accountExists(accountNumber)) {
                    transactionResultArea.setText("Error: Account number not found.\n" +
                            "Please use one of the available account numbers listed above.");
                    return;
                }
                
                double amount = Double.parseDouble(amountText);
                
                if (amount <= 0) {
                    transactionResultArea.setText("Error: Deposit amount must be positive.");
                    return;
                }
                
                if (bankService.deposit(accountNumber, amount)) {
                    SimpleBankAccount account = bankService.findAccount(accountNumber);
                    transactionResultArea.setText("✓ DEPOSIT SUCCESSFUL!\n\n" +
                            "Account Number: " + accountNumber + "\n" +
                            "Account Holder: " + account.getAccountHolderName() + "\n" +
                            "Deposited Amount: ₹" + String.format("%.2f", amount) + "\n" +
                            "New Balance: ₹" + String.format("%.2f", account.getBalance()) + "\n\n" +
                            "Transaction completed at " + java.time.LocalDateTime.now().format(
                                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    
                    amountField.setText("");
                } else {
                    transactionResultArea.setText("Error: Deposit failed. Please try again.");
                }
                
            } catch (NumberFormatException ex) {
                transactionResultArea.setText("Error: Please enter a valid numeric amount.");
            }
        }
    }
    
    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String accountNumber = accountNumberField.getText().trim();
                String amountText = amountField.getText().trim();
                
                if (accountNumber.isEmpty() || amountText.isEmpty()) {
                    transactionResultArea.setText("Error: Please enter both account number and amount.");
                    return;
                }
                
                if (!bankService.accountExists(accountNumber)) {
                    transactionResultArea.setText("Error: Account number not found.\n" +
                            "Please use one of the available account numbers listed above.");
                    return;
                }
                
                double amount = Double.parseDouble(amountText);
                
                if (amount <= 0) {
                    transactionResultArea.setText("Error: Withdrawal amount must be positive.");
                    return;
                }
                
                SimpleBankAccount account = bankService.findAccount(accountNumber);
                if (!account.hasSufficientBalance(amount)) {
                    transactionResultArea.setText("Error: Insufficient balance!\n\n" +
                            "Current Balance: ₹" + String.format("%.2f", account.getBalance()) + "\n" +
                            "Requested Amount: ₹" + String.format("%.2f", amount) + "\n" +
                            "Shortfall: ₹" + String.format("%.2f", amount - account.getBalance()));
                    return;
                }
                
                if (bankService.withdraw(accountNumber, amount)) {
                    transactionResultArea.setText("✓ WITHDRAWAL SUCCESSFUL!\n\n" +
                            "Account Number: " + accountNumber + "\n" +
                            "Account Holder: " + account.getAccountHolderName() + "\n" +
                            "Withdrawn Amount: ₹" + String.format("%.2f", amount) + "\n" +
                            "Remaining Balance: ₹" + String.format("%.2f", account.getBalance()) + "\n\n" +
                            "Transaction completed at " + java.time.LocalDateTime.now().format(
                                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    
                    amountField.setText("");
                } else {
                    transactionResultArea.setText("Error: Withdrawal failed. Please try again.");
                }
                
            } catch (NumberFormatException ex) {
                transactionResultArea.setText("Error: Please enter a valid numeric amount.");
            }
        }
    }
    
    private class BalanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText().trim();
            
            if (accountNumber.isEmpty()) {
                transactionResultArea.setText("Error: Please enter account number.");
                return;
            }
            
            SimpleBankAccount account = bankService.findAccount(accountNumber);
            
            if (account != null) {
                transactionResultArea.setText("ACCOUNT INFORMATION\n\n" +
                        "Account Number: " + account.getAccountNumber() + "\n" +
                        "Account Holder: " + account.getAccountHolderName() + "\n" +
                        "Current Balance: ₹" + String.format("%.2f", account.getBalance()) + "\n\n" +
                        "Balance checked at " + java.time.LocalDateTime.now().format(
                                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                transactionResultArea.setText("Error: Account not found.\n" +
                        "Please use one of the available account numbers listed above.");
            }
        }
    }
    
    private class RefreshAccountsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshAccountsList();
        }
    }
    
    private void refreshAccountsList() {
        List<SimpleBankAccount> accounts = bankService.getAllAccounts();
        
        StringBuilder sb = new StringBuilder();
        sb.append("SIMPLE BANK - ALL ACCOUNTS\n");
        sb.append("=".repeat(60)).append("\n\n");
        sb.append("Total Accounts: ").append(accounts.size()).append("\n\n");
        
        sb.append(String.format("%-12s %-20s %-15s%n", "Account No.", "Account Holder", "Balance"));
        sb.append("-".repeat(60)).append("\n");
        
        double totalBalance = 0;
        for (SimpleBankAccount account : accounts) {
            sb.append(String.format("%-12s %-20s ₹%-14.2f%n",
                    account.getAccountNumber(),
                    account.getAccountHolderName(),
                    account.getBalance()));
            totalBalance += account.getBalance();
        }
        
        sb.append("-".repeat(60)).append("\n");
        sb.append(String.format("%-32s ₹%-14.2f%n", "TOTAL BANK BALANCE:", totalBalance));
        sb.append("\n\nLast Updated: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        accountsListArea.setText(sb.toString());
    }
    
    public static void main(String[] args) {
        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            new SimpleBankGUI().setVisible(true);
        });
    }
}
