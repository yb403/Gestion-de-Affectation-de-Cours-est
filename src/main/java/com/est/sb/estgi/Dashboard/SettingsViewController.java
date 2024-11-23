package com.est.sb.estgi.Dashboard;

import com.est.sb.estgi.actors.User;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SettingsViewController {

    public TextField emailField;
    public Label welcome;
    public TextField FnameField;
    public TextField LnameField;
    public RadioButton male;
    public RadioButton female;
    public PasswordField passwordField;
    private User user;
    public void setUserData(User user) {
        this.user = user;
        emailField.setText(user.getEmail());
        FnameField.setText(user.getFname());
        LnameField.setText(user.getLname());
        passwordField.setText(user.getPassword());
    }
}
