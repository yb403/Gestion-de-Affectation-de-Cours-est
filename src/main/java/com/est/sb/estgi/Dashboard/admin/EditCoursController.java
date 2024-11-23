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

public class EditCoursController {

    @FXML
    private ComboBox<String> teacherList;

    @FXML
    private TextField titleField;
    @FXML
    private TextField IdField;
    @FXML
    private TextArea descriptionArea;
    private List<Teacher> teachers = new ArrayList<>();
    @FXML
    public void initialize() {

    }

    public void setCoursData(Cours cours) {
        titleField.setText(cours.getTitle());
        descriptionArea.setText(cours.getDescription());
        IdField.setText(String.valueOf(cours.getId()));
        try{
            for (User teacher: DatabaseHelper.getAll(Role.TEACHER)){
                teachers.add((Teacher) teacher);
                teacherList.getItems().add(teacher.getName());
                if (((Teacher) teacher).getId() == cours.getTeacher().getId()){
                    teacherList.getSelectionModel().select(teacher.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            teacherList.getItems().addAll("NOT", "FOUND");
        }



    }

    @FXML
    public void handleSubmitButton(){


        String title = titleField.getText();
        String description = descriptionArea.getText();
        if (description.isEmpty()|| title.isEmpty() || teacherList.getSelectionModel().getSelectedIndex() == -1) {
            Utils.showAlert("Error", "Please fill in all fields and select a teacher.");
        }else {
            try{
                DatabaseHelper.updateCours(new Cours(Integer.parseInt(IdField.getText()),title,description,teachers.get(teacherList.getSelectionModel().getSelectedIndex())));
                Utils.showAlert("Successfully", "Course Updated Successfully.");

            } catch (Exception e) {
                Utils.showAlert("Error", "Could not update Cours.");
                e.printStackTrace();
            }}

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
