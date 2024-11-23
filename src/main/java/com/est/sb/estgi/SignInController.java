package com.est.sb.estgi;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import com.est.sb.estgi.actors.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class SignInController {
    @FXML
    private Label welcome;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.isEmpty()  || password.isEmpty()) {
            Utils.showAlert("Error", "Please fill in all fields.");
            return;
        }else if (!Utils.isValidEmail(email)) {
            Utils.showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }
        try{
            User user = DatabaseHelper.Authenticate(emailField.getText(),passwordField.getText());
            if (user != null) {

                try {

                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
                    Parent newRoot = loader.load();
                    DashboardController controller = loader.getController();
                    //if (user instanceof Student)
                    //if (user instanceof Teacher)
                    controller.SetUserData(user);
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(newRoot, 920, 540));
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else {
                Utils.showAlertError("Error","Invalid email or password");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        emailField.setText("admin@admin.com");
        passwordField.setText("admin");
    }
    @FXML
    private void handleButtonSignUp(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot,1020, 540));
        } catch (Exception ignored) {

        }
    }



}