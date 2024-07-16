package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class manageEmployeeDialog extends Dialog<Mitarbeiter> {
    private Mitarbeiter mitarbeiter;

    public manageEmployeeDialog(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;

        setTitle("Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " bearbeiten/verwalten");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField(mitarbeiter.getNachname());
        nachnameTF.setPromptText("Nachname des Mitarbeiters");

        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField(mitarbeiter.getVorname());
        vornameTF.setPromptText("Vorname des Mitarbeiters");

        Label geburtsdatumL = new Label("Geb. Dat.:");
        DatePicker geburtsdatumDP = new DatePicker(mitarbeiter.getGeburtsdatum());

        Label geschlechtL = new Label("Geschlecht:");
        ComboBox<String> geschlechtCB = new ComboBox<>();
        geschlechtCB.getItems().addAll("Männlich", "Weiblich", "Inter", "Divers", "Offen", "keine Angabe");
        geschlechtCB.setValue(mitarbeiter.getGeschlecht());

        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField(mitarbeiter.getAdresse());
        adresseTF.setPromptText("Adresse des Mitarbeiters");

        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField(mitarbeiter.getTelefonnummer());
        telefonnummerTF.setPromptText("Telefonnummer eingeben");

        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField(mitarbeiter.getEmail());
        emailTF.setPromptText("E-Mail Adresse vom Mitarbeiter");

        Label gehaltL = new Label("Gehalt:");
        TextField gehaltTF = new TextField(Double.toString(mitarbeiter.getGehalt()));
        gehaltTF.setPromptText("üblich: 2500");

        ButtonType buttonType = new ButtonType("Ändern", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

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

        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Medikament ändern");
                confirmation.setHeaderText("Sind Sie sicher?");
                confirmation.setContentText("Sind Sie sicher, dass Sie den Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " ändern wollen?");

                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein");
                ButtonType cancel = new ButtonType("Abbrechen");

                confirmation.getButtonTypes().setAll(yes, no, cancel);

                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    try {
                        mitarbeiter.setNachname(nachnameTF.getText());
                        mitarbeiter.setVorname(vornameTF.getText());
                        mitarbeiter.setGeburtsdatum(geburtsdatumDP.getValue());
                        mitarbeiter.setGeschlecht(geschlechtCB.getValue());
                        mitarbeiter.setAdresse(adresseTF.getText());
                        mitarbeiter.setTelefonnummer(telefonnummerTF.getText());
                        mitarbeiter.setEmail(emailTF.getText());
                        mitarbeiter.setGehalt(Double.parseDouble(gehaltTF.getText()));
                        return mitarbeiter;
                    } catch (APOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Fehler");
                        errorAlert.setHeaderText("Fehler beim Ändern/Verwalten eines Mitarbeiters");
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