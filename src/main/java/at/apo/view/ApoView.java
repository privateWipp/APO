package at.apo.view;

import at.apo.control.ApoController;
import at.apo.model.Apotheke;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApoView extends BorderPane {
    private Apotheke model;
    private ApoController ctrl;
    private Stage stage;

    public ApoView(Apotheke apotheke) {
        this.model = apotheke;
        this.ctrl = new ApoController(this);
        this.stage = new Stage();
        this.stage.setTitle(this.model.getName() + " : Apotheke");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, 1280, 720);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu mitarbeiter = new Menu("Mitarbeiter");
        MenuItem manageEmployees = new MenuItem("verwalten");
        mitarbeiter.getItems().add(manageEmployees);

        menuBar.getMenus().addAll(mitarbeiter);

        setTop(menuBar);

        // Verwalten der Menüs
        manageEmployees.setOnAction(e -> this.ctrl.manageEmployees());

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }
}
