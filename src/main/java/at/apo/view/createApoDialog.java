package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class createApoDialog extends Dialog<Apotheke> {
    public createApoDialog() {
        setTitle("neue Apotheke");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox nameHBox = new HBox();
        Label nameL = new Label("Name:");
        TextField nameTF = new TextField();
        nameTF.setPromptText("Name der Apotheke");
        nameHBox.getChildren().addAll(nameL, nameTF);
        nameHBox.setPadding(new Insets(10, 10, 0, 10));
        nameHBox.setSpacing(10);

        HBox adresseHBox = new HBox();
        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField();
        adresseTF.setPromptText("Adresse der Apotheke");
        adresseHBox.getChildren().addAll(adresseL, adresseTF);
        adresseHBox.setPadding(new Insets(10, 10, 0, 10));
        adresseHBox.setSpacing(10);

        HBox telefonnummerHBox = new HBox();
        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setPromptText("Telefonnummer eingeben");
        telefonnummerHBox.getChildren().addAll(telefonnummerL, telefonnummerTF);
        telefonnummerHBox.setPadding(new Insets(10, 10, 0, 10));
        telefonnummerHBox.setSpacing(10);

        HBox emailHBox = new HBox();
        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setPromptText("E-Mail Adresse der Apotheke");
        emailHBox.getChildren().addAll(emailL, emailTF);
        emailHBox.setPadding(new Insets(10, 10, 0, 10));
        emailHBox.setSpacing(10);

        HBox budgetHBox = new HBox();
        Label budgetL = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("Budget der Apotheke");
        budgetHBox.getChildren().addAll(budgetL, budgetTF);
        budgetHBox.setPadding(new Insets(10, 10, 0, 10));
        budgetHBox.setSpacing(10);

        flowPane.getChildren().addAll(nameHBox, adresseHBox, telefonnummerHBox, emailHBox, budgetHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Erstellen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * Was passiert, wenn auf den Button(-Type) "Erstellen" gedrückt wird?
         * =>
         */
        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    String nameTFInput = nameTF.getText();
                    String adresseTFInput = adresseTF.getText();
                    String telefonnummerTFInput = telefonnummerTF.getText();
                    String emailTFInput = emailTF.getText();
                    double budgetTFInput = Double.parseDouble(budgetTF.getText());

                    return new Apotheke(nameTFInput, adresseTFInput, telefonnummerTFInput, emailTFInput, budgetTFInput);
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
