package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddStudentController {
    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;
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
    public void setStudentData(Student student) {
        studentid.setText(String.valueOf(student.getId()));
        FnameField.setText(student.getFname());
        LnameField.setText(student.getLname());
        passwordField.setText("");
        emailField.setText(student.getEmail());

    }
    private ToggleGroup genderGroup;
    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        male.setSelected(true);

    }


    @FXML
    public void handleGeneratePassButton(){
        passwordField.setText(Utils.generatePassword());
    }
    @FXML
    public void handleBackButton(){
        try{
            // Load the EditStudentView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/ManageStudentsView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception ignored) {

        }
    }
    @FXML
    private void handleSubmit() {
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Toggle selectedToggle = genderGroup.getSelectedToggle();
        String gender = selectedToggle != null ? ((RadioButton) selectedToggle).getText() : "Not selected";
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty()) {
            Utils.showAlert("Error", "Please fill in all fields and select a gender.");
        }else if (!Utils.isValidEmail(email)) {
            Utils.showAlert("Invalid Email", "Please enter a valid email address.");
        }
        try{
            DatabaseHelper.saveUser(new Student(0,Fname,Lname,email,password));
            clearFields();
            Utils.showAlert("Successfully", "Student Created Successfully.");

        } catch (Exception e) {
            Utils.showAlert("Error", "Could not create student.");
            e.printStackTrace();
        }
    }


    public void clearFields() {
        FnameField.clear();
        LnameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}
