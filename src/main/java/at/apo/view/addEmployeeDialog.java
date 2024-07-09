package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class addEmployeeDialog extends Dialog<Mitarbeiter> {
    public addEmployeeDialog() {
        setTitle("neuer Mitarbeiter");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField();
        nachnameTF.setPromptText("Nachname des Mitarbeiters");
        gridPane.add(nachnameL, 0, 0);
        gridPane.add(nachnameTF, 1, 0);

        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField();
        vornameTF.setPromptText("Vorname des Mitarbeiters");
        gridPane.add(vornameL, 0, 1);
        gridPane.add(vornameTF, 1, 1);

        Label geburtsdatumL = new Label("Geb. Dat.:");
        DatePicker geburtsdatumDP = new DatePicker();
        gridPane.add(geburtsdatumL, 0, 2);
        gridPane.add(geburtsdatumDP, 1, 2);

        Label geschlechtL = new Label("Geschlecht:");
        ComboBox<String> geschlechtCB = new ComboBox<>();
        geschlechtCB.getItems().addAll("Männlich", "Weiblich", "Inter", "Divers", "Offen", "keine Angabe");
        gridPane.add(geschlechtL, 0, 3);
        gridPane.add(geschlechtCB, 1, 3);

        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField();
        adresseTF.setPromptText("Adresse des Mitarbeiters");
        gridPane.add(adresseL, 0, 4);
        gridPane.add(adresseTF, 1, 4);

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setPromptText("Telefonnummer eingeben");
        gridPane.add(telefonnummerL, 0, 5);
        gridPane.add(telefonnummerTF, 1, 5);

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setPromptText("E-Mail Adresse vom Mitarbeiter");
        gridPane.add(emailL, 0, 6);
        gridPane.add(emailTF, 1, 6);

        Label gehaltL = new Label("Gehalt:");
        TextField gehaltTF = new TextField();
        gehaltTF.setPromptText("üblich: 2500");
        Label euroIcon = new Label("€");
        gridPane.add(gehaltL, 0, 7);
        gridPane.add(gehaltTF, 1, 7);
        gridPane.add(euroIcon, 2, 7);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    String nachnameTFInput = nachnameTF.getText();
                    String vornameTFInput = vornameTF.getText();
                    LocalDate geburtsdatumDPInput = geburtsdatumDP.getValue();
                    String geschlechtCBInput = geschlechtCB.getValue();
                    String adresseTFInput = adresseTF.getText();
                    String telefonnummerTFInput = telefonnummerTF.getText();
                    String emailTFInput = emailTF.getText();
                    double gehaltTFInput = Double.parseDouble(gehaltTF.getText());

                    return new Mitarbeiter(nachnameTFInput, vornameTFInput, geburtsdatumDPInput, geschlechtCBInput, adresseTFInput, telefonnummerTFInput, emailTFInput, gehaltTFInput);
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Erstellen des neuen Mitarbeiters.");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
