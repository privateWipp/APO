package at.apo.view;

import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class nameButtonDialog extends Dialog<String> {
    private Apotheke model;

    public nameButtonDialog(Apotheke model) {
        this.model = model;

        setTitle("offiziellen Namen der Apotheke ändern");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox nameHBox = new HBox();
        Label nameL = new Label("Name:");
        TextField nameTF = new TextField();
        nameTF.setText(this.model.getName());
        nameHBox.getChildren().addAll(nameL, nameTF);
        nameHBox.setPadding(new Insets(10, 10, 10, 10));
        nameHBox.setSpacing(10);

        flowPane.getChildren().addAll(nameHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Namen ändern");
                confirmation.setHeaderText("offiziellen Namen verändern");
                confirmation.setContentText("Sind Sie sicher, dass Sie den momentanen offiziellen Namen der Apotheke von " + this.model.getName() + " auf " + nameTF.getText() + " ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    return nameTF.getText();
                } else {
                    close();
                }
            }
            return null;
        });
    }
}
