package com.est.sb.estgi;
import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private VBox sidebar;

    @FXML
    private StackPane contentArea;
    private String userRole; // or "student"
    private static DashboardController instance;
    private User userData;
    @FXML
    public void initialize() {

        instance = this;

    }


    public void SetUserData(User user) {
        userData = user;
        userRole = user.getRole();
        setupSidebar();
    }
    public static StackPane getContentArea() {
        return instance.contentArea;
    }
    private void setupSidebar() {
        sidebar.getChildren().clear();

        if (userRole.equals(Role.ADMIN.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("Manage Courses", "#handleManageCourses");
            addSidebarButton("Manage Teachers", "#handleManageTeachers");
            addSidebarButton("Manage Students", "#handleManageStudents");
            addSidebarButton("Profile", "#handleSettings");
        } else if (userRole.equals(Role.STUDENT.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("My Courses", "#handleMyCourses");
            addSidebarButton("My Teachers", "#handleMyTeachers");
            addSidebarButton("Exams", "#handleExams");
            addSidebarButton("Profile", "#handleProfile");
        } else if (userRole.equals(Role.TEACHER.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("My Courses", "#handleMyCourses");
            addSidebarButton("Teachers", "#handleMyTeachers");
            addSidebarButton("Exams", "#handleExams");
            addSidebarButton("Profile", "#handleProfile");
        }
    }

    private void addSidebarButton(String text, String actionMethod) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff; -fx-background-color: #2f2f39;");
        button.setOnAction(event -> {
            try {
                this.getClass().getMethod(actionMethod.substring(1)).invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sidebar.getChildren().add(button);
    }

    // Define button actions
    public void handleHome() {
        loadContent("HomeView.fxml");
    }

    public void handleManageStudents() {
        loadContent("ManageStudentsView.fxml");
    }

    public void handleManageTeachers() {
        loadContent("ManageTeachersView.fxml");
    }

    public void handleManageCourses() {
        loadContent("ManageCoursesView.fxml");
    }

    public void handleSettings() {
        loadContent("SettingsView.fxml");
    }

    public void handleMyCourses() {
        loadContent("MyCoursesView.fxml");
    }

    public void handleMyTeachers() {
        loadContent("MyTeachersView.fxml");
    }

    public void handleExams() {
        loadContent("ExamsView.fxml");
    }

    public void handleProfile() {
        loadContent("ProfileView.fxml");
    }


    private void loadContent(String fxmlFile) {
        try {
            System.out.println(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            contentArea.getChildren().clear(); // Clear the old content

            contentArea.getChildren().add(view); // Add the new content
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
