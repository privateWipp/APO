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
    private Stage stage;

    @Override
    public void start(Stage stage) {
        instance = this;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.screenWidth = primaryScreenBounds.getWidth();
        this.screenHeight = primaryScreenBounds.getHeight();
        this.stage = stage;

        View view = new View();
        Scene scene = new Scene(view, getScreenWidth() * 0.25, getScreenHeight() * 0.4);
        this.stage.setTitle("APO");
        this.stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void close() {
        this.stage.close();
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