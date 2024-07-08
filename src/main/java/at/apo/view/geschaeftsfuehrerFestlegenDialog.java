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
        gridPane.add(nachnameL, 0, 0);
        gridPane.add(nachnameTF, 1, 0);

        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField();
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
        gridPane.add(adresseL, 0, 4);
        gridPane.add(adresseTF, 1, 4);

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        gridPane.add(telefonnummerL, 0, 5);
        gridPane.add(telefonnummerTF, 1, 5);

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
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
            nachnameTF.setPromptText("Nachname des Geschäftsführers");
            vornameTF.setPromptText("Vorname des Geschäftsführers");
            adresseTF.setPromptText("Adresse des Geschäftsführers");
            telefonnummerTF.setPromptText("Telefonnummer eingeben");
            emailTF.setPromptText("E-Mail Adresse vom Geschäftsführer");

            buttonType = new ButtonType("Festlegen", ButtonBar.ButtonData.APPLY);
        }

        getDialogPane().getButtonTypes().add(buttonType);
        getDialogPane().setContent(gridPane);

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

                    return new Geschaeftsfuehrer(nachnameTFInput, vornameTFInput, geburtsdatumDPInput, geschlechtCBInput, adresseTFInput, telefonnummerTFInput, emailTFInput);
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
