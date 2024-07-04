package at.apo.view;

import at.apo.model.APOException;
import at.apo.model.Geschaeftsfuehrer;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class geschaeftsfuehrerFestlegenDialog extends Dialog<Geschaeftsfuehrer> {
    public geschaeftsfuehrerFestlegenDialog() {
        setTitle("Geschäftsführer festlegen");

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox nameHBox = new HBox();
        Label nachnameL = new Label("Nachname:");
        TextField nachnameTF = new TextField();
        nachnameTF.setPromptText("Nachname des Geschäftsführers");
        Label vornameL = new Label("Vorname:");
        TextField vornameTF = new TextField();
        vornameTF.setPromptText("Vorname des Geschäftsführers");
        nameHBox.getChildren().addAll(nachnameL, nachnameTF, vornameL, vornameTF);
        nameHBox.setPadding(new Insets(10, 10, 0, 10));
        nameHBox.setSpacing(10);

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
        adresseTF.setPromptText("Adresse des Geschäftsführers");
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
        emailTF.setPromptText("E-Mail Adresse vom Geschäftsführer");
        emailHBox.getChildren().addAll(emailL, emailTF);
        emailHBox.setPadding(new Insets(10, 10, 0, 10));
        emailHBox.setSpacing(10);

        flowPane.getChildren().addAll(nameHBox, geburtsdatumHBox, geschlechtHBox, adresseHBox, telefonnummerHBox, emailHBox);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Festlegen", ButtonBar.ButtonData.APPLY);
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

                    return new Geschaeftsfuehrer(nachnameTFInput, vornameTFInput, geburtsdatumDPInput, geschlechtCBInput, adresseTFInput, telefonnummerTFInput, emailTFInput);
                } catch (APOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Fehler beim Festlegen des Geschäftsführers");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
            return null;
        });
    }
}
