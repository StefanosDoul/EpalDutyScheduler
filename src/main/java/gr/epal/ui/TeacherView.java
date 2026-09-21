package gr.epal.ui;

import gr.epal.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherView extends VBox {

    private final TableView<TeacherRow> teacherTable;

    public TeacherView() {

        setPadding(new Insets(20));
        setSpacing(15);

        Label title = new Label("Καθηγητές");

        teacherTable = new TableView<>();

        createColumns();
        loadTeachers();

        getChildren().addAll(title, teacherTable);
    }

    private void createColumns() {

        TableColumn<TeacherRow, String> lastNameColumn =
                new TableColumn<>("Επώνυμο");

        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        TableColumn<TeacherRow, String> firstNameColumn =
                new TableColumn<>("Όνομα");

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        TableColumn<TeacherRow, String> statusColumn =
                new TableColumn<>("Κατάσταση");

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        teacherTable.getColumns().addAll(
                lastNameColumn,
                firstNameColumn,
                statusColumn
        );
    }

    private void loadTeachers() {

        ObservableList<TeacherRow> teachers =
                FXCollections.observableArrayList();

        String sql = """
                SELECT id, first_name, last_name, active
                FROM Teacher
                ORDER BY last_name, first_name
                """;

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                boolean active =
                        resultSet.getInt("active") == 1;

                String status = active ? "Ενεργός" : "Ανενεργός";

                teachers.add(
                        new TeacherRow(
                                id,
                                firstName,
                                lastName,
                                status
                        )
                );
            }

            teacherTable.setItems(teachers);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static class TeacherRow {

        private final int id;
        private final String firstName;
        private final String lastName;
        private final String status;

        public TeacherRow(
                int id,
                String firstName,
                String lastName,
                String status) {

            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getStatus() {
            return status;
        }
    }
}