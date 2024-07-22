package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class displayOeffnungszeiten extends BorderPane {
    private APO apoInstance;
    private Apotheke model;

    private Stage stage;

    public displayOeffnungszeiten(Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.model = model;

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Öffnungszeiten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.35, this.apoInstance.getScreenHeight() * 0.5);
        this.stage.setScene(scene);

        // Öffnungszeiten der Apotheke IRGENDWIE anzeigen lassen...
        // ...
        // ...

        this.stage.show();
    }
}
