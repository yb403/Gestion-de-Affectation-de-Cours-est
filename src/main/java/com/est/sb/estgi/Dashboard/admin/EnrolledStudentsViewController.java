package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrolledStudentsViewController{
    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    public TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> actionsColumn;
    private Cours cours;

    @FXML
    public void initialize() {
        // Set up columns for the table

    }

     public void setCours(Cours cours) {
        this.cours = cours;
         idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
         nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
         emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


         studentTable.getItems().setAll(getStudents());
     }
    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            for (Student s : DatabaseHelper.getEnrolledStudents(cours.getId()))
                students.add((Student) s);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return students;
    }

    // Handle the "Edit" button click
    public void handleEdit(Student student) {
        try {

            // Load the EditStudentView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/EditStudentView.fxml"));
            Parent editView = loader.load();
            EditStudentController controller = loader.getController();

            controller.setStudentData(student);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle the "Delete" button click
    public void handleDelete(Student student) {

        Optional<ButtonType> result = Utils.showConfirmationAlert("Delete Confirmation", "Are you sure you want to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseHelper.deleteUser(student.getId());
                studentTable.getItems().remove(student);
            } catch (Exception e) {
                Utils.showAlert("Error", "Failed to delete student");

            }
        } else {
            System.out.println("User chose Cancel. Aborting deletion.");
        }
    }


    @FXML
    public void handleCreateStudentButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/AddStudentView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

