package at.apo.view;

import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class emailButtonDialog extends Dialog<String> {
    private Apotheke model;

    public emailButtonDialog(Apotheke model) {
        this.model = model;

        setTitle("E-Mail Adresse der Apotheke ändern");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox emailHBox = new HBox();
        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setText(this.model.getEmail());
        emailHBox.getChildren().addAll(emailL, emailTF);
        emailHBox.setPadding(new Insets(10, 10, 10, 10));
        emailHBox.setSpacing(10);

        flowPane.getChildren().addAll(emailHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("E-Mail Adresse ändern");
                confirmation.setHeaderText("E-Mail Adresse der Apotheke verändern");
                confirmation.setContentText("Sind Sie sicher, dass Sie die aktuelle E-Mail Adresse von der Apotheke " + this.model.getName() + " von " + this.model.getEmail() + " auf " + emailTF.getText() + " ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    return emailTF.getText();
                } else {
                    close();
                }
            }
            return null;
        });
    }
}
