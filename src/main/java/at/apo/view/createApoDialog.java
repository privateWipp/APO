package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class createApoDialog extends Dialog<Apotheke> {
    public createApoDialog() {
        setTitle("neue Apotheke");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameL = new Label("Name:");
        TextField nameTF = new TextField();
        nameTF.setPromptText("Name der Apotheke");

        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField();
        adresseTF.setPromptText("Adresse der Apotheke");

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setPromptText("Telefonnummer eingeben");

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setPromptText("E-Mail Adresse der Apotheke");

        Label budgetL = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("Budget der Apotheke");

        gridPane.add(nameL, 0, 0);
        gridPane.add(nameTF, 1, 0);
        gridPane.add(adresseL, 0, 1);
        gridPane.add(adresseTF, 1, 1);
        gridPane.add(telefonnummerL, 0, 2);
        gridPane.add(telefonnummerTF, 1, 2);
        gridPane.add(emailL, 0, 3);
        gridPane.add(emailTF, 1, 3);
        gridPane.add(budgetL, 0, 4);
        gridPane.add(budgetTF, 1, 4);
        gridPane.add(new Label("€"), 2, 4);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Erstellen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    return new Apotheke(nameTF.getText(), adresseTF.getText(), telefonnummerTF.getText(), emailTF.getText(), Double.parseDouble(budgetTF.getText()));
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Erstellen der Apotheke");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
