package com.est.sb.estgi;


import com.est.sb.estgi.actors.Cours;
import com.est.sb.estgi.actors.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoursesViewController {

    @FXML
    private ListView<HBox> courseListView;

    private ObservableList<HBox> courseItems = FXCollections.observableArrayList();
    private List<Cours> coursList = new ArrayList<>();
    @FXML

    private User user;

    public void setUserData(User user) {
        this.user = user;
        loadCourses();
    }
    public void initialize() {

        courseListView.setItems(courseItems);
    }

    private void loadCourses() {
        try{
            coursList = DatabaseHelper.getAvailableCourse(user.getId());
            for(Cours cours: coursList){
                //String text = cours.getTitle() + " Prof: " + cours.getTeacherName();
                String text = cours.getTitle();
                addCourse(text, cours);
            }


        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    private void addCourse(String courseName, Cours cours) {
        HBox courseItem = new HBox(10);
        courseItem.setSpacing(10);
        courseItem.setPadding(new Insets(5));
        courseItem.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");

        Text courseText = new Text(courseName);
        courseText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button enrollButton = new Button("Enroll");
        enrollButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        enrollButton.setOnAction(event -> enrollInCourse(courseItem, cours));

        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #5d6c5d; -fx-text-fill: white;");
        detailsButton.setOnAction(event -> coursDetails(courseName));


        // Add the course name, spacer, and button to the HBox
        courseItem.getChildren().addAll(courseText, spacer,detailsButton, enrollButton);
        courseItems.add(courseItem);
    }


    private void enrollInCourse(HBox courseItem, Cours cours) {
        Utils.showAlert("Cours Enrolled","YOu have been encrolled on this cours!!");
        try {
            courseItems.remove(courseItem);
            if (cours != null) {
                coursList.remove(cours);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("Error", "Failed to remove course.");
        }

        try{
            DatabaseHelper.insertEnrolled(user.getId(), cours.getId());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void coursDetails(String courseName) {

        try{
            int courseId = 0;
            for (Cours c : coursList){
                if (Objects.equals(c.getTitle(), courseName)){
                    courseId = c.getId();
                }
            }

            Cours crs = DatabaseHelper.getCoursById(courseId);
            Utils.showAlert("Cours Details", crs.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("Error", "Error");

        }

    }
}
