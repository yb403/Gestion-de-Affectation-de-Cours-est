package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public void setStudentData(Student student) {
        studentid.setText(String.valueOf(student.getId()));
        FnameField.setText(student.getFname());
        LnameField.setText(student.getLname());
        passwordField.setText("");
        emailField.setText(student.getEmail());

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
