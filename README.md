# Java Bank Application (MySQL Version)

This is a simple bank management system built with Java, Swing GUI, and MySQL database. It demonstrates OOP principles, database connectivity, and a user-friendly interface.

## Features
- Create new bank accounts
- Deposit and withdraw money
- View account balances
- All data stored in MySQL database
- GUI built with Java Swing
- Rupee (₹) currency support

## Requirements
- Java JDK 8 or higher
- MySQL Server (with a database and user created)
- MySQL Connector/J (JAR file in `lib/` folder)

## Setup Instructions
1. Clone/download the project.
2. Place `mysql-connector-j-9.4.0.jar` in the `lib` folder.
3. Update your MySQL credentials in `src/DatabaseManager.java` if needed.
4. Compile the project:
   ```powershell
   javac -cp ".;lib/mysql-connector-j-9.4.0.jar" src/*.java
   ```
5. Run the application:
   ```powershell
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
├── run.bat
├── README.md
```

## Author
- Mrudangam Eswar

## License
MIT
