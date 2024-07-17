package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Bestellung;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class changeStatusDialog extends Dialog<Bestellung> {
    private Bestellung bestellung;

    public changeStatusDialog(Bestellung bestellung) {
        this.bestellung = bestellung;

        setTitle(bestellung.getBezeichnung() + " : Bestellungsstatus ändern");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label statusL = new Label("Status:");
        ComboBox<String> statusCB = new ComboBox<String>();
        statusCB.getItems().addAll("BESTELLT", "VERSANDT", "ZUGESTELLT");
        statusCB.setValue(bestellung.getBestellstatus());

        gridPane.add(statusL, 0, 0);
        gridPane.add(statusCB, 1, 0);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                if (statusCB.getValue() != null) {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Sind Sie sicher?");
                    confirmation.setHeaderText("Bestellstatus ändern");
                    confirmation.setContentText("Sind Sie sicher, dass sie den Bestellstatus von '" + this.bestellung.getBestellstatus() + "' auf '" + statusCB.getValue() + "' ändern wollen?");

                    ButtonType yes = new ButtonType("Ja");
                    ButtonType no = new ButtonType("Nein");
                    ButtonType cancel = new ButtonType("Abbrechen");

                    confirmation.getButtonTypes().setAll(yes, no, cancel);

                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isPresent() && result.get() == yes) {
                        try {
                            this.bestellung.setBestellstatus(statusCB.getValue());
                            return this.bestellung;
                        } catch (APOException e) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Fehler");
                            errorAlert.setHeaderText("Fehler beim Ändern des Bestellstatus der Bestellung, mit der Bezeichnung: " + this.bestellung.getBezeichnung());
                            errorAlert.setContentText(e.getMessage());
                            errorAlert.showAndWait();
                        }
                    } else {
                        confirmation.close();
                    }
                } else {
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("ACHTUNG");
                    information.setHeaderText("ausgewählter Bestellstatus");
                    information.setContentText("Sie haben keinen Bestellstatus ausgewählt!\nKeine Änderungen werden vorgenommen.");
                    information.showAndWait();
                }
            }
            return null;
        });
    }
}
