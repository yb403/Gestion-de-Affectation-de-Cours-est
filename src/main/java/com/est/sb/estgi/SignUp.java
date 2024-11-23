package com.est.sb.estgi;

import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp {
    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton student;

    @FXML
    private RadioButton teacher;

    @FXML
    private Button signup;
    @FXML
    private TextField FnameField;
    @FXML
    private TextField LnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    private ToggleGroup genderGroup;
    private ToggleGroup roleGroup;
    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        male.setSelected(true);
        roleGroup = new ToggleGroup();
        student.setToggleGroup(roleGroup);
        teacher.setToggleGroup(roleGroup);
        student.setSelected(true);
    }
    private boolean isValidEmail(String email) {
        // Regex for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    @FXML
    private void handleSubmit() {
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Toggle selectedToggle = genderGroup.getSelectedToggle();
        Toggle selectedRoleToggle = roleGroup.getSelectedToggle();
        String gender = selectedToggle != null ? ((RadioButton) selectedToggle).getText() : "Not selected";
        String role = selectedRoleToggle != null ? ((RadioButton) selectedRoleToggle).getText().toUpperCase() : "Not selected";
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty() || gender.equals("Not selected")) {
            showAlert("Error", "Please fill in all fields and select a gender.");
        }else if (!isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
        }
        try{
            if (role.equals("STUDENT")){
                DatabaseHelper.saveUser(new Student(0,Fname,Lname,email,password));
            } else if (role.equals("TEACHER")) {
                DatabaseHelper.saveUser(new Student(0,Fname,Lname,email,password));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
