package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class manageEmployeeDialog extends Dialog<Mitarbeiter> {
    private Apotheke model;
    private Mitarbeiter mitarbeiter;

    public manageEmployeeDialog(Apotheke model, Mitarbeiter mitarbeiter) {
        this.model = model;
        this.mitarbeiter = mitarbeiter;

        setTitle("Mitarbeiter (" + this.mitarbeiter.getVorname() + " " + this.mitarbeiter.getNachname() + ") verwalten : " + this.model.getName());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField(this.mitarbeiter.getNachname());
        nachnameTF.setPromptText("Nachname des Mitarbeiters");

        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField(this.mitarbeiter.getVorname());
        vornameTF.setPromptText("Vorname des Mitarbeiters");

        Label geburtsdatumL = new Label("Geb. Dat.:");
        DatePicker geburtsdatumDP = new DatePicker(this.mitarbeiter.getGeburtsdatum());

        Label geschlechtL = new Label("Geschlecht:");
        ComboBox<String> geschlechtCB = new ComboBox<>();
        geschlechtCB.getItems().addAll("Männlich", "Weiblich", "Inter", "Divers", "Offen", "keine Angabe");
        geschlechtCB.setValue(this.mitarbeiter.getGeschlecht());

        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField(this.mitarbeiter.getAdresse());
        adresseTF.setPromptText("Adresse des Mitarbeiters");

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField(this.mitarbeiter.getTelefonnummer());
        telefonnummerTF.setPromptText("Telefonnummer eingeben");

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField(this.mitarbeiter.getEmail());
        emailTF.setPromptText("E-Mail Adresse vom Mitarbeiter");

        Label gehaltL = new Label("Gehalt:");
        TextField gehaltTF = new TextField(Double.toString(this.mitarbeiter.getGehalt()));
        gehaltTF.setPromptText("üblich: 2500");

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
        gridPane.add(gehaltL, 0, 7);
        gridPane.add(gehaltTF, 1, 7);
        gridPane.add(new Label("€"), 2, 7);

        getDialogPane().setContent(gridPane);

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Mitarbeiter verwalten");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie neue Änderungen am Mitarbeiter " + this.mitarbeiter.getVorname() + " " + this.mitarbeiter.getNachname() + " vornehmen wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    try {
                        this.mitarbeiter.setNachname(nachnameTF.getText());
                        this.mitarbeiter.setVorname(vornameTF.getText());
                        this.mitarbeiter.setGeburtsdatum(geburtsdatumDP.getValue());
                        this.mitarbeiter.setGeschlecht(geschlechtCB.getValue());
                        this.mitarbeiter.setAdresse(adresseTF.getText());
                        this.mitarbeiter.setTelefonnummer(telefonnummerTF.getText());
                        this.mitarbeiter.setEmail(emailTF.getText());
                        this.mitarbeiter.setGehalt(Double.parseDouble(gehaltTF.getText()));

                        return this.mitarbeiter;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Verwalten eines Mitarbeiters");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    confirmation.close();
                }
            }
            return null;
        });
    }
}