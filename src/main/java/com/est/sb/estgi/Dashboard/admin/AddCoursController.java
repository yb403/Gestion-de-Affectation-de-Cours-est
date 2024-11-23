package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class AddCoursController {

    @FXML
    private ComboBox<String> teacherList;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    private List<Teacher> teachers = new ArrayList<>();
    @FXML
    public void initialize() {
        teacherList.setValue("Chose Teacher");
        try{
            for (User teacher: DatabaseHelper.getAll(Role.TEACHER)){
                teachers.add((Teacher) teacher);
                teacherList.getItems().add(teacher.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            teacherList.getItems().addAll("Admin", "Teacher");
        }
    }

    @FXML
    public void handleSubmitButton(){

        System.out.println(teacherList.getSelectionModel().getSelectedItem());
        System.out.println(teacherList.getSelectionModel().getSelectedIndex());
        String title = titleField.getText();
        String description = descriptionArea.getText();
        if (description.isEmpty()|| title.isEmpty() || teacherList.getSelectionModel().getSelectedIndex() == -1) {
            Utils.showAlert("Error", "Please fill in all fields and select a teacher.");
        }else {
        try{
            DatabaseHelper.saveCours(new Cours(0,title,description,teachers.get(teacherList.getSelectionModel().getSelectedIndex())));
            clearFields();
            Utils.showAlert("Successfully", "Student Created Successfully.");

        } catch (Exception e) {
            Utils.showAlert("Error", "Could not create Cours.");
            e.printStackTrace();
        }}

    }

    private void clearFields() {
        titleField.clear();
        descriptionArea.clear();
        teacherList.setValue("Chose Teacher");
    }

    @FXML
    public void handleBackButton(){
        try{
            // Load the EditStudentView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/ManageCoursesView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception ignored) {

        }
    }
}
