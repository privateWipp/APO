package at.apo;

import at.apo.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class APO extends Application {
    @Override
    public void start(Stage stage) {
        View view = new View();
        Scene scene = new Scene(view, 800, 600);
        stage.setTitle("APO");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}