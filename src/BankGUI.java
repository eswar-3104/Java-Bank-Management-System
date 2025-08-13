import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class BankGUI extends JFrame {
    private BankService bankService;
    private JTabbedPane tabbedPane;
    
    // Create Account Panel Components
    private JTextField nameField;
    private JTextField initialDepositField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField cityField;
    private JTextField deleteAccountField;
    private JTextArea resultArea;
    
    // Transaction Panel Components
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextArea transactionResultArea;
    
    // View Accounts Panel Components
    private JTextArea accountsListArea;
    
    // Transfer Money Panel Components
    private JTextField fromAccountField;
    private JTextField fromNameField;
    private JTextField toAccountField;
    private JTextField toNameField;
    private JTextField transferAmountField;
    private JTextArea transferResultArea;
    
    public BankGUI() {
        bankService = new BankService();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);  // Increased window size
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Bank Administration", createAccountPanel());
        tabbedPane.addTab("Transactions", createTransactionPanel());
        tabbedPane.addTab("Transfer Money", createTransferPanel());
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
        JLabel titleLabel = new JLabel("Bank Administration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create Account Section
        JPanel createAccountSection = new JPanel();
        createAccountSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Create New Account", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
        createAccountSection.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        createAccountSection.add(new JLabel("Account Holder Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(25);
        nameField.setForeground(Color.BLACK);
        nameField.setBackground(Color.WHITE);
        createAccountSection.add(nameField, gbc);
        
        // Phone field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        createAccountSection.add(new JLabel("Phone Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        phoneField = new JTextField(25);
        phoneField.setForeground(Color.BLACK);
        phoneField.setBackground(Color.WHITE);
        createAccountSection.add(phoneField, gbc);
        
        // Email field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        createAccountSection.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        emailField = new JTextField(25);
        emailField.setForeground(Color.BLACK);
        emailField.setBackground(Color.WHITE);
        createAccountSection.add(emailField, gbc);
        
        // City field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        createAccountSection.add(new JLabel("City:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cityField = new JTextField(25);
        cityField.setForeground(Color.BLACK);
        cityField.setBackground(Color.WHITE);
        createAccountSection.add(cityField, gbc);
        
        // Initial deposit field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        createAccountSection.add(new JLabel("Initial Deposit:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        initialDepositField = new JTextField(25);
        initialDepositField.setForeground(Color.BLACK);
        initialDepositField.setBackground(Color.WHITE);
        createAccountSection.add(initialDepositField, gbc);
        
        // Buttons panel for Create Account section
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);
        JPanel createButtonPanel = new JPanel(new FlowLayout());
        
        JButton clearButton = new JButton("Clear Fields");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(108, 117, 125));
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(new ClearFieldsListener());
        
        JButton createButton = new JButton("Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.BLACK);
        createButton.addActionListener(new CreateAccountListener());
        
        createButtonPanel.add(clearButton);
        createButtonPanel.add(createButton);
        createAccountSection.add(createButtonPanel, gbc);
        
        // Delete Account Section
        JPanel deleteAccountSection = new JPanel(new GridBagLayout());
        deleteAccountSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Delete Account", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);
        
        gbc2.gridx = 0; gbc2.gridy = 0; gbc2.anchor = GridBagConstraints.EAST;
        deleteAccountSection.add(new JLabel("Account Number to Delete:"), gbc2);
        
        gbc2.gridx = 1; gbc2.fill = GridBagConstraints.HORIZONTAL; gbc2.weightx = 1.0;
        deleteAccountField = new JTextField(25);
        deleteAccountField.setForeground(Color.BLACK);
        deleteAccountField.setBackground(Color.WHITE);
        deleteAccountSection.add(deleteAccountField, gbc2);
        
        // Delete button
        gbc2.gridx = 0; gbc2.gridy = 1; gbc2.gridwidth = 2; gbc2.fill = GridBagConstraints.NONE;
        gbc2.anchor = GridBagConstraints.CENTER; gbc2.insets = new Insets(20, 10, 10, 10);
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.addActionListener(new DeleteAccountListener());
        deleteAccountSection.add(deleteButton, gbc2);
        
        // Layout the sections
    JPanel sectionsPanel = new JPanel(new GridLayout(2, 1, 10, 20));
    sectionsPanel.add(createAccountSection);

    // Lower section: Delete Account form stacked above Result
    JPanel lowerStackPanel = new JPanel(new BorderLayout(10, 0));
    lowerStackPanel.add(deleteAccountSection, BorderLayout.NORTH);

    // Result area
    resultArea = new JTextArea(8, 50);
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
    scrollPane.setPreferredSize(new Dimension(600, 180));
    lowerStackPanel.add(scrollPane, BorderLayout.CENTER);

    sectionsPanel.add(lowerStackPanel);

    mainPanel.add(sectionsPanel, BorderLayout.NORTH);

    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(mainPanel, BorderLayout.CENTER);

    return panel;
    }
    
    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Transfer Money", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // From account section
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("From Account Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fromAccountField = new JTextField(20);
        fromAccountField.setForeground(Color.BLACK);
        fromAccountField.setBackground(Color.WHITE);
        formPanel.add(fromAccountField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(new JLabel("From Account Holder Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fromNameField = new JTextField(20);
        fromNameField.setEditable(false);
        formPanel.add(fromNameField, gbc);
        
        // To account section
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("To Account Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        toAccountField = new JTextField(20);
        toAccountField.setForeground(Color.BLACK);
        toAccountField.setBackground(Color.WHITE);
        formPanel.add(toAccountField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(new JLabel("To Account Holder Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        toNameField = new JTextField(20);
        toNameField.setEditable(false);
        formPanel.add(toNameField, gbc);
        
        // Amount field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Transfer Amount:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        transferAmountField = new JTextField(20);
        transferAmountField.setForeground(Color.BLACK);
        transferAmountField.setBackground(Color.WHITE);
        formPanel.add(transferAmountField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton validateButton = new JButton("Validate Accounts");
        validateButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateButton.addActionListener(new ValidateAccountsListener());
        
        JButton transferButton = new JButton("Transfer Money");
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        transferButton.setBackground(new Color(40, 167, 69));
        transferButton.setForeground(Color.BLACK);
        transferButton.addActionListener(new TransferMoneyListener());
        
        buttonPanel.add(validateButton);
        buttonPanel.add(transferButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        // Result area
        transferResultArea = new JTextArea(15, 40);
        transferResultArea.setEditable(false);
        transferResultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane transferScrollPane = new JScrollPane(transferResultArea);
        transferScrollPane.setBorder(BorderFactory.createTitledBorder("Transfer Results"));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(transferScrollPane, BorderLayout.SOUTH);
        
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
        accountNumberField.setForeground(Color.BLACK);
        accountNumberField.setBackground(Color.WHITE);
        formPanel.add(accountNumberField, gbc);
        
        // Amount field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Amount:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        amountField = new JTextField(20);
        amountField.setForeground(Color.BLACK);
        amountField.setBackground(Color.WHITE);
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
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String city = cityField.getText().trim();
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
                
                String accountNumber = bankService.createAccount(name, initialDeposit, phone, email, city);
                
                if (accountNumber != null) {
            resultArea.setText("Account created successfully!\n\n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n" +
                "City: " + city + "\n" +
                "Initial Balance: ₹" + String.format("%.2f", initialDeposit) + "\n\n" +
                "Please save your account number for future transactions.");
                    
                    // Clear fields
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    cityField.setText("");
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
    
    private class ClearFieldsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear all form fields
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            cityField.setText("");
            initialDepositField.setText("");
            deleteAccountField.setText("");
            resultArea.setText("");
        }
    }
    
    private class RefreshAccountsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshAccountsList();
        }
    }
    
    private class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountNumber = deleteAccountField.getText().trim();
            
            if (accountNumber.isEmpty()) {
                resultArea.setText("Error: Please enter account number to delete.");
                return;
            }
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(
                BankGUI.this,
                "Are you sure you want to delete account " + accountNumber + "?",
                "Confirm Account Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bankService.deleteAccount(accountNumber)) {
                    resultArea.setText("SUCCESS!\n\nAccount " + accountNumber + 
                        " has been successfully deleted from the system.");
                    deleteAccountField.setText("");
                } else {
                    resultArea.setText("ERROR!\n\nFailed to delete account " + accountNumber + 
                        ".\n\nPossible reasons:\n- Account does not exist\n- Database error");
                }
            } else {
                resultArea.setText("Deletion cancelled by user.");
            }
        }
    }
    
    private class ValidateAccountsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromAccount = fromAccountField.getText().trim();
            String toAccount = toAccountField.getText().trim();
            
            if (fromAccount.isEmpty() || toAccount.isEmpty()) {
                transferResultArea.setText("Error: Please enter both account numbers.");
                return;
            }
            
            BankAccount fromAcc = bankService.getAccountInfo(fromAccount);
            BankAccount toAcc = bankService.getAccountInfo(toAccount);
            
            StringBuilder sb = new StringBuilder();
            sb.append("Account Validation Results:\n\n");
            
            if (fromAcc != null) {
                sb.append("From Account FOUND:\n");
                sb.append("Account: ").append(fromAcc.getAccountNumber()).append("\n");
                sb.append("Holder: ").append(fromAcc.getAccountHolderName()).append("\n");
                sb.append("Balance: ₹").append(String.format("%.2f", fromAcc.getBalance())).append("\n\n");
                fromNameField.setText(fromAcc.getAccountHolderName());
            } else {
                sb.append("From Account NOT FOUND: ").append(fromAccount).append("\n\n");
                fromNameField.setText("");
            }
            
            if (toAcc != null) {
                sb.append("To Account FOUND:\n");
                sb.append("Account: ").append(toAcc.getAccountNumber()).append("\n");
                sb.append("Holder: ").append(toAcc.getAccountHolderName()).append("\n");
                sb.append("Balance: ₹").append(String.format("%.2f", toAcc.getBalance())).append("\n");
                toNameField.setText(toAcc.getAccountHolderName());
            } else {
                sb.append("To Account NOT FOUND: ").append(toAccount).append("\n");
                toNameField.setText("");
            }
            
            transferResultArea.setText(sb.toString());
        }
    }
    
    private class TransferMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String fromAccount = fromAccountField.getText().trim();
                String toAccount = toAccountField.getText().trim();
                String amountText = transferAmountField.getText().trim();
                
                if (fromAccount.isEmpty() || toAccount.isEmpty() || amountText.isEmpty()) {
                    transferResultArea.setText("Error: Please fill all fields and validate accounts first.");
                    return;
                }
                
                double amount = Double.parseDouble(amountText);
                
                if (amount <= 0) {
                    transferResultArea.setText("Error: Transfer amount must be positive.");
                    return;
                }
                
                if (bankService.transferMoney(fromAccount, toAccount, amount)) {
                    BankAccount fromAcc = bankService.getAccountInfo(fromAccount);
                    BankAccount toAcc = bankService.getAccountInfo(toAccount);
                    
                    transferResultArea.setText("Transfer Successful!\n\n" +
                        "From: " + fromAccount + " (" + fromAcc.getAccountHolderName() + ")\n" +
                        "To: " + toAccount + " (" + toAcc.getAccountHolderName() + ")\n" +
                        "Amount: ₹" + String.format("%.2f", amount) + "\n\n" +
                        "Updated Balances:\n" +
                        "From Account: ₹" + String.format("%.2f", fromAcc.getBalance()) + "\n" +
                        "To Account: ₹" + String.format("%.2f", toAcc.getBalance()));
                    
                    // Clear fields
                    transferAmountField.setText("");
                } else {
                    transferResultArea.setText("Error: Transfer failed. Please check account numbers and ensure sufficient balance.");
                }
                
            } catch (NumberFormatException ex) {
                transferResultArea.setText("Error: Please enter a valid transfer amount.");
            }
        }
    }
    
    private void refreshAccountsList() {
        List<BankAccount> accounts = bankService.getAllAccounts();
        
        if (accounts.isEmpty()) {
            accountsListArea.setText("No accounts found.\n\nCreate some accounts using the 'Bank Administration' tab.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Total Accounts: ").append(accounts.size()).append("\n\n");
            sb.append(String.format("%-12s %-20s %-15s %-20s %-15s %-12s%n", 
                "Account No.", "Name", "Phone", "Email", "City", "Balance"));
            sb.append("-".repeat(95)).append("\n");
            
            for (BankAccount account : accounts) {
                sb.append(String.format("%-12s %-20s %-15s %-20s %-15s ₹%-11.2f%n",
                        account.getAccountNumber(),
                        account.getAccountHolderName().length() > 18 ? 
                            account.getAccountHolderName().substring(0, 18) + ".." :
                            account.getAccountHolderName(),
                        account.getPhoneNumber(),
                        account.getEmail().length() > 18 ? 
                            account.getEmail().substring(0, 18) + ".." :
                            account.getEmail(),
                        account.getCity(),
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
