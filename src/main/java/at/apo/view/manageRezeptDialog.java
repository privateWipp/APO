package at.apo.view;

import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class manageRezeptDialog extends Dialog<Rezept> {
    private Apotheke model;
    private Rezept rezept;
    private ListView<Medikament> medikamentenListView;

    public manageRezeptDialog(Apotheke model, Rezept rezept) {
        this.model = model;
        this.rezept = rezept;
        this.medikamentenListView = new ListView<Medikament>();

        setTitle("Rezept ändern");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        Label kundeL = new Label("an Patient:");
        TextField kundeTF = new TextField(this.rezept.getPatient().getName());
        kundeTF.setPromptText("Nach- und Vorname des Patienten");
        kundeTF.setMinWidth(300);

        Label arztL = new Label("ausgeschrieben von (Arzt):");
        TextField arztTF = new TextField(this.rezept.getArzt());
        arztTF.setPromptText("Vor- und Nachname des Arztes");
        arztTF.setMinWidth(300);

        Label medikamenteL = new Label("Medikamente:");
        ComboBox<Medikament> medikamenteCB = new ComboBox<Medikament>();
        for (Medikament medikament : this.model.getMedikamente()) {
            medikamenteCB.getItems().add(medikament);
        }
        for (Medikament medikament : this.rezept.getMedikamente()) {
            this.medikamentenListView.getItems().add(medikament);
        }
        Button addMed = new Button("+");
        addMed.disableProperty().bind(medikamenteCB.getSelectionModel().selectedItemProperty().isNull());
        addMed.setOnAction(e -> {
            if (medikamenteCB.getValue() != null && !this.medikamentenListView.getItems().contains(medikamenteCB.getValue())) {
                this.medikamentenListView.getItems().add(medikamenteCB.getValue());
                this.medikamentenListView.refresh();
                medikamenteCB.setValue(null);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Medikament hinzufügen");
                errorAlert.setHeaderText("übergebenes Medikament");
                errorAlert.setContentText("Das übergebene/ausgewählte Medikament für die Liste ist entweder ungültig oder bereits vorhanden!");
                medikamenteCB.setValue(null);
                errorAlert.showAndWait();
            }
        });

        Label ausstellungsDatumL = new Label("Ausstellungsdatum:");
        TextField ausstellungsDatumTF = new TextField(this.rezept.getAusstellungsDatum().toString());
        ausstellungsDatumTF.setDisable(true);

        Label gueltigBisL = new Label("gültig bis:");
        DatePicker gueltigBisDP = new DatePicker(this.rezept.getGueltigBis());

        Label anzWiederholungenL = new Label("Anz. an Wiederholungen:");
        ComboBox<Integer> anzWiederholungenCB = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            anzWiederholungenCB.getItems().add(i);
        }
        anzWiederholungenCB.setValue(this.rezept.getAnzDerWiederholungen());

        Label rezeptArtL = new Label("Rezeptart:");
        TextField rezeptArtTF = new TextField(this.rezept.getRezeptArt());
        rezeptArtTF.setPromptText("z.B.: elektronisches Rezept (E-Rezept)");

        Label bemerkungL = new Label("Bemerkung:");
        TextField bemerkungTF = new TextField(this.rezept.getBemerkung());
        bemerkungTF.setPromptText("Bemerkungen für das Rezept");

        gridPane.add(kundeL, 0, 0);
        gridPane.add(kundeTF, 1, 0);
        gridPane.add(arztL, 0, 1);
        gridPane.add(arztTF, 1, 1);
        gridPane.add(medikamenteL, 0, 2);
        gridPane.add(medikamenteCB, 1, 2);
        gridPane.add(addMed, 2, 2);
        gridPane.add(this.medikamentenListView, 1, 3);
        gridPane.add(ausstellungsDatumL, 0, 4);
        gridPane.add(ausstellungsDatumTF, 1, 4);
        gridPane.add(gueltigBisL, 0, 5);
        gridPane.add(gueltigBisDP, 1, 5);
        gridPane.add(anzWiederholungenL, 0, 6);
        gridPane.add(anzWiederholungenCB, 1, 6);
        gridPane.add(rezeptArtL, 0, 7);
        gridPane.add(rezeptArtTF, 1, 7);
        gridPane.add(bemerkungL, 0, 8);
        gridPane.add(bemerkungTF, 1, 8);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Rezept ändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie das aktuelle Rezept verändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    try {
                        this.rezept.setPatient(new Kunde(kundeTF.getText()));
                        this.rezept.setArzt(arztTF.getText());
                        this.rezept.setMedikamente(new ArrayList<Medikament>(this.medikamentenListView.getItems()));
                        this.rezept.setGueltigBis(gueltigBisDP.getValue());
                        this.rezept.setAnzDerWiederholungen(anzWiederholungenCB.getValue());
                        this.rezept.setRezeptArt(rezeptArtTF.getText());
                        this.rezept.berechnePreis();
                        this.rezept.setBemerkung(bemerkungTF.getText());

                        return this.rezept;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Hinzufügen eines neuen Rezepts");
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