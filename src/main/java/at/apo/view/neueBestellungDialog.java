package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import at.apo.model.Medikament;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class neueBestellungDialog extends Dialog<Bestellung> {
    private ApoView view;
    private Apotheke model;
    private ListView<Medikament> medikamentenListView;
    private SimpleStringProperty kostenProperty;

    public neueBestellungDialog(ApoView view, Apotheke model) {
        this.view = view;
        this.model = model;
        this.medikamentenListView = new ListView<Medikament>();
        this.kostenProperty = new SimpleStringProperty();

        setTitle("neue Bestellung aufgeben");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label bezeichnungL = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField();
        bezeichnungTF.setPromptText("Bezeichnung für die Bestellung");

        Label datumL = new Label("Datum:");
        DatePicker datePickerDP = new DatePicker(LocalDate.now());
        datePickerDP.setDisable(true);

        Label medikamenteL = new Label("Medikamente:");
        ComboBox<Medikament> medikamenteCB = new ComboBox<Medikament>();
        for(Medikament medikament : this.model.getMedikamente()) {
            medikamenteCB.getItems().add(medikament);
        }
        Button neuesMedikament = new Button("neues Medikament");
        neuesMedikament.setOnAction(e -> {
            addMedikamentDialog addMedikamentDialog = new addMedikamentDialog();
            Optional<Medikament> m = addMedikamentDialog.showAndWait();

            m.ifPresent(medikament -> {
                try {
                    this.model.addMedikament(medikament);
                    this.view.loadListViews();
                    this.view.setChanged(true);
                    System.out.println("Das Medikament " + medikament.getBezeichnung() + " wurde in die Apotheke " + this.model.getName() + " mit " + medikament.getLagerbestand() + " Stück aufgenommen.");

                    if(!this.medikamentenListView.getItems().contains(medikament)) {
                        this.medikamentenListView.getItems().add(medikament);
                        this.medikamentenListView.refresh();
                    } else {
                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("Info / Warnung");
                        information.setHeaderText("gleiches Medikament");
                        information.setContentText("ACHTUNG:\n" + "Ein genau gleiches Medikament existiert bereits in der Liste!");
                        information.showAndWait();
                    }
                } catch (APOException ex) {
                    this.view.errorAlert("Fehler beim Hinzufügen eines neuen Medikaments", ex.getMessage());
                    System.out.println("Fehler: Beim Aufnehmen eines neuen Medikaments in die Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
                }
            });
        });
        Button addMed = new Button("+");
        addMed.disableProperty().bind(medikamenteCB.getSelectionModel().selectedItemProperty().isNull());
        addMed.setOnAction(e -> {
            if(medikamenteCB.getValue() != null && !this.medikamentenListView.getItems().contains(medikamenteCB.getValue())) {
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

        Label kostenL = new Label("Gesamtkosten:");
        TextField kostenTF = new TextField();
        kostenTF.setPromptText("automatische Berechnung");
        kostenTF.setDisable(true);
        kostenTF.textProperty().bind(this.kostenProperty);

        gridPane.add(bezeichnungL, 0, 0);
        gridPane.add(bezeichnungTF, 1, 0);
        gridPane.add(datumL, 0, 1);
        gridPane.add(datePickerDP, 1, 1);
        gridPane.add(medikamenteL, 0, 2);
        gridPane.add(medikamenteCB, 1, 2);
        gridPane.add(addMed, 2, 2);
        gridPane.add(neuesMedikament, 3, 2);
        gridPane.add(this.medikamentenListView, 1, 3);
        gridPane.add(kostenL, 0, 4);
        gridPane.add(kostenTF, 1, 4);
        gridPane.add(new Label("€"), 2, 4);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Bestellen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    return new Bestellung(bezeichnungTF.getText(), new ArrayList<Medikament>(this.medikamentenListView.getItems()));
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Aufgeben einer neuen Bestellung");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }

    private void updateKosten() {
        double gesamtkosten = 0;
        for(Medikament medikament : this.medikamentenListView.getItems()) {
            gesamtkosten += medikament.getPreis();
        }
        this.kostenProperty.set(String.format("%.2f €", gesamtkosten));
    }
}
