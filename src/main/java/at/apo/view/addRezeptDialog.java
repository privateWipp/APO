package at.apo.view;

import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class addRezeptDialog extends Dialog<Rezept> {
    private Apotheke model;
    private ListView<Medikament> medikamentenListView;

    public addRezeptDialog(Apotheke model) {
        this.model = model;
        this.medikamentenListView = new ListView<Medikament>();

        setTitle("neues Rezept freigeben");

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
        for(Medikament medikament : this.model.getMedikamente()) {
            medikamenteCB.getItems().add(medikament);
        }
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