package at.apo;

import at.apo.view.View;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class APO extends Application {
    private double screenWidth;
    private double screenHeight;
    private static APO instance;

    @Override
    public void start(Stage stage) {
        instance = this;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.screenWidth = primaryScreenBounds.getWidth();
        this.screenHeight = primaryScreenBounds.getHeight();

        View view = new View();
        Scene scene = new Scene(view, getScreenWidth() * 0.25, getScreenHeight() * 0.4);
        stage.setTitle("APO");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public double getScreenWidth() {
        return this.screenWidth;
    }

    public double getScreenHeight() {
        return this.screenHeight;
    }

    public static APO getInstance() {
        return instance;
    }
}