package com.est.sb.estgi;

import com.est.sb.estgi.actors.User;
import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeViewController {

    @FXML
    private Label welcomeField;
    private User user;
    public void setUserData(User user) {
       this.user = user;
        welcomeField.setText("Welcome " + user.getFname());
    }

    @FXML
    private void initialize() {

    }

}
