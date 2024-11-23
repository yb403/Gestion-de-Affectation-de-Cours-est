package com.est.sb.estgi;

import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import com.est.sb.estgi.actors.User;

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

    private static void insertStudent(int id, User user) throws SQLException {
        String query = "INSERT INTO students (user_id) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private static void insertTeacher(int id, User user) throws SQLException {
        String query = "INSERT INTO teachers (user_id) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public static void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    public static void saveUser(User user) throws SQLException {

        String query = "INSERT INTO users (Fname, Lname, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFname());
            stmt.setString(2, user.getLname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        System.out.println("Inserted user ID: " + userId);
                        if (user.getRole().equals("STUDENT")) {
                            insertStudent(userId, user);
                        } else if (user.getRole().equals("TEACHER")) {insertTeacher(userId, user);}

                    }
                }
            }
        }

    }
    public static List<User> getAll(Role role) throws SQLException {
        String query = "SELECT * FROM users WHERE role = ?";
        List<User> users = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (role.equals(Role.STUDENT)){
                    users.add(new Student(rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")));
                }
                else if (role.equals(Role.TEACHER)){
                    users.add(new Teacher(rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")));

                }

            }
        }
        return users;
    }
}