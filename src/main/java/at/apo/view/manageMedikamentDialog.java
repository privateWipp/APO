package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Medikament;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class manageMedikamentDialog extends Dialog<Medikament> {
    private Medikament medikament;

    public manageMedikamentDialog(Medikament medikament) {
        this.medikament = medikament;

        setTitle("Medikament: " + medikament.getBezeichnung() + " verwalten/ändern");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label bezeichnungL = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField(medikament.getBezeichnung());
        bezeichnungTF.setPromptText("Bsp.: Ibuprofen, Aspirin (C), Pilka, ...");
        gridPane.add(bezeichnungL, 0, 0);
        gridPane.add(bezeichnungTF, 1, 0);

        Label preisL = new Label("Preis:");
        TextField preisTF = new TextField(Double.toString(medikament.getPreis()));
        preisTF.setPromptText("Preis für das Medikament");
        gridPane.add(preisL, 0, 1);
        gridPane.add(preisTF, 1, 1);

        Label lagerbestandL = new Label("Lagerbestand/Anzahl:");
        TextField lagerbestandTF = new TextField(Integer.toString(medikament.getLagerbestand()));
        lagerbestandTF.setPromptText("mind. 1 - MAX. 500");
        gridPane.add(lagerbestandL, 0, 2);
        gridPane.add(lagerbestandTF, 1, 2);

        Label verfallsdatumL = new Label("Verfallsdatum:");
        DatePicker verfallsdatumDP = new DatePicker(medikament.getVerfallsdatum());
        gridPane.add(verfallsdatumL, 0, 3);
        gridPane.add(verfallsdatumDP, 1, 3);

        Label herstellerL = new Label("Hersteller:");
        TextField herstellerTF = new TextField(medikament.getHersteller());
        herstellerTF.setPromptText("Hersteller des Medikaments");
        gridPane.add(herstellerL, 0, 4);
        gridPane.add(herstellerTF, 1, 4);

        Label wirkstoffL = new Label("Wirkstoff/e:");
        TextField wirkstoffTF = new TextField(medikament.getWirkstoff());
        wirkstoffTF.setPromptText("Hauptwirkstoff/e des Medikaments");
        gridPane.add(wirkstoffL, 0, 5);
        gridPane.add(wirkstoffTF, 1, 5);

        Label dosierungL = new Label("Dosierung:");
        TextField dosierungTF = new TextField(medikament.getDosierung());
        dosierungTF.setPromptText("z.B.: 15ng / ml");
        gridPane.add(dosierungL, 0, 6);
        gridPane.add(dosierungTF, 1, 6);

        Label rezeptpflichtig = new Label("Rezeptpflichtig:");
        ComboBox<String> rezeptpflichtigCB = new ComboBox<String>();
        rezeptpflichtigCB.getItems().addAll("Ja", "Nein");
        rezeptpflichtigCB.setValue((medikament.isRezeptpflichtig() ? "Ja" : "Nein"));
        gridPane.add(rezeptpflichtig, 0, 7);
        gridPane.add(rezeptpflichtigCB, 1, 7);

        Label nebenwirkungenL = new Label("Nebenwirkungen:");
        TextField nebenwirkungenTF = new TextField(medikament.getNebenwirkungen());
        nebenwirkungenTF.setPromptText("Wenn keine => Feld leer lassen");
        gridPane.add(nebenwirkungenL, 0, 8);
        gridPane.add(nebenwirkungenTF, 1, 8);

        Label lagerbedingungenL = new Label("Lagerbedingungen:");
        TextField lagerbedingungenTF = new TextField(medikament.getLagerbedingungen());
        lagerbedingungenTF.setPromptText("Wenn keine => Feld leer lassen");
        gridPane.add(lagerbedingungenL, 0, 9);
        gridPane.add(lagerbedingungenTF, 1, 9);

        Label verfuegbarL = new Label("Verfügbar:");
        ComboBox<String> verfuegbarCB = new ComboBox<String>();
        verfuegbarCB.getItems().addAll("Ja", "Nein");
        verfuegbarCB.setValue((medikament.isVerfuegbar() ? "Ja" : "Nein"));
        gridPane.add(verfuegbarL, 0, 10);
        gridPane.add(verfuegbarCB, 1, 10);

        Label beschreibungL = new Label("Beschreibung:");
        TextField beschreibungTF = new TextField(medikament.getBeschreibung());
        beschreibungTF.setPromptText("Beschreibung für das Medikament");
        gridPane.add(beschreibungL, 0, 11);
        gridPane.add(beschreibungTF, 1, 11);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        getDialogPane().setContent(gridPane);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Medikament ändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie das Medikament " + medikament.getBezeichnung() + " verändern/bearbeiten wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == yes) {
                    try {
                        medikament.setBezeichnung(bezeichnungTF.getText());
                        medikament.setPreis(Double.parseDouble(preisTF.getText()));
                        medikament.setLagerbestand(Integer.parseInt(lagerbestandTF.getText()));
                        medikament.setVerfallsdatum(verfallsdatumDP.getValue());
                        medikament.setHersteller(herstellerTF.getText());
                        medikament.setWirkstoff(wirkstoffTF.getText());
                        medikament.setDosierung(dosierungTF.getText());
                        if(rezeptpflichtigCB.getValue().equals("Ja")) {
                            medikament.setRezeptpflichtig(true);
                        } else if(rezeptpflichtigCB.getValue().equals("Nein")){
                            medikament.setRezeptpflichtig(false);
                        }
                        medikament.setNebenwirkungen(nebenwirkungenTF.getText());
                        medikament.setLagerbedingungen(lagerbedingungenTF.getText());
                        if(verfuegbarCB.getValue().equals("Ja")) {
                            medikament.setVerfuegbar(true);
                        } else if(verfuegbarCB.getValue().equals("Nein")) {
                            medikament.setVerfuegbar(false);
                        }
                        medikament.setBeschreibung(beschreibungTF.getText());

                        return medikament;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Hinzufügen eines neuen Medikaments.");
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
