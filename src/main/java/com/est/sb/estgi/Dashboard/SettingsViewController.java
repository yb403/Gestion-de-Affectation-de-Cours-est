package com.est.sb.estgi.Dashboard;

import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SettingsViewController {

    public TextField emailField;
    public Label welcome;
    public TextField FnameField;
    public TextField LnameField;
    public PasswordField passwordField;
    public TextField idField;
    public void setUserId(int userId) {
        try{
            User user = DatabaseHelper.getUser(userId);
            if (user == null)return;
            emailField.setText(user.getEmail());
            FnameField.setText(user.getFname());
            LnameField.setText(user.getLname());
            passwordField.setText(user.getPassword());
            idField.setText(String.valueOf(user.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void handleUpdateButton() {
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty() || idField.getText().isEmpty() ) {
            Utils.showAlert("Error", "Please fill in all fields");
        }else if (!Utils.isValidEmail(email)) {
            Utils.showAlert("Invalid Email", "Please enter a valid email address.");
        }else {
            try {

                DatabaseHelper.updateUser(new Student(Integer.parseInt(idField.getText()),
                        Fname, Lname, email, password));
                Utils.showAlert("Successfully", "Updated Successfully.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
