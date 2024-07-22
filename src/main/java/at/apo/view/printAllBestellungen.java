package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class printAllBestellungen extends BorderPane {
    private APO apoInstance;
    private Apotheke originalModel;
    private Apotheke model;
    private TextArea textArea;
    private Stage stage;

    public printAllBestellungen(Apotheke originalModel) {
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
        this.stage.setTitle("alle Bestellungen ausgeben : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.35, this.apoInstance.getScreenHeight() * 0.5);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortBestellnummer = new MenuItem("sortieren nach Bestellnummer");
        MenuItem sortBezeichnung = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortDatum = new MenuItem("sortieren nach Datum");
        MenuItem sortKosten = new MenuItem("sortieren nach (Gesamt-)Kosten");
        MenuItem sortStatus = new MenuItem("sortieren nach (Bestell-)Status");
        bearbeiten.getItems().addAll(sortBestellnummer, sortBezeichnung, sortDatum, sortKosten, sortStatus);

        menuBar.getMenus().addAll(bearbeiten);

        sortBestellnummer.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBestellnummer));
            updateTextArea();
        });
        sortBezeichnung.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBezeichnung));
            updateTextArea();
        });
        sortDatum.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getDatum));
            updateTextArea();
        });
        sortKosten.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getGesamtkosten));
            updateTextArea();
        });
        sortStatus.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBestellstatus));
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
        for(Bestellung bestellung : this.model.getBestellungen()) {
            this.textArea.appendText(bestellung.toString() + "\n\n");
        }
    }
}
