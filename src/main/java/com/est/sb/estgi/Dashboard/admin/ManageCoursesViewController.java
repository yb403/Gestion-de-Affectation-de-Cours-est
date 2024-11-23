package com.est.sb.estgi.Dashboard.admin;

import com.est.sb.estgi.DashboardController;
import com.est.sb.estgi.DatabaseHelper;
import com.est.sb.estgi.actors.Cours;
import com.est.sb.estgi.actors.Student;
import com.est.sb.estgi.actors.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ManageCoursesViewController {
    @FXML
    private TableView<Cours> coursTable;

    @FXML
    private TableColumn<Cours, Integer> idColumn;

    @FXML
    private TableColumn<Cours, String> titleColumn;
    @FXML
    public TableColumn<Cours, String>  descriptionColumn;

    @FXML
    public TableColumn<Cours, String>  teacherColumn;
    @FXML
    private TableColumn<Cours, String> actionsColumn;

    @FXML
    public void initialize() {
        // Set up columns for the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        // Set the custom cell factory for the "Actions" column
        actionsColumn.setCellFactory(param -> new TableCell<Cours, String>() {
            private final Button detailsButton = new Button("Details");
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                // Edit button action
                editButton.setOnAction(event -> {
                    Cours cour = getTableRow().getItem();
                    if (cour != null) {
                        handleEdit(cour);
                    }
                });

                // Delete button action
                deleteButton.setOnAction(event -> {
                    Cours cour = getTableRow().getItem();
                    if (cour != null) {
                        handleDelete(cour);
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
                    HBox hbox = new HBox(10, detailsButton,editButton, deleteButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        // Populate the table with dummy data
        coursTable.getItems().setAll(getCours());
    }


    @FXML
    public void handleCreateCoursButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/est/sb/estgi/AddCoursView.fxml"));
            Parent editView = loader.load();
            DashboardController.getContentArea().getChildren().clear(); // Clear existing content
            DashboardController.getContentArea().getChildren().add(editView); // Add the edit view
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // Dummy data for cours
    private List<Cours> getCours() {
        try{
            return DatabaseHelper.getAllCours();
        } catch (Exception e) {
            e.printStackTrace();

            List<Cours> cours = new ArrayList<>();
            Teacher teacher = new Teacher(0,"ysf","baddi","baddi@est.sb","0101010101");
            for (int i =0; i<20; i++){
                cours.add(new Cours(i,"C++","Programmation shit",teacher));
            }
            return cours;
        }

    }

    // Handle the "Edit" button click
    public void handleEdit(Cours cour) {
        //System.out.println("Edit student: " + student.getName());
        // Implement your edit logic here (e.g., show a dialog to edit student details)
    }

    // Handle the "Delete" button click
    public void handleDelete(Cours cour) {
        //System.out.println("Delete student: " + student.getName());
        // Implement your delete logic here (e.g., remove student from the database)
        coursTable.getItems().remove(cour); // Remove from the TableView
    }
}

