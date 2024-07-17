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

        Menu rezeptpflichtig = new Menu("rezeptpflichtig");
        MenuItem sortBezeichnungR = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortPreisR = new MenuItem("sortieren nach Preis");
        MenuItem sortLagerbestandR = new MenuItem("sortieren nach Lagerbestand/Anzahl");
        MenuItem sortVerfallsdatumR = new MenuItem("sortieren nach Verfallsdatum");
        MenuItem sortVerfuegbarR = new MenuItem("sortieren nach Verfügbarkeit");
        rezeptpflichtig.getItems().addAll(sortBezeichnungR, sortPreisR, sortLagerbestandR, sortVerfallsdatumR, sortVerfuegbarR);

        Menu nichtRezeptpflichtig = new Menu("nicht rezeptpflichtig");
        MenuItem sortBezeichnungNR = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortPreisNR = new MenuItem("sortieren nach Preis");
        MenuItem sortLagerbestandNR = new MenuItem("sortieren nach Lagerbestand/Anzahl");
        MenuItem sortVerfallsdatumNR = new MenuItem("sortieren nach Verfallsdatum");
        MenuItem sortVerfuegbarNR = new MenuItem("sortieren nach Verfügbarkeit");
        nichtRezeptpflichtig.getItems().addAll(sortBezeichnungNR, sortPreisNR, sortLagerbestandNR, sortVerfallsdatumNR, sortVerfuegbarNR);

        bearbeiten.getItems().addAll(rezeptpflichtig, nichtRezeptpflichtig);

        menuBar.getMenus().add(bearbeiten);

        sortBezeichnungR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getBezeichnung));
            updateTextArea();
        });
        sortBezeichnungNR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getBezeichnung));
            updateTextArea();
        });
        sortPreisR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getPreis));
            updateTextArea();
        });
        sortPreisNR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getPreis));
            updateTextArea();
        });
        sortLagerbestandR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getLagerbestand));
            updateTextArea();
        });
        sortLagerbestandNR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getLagerbestand));
            updateTextArea();
        });
        sortVerfallsdatumR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getVerfallsdatum));
            updateTextArea();
        });
        sortVerfallsdatumNR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::getVerfallsdatum));
            updateTextArea();
        });
        sortVerfuegbarR.setOnAction(e -> {
            this.model.getMedikamente().sort(Comparator.comparing(Medikament::isVerfuegbar));
            updateTextArea();
        });
        sortVerfuegbarNR.setOnAction(e -> {
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
