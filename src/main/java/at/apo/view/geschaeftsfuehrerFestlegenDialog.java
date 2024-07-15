package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Geschaeftsfuehrer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class geschaeftsfuehrerFestlegenDialog extends Dialog<Geschaeftsfuehrer> {
    private Apotheke model;

    public geschaeftsfuehrerFestlegenDialog(Apotheke model, boolean change) {
        this.model = model;

        setTitle("Geschäftsführer festlegen");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField();
        nachnameTF.setPromptText("Nachname des Geschäftsführers");

        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField();
        vornameTF.setPromptText("Vorname des Geschäftsführers");

        Label geburtsdatumL = new Label("Geb. Dat.:");
        DatePicker geburtsdatumDP = new DatePicker();

        Label geschlechtL = new Label("Geschlecht:");
        ComboBox<String> geschlechtCB = new ComboBox<>();
        geschlechtCB.getItems().addAll("Männlich", "Weiblich", "Inter", "Divers", "Offen", "keine Angabe");

        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField();
        adresseTF.setPromptText("Adresse des Geschäftsführers");

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setPromptText("Telefonnummer eingeben");

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setPromptText("E-Mail Adresse vom Geschäftsführer");

        gridPane.add(nachnameL, 0, 0);
        gridPane.add(nachnameTF, 1, 0);
        gridPane.add(vornameL, 0, 1);
        gridPane.add(vornameTF, 1, 1);
        gridPane.add(geburtsdatumL, 0, 2);
        gridPane.add(geburtsdatumDP, 1, 2);
        gridPane.add(geschlechtL, 0, 3);
        gridPane.add(geschlechtCB, 1, 3);
        gridPane.add(adresseL, 0, 4);
        gridPane.add(adresseTF, 1, 4);
        gridPane.add(telefonnummerL, 0, 5);
        gridPane.add(telefonnummerTF, 1, 5);
        gridPane.add(emailL, 0, 6);
        gridPane.add(emailTF, 1, 6);

        ButtonType buttonType;

        if (change) {
            nachnameTF.setText(this.model.getGeschaeftsfuehrer().getNachname());
            vornameTF.setText(this.model.getGeschaeftsfuehrer().getVorname());
            geburtsdatumDP.setValue(this.model.getGeschaeftsfuehrer().getGeburtsdatum());
            geschlechtCB.setValue(this.model.getGeschaeftsfuehrer().getGeschlecht());
            adresseTF.setText(this.model.getGeschaeftsfuehrer().getAdresse());
            telefonnummerTF.setText(this.model.getGeschaeftsfuehrer().getTelefonnummer());
            emailTF.setText(this.model.getGeschaeftsfuehrer().getEmail());

            buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        } else {
            buttonType = new ButtonType("Festlegen", ButtonBar.ButtonData.APPLY);
        }

        getDialogPane().getButtonTypes().add(buttonType);
        getDialogPane().setContent(gridPane);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    return new Geschaeftsfuehrer(nachnameTF.getText(), vornameTF.getText(), geburtsdatumDP.getValue(), geschlechtCB.getValue(), adresseTF.getText(), telefonnummerTF.getText(), emailTF.getText());
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Festlegen des Geschäftsführers.");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
