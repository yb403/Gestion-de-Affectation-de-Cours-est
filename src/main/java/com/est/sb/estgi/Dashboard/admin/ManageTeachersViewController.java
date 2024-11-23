package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.actors.Student;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ManageTeachersViewController {
    @FXML
    private TableView<Student> teacherTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    public TableColumn<Student, String>  emailColumn;

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

        // Populate the table with dummy data
        teacherTable.getItems().setAll(getStudents());
    }

    // Dummy data for students
    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (int i =0; i<20; i++){
            students.add(new Student(i,"Yassine","Ackrmane","dev@abstract.com","aahahahaa"));
        }
        return students;
    }

    // Handle the "Edit" button click
    public void handleEdit(Student student) {
        System.out.println("Edit student: " + student.getName());
        // Implement your edit logic here (e.g., show a dialog to edit student details)
    }

    // Handle the "Delete" button click
    public void handleDelete(Student student) {
        System.out.println("Delete student: " + student.getName());
        // Implement your delete logic here (e.g., remove student from the database)
        teacherTable.getItems().remove(student); // Remove from the TableView
    }
}

