package com.est.sb.estgi;
import com.est.sb.estgi.Dashboard.SettingsViewController;
import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

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
        handleHome();
    }
    public static StackPane getContentArea() {
        return instance.contentArea;
    }
    private void setupSidebar() {
        //sidebar.getChildren().clear();

        if (userRole.equals(Role.ADMIN.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("Manage Courses", "#handleManageCourses");
            addSidebarButton("Manage Teachers", "#handleManageTeachers");
            addSidebarButton("Manage Students", "#handleManageStudents");
            addSidebarButton("Profile", "#handleSettings");
        } else if (userRole.equals(Role.STUDENT.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("My Courses", "#handleMyCourses");
            addSidebarButton("Courses", "#handleAvailableCourses");
            addSidebarButton("Profile", "#handleSettings");
        } else if (userRole.equals(Role.TEACHER.name())) {
            addSidebarButton("Home", "#handleHome");
            addSidebarButton("My Courses", "#handleMyCoursesTeacher");
            addSidebarButton("Profile", "#handleSettings");
        }

        addSidebarButton("Logout", "#handleLogout");
    }

    public void handleLogout(){
        Optional<ButtonType> result = Utils.showConfirmationAlert("Logout Confirmation", "You want to Logout?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
                Parent root = loader.load();
                // Get the current stage (window)
                Stage stage = (Stage) sidebar.getScene().getWindow();
                stage.setScene(new Scene(root, 820, 540));

                // Optionally, you can show the stage again
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showAlert("Error", "Failed to Logout");

            }
        }
    }
    public void handleMyCoursesTeacher() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/MyCoursesViewTeacher.fxml"));
            Parent editView = loader.load();
            MyCoursesViewTeacherController controller = loader.getController();
            controller.setUserData(userData);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addSidebarButton(String text, String actionMethod) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        if (Objects.equals(text, "Logout")){
            button.setStyle("-fx-font-size: 19; -fx-text-fill: #ffffff;; -fx-min-width: 197; -fx-background-color: #550a27;");
        } else {
            button.setStyle("-fx-font-size: 19; -fx-text-fill: #ffffff; -fx-background-color: #2f2f39; -fx-min-width: 197");
        }

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/HomeView.fxml"));
            Parent editView = loader.load();
            HomeViewController controller = loader.getController();
            controller.setUserId(userData.getId());
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/SettingsView.fxml"));
            Parent editView = loader.load();
            SettingsViewController controller = loader.getController();
            controller.setUserId(userData.getId());
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleMyCourses() {



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/MyCoursesView.fxml"));
            Parent editView = loader.load();
            MyCoursesViewController controller = loader.getController();
            controller.setUserData(userData);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAvailableCourses() {



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/CoursesView.fxml"));
            Parent editView = loader.load();
            CoursesViewController controller = loader.getController();
            controller.setUserData(userData);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
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
