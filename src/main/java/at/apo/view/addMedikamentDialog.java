package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Medikament;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class addMedikamentDialog extends Dialog<Medikament> {
    public addMedikamentDialog() {
        setTitle("neues Medikament");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label bezeichnungL = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField();
        bezeichnungTF.setPromptText("Bsp.: Ibuprofen, Aspirin (C), Pilka, ...");

        Label preisL = new Label("Preis:");
        TextField preisTF = new TextField();
        preisTF.setPromptText("Preis für das Medikament");

        Label lagerbestandL = new Label("Lagerbestand/Anzahl:");
        TextField lagerbestandTF = new TextField();
        lagerbestandTF.setPromptText("mind. 1 - MAX. 500");

        Label verfallsdatumL = new Label("Verfallsdatum:");
        DatePicker verfallsdatumDP = new DatePicker();

        Label herstellerL = new Label("Hersteller:");
        TextField herstellerTF = new TextField();
        herstellerTF.setPromptText("Hersteller des Medikaments");

        Label wirkstoffL = new Label("Wirkstoff/e:");
        TextField wirkstoffTF = new TextField();
        wirkstoffTF.setPromptText("Hauptwirkstoff/e des Medikaments");

        Label dosierungL = new Label("Dosierung:");
        TextField dosierungTF = new TextField();
        dosierungTF.setPromptText("z.B.: 15ng / ml");

        Label rezeptpflichtig = new Label("Rezeptpflichtig:");
        ComboBox<String> rezeptpflichtigCB = new ComboBox<String>();
        rezeptpflichtigCB.getItems().addAll("Ja", "Nein");

        Label nebenwirkungenL = new Label("Nebenwirkungen:");
        TextField nebenwirkungenTF = new TextField();
        nebenwirkungenTF.setPromptText("Wenn keine => Feld leer lassen");

        Label lagerbedingungenL = new Label("Lagerbedingungen:");
        TextField lagerbedingungenTF = new TextField();
        lagerbedingungenTF.setPromptText("Wenn keine => Feld leer lassen");

        Label verfuegbarL = new Label("Verfügbar:");
        ComboBox<String> verfuegbarCB = new ComboBox<String>();
        verfuegbarCB.getItems().addAll("Ja", "Nein");

        Label beschreibungL = new Label("Beschreibung:");
        TextField beschreibungTF = new TextField();
        beschreibungTF.setPromptText("Beschreibung für das Medikament");

        gridPane.add(bezeichnungL, 0, 0);
        gridPane.add(bezeichnungTF, 1, 0);
        gridPane.add(preisL, 0, 1);
        gridPane.add(preisTF, 1, 1);
        gridPane.add(lagerbestandL, 0, 2);
        gridPane.add(lagerbestandTF, 1, 2);
        gridPane.add(verfallsdatumL, 0, 3);
        gridPane.add(verfallsdatumDP, 1, 3);
        gridPane.add(herstellerL, 0, 4);
        gridPane.add(herstellerTF, 1, 4);
        gridPane.add(wirkstoffL, 0, 5);
        gridPane.add(wirkstoffTF, 1, 5);
        gridPane.add(dosierungL, 0, 6);
        gridPane.add(dosierungTF, 1, 6);
        gridPane.add(rezeptpflichtig, 0, 7);
        gridPane.add(rezeptpflichtigCB, 1, 7);
        gridPane.add(nebenwirkungenL, 0, 8);
        gridPane.add(nebenwirkungenTF, 1, 8);
        gridPane.add(lagerbedingungenL, 0, 9);
        gridPane.add(lagerbedingungenTF, 1, 9);
        gridPane.add(verfuegbarL, 0, 10);
        gridPane.add(verfuegbarCB, 1, 10);
        gridPane.add(beschreibungL, 0, 11);
        gridPane.add(beschreibungTF, 1, 11);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    String bezeichnungTFInput = bezeichnungTF.getText();
                    double preisTFInput = Double.parseDouble(preisTF.getText());
                    int lagerbestandTFInput = Integer.parseInt(lagerbestandTF.getText());
                    LocalDate verfallsdatumDPInput = verfallsdatumDP.getValue();
                    String herstellerTFInput = herstellerTF.getText();
                    String wirkstoffTFInput = wirkstoffTF.getText();
                    String dosierungTFInput = dosierungTF.getText();
                    boolean rezeptpflichtigCBInput = false;
                    if(rezeptpflichtigCB.getValue().equals("Ja")) {
                        rezeptpflichtigCBInput = true;
                    }
                    String nebenwirkungenTFInput = nebenwirkungenTF.getText();
                    String lagerbedingungenTFInput = lagerbedingungenTF.getText();
                    boolean verfuegbarCBInput = false;
                    if(verfuegbarCB.getValue().equals("Ja")) {
                        verfuegbarCBInput = true;
                    }
                    String beschreibungTFInput = beschreibungTF.getText();

                    return new Medikament(bezeichnungTFInput, preisTFInput, lagerbestandTFInput, verfallsdatumDPInput, herstellerTFInput, wirkstoffTFInput, dosierungTFInput, rezeptpflichtigCBInput, nebenwirkungenTFInput, lagerbedingungenTFInput, verfuegbarCBInput, beschreibungTFInput);
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Hinzufügen eines neuen Medikaments.");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
