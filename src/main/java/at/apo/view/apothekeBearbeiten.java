package at.apo.view;

import at.apo.APO;
import at.apo.control.editApoController;
import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class apothekeBearbeiten extends FlowPane {
    private APO apoInstance;
    private ApoView mainView;
    private Apotheke model;
    private editApoController ctrl;
    private Stage stage;

    public apothekeBearbeiten(ApoView mainView, Apotheke model) {
        this.setOrientation(Orientation.VERTICAL);

        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.model = model;
        this.ctrl = new editApoController(this.mainView, this.model);
        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("allgemeine Informationen der Apotheke " + this.model.getName() + " bearbeiten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);
        this.setPadding(new Insets(30, 30, 30, 30));

        ImageView nameImageView = createImageView("/imgs/name_icon.png");
        ImageView adresseImageView = createImageView("/imgs/adresse_icon.png");
        ImageView telefonnummerImageView = createImageView("/imgs/telefonnummer_icon.png");
        ImageView emailImageView = createImageView("/imgs/email_icon.png");

        Button nameButton = new Button("", nameImageView);
        Button adresseButton = new Button("", adresseImageView);
        Button telefonnummerButton = new Button("", telefonnummerImageView);
        Button emailButton = new Button("", emailImageView);

        Label nameLabel = new Label("Name");
        Label nameDescriptionLabel = new Label("ändere den offizielen Namen der Apotheke");

        Label adresseLabel = new Label("Adresse");
        Label adresseDescriptionLabel = new Label("Die Apotheke ist umgezogen? Ändere JETZT die Adresse!");

        Label telefonnummerLabel = new Label("Telefonnummer");
        Label telefonnummerDescriptionLabel = new Label("Änderungen für die Tel. Nr. sind HIER festzulegen");

        Label emailLabel = new Label("E-Mail Adresse");
        Label emailDescriptionLabel = new Label("E-Mail Adresse ändern");

        HBox nameHBox = createIconLabelBox(nameButton, nameLabel, nameDescriptionLabel);
        HBox adresseHBox = createIconLabelBox(adresseButton, adresseLabel, adresseDescriptionLabel);
        HBox telefonnummerHBox = createIconLabelBox(telefonnummerButton, telefonnummerLabel, telefonnummerDescriptionLabel);
        HBox emailHBox = createIconLabelBox(emailButton, emailLabel, emailDescriptionLabel);

        nameHBox.setPadding(new Insets(0, 0, 30, 0));
        adresseHBox.setPadding(new Insets(0, 0, 30, 0));
        telefonnummerHBox.setPadding(new Insets(0, 0, 30, 0));
        emailHBox.setPadding(new Insets(0, 0, 30, 0));

        applyStyles(nameHBox);
        applyStyles(adresseHBox);
        applyStyles(telefonnummerHBox);
        applyStyles(emailHBox);

        nameButton.setOnAction(e -> this.ctrl.nameButton());
        adresseButton.setOnAction(e -> this.ctrl.adresseButton());
        telefonnummerButton.setOnAction(e -> this.ctrl.telefonnummerButton());
        emailButton.setOnAction(e -> this.ctrl.emailButton());

        this.getChildren().addAll(nameHBox, adresseHBox, telefonnummerHBox, emailHBox);

        this.stage.show();
    }

    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }

    private HBox createIconLabelBox(Button button, Label label1, Label label2) {
        VBox labelsVBox = new VBox(label1);
        if (label2 != null) {
            labelsVBox.getChildren().add(label2);
        }
        return new HBox(10, button, labelsVBox);
    }

    private void applyStyles(HBox hBox) {
        VBox vBox = (VBox) hBox.getChildren().get(1);

        Label titleLabel = (Label) vBox.getChildren().get(0);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        if (vBox.getChildren().size() > 1) {
            Label descriptionLabel = (Label) vBox.getChildren().get(1);
            descriptionLabel.setStyle("-fx-font-size: 16px;");
        }
    }
}
