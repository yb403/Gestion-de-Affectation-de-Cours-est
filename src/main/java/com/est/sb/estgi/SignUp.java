package com.est.sb.estgi;

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
    @FXML
    public void initialize() {
        // Create a ToggleGroup for the RadioButtons
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);

        // Set default selection if needed
        male.setSelected(true);
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
        String gender = selectedToggle != null ? ((RadioButton) selectedToggle).getText() : "Not selected";
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty() || gender.equals("Not selected")) {
            showAlert("Error", "Please fill in all fields and select a gender.");
        }else if (!isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
