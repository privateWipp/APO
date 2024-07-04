package at.apo.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class aboutMe extends BorderPane {
    private Stage stage;

    public aboutMe() {
        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Über mich");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, 400, 300);
        this.stage.setScene(scene);

        // ÜBER MICH
        VBox ueberMichVBox = new VBox();
        ueberMichVBox.setPadding(new Insets(0, 10, 0, 10));
        Text ueberMich = new Text(
                "APO - Apothekensimulator\n\n" +
                        "APO ist ein von mir, Jonas Mader, selbstgeschriebens JavaFX-Programm.\n" +
                        "Es dient dazu eine/mehrere Apotheke/n zu simulieren und dabei\n" +
                        "verschiedenste Szenarien durchzuprobieren.\n\n" +
                        "Die Idee hatte ich bekommen aufgrund meines Praktikums.\n" +
                        "Zum Zeitpunkt, wo ich dieses Programm schreibe bin ich 15 Jahre alt\n" +
                        "und mache mein aller erstes Praktikum in der Firma 'Apotronik'.\n" +
                        "Daher auch der Name: APO");
        ueberMichVBox.getChildren().add(ueberMich);

        setCenter(ueberMichVBox);

        this.stage.show();
    }
}
