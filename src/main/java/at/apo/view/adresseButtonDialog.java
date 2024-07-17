package at.apo.view;

import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class adresseButtonDialog extends Dialog<String> {
    private Apotheke model;

    public adresseButtonDialog(Apotheke model) {
        this.model = model;

        setTitle("Adresse ändern : " + this.model.getName());

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox adresseHBox = new HBox();
        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField(this.model.getAdresse());
        adresseHBox.getChildren().addAll(adresseL, adresseTF);
        adresseHBox.setPadding(new Insets(10, 10, 10, 10));
        adresseHBox.setSpacing(10);

        flowPane.getChildren().addAll(adresseHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Adresse ändern");
                confirmation.setHeaderText("Adresse der Apotheke " + this.model.getName() + " verändern");
                confirmation.setContentText("Sind Sie sicher, dass Sie die Adresse der Apotheke " + this.model.getName() + " von " + this.model.getAdresse() + " auf " + adresseTF.getText() + " ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    return adresseTF.getText();
                } else {
                    confirmation.close();
                }
            }
            return null;
        });
    }
}
