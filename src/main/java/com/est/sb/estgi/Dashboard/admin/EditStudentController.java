package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.security.SecureRandom;
import java.util.Objects;

public class EditStudentController {

    @FXML
    private TextField FnameField;

    @FXML
    private TextField LnameField;

    @FXML
    private TextField studentid;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;


    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;
    private ToggleGroup genderGroup;
    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        male.setSelected(true);

    }
    public void setStudentData(Student student) {
        studentid.setText(String.valueOf(student.getId()));
        FnameField.setText(student.getFname());
        LnameField.setText(student.getLname());
        passwordField.setText(student.getPassword());
        emailField.setText(student.getEmail());
    }


    @FXML
    public void handleGeneratePassButton() {
        passwordField.setText(Utils.generatePassword());
    }

    @FXML
    public void handleUpdateButton() {
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty()) {
            Utils.showAlert("Error", "Please fill in all fields and select a gender.");
        }else if (!Utils.isValidEmail(email)) {
            Utils.showAlert("Invalid Email", "Please enter a valid email address.");
        }else {
            try {
                DatabaseHelper.updateUser(new Student(Integer.parseInt(studentid.getText()),
                        Fname, Lname, email, password));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void handleBackButton() {
        try {
            // Load the EditStudentView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/ManageStudentsView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception ignored) {

        }

    }
}
