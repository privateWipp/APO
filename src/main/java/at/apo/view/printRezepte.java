package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import at.apo.model.Rezept;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class printRezepte extends BorderPane {
    private APO apoInstance;
    private Apotheke originalModel;
    private Apotheke model;
    private TextArea textArea;
    private Stage stage;

    public printRezepte(Apotheke originalModel) {
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
        this.stage.setTitle("Rezepte-Liste ausgeben");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortRNr = new MenuItem("nach Rezeptnummer sortieren");
        MenuItem sortArzt = new MenuItem("nach Arzt sortieren");
        MenuItem sortAusstellungsdatum = new MenuItem("nach Ausstellungsdatum sortieren");
        MenuItem sortGueltigBis = new MenuItem("nach 'gültig bis' sortieren");
        MenuItem sortAnzWiederholungen = new MenuItem("nach Anz. d. Wiederholungen sortieren");
        MenuItem sortRezeptart = new MenuItem("nach Rezeptart sortieren");
        MenuItem sortPreis = new MenuItem("nach Preis sortieren");
        bearbeiten.getItems().addAll(sortRNr, sortArzt, sortAusstellungsdatum, sortGueltigBis, sortAnzWiederholungen, sortRezeptart, sortPreis);

        menuBar.getMenus().addAll(bearbeiten);

        setTop(menuBar);

        sortRNr.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getRezeptnummer));
            updateTextArea();
        });
        sortArzt.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getArzt));
            updateTextArea();
        });
        sortAusstellungsdatum.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getAusstellungsDatum));
            updateTextArea();
        });
        sortGueltigBis.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getGueltigBis));
            updateTextArea();
        });
        sortAnzWiederholungen.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getAnzDerWiederholungen));
            updateTextArea();
        });
        sortRezeptart.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getRezeptArt));
            updateTextArea();
        });
        sortPreis.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getPreis));
            updateTextArea();
        });

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.textArea);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    private void updateTextArea() {
        this.textArea.setText(null);
        for(Rezept rezept : this.model.getRezepte()) {
            this.textArea.appendText(rezept.toString() + "\n\n");
        }
    }
}
