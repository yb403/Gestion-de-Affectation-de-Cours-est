package com.est.sb.estgi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/estsb"; // Replace 'school' with your database name
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = ""; // Replace with your MySQL password

    // Connect to MySQL database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Save a user (insert)
    public static void saveUser(String name, String email, String role) throws SQLException {
        String query = "INSERT INTO user (id, Fname, Lname, email, password, role) VALUES (?, ?, ?,?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, role);
            stmt.executeUpdate();
        }
    }
    public static List<String> getUsers() throws SQLException {
        String query = "SELECT * FROM users";
        List<String> users = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String user = "ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Role: " + rs.getString("role");
                users.add(user);
            }
        }
        return users;
    }

    // Update a user
    public static void updateUser(int id, String name, String email, String role) throws SQLException {
        String query = "UPDATE users SET name = ?, email = ?, role = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, role);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    // Delete a user
    public static void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}