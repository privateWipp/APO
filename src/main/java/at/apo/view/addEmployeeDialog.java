package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class addEmployeeDialog extends Dialog<Mitarbeiter> {
    public addEmployeeDialog() {
        setTitle("neuer Mitarbeiter");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox nachnameHBox = new HBox();
        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField();
        nachnameTF.setPromptText("Nachname des Mitarbeiters");
        nachnameHBox.getChildren().addAll(nachnameL, nachnameTF);
        nachnameHBox.setPadding(new Insets(10, 10, 0, 10));
        nachnameHBox.setSpacing(10);

        HBox vornameHBox = new HBox();
        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField();
        vornameTF.setPromptText("Vorname des Mitarbeiters");
        vornameHBox.getChildren().addAll(vornameL, vornameTF);
        vornameHBox.setPadding(new Insets(10, 10, 0, 10));
        vornameHBox.setSpacing(10);

        HBox geburtsdatumHBox = new HBox();
        Label geburtsdatumL = new Label("Geb. Dat.:");
        DatePicker geburtsdatumDP = new DatePicker();
        geburtsdatumHBox.getChildren().addAll(geburtsdatumL, geburtsdatumDP);
        geburtsdatumHBox.setPadding(new Insets(10, 10, 0, 10));
        geburtsdatumHBox.setSpacing(10);

        HBox geschlechtHBox = new HBox();
        Label geschlechtL = new Label("Geschlecht:");
        ComboBox<String> geschlechtCB = new ComboBox<String>();
        geschlechtCB.getItems().addAll("Männlich", "Weiblich", "Inter", "Divers", "Offen", "keine Angabe");
        geschlechtHBox.getChildren().addAll(geschlechtL, geschlechtCB);
        geschlechtHBox.setPadding(new Insets(10, 10, 0, 10));
        geschlechtHBox.setSpacing(10);

        HBox adresseHBox = new HBox();
        Label adresseL = new Label("Adresse:");
        TextField adresseTF = new TextField();
        adresseTF.setPromptText("Adresse des Mitarbeiters");
        adresseHBox.getChildren().addAll(adresseL, adresseTF);
        adresseHBox.setPadding(new Insets(10, 10, 0, 10));
        adresseHBox.setSpacing(10);

        HBox telefonnummerHBox = new HBox();
        Label telefonnummerL = new Label("Tel. Nr.:");
        TextField telefonnummerTF = new TextField();
        telefonnummerTF.setPromptText("Telefonnummer eingeben");
        telefonnummerHBox.getChildren().addAll(telefonnummerL, telefonnummerTF);
        telefonnummerHBox.setPadding(new Insets(10, 10, 0, 10));
        telefonnummerHBox.setSpacing(10);

        HBox emailHBox = new HBox();
        Label emailL = new Label("E-Mail Adresse:");
        TextField emailTF = new TextField();
        emailTF.setPromptText("E-Mail Adresse vom Mitarbeiter");
        emailHBox.getChildren().addAll(emailL, emailTF);
        emailHBox.setPadding(new Insets(10, 10, 0, 10));
        emailHBox.setSpacing(10);

        HBox gehaltHBox = new HBox();
        Label gehaltL = new Label("Gehalt:");
        TextField gehaltTF = new TextField();
        gehaltTF.setPromptText("üblich: 2500");
        Label euroIcon = new Label("€");
        gehaltHBox.getChildren().addAll(gehaltL, gehaltTF, euroIcon);
        gehaltHBox.setPadding(new Insets(10, 10, 0, 10));
        gehaltHBox.setSpacing(10);

        flowPane.getChildren().addAll(nachnameHBox, vornameHBox, geburtsdatumHBox, geschlechtHBox, adresseHBox, telefonnummerHBox, emailHBox, gehaltHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
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
                    errorAlert.setHeaderText("Fehler beim Erstellen des neuen Mitarbeiters");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
