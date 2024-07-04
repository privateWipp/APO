package at.apo.view;

import at.apo.APO;
import at.apo.control.ApoController;
import at.apo.model.Apotheke;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class ApoView extends BorderPane {
    private APO apoInstance;
    private View mainView;
    private Apotheke originalModel;
    private Apotheke model;
    private ApoController ctrl;
    private boolean saved = true;
    private Stage stage;

    public ApoView(View mainView, Apotheke apotheke) {
        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.model = apotheke;
        this.ctrl = new ApoController(this, this.model);
        if(this.saved) {
            this.originalModel = deepCopy(apotheke);
        }
        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle(this.model.getName() + " : Apotheke");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.8, this.apoInstance.getScreenHeight() * 0.8);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu mitarbeiter = new Menu("Mitarbeiter");
        MenuItem manageEmployees = new MenuItem("verwalten");
        mitarbeiter.getItems().add(manageEmployees);

        Menu optionen = new Menu("Optionen");
        MenuItem geschaeftsfuehrerFestlegen = new MenuItem("Geschäftsführer festlegen");
        optionen.getItems().add(geschaeftsfuehrerFestlegen);

        menuBar.getMenus().addAll(mitarbeiter, optionen);

        setTop(menuBar);

        // Verwalten der Menüs
        manageEmployees.setOnAction(e -> this.ctrl.manageEmployees());
        geschaeftsfuehrerFestlegen.setOnAction(e -> this.ctrl.geschaeftsfuehrerFestlegen());

        // -------------------------------------------------------------------------------------------------------------

        this.stage.setOnCloseRequest(event -> {
            event.consume();
            saveConfirmation(this.stage);
        });

        this.stage.show();
    }

    private Apotheke deepCopy(Apotheke original) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(original);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Apotheke) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            errorAlert("Fehler beim Erstellen der 'deepCopy'..", e.getMessage());
        }
        return original;
    }

    private void saveConfirmation(Stage stage) {
        if (!this.originalModel.equals(this.model)) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("ungespeicherte Änderungen");
            confirmation.setHeaderText("Speichern?");
            confirmation.setContentText("Es wurden (noch nicht gespeicherte) Änderungen vorgenommen.\n" +
                    "Wollen Sie speichern?");

            ButtonType yes = new ButtonType("Ja");
            ButtonType no = new ButtonType("Nein");
            ButtonType cancel = new ButtonType("Abbrechen");

            confirmation.getButtonTypes().setAll(yes, no, cancel);

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == yes) { // SPEICHERN
                File file = new File("apotheken", this.model.getName() + ".apo");
                try {
                    this.model.speichern(file);
                    this.saved = true;
                    this.mainView.getApothekenListView().refresh();
                    this.stage.close();
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Speichern");
                    errorAlert.setContentText("Es gab einen Fehler beim Speichern der Apotheke:\n" +
                            this.model.getName());
                    errorAlert.showAndWait();
                }
            } else if (result.isPresent() && result.get() == no) { // NICHT SPEICHERN
                this.saved = false;
                this.model = deepCopy(this.originalModel);
                this.stage.close();
            } else {
                // bei Abbrechen => nichts tun
            }
        } else {
            this.stage.close();
        }
    }

    public void errorAlert(String headerText, String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }
}
