package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import at.apo.model.Medikament;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

public class manageBestellungDialog extends Dialog<Bestellung> {
    private Bestellung bestellung;
    private ApoView view;
    private Apotheke model;
    private ListView<Medikament> medikamentenListView;
    private TextField kostenTF;

    public manageBestellungDialog(Bestellung bestellung, ApoView view, Apotheke model) {
        this.bestellung = bestellung;
        this.view = view;
        this.model = model;
        this.medikamentenListView = new ListView<Medikament>();
        loadMedikamentenListView();
        this.kostenTF = new TextField();

        setTitle(this.bestellung.getBezeichnung() + " : Bestellung verwalten");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label bezeichnungL = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField(this.bestellung.getBezeichnung());
        bezeichnungTF.setPromptText("Bezeichnung für die Bestellung");

        Label datumL = new Label("Datum:");
        DatePicker datePickerDP = new DatePicker(bestellung.getDatum());
        datePickerDP.setDisable(true);

        Label medikamenteL = new Label("Medikamente:");
        ComboBox<Medikament> medikamenteCB = new ComboBox<Medikament>();
        for (Medikament medikament : this.model.getMedikamente()) {
            medikamenteCB.getItems().add(medikament);
        }
        Button neuesMedikament = new Button("neues Medikament");
        neuesMedikament.setOnAction(e -> {
            addMedikamentDialog addMedikamentDialog = new addMedikamentDialog(this.model);
            Optional<Medikament> m = addMedikamentDialog.showAndWait();

            m.ifPresent(medikament -> {
                try {
                    this.model.addMedikament(medikament);
                    this.view.loadListViews();
                    this.view.setChanged(true);
                    System.out.println("Das Medikament " + medikament.getBezeichnung() + " wurde in die Apotheke " + this.model.getName() + " mit " + medikament.getLagerbestand() + " Stück aufgenommen.");

                    if (!this.medikamentenListView.getItems().contains(medikament)) {
                        this.medikamentenListView.getItems().add(medikament);
                        this.medikamentenListView.refresh();
                    } else {
                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("ACHTUNG");
                        information.setHeaderText("gleiches Medikament vorhanden");
                        information.setContentText("Ein genau gleiches Medikament existiert bereits in dieser Liste!");
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
            if (!this.medikamentenListView.getItems().contains(medikamenteCB.getValue())) {
                this.medikamentenListView.getItems().add(medikamenteCB.getValue());
                this.medikamentenListView.refresh();
                medikamenteCB.setValue(null);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Medikament hinzufügen");
                errorAlert.setHeaderText("übergebenes Medikament");
                errorAlert.setContentText("Das gewählte Medikament ist in der Liste bereits vorhanden!");
                medikamenteCB.setValue(null);
                errorAlert.showAndWait();
            }
        });

        Label kostenL = new Label("Gesamtkosten:");
        this.kostenTF.setPromptText("automatische Berechnung");
        this.kostenTF.setDisable(true);

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
        gridPane.add(this.kostenTF, 1, 4);
        gridPane.add(new Label("€"), 2, 4);

        getDialogPane().setContent(gridPane);

        this.medikamentenListView.getItems().addListener((ListChangeListener<Medikament>) c -> {
            updateKosten();
        });

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Bestellung verändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie diese Bestellung (" + this.bestellung.getBezeichnung() + ") verändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    try {
                        this.bestellung.setBezeichnung(bezeichnungTF.getText());
                        this.bestellung.setMedikamente(new ArrayList<Medikament>(this.medikamentenListView.getItems()));
                        this.bestellung.berechneGesamtkosten();
                        return this.bestellung;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Aufgeben einer neuen Bestellung");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    confirmation.close();
                }
            }
            return null;
        });
        updateKosten();
    }

    private void updateKosten() {
        double gesamtkosten = 0;
        for (Medikament medikament : this.medikamentenListView.getItems()) {
            gesamtkosten += medikament.getPreis();
        }
        this.kostenTF.setText(String.format("%.2f", gesamtkosten));
    }

    private void loadMedikamentenListView() {
        this.medikamentenListView.getItems().clear();
        for (Medikament medikament : this.bestellung.getMedikamente()) {
            this.medikamentenListView.getItems().add(medikament);
        }
    }
}
