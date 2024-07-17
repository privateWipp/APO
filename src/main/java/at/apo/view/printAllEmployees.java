package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class printAllEmployees extends BorderPane {
    private APO apoInstance;
    private Apotheke originalModel;
    private Apotheke model;
    private TextArea textArea;
    private Stage stage;

    public printAllEmployees(Apotheke originalModel) {
        this.apoInstance = APO.getInstance();
        this.originalModel = originalModel;
        this.model = this.originalModel.clone();

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        updateTextArea();

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("alle Mitarbeiter ausgeben : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortID = new MenuItem("nach ID sortieren");
        MenuItem sortNachname = new MenuItem("nach Nachnamen sortieren");
        MenuItem sortVorname = new MenuItem("nach Vornamen sortieren");
        MenuItem sortGebDat = new MenuItem("nach Geb. Dat. sortieren");
        MenuItem sortGehalt = new MenuItem("nach Gehalt sortieren");
        bearbeiten.getItems().addAll(sortID, sortNachname, sortVorname, sortGebDat, sortGehalt);

        menuBar.getMenus().addAll(bearbeiten);

        sortID.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getId));
            updateTextArea();
        });
        sortNachname.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getNachname));
            updateTextArea();
        });
        sortVorname.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getVorname));
            updateTextArea();
        });
        sortGebDat.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getGeburtsdatum));
            updateTextArea();
        });
        sortGehalt.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getGehalt));
            updateTextArea();
        });

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.textArea);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    private void updateTextArea() {
        this.textArea.setText(null);
        for(Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
            this.textArea.appendText(mitarbeiter.toString() + "\n\n");
        }
    }
}
