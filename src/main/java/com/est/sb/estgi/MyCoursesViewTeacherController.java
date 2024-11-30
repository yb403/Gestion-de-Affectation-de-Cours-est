package com.est.sb.estgi;

import com.est.sb.estgi.Dashboard.admin.EnrolledStudentsViewController;
import com.est.sb.estgi.actors.Cours;
import com.est.sb.estgi.actors.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyCoursesViewTeacherController {

    private User user;

    @FXML
    private ListView<HBox> courseListView;

    private ObservableList<HBox> courseItems = FXCollections.observableArrayList();
    private List<Cours> coursList = new ArrayList<>();
    public void setUserData(User user) {
        this.user = user;
        loadCourses();
    }
    public void initialize() {

        courseListView.setItems(courseItems);
    }

    private void loadCourses() {
        try{
            coursList = DatabaseHelper.getMyCourseTeacher(user.getId());
            System.out.println(coursList);
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

        Button enrollButton = new Button("Enrolled Students");
        enrollButton.setStyle("-fx-background-color: #088f2b; -fx-text-fill: white;");
        enrollButton.setOnAction(event -> enrollInCourse(courseItem, cours));

        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #5d6c5d; -fx-text-fill: white;");
        detailsButton.setOnAction(event -> coursDetails(courseName));


        // Add the course name, spacer, and button to the HBox
        courseItem.getChildren().addAll(courseText, spacer,detailsButton, enrollButton);
        courseItems.add(courseItem);
    }

    private void enrollInCourse(HBox courseItem, Cours cours) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/EnrolledStudentsView.fxml"));
            Parent editView = loader.load();
            EnrolledStudentsViewController controller = loader.getController();
            controller.setCours(cours);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
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