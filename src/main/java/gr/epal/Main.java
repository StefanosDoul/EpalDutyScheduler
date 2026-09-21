package gr.epal;

import gr.epal.ui.TeacherView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        TeacherView teacherView = new TeacherView();

        Scene scene = new Scene(teacherView, 800, 500);

        stage.setTitle("Πρόγραμμα Εφημεριών ΕΠΑΛ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}