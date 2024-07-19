package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import at.apo.model.Medikament;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class printAllMedikamente extends BorderPane {
    private APO apoInstance;
    private Apotheke originalModel;
    private Apotheke model;
    private TextArea textArea;
    private Stage stage;

    public printAllMedikamente(Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.originalModel = model;
        this.model = this.originalModel.clone();

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        updateTextArea();

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("alle Medikamente ausgeben : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.15, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortRezeptpflichtig = new MenuItem("sortieren nach rezeptpflichtig");
        MenuItem sortBezeichnung = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortPreis = new MenuItem("sortieren nach Preis");
        MenuItem sortLagerbestand = new MenuItem("sortieren nach Lagerbestand/Anzahl");
        MenuItem sortVerfallsdatum = new MenuItem("sortieren nach Verfallsdatum");
        MenuItem sortVerfuegbar = new MenuItem("sortieren nach Verfügbarkeit");
        bearbeiten.getItems().addAll(sortRezeptpflichtig, sortBezeichnung, sortPreis, sortLagerbestand, sortVerfallsdatum, sortVerfuegbar);

        menuBar.getMenus().add(bearbeiten);

        sortRezeptpflichtig.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::isRezeptpflichtig));
            updateTextArea();
        });

        sortBezeichnung.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getBezeichnung));
            updateTextArea();
        });
        sortPreis.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getPreis));
            updateTextArea();
        });
        sortLagerbestand.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getLagerbestand));
            updateTextArea();
        });
        sortVerfallsdatum.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getVerfallsdatum));
            updateTextArea();
        });
        sortVerfuegbar.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::isVerfuegbar));
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
        for(Medikament medikament : this.model.getMedikamente()) {
            this.textArea.appendText(medikament.toString() + "\n\n");
        }
    }
}
