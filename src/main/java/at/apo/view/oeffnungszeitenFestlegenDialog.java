package at.apo.view;

import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.HashMap;

public class oeffnungszeitenFestlegenDialog extends Dialog<HashMap<String, String>> {
    private Apotheke model;

    public oeffnungszeitenFestlegenDialog(Apotheke model, boolean change) {
        this.model = model;

        setTitle("Öffnungszeiten festlegen");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Text infoText = new Text("Wählen Sie bitte in der Reihenfolge:\n\n" +
                "Stunde/Minute - Stunde/Minute\n");
        gridPane.add(infoText, 0, 0, 7, 1);

        // --------------------------------MONTAG--------------------------------
        Label montagL = new Label("Montag:");
        ComboBox<Integer> montagH1 = new ComboBox<>();
        Label montagT1 = new Label(" : ");
        ComboBox<Integer> montagM1 = new ComboBox<>();
        Label montagBis = new Label(" bis ");
        ComboBox<Integer> montagH2 = new ComboBox<>();
        Label montagT2 = new Label(" : ");
        ComboBox<Integer> montagM2 = new ComboBox<>();

        gridPane.add(montagL, 0, 1);
        gridPane.add(montagH1, 1, 1);
        gridPane.add(montagT1, 2, 1);
        gridPane.add(montagM1, 3, 1);
        gridPane.add(montagBis, 4, 1);
        gridPane.add(montagH2, 5, 1);
        gridPane.add(montagT2, 6, 1);
        gridPane.add(montagM2, 7, 1);

        // --------------------------------DIENSTAG--------------------------------
        Label dienstagL = new Label("Dienstag:");
        ComboBox<Integer> dienstagH1 = new ComboBox<>();
        Label dienstagT1 = new Label(" : ");
        ComboBox<Integer> dienstagM1 = new ComboBox<>();
        Label dienstagBis = new Label(" bis ");
        ComboBox<Integer> dienstagH2 = new ComboBox<>();
        Label dienstagT2 = new Label(" : ");
        ComboBox<Integer> dienstagM2 = new ComboBox<>();

        gridPane.add(dienstagL, 0, 2);
        gridPane.add(dienstagH1, 1, 2);
        gridPane.add(dienstagT1, 2, 2);
        gridPane.add(dienstagM1, 3, 2);
        gridPane.add(dienstagBis, 4, 2);
        gridPane.add(dienstagH2, 5, 2);
        gridPane.add(dienstagT2, 6, 2);
        gridPane.add(dienstagM2, 7, 2);

        // --------------------------------MITTWOCH--------------------------------
        Label mittwochL = new Label("Mittwoch:");
        ComboBox<Integer> mittwochH1 = new ComboBox<>();
        Label mittwochT1 = new Label(" : ");
        ComboBox<Integer> mittwochM1 = new ComboBox<>();
        Label mittwochBis = new Label(" bis ");
        ComboBox<Integer> mittwochH2 = new ComboBox<>();
        Label mittwochT2 = new Label(" : ");
        ComboBox<Integer> mittwochM2 = new ComboBox<>();

        gridPane.add(mittwochL, 0, 3);
        gridPane.add(mittwochH1, 1, 3);
        gridPane.add(mittwochT1, 2, 3);
        gridPane.add(mittwochM1, 3, 3);
        gridPane.add(mittwochBis, 4, 3);
        gridPane.add(mittwochH2, 5, 3);
        gridPane.add(mittwochT2, 6, 3);
        gridPane.add(mittwochM2, 7, 3);

        // --------------------------------DONNERSTAG--------------------------------
        Label donnerstagL = new Label("Donnerstag:");
        ComboBox<Integer> donnerstagH1 = new ComboBox<>();
        Label donnerstagT1 = new Label(" : ");
        ComboBox<Integer> donnerstagM1 = new ComboBox<>();
        Label donnerstagBis = new Label(" bis ");
        ComboBox<Integer> donnerstagH2 = new ComboBox<>();
        Label donnerstagT2 = new Label(" : ");
        ComboBox<Integer> donnerstagM2 = new ComboBox<>();

        gridPane.add(donnerstagL, 0, 4);
        gridPane.add(donnerstagH1, 1, 4);
        gridPane.add(donnerstagT1, 2, 4);
        gridPane.add(donnerstagM1, 3, 4);
        gridPane.add(donnerstagBis, 4, 4);
        gridPane.add(donnerstagH2, 5, 4);
        gridPane.add(donnerstagT2, 6, 4);
        gridPane.add(donnerstagM2, 7, 4);

        // --------------------------------FREITAG--------------------------------
        Label freitagL = new Label("Freitag:");
        ComboBox<Integer> freitagH1 = new ComboBox<>();
        Label freitagT1 = new Label(" : ");
        ComboBox<Integer> freitagM1 = new ComboBox<>();
        Label freitagBis = new Label(" bis ");
        ComboBox<Integer> freitagH2 = new ComboBox<>();
        Label freitagT2 = new Label(" : ");
        ComboBox<Integer> freitagM2 = new ComboBox<>();

        gridPane.add(freitagL, 0, 5);
        gridPane.add(freitagH1, 1, 5);
        gridPane.add(freitagT1, 2, 5);
        gridPane.add(freitagM1, 3, 5);
        gridPane.add(freitagBis, 4, 5);
        gridPane.add(freitagH2, 5, 5);
        gridPane.add(freitagT2, 6, 5);
        gridPane.add(freitagM2, 7, 5);

        // -------------------------------- TIME OPTIONS --------------------------------
        for (int i = 0; i < 24; i++) {
            montagH1.getItems().add(i);
            montagH2.getItems().add(i);
            dienstagH1.getItems().add(i);
            dienstagH2.getItems().add(i);
            mittwochH1.getItems().add(i);
            mittwochH2.getItems().add(i);
            donnerstagH1.getItems().add(i);
            donnerstagH2.getItems().add(i);
            freitagH1.getItems().add(i);
            freitagH2.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            montagM1.getItems().add(i);
            montagM2.getItems().add(i);
            dienstagM1.getItems().add(i);
            dienstagM2.getItems().add(i);
            mittwochM1.getItems().add(i);
            mittwochM2.getItems().add(i);
            donnerstagM1.getItems().add(i);
            donnerstagM2.getItems().add(i);
            freitagM1.getItems().add(i);
            freitagM2.getItems().add(i);
        }

        ButtonType buttonType;

        if (change) {
            HashMap<String, String> oeffnungszeiten = model.getOeffnungszeiten();
            setComboBoxValues(montagH1, montagM1, montagH2, montagM2, oeffnungszeiten.get("Montag"));
            setComboBoxValues(dienstagH1, dienstagM1, dienstagH2, dienstagM2, oeffnungszeiten.get("Dienstag"));
            setComboBoxValues(mittwochH1, mittwochM1, mittwochH2, mittwochM2, oeffnungszeiten.get("Mittwoch"));
            setComboBoxValues(donnerstagH1, donnerstagM1, donnerstagH2, donnerstagM2, oeffnungszeiten.get("Donnerstag"));
            setComboBoxValues(freitagH1, freitagM1, freitagH2, freitagM2, oeffnungszeiten.get("Freitag"));

            buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        } else {
            buttonType = new ButtonType("Festlegen", ButtonBar.ButtonData.APPLY);
        }

        getDialogPane().getButtonTypes().add(buttonType);

        getDialogPane().setContent(gridPane);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    String montag = montagH1.getValue() + ":" + montagM1.getValue() + " bis " + montagH2.getValue() + ":" + montagM2.getValue();
                    String dienstag = dienstagH1.getValue() + ":" + dienstagM1.getValue() + " bis " + dienstagH2.getValue() + ":" + dienstagM2.getValue();
                    String mittwoch = mittwochH1.getValue() + ":" + mittwochM1.getValue() + " bis " + mittwochH2.getValue() + ":" + mittwochM2.getValue();
                    String donnerstag = donnerstagH1.getValue() + ":" + donnerstagM1.getValue() + " bis " + donnerstagH2.getValue() + ":" + donnerstagM2.getValue();
                    String freitag = freitagH1.getValue() + ":" + freitagM1.getValue() + " bis " + freitagH2.getValue() + ":" + freitagM2.getValue();

                    HashMap<String, String> oeffnungszeiten = new HashMap<>();
                    oeffnungszeiten.put("Montag", montag);
                    oeffnungszeiten.put("Dienstag", dienstag);
                    oeffnungszeiten.put("Mittwoch", mittwoch);
                    oeffnungszeiten.put("Donnerstag", donnerstag);
                    oeffnungszeiten.put("Freitag", freitag);

                    return oeffnungszeiten;
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Festlegen der Öffnungszeiten");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }

    private void setComboBoxValues(ComboBox<Integer> h1, ComboBox<Integer> m1, ComboBox<Integer> h2, ComboBox<Integer> m2, String zeit) {
        if (zeit != null && !zeit.isEmpty()) {
            String[] parts = zeit.split(" bis ");
            String[] start = parts[0].split(":");
            String[] end = parts[1].split(":");

            h1.setValue(Integer.parseInt(start[0]));
            m1.setValue(Integer.parseInt(start[1]));
            h2.setValue(Integer.parseInt(end[0]));
            m2.setValue(Integer.parseInt(end[1]));
        }
    }
}
