package dao;

import config.DbConnection;
import models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {
    //create a new bank account
    public boolean createAccount(int userId, String accountType) {
        try (Connection con = DbConnection.getConnection()) {
            String sql = "INSERT into accounts (user_id, account_type, balance) VALUES (?,?,0.00)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, accountType);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //check if user already has an account
    public Account getAccountByUserId(int userId) {
        try (Connection con = DbConnection.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE user_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_type"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public double getBalance(int accountId){
        try(Connection con = DbConnection.getConnection()){
            String sql = "SELECT balance from accounts WHERE account_id = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,accountId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) { return rs.getDouble("balance");
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

//    public boolean updateBalance(int account_id, double balance){
//        try(Connection con = DbConnection.getConnection()){
//            String sql = "UPDATE accounts SET balance= ? WHERE account_id=?";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1,account_id);
//            stmt.setDouble(2, balance);
//            return stmt.executeUpdate()>0;
//        }catch(SQLException e){
//
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
}
