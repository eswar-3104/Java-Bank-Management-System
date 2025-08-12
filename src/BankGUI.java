import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BankGUI extends JFrame {
    private BankService bankService;
    private JTabbedPane tabbedPane;
    
    // Create Account Panel Components
    private JTextField nameField;
    private JTextField initialDepositField;
    private JTextArea resultArea;
    
    // Transaction Panel Components
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextArea transactionResultArea;
    
    // View Accounts Panel Components
    private JTextArea accountsListArea;
    
    public BankGUI() {
        bankService = new BankService();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Create Account", createAccountPanel());
        tabbedPane.addTab("Transactions", createTransactionPanel());
        tabbedPane.addTab("View Accounts", createViewAccountsPanel());
        
        add(tabbedPane);
        
        // Add window closing listener
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                bankService.close();
                System.exit(0);
            }
        });
    }
    
    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Create New Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Account Holder Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Initial deposit field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Initial Deposit:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        initialDepositField = new JTextField(20);
        formPanel.add(initialDepositField, gbc);
        
        // Create button
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        JButton createButton = new JButton("Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.addActionListener(new CreateAccountListener());
        formPanel.add(createButton, gbc);
        
        // Result area
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Account Transactions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Account number field
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Account Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        accountNumberField = new JTextField(20);
        formPanel.add(accountNumberField, gbc);
        
        // Amount field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Amount:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        amountField = new JTextField(20);
        formPanel.add(amountField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        depositButton.addActionListener(new DepositListener());
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        withdrawButton.addActionListener(new WithdrawListener());
        
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setFont(new Font("Arial", Font.BOLD, 14));
        balanceButton.addActionListener(new BalanceListener());
        
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(balanceButton);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        // Result area
        transactionResultArea = new JTextArea(15, 40);
        transactionResultArea.setEditable(false);
        transactionResultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(transactionResultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction Results"));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createViewAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("All Accounts", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh Account List");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(new RefreshAccountsListener());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        
        // Accounts list area
        accountsListArea = new JTextArea(20, 60);
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
    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText().trim();
                String depositText = initialDepositField.getText().trim();
                
                if (name.isEmpty()) {
                    resultArea.setText("Error: Please enter account holder name.");
                    return;
                }
                
                double initialDeposit = 0;
                if (!depositText.isEmpty()) {
                    initialDeposit = Double.parseDouble(depositText);
                }
                
                if (initialDeposit < 0) {
                    resultArea.setText("Error: Initial deposit cannot be negative.");
                    return;
                }
                
                String accountNumber = bankService.createAccount(name, initialDeposit);
                
                if (accountNumber != null) {
            resultArea.setText("Account created successfully!\n\n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + name + "\n" +
                "Initial Balance: ₹" + String.format("%.2f", initialDeposit) + "\n\n" +
                "Please save your account number for future transactions.");
                    
                    // Clear fields
                    nameField.setText("");
                    initialDepositField.setText("");
                } else {
                    resultArea.setText("Error: Failed to create account. Please try again.");
                }
                
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Please enter a valid amount for initial deposit.");
            }
        }
    }
    
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
                
                double amount = Double.parseDouble(amountText);
                
                if (amount <= 0) {
                    transactionResultArea.setText("Error: Deposit amount must be positive.");
                    return;
                }
                
                if (bankService.deposit(accountNumber, amount)) {
                    BankAccount account = bankService.getAccountInfo(accountNumber);
            transactionResultArea.setText("Deposit successful!\n\n" +
                "Account: " + accountNumber + "\n" +
                "Deposited: ₹" + String.format("%.2f", amount) + "\n" +
                "New Balance: ₹" + String.format("%.2f", account.getBalance()));
                    
                    amountField.setText("");
                } else {
                    transactionResultArea.setText("Error: Deposit failed. Please check account number.");
                }
                
            } catch (NumberFormatException ex) {
                transactionResultArea.setText("Error: Please enter a valid amount.");
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
                
                double amount = Double.parseDouble(amountText);
                
                if (amount <= 0) {
                    transactionResultArea.setText("Error: Withdrawal amount must be positive.");
                    return;
                }
                
                if (bankService.withdraw(accountNumber, amount)) {
                    BankAccount account = bankService.getAccountInfo(accountNumber);
            transactionResultArea.setText("Withdrawal successful!\n\n" +
                "Account: " + accountNumber + "\n" +
                "Withdrawn: ₹" + String.format("%.2f", amount) + "\n" +
                "New Balance: ₹" + String.format("%.2f", account.getBalance()));
                    
                    amountField.setText("");
                } else {
                    transactionResultArea.setText("Error: Withdrawal failed. " +
                            "Please check account number and ensure sufficient balance.");
                }
                
            } catch (NumberFormatException ex) {
                transactionResultArea.setText("Error: Please enter a valid amount.");
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
            
            BankAccount account = bankService.getAccountInfo(accountNumber);
            
            if (account != null) {
        transactionResultArea.setText("Account Information:\n\n" +
            "Account Number: " + account.getAccountNumber() + "\n" +
            "Account Holder: " + account.getAccountHolderName() + "\n" +
            "Current Balance: ₹" + String.format("%.2f", account.getBalance()));
            } else {
                transactionResultArea.setText("Error: Account not found. Please check account number.");
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
        List<BankAccount> accounts = bankService.getAllAccounts();
        
        if (accounts.isEmpty()) {
            accountsListArea.setText("No accounts found.\n\nCreate some accounts using the 'Create Account' tab.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Total Accounts: ").append(accounts.size()).append("\n\n");
            sb.append(String.format("%-12s %-25s %-15s%n", "Account No.", "Account Holder", "Balance"));
            sb.append("-".repeat(55)).append("\n");
            
            for (BankAccount account : accounts) {
                sb.append(String.format("%-12s %-25s $%-14.2f%n",
                        account.getAccountNumber(),
                        account.getAccountHolderName(),
                        account.getBalance()));
            }
            
            accountsListArea.setText(sb.toString());
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel if system L&F is not available
            e.printStackTrace();
        }
        
        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            new BankGUI().setVisible(true);
        });
    }
}
