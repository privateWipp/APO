package at.apo.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.HashMap;

public class oeffnungszeitenFestlegenDialog extends Dialog<HashMap<String, String>> {
    public oeffnungszeitenFestlegenDialog() {
        setTitle("Öffnungszeiten festlegen");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setPadding(new Insets(10, 10, 0, 0));

        Text infoText = new Text("Wählen Sie bitte in der Reihenfolge:\n" +
                "Stunde/Minute - Stunde/Minute");

        // --------------------------------MONTAG--------------------------------
        Label montagL = new Label("Montag:");
        HBox montagHBox = new HBox();
        ComboBox<Integer> montagH1 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            montagH1.getItems().add(i);
        }
        Label montagT1 = new Label(" : ");
        ComboBox<Integer> montagM1 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            montagM1.getItems().add(i);
        }

        Label montagBis = new Label(" bis ");

        ComboBox<Integer> montagH2 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            montagH2.getItems().add(i);
        }
        Label montagT2 = new Label(" : ");
        ComboBox<Integer> montagM2 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            montagM2.getItems().add(i);
        }

        montagHBox.getChildren().addAll(montagH1, montagT1, montagM1, montagBis, montagH2, montagT2, montagM2);

        // --------------------------------DIENSTAG--------------------------------
        Label dienstagL = new Label("Dienstag:");
        HBox dienstagHBox = new HBox();
        ComboBox<Integer> dienstagH1 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            dienstagH1.getItems().add(i);
        }
        Label dienstagT1 = new Label(" : ");
        ComboBox<Integer> dienstagM1 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            dienstagM1.getItems().add(i);
        }

        Label dienstagBis = new Label(" bis ");

        ComboBox<Integer> dienstagH2 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            dienstagH2.getItems().add(i);
        }
        Label dienstagT2 = new Label(" : ");
        ComboBox<Integer> dienstagM2 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            dienstagM2.getItems().add(i);
        }

        dienstagHBox.getChildren().addAll(dienstagH1, dienstagT1, dienstagM1, dienstagBis, dienstagH2, dienstagT2, dienstagM2);

        // --------------------------------MITTWOCH--------------------------------
        Label mittwochL = new Label("Mittwoch:");
        HBox mittwochHBox = new HBox();
        ComboBox<Integer> mittwochH1 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            mittwochH1.getItems().add(i);
        }
        Label mittwochT1 = new Label(" : ");
        ComboBox<Integer> mittwochM1 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            mittwochM1.getItems().add(i);
        }

        Label mittwochBis = new Label(" bis ");

        ComboBox<Integer> mittwochH2 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            mittwochH2.getItems().add(i);
        }
        Label mittwochT2 = new Label(" : ");
        ComboBox<Integer> mittwochM2 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            mittwochM2.getItems().add(i);
        }

        mittwochHBox.getChildren().addAll(mittwochH1, mittwochT1, mittwochM1, mittwochBis, mittwochH2, mittwochT2, mittwochM2);

        // --------------------------------DONNERSTAG--------------------------------
        Label donnerstagL = new Label("Donnerstag:");
        HBox donnerstagHBox = new HBox();
        ComboBox<Integer> donnerstagH1 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            donnerstagH1.getItems().add(i);
        }
        Label donnerstagT1 = new Label(" : ");
        ComboBox<Integer> donnerstagM1 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            donnerstagM1.getItems().add(i);
        }

        Label donnerstagBis = new Label(" bis ");

        ComboBox<Integer> donnerstagH2 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            donnerstagH2.getItems().add(i);
        }
        Label donnerstagT2 = new Label(" : ");
        ComboBox<Integer> donnerstagM2 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            donnerstagM2.getItems().add(i);
        }

        donnerstagHBox.getChildren().addAll(donnerstagH1, donnerstagT1, donnerstagM1, donnerstagBis, donnerstagH2, donnerstagT2, donnerstagM2);

        // --------------------------------FREITAG--------------------------------
        Label freitagL = new Label("Freitag:");
        HBox freitagHBox = new HBox();
        ComboBox<Integer> freitagH1 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            freitagH1.getItems().add(i);
        }
        Label freitagT1 = new Label(" : ");
        ComboBox<Integer> freitagM1 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            freitagM1.getItems().add(i);
        }

        Label freitagBis = new Label(" bis ");

        ComboBox<Integer> freitagH2 = new ComboBox<Integer>();
        for(int i = 0; i < 24; i++) {
            freitagH2.getItems().add(i);
        }
        Label freitagT2 = new Label(" : ");
        ComboBox<Integer> freitagM2 = new ComboBox<Integer>();
        for(int i = 0; i < 60; i++) {
            freitagM2.getItems().add(i);
        }

        freitagHBox.getChildren().addAll(freitagH1, freitagT1, freitagM1, freitagBis, freitagH2, freitagT2, freitagM2);

        flowPane.getChildren().addAll(infoText, montagL, montagHBox, dienstagL, dienstagHBox, mittwochL, mittwochHBox, donnerstagL, donnerstagHBox, freitagL, freitagHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Festlegen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {

            }
            return null;
        });
    }
}
