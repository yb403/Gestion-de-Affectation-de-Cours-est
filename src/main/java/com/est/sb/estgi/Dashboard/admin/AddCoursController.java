package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.actors.Cours;
import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.Teacher;
import com.est.sb.estgi.actors.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class AddCoursController {

    @FXML
    private ComboBox<String> teacherList;
    private List<Teacher> teachers = new ArrayList<>();
    @FXML
    public void initialize() {
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
        System.out.println(teachers.get(teacherList.getSelectionModel().getSelectedIndex()).getName());


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
