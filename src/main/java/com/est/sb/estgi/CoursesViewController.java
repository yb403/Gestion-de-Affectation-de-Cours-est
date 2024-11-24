package com.est.sb.estgi;


import com.est.sb.estgi.actors.Cours;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CoursesViewController {

    @FXML
    private ListView<HBox> courseListView;

    private ObservableList<HBox> courseItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadCourses();
        courseListView.setItems(courseItems);
    }

    private void loadCourses() {
        try{
            for(Cours cours: DatabaseHelper.getAllCours()){
                String text = cours.getTitle() + " Prof: " + cours.getTeacherName();
                addCourse(text);
            }


        } catch (Exception e) {
            addCourse("Math 101");

        }

    }

    private void addCourse(String courseName) {
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
        enrollButton.setOnAction(event -> enrollInCourse(courseName));

        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #5d6c5d; -fx-text-fill: white;");
        detailsButton.setOnAction(event -> enrollInCourse(courseName));


        // Add the course name, spacer, and button to the HBox
        courseItem.getChildren().addAll(courseText, spacer,detailsButton, enrollButton);
        courseItems.add(courseItem);
    }

    private void enrollInCourse(String courseName) {
        System.out.println("Enrolled in course: " + courseName);
    }
}
