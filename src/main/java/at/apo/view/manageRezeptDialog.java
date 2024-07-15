package at.apo.view;

import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class manageRezeptDialog extends Dialog<Rezept> {
    private Apotheke model;
    private Rezept rezept;
    private ArrayList<ComboBox<Medikament>> medikamentenCombos;

    public manageRezeptDialog(Apotheke model, Rezept rezept) {
        this.model = model;
        this.rezept = rezept;

        setTitle("Rezept bearbeiten");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        Label kundeL = new Label("an Patient:");
        TextField kundeTF = new TextField();
        kundeTF.setPromptText("Nach- und Vorname des Patienten");
        kundeTF.setText(this.rezept.getPatient().getName());
        kundeTF.setMinWidth(300);

        Label arztL = new Label("ausgeschrieben von (Arzt):");
        TextField arztTF = new TextField();
        arztTF.setPromptText("Vor- und Nachname des Arztes");
        arztTF.setText(this.rezept.getArzt());
        arztTF.setMinWidth(300);

        Label anzMedikamentenL = new Label("anz. an Medikamenten:");
        ComboBox<Integer> anzMedikamentenCB = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            anzMedikamentenCB.getItems().add(i);
        }
        anzMedikamentenCB.setValue(this.rezept.getMedikamente().size());

        gridPane.add(kundeL, 0, 0);
        gridPane.add(kundeTF, 1, 0);
        gridPane.add(arztL, 0, 1);
        gridPane.add(arztTF, 1, 1);
        gridPane.add(anzMedikamentenL, 0, 2);
        gridPane.add(anzMedikamentenCB, 1, 2);

        // Initialize the medication ComboBoxes based on the current prescription
        anzMedikamentenCB.getSelectionModel().select(this.rezept.getMedikamente().size());

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
        TextField rezeptArtTF = new TextField();
        rezeptArtTF.setPromptText("z.B.: elektronisches Rezept (E-Rezept)");
        rezeptArtTF.setText(this.rezept.getRezeptArt());

        Label bemerkungL = new Label("Bemerkung:");
        TextField bemerkungTF = new TextField();
        bemerkungTF.setPromptText("Bemerkungen für das Rezept");
        bemerkungTF.setText(this.rezept.getBemerkung());

        this.medikamentenCombos = new ArrayList<>();

        // Listener for the ComboBox that determines the number of medication ComboBoxes
        anzMedikamentenCB.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Remove existing ComboBoxes for medications
            for (ComboBox<Medikament> comboBox : this.medikamentenCombos) {
                gridPane.getChildren().remove(comboBox);
            }
            this.medikamentenCombos.clear();

            // Add new ComboBoxes for medications based on the selected number
            if (newVal != null) {
                for (int i = 0; i < newVal; i++) {
                    ComboBox<Medikament> medikamentCB = new ComboBox<>();
                    medikamentCB.setMinWidth(300);
                    for (Medikament medikament : this.model.getMedikamente()) {
                        medikamentCB.getItems().add(medikament);
                    }
                    medikamentenCombos.add(medikamentCB);
                    gridPane.add(medikamentCB, 1, 3 + i);
                }

                // Re-fill the medication ComboBoxes with existing prescription data
                for (int i = 0; i < this.rezept.getMedikamente().size(); i++) {
                    medikamentenCombos.get(i).setValue(this.rezept.getMedikamente().get(i));
                }
            }

            // Adjust positions of the following fields dynamically
            int startRow = 3 + (newVal != null ? newVal : 0);
            gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) >= 3 + (oldVal != null ? oldVal : 0));
            gridPane.add(ausstellungsDatumL, 0, startRow);
            gridPane.add(ausstellungsDatumTF, 1, startRow);
            gridPane.add(gueltigBisL, 0, startRow + 1);
            gridPane.add(gueltigBisDP, 1, startRow + 1);
            gridPane.add(anzWiederholungenL, 0, startRow + 2);
            gridPane.add(anzWiederholungenCB, 1, startRow + 2);
            gridPane.add(rezeptArtL, 0, startRow + 3);
            gridPane.add(rezeptArtTF, 1, startRow + 3);
            gridPane.add(bemerkungL, 0, startRow + 4);
            gridPane.add(bemerkungTF, 1, startRow + 4);
        });

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);

        VBox vbox = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        getDialogPane().setContent(vbox);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Rezept verändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie das aktuelle Rezept ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    try {
                        this.rezept.setPatient(new Kunde(kundeTF.getText()));
                        this.rezept.setArzt(arztTF.getText());
                        ArrayList<Medikament> medikamente = new ArrayList<>();
                        for (ComboBox<Medikament> medikamentCB : this.medikamentenCombos) {
                            medikamente.add(medikamentCB.getValue());
                        }
                        this.rezept.setMedikamente(medikamente);
                        this.rezept.setGueltigBis(gueltigBisDP.getValue());
                        this.rezept.setAnzDerWiederholungen(anzWiederholungenCB.getValue());
                        this.rezept.setRezeptArt(rezeptArtTF.getText());
                        this.rezept.setBemerkung(bemerkungTF.getText());
                        this.rezept.berechnePreis();

                        return this.rezept;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Ändern des Rezepts.");
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
