package com.est.sb.estgi;

import com.est.sb.estgi.actors.User;
import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeViewController {

    @FXML
    private Label welcomeField;
    private User user;
    public void setUserId(int userId) {
       try{
           this.user = DatabaseHelper.getUser(userId);
           welcomeField.setText("Welcome " + this.user.getFname());
       } catch (Exception e) {
           e.printStackTrace();

       }

    }

    @FXML
    private void initialize() {

    }

}
