package at.apo.view;

import at.apo.model.Apotheke;
import at.apo.model.Kunde;
import at.apo.model.Medikament;
import at.apo.model.Rezept;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class addRezeptDialog extends Dialog<Rezept> {
    private Apotheke model;
    private ArrayList<ComboBox<Medikament>> medikamentenCombos;

    public addRezeptDialog(Apotheke model) {
        this.model = model;

        setTitle("neues Rezept freigeben");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label kundeL = new Label("an Patient:");
        ComboBox<Kunde> kundeCB = new ComboBox<Kunde>();
        for(Kunde kunde : this.model.getKunden()) {
            kundeCB.getItems().add(kunde);
        }

        Label arztL = new Label("ausgeschrieben von (Arzt):");
        TextField arztTF = new TextField();
        arztTF.setPromptText("Vor- und Nachname des Arztes");

        Label anzMedikamentenL = new Label("anz. an Medikamenten:");
        ComboBox<Integer> anzMedikamentenCB = new ComboBox<Integer>();
        for(int i = 1; i <= 10; i++) {
            anzMedikamentenCB.getItems().add(i);
        }

        gridPane.add(kundeL, 0, 0);
        gridPane.add(kundeCB, 1, 0);
        gridPane.add(arztL, 0, 1);
        gridPane.add(arztTF, 1, 1);
        gridPane.add(anzMedikamentenL, 0, 2);
        gridPane.add(anzMedikamentenCB, 1, 2);

        this.medikamentenCombos = new ArrayList<ComboBox<Medikament>>();

        anzMedikamentenCB.valueProperty().addListener((obs, oldVal, newVal) -> {
            for (ComboBox<Medikament> comboBox : medikamentenCombos) {
                gridPane.getChildren().remove(comboBox);
            }
            medikamentenCombos.clear();

            if (newVal != null) {
                for (int i = 0; i < newVal; i++) {
                    ComboBox<Medikament> medikamentCB = new ComboBox<>();
                    for (Medikament medikament : this.model.getMedikamente()) {
                        medikamentCB.getItems().add(medikament);
                    }
                    medikamentenCombos.add(medikamentCB);
                    gridPane.add(medikamentCB, 1, 3 + i);
                }
            }
        });
    }
}
