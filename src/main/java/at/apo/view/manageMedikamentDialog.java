package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Medikament;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class manageMedikamentDialog extends Dialog<Medikament> {
    private Apotheke model;
    private Medikament medikament;

    public manageMedikamentDialog(Apotheke model, Medikament medikament) {
        this.model = model;
        this.medikament = medikament;

        setTitle("Medikament (" + this.medikament.getBezeichnung() + ") verwalten : " + this.model.getName());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label bezeichnungL = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField(this.medikament.getBezeichnung());
        bezeichnungTF.setPromptText("Bsp.: Ibuprofen, Aspirin (C), Pilka, ...");

        Label preisL = new Label("Preis:");
        TextField preisTF = new TextField(Double.toString(this.medikament.getPreis()));
        preisTF.setPromptText("Preis für das Medikament");

        Label lagerbestandL = new Label("Lagerbestand/Anzahl:");
        TextField lagerbestandTF = new TextField(Integer.toString(this.medikament.getLagerbestand()));
        lagerbestandTF.setPromptText("mind. 1 - MAX. 500");

        Label verfallsdatumL = new Label("Verfallsdatum:");
        DatePicker verfallsdatumDP = new DatePicker(this.medikament.getVerfallsdatum());

        Label herstellerL = new Label("Hersteller:");
        TextField herstellerTF = new TextField(this.medikament.getHersteller());
        herstellerTF.setPromptText("Hersteller des Medikaments");

        Label wirkstoffL = new Label("Wirkstoff/e:");
        TextField wirkstoffTF = new TextField(this.medikament.getWirkstoff());
        wirkstoffTF.setPromptText("Hauptwirkstoff/e des Medikaments");

        Label dosierungL = new Label("Dosierung:");
        TextField dosierungTF = new TextField(this.medikament.getDosierung());
        dosierungTF.setPromptText("z.B.: 15ng / ml");

        Label rezeptpflichtig = new Label("Rezeptpflichtig:");
        ComboBox<String> rezeptpflichtigCB = new ComboBox<String>();
        rezeptpflichtigCB.getItems().addAll("Ja", "Nein");
        rezeptpflichtigCB.setValue((this.medikament.isRezeptpflichtig() ? "Ja" : "Nein"));

        Label nebenwirkungenL = new Label("Nebenwirkungen:");
        TextField nebenwirkungenTF = new TextField(this.medikament.getNebenwirkungen());
        nebenwirkungenTF.setPromptText("Wenn keine => Feld leer lassen");

        Label lagerbedingungenL = new Label("Lagerbedingungen:");
        TextField lagerbedingungenTF = new TextField(this.medikament.getLagerbedingungen());
        lagerbedingungenTF.setPromptText("Wenn keine => Feld leer lassen");

        Label verfuegbarL = new Label("Verfügbar:");
        ComboBox<String> verfuegbarCB = new ComboBox<String>();
        verfuegbarCB.getItems().addAll("Ja", "Nein");
        verfuegbarCB.setValue((this.medikament.isVerfuegbar() ? "Ja" : "Nein"));

        Label beschreibungL = new Label("Beschreibung:");
        TextField beschreibungTF = new TextField(this.medikament.getBeschreibung());
        beschreibungTF.setPromptText("Beschreibung für das Medikament");

        gridPane.add(bezeichnungL, 0, 0);
        gridPane.add(bezeichnungTF, 1, 0);
        gridPane.add(preisL, 0, 1);
        gridPane.add(preisTF, 1, 1);
        gridPane.add(new Label("€"), 2, 1);
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

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Medikament ändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie das Medikament " + this.medikament.getBezeichnung() + " verändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == yes) {
                    try {
                        this.medikament.setBezeichnung(bezeichnungTF.getText());
                        this.medikament.setPreis(Double.parseDouble(preisTF.getText()));
                        this.medikament.setLagerbestand(Integer.parseInt(lagerbestandTF.getText()));
                        this.medikament.setVerfallsdatum(verfallsdatumDP.getValue());
                        this.medikament.setHersteller(herstellerTF.getText());
                        this.medikament.setWirkstoff(wirkstoffTF.getText());
                        this.medikament.setDosierung(dosierungTF.getText());
                        if(rezeptpflichtigCB.getValue().equals("Ja")) {
                            this.medikament.setRezeptpflichtig(true);
                        } else if(rezeptpflichtigCB.getValue().equals("Nein")){
                            this.medikament.setRezeptpflichtig(false);
                        }
                        this.medikament.setNebenwirkungen(nebenwirkungenTF.getText());
                        this.medikament.setLagerbedingungen(lagerbedingungenTF.getText());
                        if(verfuegbarCB.getValue().equals("Ja")) {
                            this.medikament.setVerfuegbar(true);
                        } else if(verfuegbarCB.getValue().equals("Nein")) {
                            this.medikament.setVerfuegbar(false);
                        }
                        this.medikament.setBeschreibung(beschreibungTF.getText());

                        return this.medikament;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Verwalten des Medikaments: " + this.medikament.getBezeichnung());
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    confirmation.close();
                }
            }
            return null;
        });
    }
}
