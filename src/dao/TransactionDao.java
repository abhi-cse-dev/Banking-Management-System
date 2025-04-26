package dao;

import config.DbConnection;
import models.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    //deposit money
    public boolean deposit(int accountId, double amount) {
        try (Connection con = DbConnection.getConnection()) {
            con.setAutoCommit(false);
            String updateBalanceSQL = "UPDATE accounts SET balance = balance+? WHERE account_id=?";
            PreparedStatement updateStmt = con.prepareStatement(updateBalanceSQL);
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountId);

            int rowsUpdated = updateStmt.executeUpdate();

            //insert transaction record
            String insertTransactionSQL = "INSERT INTO transactions(account_id,type,amount) VALUES (?,'DEPOSIT',?)";
            PreparedStatement insertStmt = con.prepareStatement(insertTransactionSQL);
            insertStmt.setInt(1, accountId);
            insertStmt.setDouble(2, amount);
            int transactionInserted = insertStmt.executeUpdate();

            if (rowsUpdated > 0 && transactionInserted > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Withdraw money
    public boolean withdraw(int accountId, double amount) {
        try (Connection con = DbConnection.getConnection()) {
            con.setAutoCommit(false);

            //Check current balance
            String balanceCheckSQL = "SELECT balance from accounts WHERE account_id = ?";
            PreparedStatement balanceStmt = con.prepareStatement(balanceCheckSQL);
            balanceStmt.setInt(1, accountId);
            ResultSet rs = balanceStmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < amount) {
                    System.out.println("Insufficient balance");
                    return false;
                }
            } else {
                System.out.println("Account not found ");
                return false;
            }


            String updateBalanceSQL = "UPDATE accounts SET balance = balance - ? WHERE account_id=?";
            PreparedStatement updateStmt = con.prepareStatement(updateBalanceSQL);
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountId);

            int rowsUpdated = updateStmt.executeUpdate();

            //insert transaction record
            String insertTransactionSQL = "INSERT INTO transactions (account_id,type,amount) VALUES (?,'WITHDRAWAL',?)";
            PreparedStatement insertStmt = con.prepareStatement(insertTransactionSQL);
            insertStmt.setInt(1, accountId);
            insertStmt.setDouble(2, amount);
            int transactionInserted = insertStmt.executeUpdate();

            if (rowsUpdated > 0 && transactionInserted > 0) {   // && transactionInserted>0
                con.commit();
                return true;
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public boolean transferFunds(int senderId, int recieverId, double amount) {
        try (Connection con = DbConnection.getConnection()) {
            con.setAutoCommit(false);

            String checkBalanceSQL = "SELECT balance from accounts where account_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkBalanceSQL);
            checkStmt.setInt(1, senderId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next() || rs.getDouble("balance") < amount) {
                System.out.println("Insufficient funds or invalid sender account");
                return false;
            }

            String deductSQL = "UPDATE accounts SET balance = balance - ? where account_id = ?";
            PreparedStatement deductStmt = con.prepareStatement(deductSQL);
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, senderId);
            deductStmt.executeUpdate();

            String creditSQL = "UPDATE accounts SET balance = balance + ? where account_id = ?";
            PreparedStatement creditStmt = con.prepareStatement(creditSQL);
            creditStmt.setDouble(1, amount);
            creditStmt.setInt(2, recieverId);
            creditStmt.executeUpdate();

            String transactionSQL = "INSERT into transactions (account_id,type, amount) VALUES (?,?,?)";
            PreparedStatement transStmt = con.prepareStatement(transactionSQL);

            transStmt.setInt(1, senderId);
             transStmt.setString(2,"TRANSFER OUT");
            transStmt.setDouble(3, amount);
            transStmt.executeUpdate();


            transStmt.setInt(1, recieverId);
             transStmt.setString(2,"TRANSFER IN");
            transStmt.setDouble(3, amount);
            transStmt.executeUpdate();

            con.commit();
            return true;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



    public List<Transaction> getTransactionHistory(int accountId){
        List<Transaction> transactions = new ArrayList<>();
        try(Connection con = DbConnection.getConnection()){
            String sql = "SELECT * FROM transactions WHERE account_id= ? ORDER BY date DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                transactions.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("date")
                ));
            }
            }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return transactions;
    }

//    public boolean addTransaction(int accountId, String type, double amount) {
//        try (Connection con = DbConnection.getConnection()) {
//            String sql = "INSERT INTO transaction (account_id, type, amount) VALUES (?,?,?)";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1, accountId);
//            stmt.setString(2, type);
//            stmt.setDouble(3, amount);
//            return stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
//

}
