package com.est.sb.estgi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class SignInController {
    @FXML
    private Label welcome;


    @FXML
    private void handleButtonClick(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot,920, 540));
        } catch (Exception ignored) {

        }
    }


    @FXML
    private void handleButtonSignUp(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot,920, 540));
        } catch (Exception ignored) {

        }
    }



}