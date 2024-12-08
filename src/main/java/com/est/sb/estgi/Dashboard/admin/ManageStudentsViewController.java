package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.User;
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

public class ManageStudentsViewController {
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


    @FXML
    public void initialize() {
        // Set up columns for the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set the custom cell factory for the "Actions" column
        actionsColumn.setCellFactory(param -> new TableCell<Student, String>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                // Edit button action
                editButton.setOnAction(event -> {
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        handleEdit(student);
                    }
                });

                // Delete button action
                deleteButton.setOnAction(event -> {
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        handleDelete(student);
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Add buttons to the row
                    HBox hbox = new HBox(10, editButton, deleteButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        studentTable.getItems().setAll(getStudents());
    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            for (User s : DatabaseHelper.getAll(Role.STUDENT))
                students.add((Student) s);
        } catch (Exception e) {
            for (int i = 0; i < 20; i++) {
                students.add(new Student(i, "NOT", "FOUND", "ERROR", "aahahahaa"));
            }

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
                e.printStackTrace();
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

