package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.Utils;
import com.est.sb.estgi.actors.Role;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
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

public class ManageTeachersViewController {
    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    private TableColumn<Teacher, Integer> idColumn;

    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    public TableColumn<Teacher, String>  emailColumn;

    @FXML
    private TableColumn<Teacher, String> actionsColumn;

    @FXML
    public void initialize() {
        // Set up columns for the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set the custom cell factory for the "Actions" column
        actionsColumn.setCellFactory(param -> new TableCell<Teacher, String>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                // Edit button action
                editButton.setOnAction(event -> {
                    Teacher student = getTableRow().getItem();
                    if (student != null) {
                        handleEdit(student);
                    }
                });

                // Delete button action
                deleteButton.setOnAction(event -> {
                    Teacher student = getTableRow().getItem();
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

        // Populate the table with dummy data
        teacherTable.getItems().setAll(getTeachers());
    }

    private List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            for (User s: DatabaseHelper.getAll(Role.TEACHER))
                teachers.add((Teacher) s);
        } catch (Exception e) {
            for (int i =0; i<20; i++){
                teachers.add(new Teacher(i,"NOT","FOUND","ERROR","aahahahaa"));
            }

        }
        return teachers;
    }

    // Handle the "Edit" button click
    public void handleEdit(Teacher teacher) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/EditTeacherView.fxml"));
            Parent editView = loader.load();
            EditTeacherController controller = loader.getController();
            controller.setTeacherData(teacher);
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleCreateTeacherButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/AddTeacherView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void handleDelete(Teacher teacher) {
        Optional<ButtonType> result = Utils.showConfirmationAlert("Delete Confirmation","Are you sure you want to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try{
                DatabaseHelper.deleteUser(teacher.getId());
                teacherTable.getItems().remove(teacher);
            } catch (Exception e) {
                Utils.showAlert("Error","Failed to delete student");

            }
        } else {
            System.out.println("User chose Cancel. Aborting deletion.");
        }
    }
}

