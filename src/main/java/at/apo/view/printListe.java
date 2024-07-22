package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import at.apo.model.Kunde;
import at.apo.model.Rezept;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class printListe extends BorderPane {
    private APO apoInstance;
    private Apotheke originalModel;
    private Apotheke model;
    private TextArea textArea;
    private Stage stage;

    public printListe(Apotheke originalModel) {
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
        this.stage.setTitle("alle Kunden ausgeben : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.35, this.apoInstance.getScreenHeight() * 0.5);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");

        Menu kunden = new Menu("Kunden");
        MenuItem sortName = new MenuItem("sortieren nach Namen");
        kunden.getItems().add(sortName);

        Menu rezepte = new Menu("Rezepte");
        MenuItem sortRNr = new MenuItem("nach Rezeptnummer sortieren");
        MenuItem sortArzt = new MenuItem("nach Arzt sortieren");
        MenuItem sortAusstellungsdatum = new MenuItem("nach Ausstellungsdatum sortieren");
        MenuItem sortGueltigBis = new MenuItem("nach 'gültig bis' sortieren");
        MenuItem sortAnzWiederholungen = new MenuItem("nach Anz. d. Wiederholungen sortieren");
        MenuItem sortRezeptart = new MenuItem("nach Rezeptart sortieren");
        MenuItem sortPreis = new MenuItem("nach Preis sortieren");
        rezepte.getItems().addAll(sortRNr, sortArzt, sortAusstellungsdatum, sortGueltigBis, sortAnzWiederholungen, sortRezeptart, sortPreis);

        bearbeiten.getItems().addAll(kunden, rezepte);

        menuBar.getMenus().addAll(bearbeiten);

        sortName.setOnAction(e -> {
            this.model.getKunden().sort(Comparator.comparing(Kunde::getName));
            updateTextArea();
        });

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

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.textArea);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    private void updateTextArea() {
        this.textArea.setText(null);
        for (Kunde kunde : this.model.getKunden()) {
            this.textArea.appendText(kunde.toString() + "\n");
            this.textArea.appendText("damit verbundene Rezepte:\n");
            for (Rezept rezept : this.model.getRezepte()) {
                if (rezept.getPatient().equals(kunde)) {
                    String formattedRezeptText = "\t" + rezept.toString().replace("\n", "\n" + "\t");
                    this.textArea.appendText(formattedRezeptText + "\n");
                }
            }
            this.textArea.appendText("\n");
        }
    }
}
