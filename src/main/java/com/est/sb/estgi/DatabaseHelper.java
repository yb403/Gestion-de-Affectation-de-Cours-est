package com.est.sb.estgi;

import com.est.sb.estgi.actors.*;
import com.sun.source.tree.CompilationUnitTree;

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

    public static void insertStudent(int id, User user) throws SQLException {
        String query = "INSERT INTO students (user_id) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    public static void insertEnrolled(int student_id, int courseID) throws SQLException {
        String query = "INSERT INTO enrolled(student_id,courseID) VALUES (?,?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, student_id);
            stmt.setInt(2, courseID);
            stmt.executeUpdate();
        }
    }

    public static void deleteEnrolled(int student_id, int courseID) throws SQLException {
        String query = "DELETE FROM enrolled WHERE student_id = ? and courseID = ?;";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, student_id);
            stmt.setInt(2, courseID);
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


    public static void saveCours(Cours cours) throws SQLException {
        String query = "INSERT INTO Cours (courseName, courseDescription,teacherID) VALUES (?,?,?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cours.getTitle());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getTeacher().getId());
            stmt.executeUpdate();
        }
    }

    public static void updateCours(Cours cours) throws SQLException {
        String query = "UPDATE  Cours SET courseName  = ?, courseDescription  = ?,teacherID = ? WHERE courseID = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cours.getTitle());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getTeacher().getId());
            stmt.setInt(4, cours.getId());
            stmt.executeUpdate();
        }
    }

    public static void deleteCours(int id) throws SQLException {
        String query = "DELETE FROM cours WHERE courseID = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET Fname = ?, Lname = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFname());
            stmt.setString(2, user.getLname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }
    public static User Authenticate(String email, String password) throws SQLException {
        String query = "SELECT id, Fname, Lname, email, password, role FROM users WHERE email = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("role").equals(Role.ADMIN.name())){
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
                else if (rs.getString("role").equals(Role.STUDENT.name())){
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                } else  if (rs.getString("role").equals(Role.TEACHER.name())){
                    return new Teacher(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }

            }
        }
        return null;
    }
    public static User getUser(int id) throws SQLException {
        String query = "SELECT id, Fname, Lname, email, password, role FROM users WHERE id= ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("role").equals(Role.ADMIN.name())){
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
                else if (rs.getString("role").equals(Role.STUDENT.name())){
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                } else  if (rs.getString("role").equals(Role.TEACHER.name())){
                    return new Teacher(
                            rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }

            }
        }
        return null;
    }


    public static List<Cours> getMyCourseTeacher(int userid) throws SQLException {
        String query = "SELECT * FROM cours WHERE teacherID = ?";
        List<Cours> cours = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                cours.add(new Cours(rs.getInt("courseID"),
                        rs.getString("courseName"),
                        rs.getString("courseDescription"),
                        new Teacher(rs.getInt("teacherID"),"","","","")));
            }
        }
        System.out.println(cours);
        return cours;
    }

    public static List<Cours> getMyCourse(int userid) throws SQLException {
        String query = "SELECT * FROM cours c WHERE EXISTS ( SELECT * FROM enrolled e WHERE c.courseID = e.courseID and e.student_id = ? );";
        List<Cours> cours = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                cours.add(new Cours(rs.getInt("courseID"),
                        rs.getString("courseName"),
                        rs.getString("courseDescription"),
                        new Teacher(rs.getInt("teacherID"),"","","","")));
            }
        }
        System.out.println(cours);
        return cours;
    }
    public static List<Cours> getAvailableCourse(int userid) throws SQLException {
        String query = "SELECT * FROM cours c WHERE NOT EXISTS ( SELECT * FROM enrolled e WHERE c.courseID = e.courseID and e.student_id = ?);";
        List<Cours> cours = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                cours.add(new Cours(rs.getInt("courseID"),
                        rs.getString("courseName"),
                        rs.getString("courseDescription"),
                        new Teacher(rs.getInt("teacherID"),"","","","")));
            }
        }
        System.out.println(cours);
        return cours;
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
    public static List<Cours> getAllCours() throws SQLException {
        String query = "SELECT c.*,u.Fname,u.Lname FROM cours c, users u WHERE c.teacherID = u.id\n";
        List<Cours> cours = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                    cours.add(new Cours(rs.getInt("courseID"),
                            rs.getString("courseName"),
                            rs.getString("courseDescription"),
                            new Teacher(rs.getInt("teacherID"),rs.getString("Fname"),rs.getString("Lname"),"","")));
            }
        }
        return cours;
    }


    public static Cours getCoursById(int id) throws SQLException {
        String query = "SELECT * FROM cours WHERE CourseID = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                return new Cours(rs.getInt("courseID"),
                        rs.getString("courseName"),
                        rs.getString("courseDescription"),
                        new Teacher(rs.getInt("teacherID"),"","","",""));

            }
        }
        return null;
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

    public static List<Student> getEnrolledStudents(int courseID) throws SQLException {
        String query = "SELECT * FROM users u, enrolled e WHERE u.id = e.student_id and e.courseID = ?";
        List<Student> student = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                student.add(new Student(rs.getInt("id"),
                            rs.getString("Fname"),
                            rs.getString("Lname"),
                            rs.getString("email"),
                            rs.getString("password")));



            }
        }
        return student;
    }
}