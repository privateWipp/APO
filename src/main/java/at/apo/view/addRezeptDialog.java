package at.apo.view;

import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

public class addRezeptDialog extends Dialog<Rezept> {
    private ApoView view;
    private Apotheke model;
    private ListView<Medikament> medikamentenListView;

    public addRezeptDialog(ApoView view, Apotheke model) {
        this.view = view;
        this.model = model;
        this.medikamentenListView = new ListView<Medikament>();

        setTitle("neues Rezept freigeben : " + this.model.getName());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        Label kundeL = new Label("an Patient:");
        TextField kundeTF = new TextField();
        kundeTF.setPromptText("Nach- und Vorname des Patienten");
        kundeTF.setMinWidth(300);

        Label arztL = new Label("ausgeschrieben von (Arzt):");
        TextField arztTF = new TextField();
        arztTF.setPromptText("Vor- und Nachname des Arztes");
        arztTF.setMinWidth(300);

        Label medikamenteL = new Label("Medikamente:");
        ComboBox<Medikament> medikamenteCB = new ComboBox<Medikament>();
        for (Medikament medikament : this.model.getMedikamente()) {
            medikamenteCB.getItems().add(medikament);
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
                errorAlert.setContentText("Das gewählte Medikament ist in der Liste bereits vorhanden!");
                medikamenteCB.setValue(null);
                errorAlert.showAndWait();
            }
        });

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

                    if(!this.medikamentenListView.getItems().contains(medikament)) {
                        this.medikamentenListView.getItems().add(medikament);
                        this.medikamentenListView.refresh();
                    } else {
                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("ACHTUNG");
                        information.setHeaderText("gleiches Medikament");
                        information.setContentText("Ein genau gleiches Medikament existiert bereits in der Liste!");
                        information.showAndWait();
                    }
                } catch (APOException ex) {
                    this.view.errorAlert("Fehler beim Hinzufügen eines neuen Medikaments", ex.getMessage());
                    System.out.println("Fehler: Beim Aufnehmen eines neuen Medikaments in die Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
                }
            });
        });

        Label ausstellungsDatumL = new Label("Ausstellungsdatum:");
        TextField ausstellungsDatumTF = new TextField("JETZT/HEUTE");
        ausstellungsDatumTF.setDisable(true);

        Label gueltigBisL = new Label("gültig bis:");
        DatePicker gueltigBisDP = new DatePicker();

        Label anzWiederholungenL = new Label("Anz. an Wiederholungen:");
        ComboBox<Integer> anzWiederholungenCB = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            anzWiederholungenCB.getItems().add(i);
        }

        Label rezeptArtL = new Label("Rezeptart:");
        TextField rezeptArtTF = new TextField();
        rezeptArtTF.setPromptText("z.B.: elektronisches Rezept (E-Rezept)");

        Label bemerkungL = new Label("Bemerkung:");
        TextField bemerkungTF = new TextField();
        bemerkungTF.setPromptText("Wenn keine => Feld leer lassen");

        gridPane.add(kundeL, 0, 0);
        gridPane.add(kundeTF, 1, 0);
        gridPane.add(arztL, 0, 1);
        gridPane.add(arztTF, 1, 1);
        gridPane.add(medikamenteL, 0, 2);
        gridPane.add(medikamenteCB, 1, 2);
        gridPane.add(addMed, 2, 2);
        gridPane.add(neuesMedikament, 3, 2);
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

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    return new Rezept(new Kunde(kundeTF.getText()), arztTF.getText(), new ArrayList<Medikament>(this.medikamentenListView.getItems()), gueltigBisDP.getValue(), anzWiederholungenCB.getValue(), rezeptArtTF.getText(), bemerkungTF.getText());
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Hinzufügen eines neuen Rezepts");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}