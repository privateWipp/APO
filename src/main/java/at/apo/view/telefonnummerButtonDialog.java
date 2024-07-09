package at.apo.view;

import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class telefonnummerButtonDialog extends Dialog<String> {
    private Apotheke model;

    public telefonnummerButtonDialog(Apotheke model) {
        this.model = model;

        setTitle("Telefonnummer der Apotheke ändern");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox telefonnummerHBox = new HBox();
        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setText(this.model.getTelefonnummer());
        telefonnummerHBox.getChildren().addAll(telefonnummerL, telefonnummerTF);
        telefonnummerHBox.setPadding(new Insets(10, 10, 10, 10));
        telefonnummerHBox.setSpacing(10);

        flowPane.getChildren().addAll(telefonnummerHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Telefonnummer ändern");
                confirmation.setHeaderText("Telefonnummer der Apotheke verändern");
                confirmation.setContentText("Sind Sie sicher, dass Sie die aktuelle Telefonnummer von der Apotheke " + this.model.getName() + " von " + this.model.getTelefonnummer() + " auf " + telefonnummerTF.getText() + " ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    return telefonnummerTF.getText();
                } else {
                    close();
                }
            }
            return null;
        });
    }
}
