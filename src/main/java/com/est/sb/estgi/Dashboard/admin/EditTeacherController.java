package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.security.SecureRandom;
import java.util.Objects;

public class EditTeacherController {

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
    public void setTeacherData(Teacher teacher) {
        studentid.setText(String.valueOf(teacher.getId()));
        FnameField.setText(teacher.getFname());
        LnameField.setText(teacher.getLname());
        passwordField.setText(teacher.getPassword());
        emailField.setText(teacher.getEmail());

    }
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
                DatabaseHelper.updateUser(new Teacher(Integer.parseInt(studentid.getText()),
                        Fname, Lname, email, password));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
}
