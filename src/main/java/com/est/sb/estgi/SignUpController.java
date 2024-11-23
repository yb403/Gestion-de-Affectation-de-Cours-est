package com.est.sb.estgi;

import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpController {
    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton student;

    @FXML
    private RadioButton teacher;

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
    private ToggleGroup roleGroup;
    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        male.setSelected(true);
        roleGroup = new ToggleGroup();
        student.setToggleGroup(roleGroup);
        teacher.setToggleGroup(roleGroup);
        student.setSelected(true);
    }

    @FXML
    private void handleSubmit() {
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Toggle selectedToggle = genderGroup.getSelectedToggle();
        Toggle selectedRoleToggle = roleGroup.getSelectedToggle();
        String gender = selectedToggle != null ? ((RadioButton) selectedToggle).getText() : "Not selected";
        String role = selectedRoleToggle != null ? ((RadioButton) selectedRoleToggle).getText().toUpperCase() : "Not selected";
        if (Fname.isEmpty()|| Lname.isEmpty() || email.isEmpty()  || password.isEmpty() || gender.equals("Not selected")) {
            Utils.showAlert("Error", "Please fill in all fields and select a gender.");
        }else if (!Utils.isValidEmail(email)) {
            Utils.showAlert("Invalid Email", "Please enter a valid email address.");
        }
        try{
            if (role.equals("STUDENT")){
                DatabaseHelper.saveUser(new Student(0,Fname,Lname,email,password));
            } else if (role.equals("TEACHER")) {
                DatabaseHelper.saveUser(new Teacher(0,Fname,Lname,email,password));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
