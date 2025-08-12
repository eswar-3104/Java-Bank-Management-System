# Java Bank Management System

A simple bank management system built with Java, Swing GUI, and MySQL.  
Features include account creation, deposit, withdrawal, and balance inquiry, with all data stored securely in a MySQL database.

## Features
- Create new bank accounts
- Deposit and withdraw money
- View account balances
- Data stored in MySQL
- User-friendly Java Swing GUI
- Rupee (₹) currency support

## Requirements
- Java JDK 8 or higher
- MySQL Server
- MySQL Connector/J (JAR file in `lib/` folder)

## Setup
1. Clone the repository.
2. Place `mysql-connector-j-9.4.0.jar` in the `lib` folder.
3. Update MySQL credentials in `src/DatabaseManager.java` if needed.
4. Compile:
   ```
   javac -cp ".;lib/mysql-connector-j-9.4.0.jar" src/*.java
   ```
5. Run:
   ```
   java -cp "src;lib/mysql-connector-j-9.4.0.jar" BankGUI
   ```

## Project Structure
```
Java project/
├── lib/
│   └── mysql-connector-j-9.4.0.jar
├── src/
│   ├── BankAccount.java
│   ├── BankService.java
│   ├── DatabaseManager.java
│   └── BankGUI.java
├── README.md
```


