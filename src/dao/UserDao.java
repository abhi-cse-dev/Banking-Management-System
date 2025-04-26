package dao;

import config.DbConnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean registerUser(String username, String password) {
        try (Connection con = DbConnection.getConnection()) {
            String sql = "INSERT INTO  users (username,password) values (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public User loginUser(String username, String password) {
        try (Connection con = DbConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? and password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }

    }

