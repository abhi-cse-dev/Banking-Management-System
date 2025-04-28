🏦 JDBC-Based CLI Banking System
A simple yet powerful Command Line Interface (CLI) banking application developed using Java and JDBC with MySQL as the database.
This project is designed to simulate core banking functionalities such as account creation, transactions, and fund transfers.

📌 Features
✅ User Registration & Login (Secure authentication)

✅ Account Creation (Savings / Current)

✅ Deposit and Withdraw Money

✅ Check Account Balance

✅ Fund Transfer (Between user's own accounts or others)

✅ Transaction History (View past transactions)

🛠️ Technologies Used
Java (Core + JDBC)

MySQL (Database)

JDBC API

Maven (optional, if you used it)

🗂️ Database Schema (MySQL)
sql
Copy
Edit
CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE accounts (
  account_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  account_type ENUM('SAVINGS', 'CURRENT'),
  balance DOUBLE DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
  transaction_id INT PRIMARY KEY AUTO_INCREMENT,
  account_id INT,
  type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER'),
  amount DOUBLE,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);



🧠 Concepts Covered
JDBC Connection Handling

SQL Query Execution

Transaction Management

Error Handling and Validation

Object-Oriented Programming (OOP) in Java

Basic CLI User Interface

📋 Future Enhancements (Optional)
Password hashing for security

Admin Panel for managing users

Email notifications for transactions

Unit testing using JUnit

GUI-based Frontend (Swing / JavaFX)

