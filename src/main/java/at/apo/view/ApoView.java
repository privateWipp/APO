package at.apo.view;

import at.apo.APO;
import at.apo.ConsoleOutputStream;
import at.apo.control.ApoController;
import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class ApoView extends BorderPane {
    private APO apoInstance;
    private View mainView;
    private Apotheke model;
    private Apotheke originalModel;
    private ApoController ctrl;
    private boolean changed;
    private ListView<Medikament> medikamentenListView;
    private ListView<Rezept> rezepteListView;
    private ListView<Bestellung> bestellungenListView;
    private ListView<Kunde> kundenListView;
    private ListView<Mitarbeiter> mitarbeiterListView;
    private TextArea changesTA;
    private String fehlendeAttribute;
    private Stage stage;

    public ApoView(View mainView, Apotheke apotheke) {
        // anderes Wichtiges!
        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.model = apotheke;
        this.ctrl = new ApoController(this, this.model);
        this.changed = false;
        this.originalModel = deepCopy(apotheke);
        // Fenster
        this.medikamentenListView = new ListView<Medikament>();
        this.rezepteListView = new ListView<Rezept>();
        this.bestellungenListView = new ListView<Bestellung>();
        this.kundenListView = new ListView<Kunde>();
        this.mitarbeiterListView = new ListView<Mitarbeiter>();
        loadListViews();

        this.changesTA = new TextArea();
        this.changesTA.setEditable(false);

        this.fehlendeAttribute = "";

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        PrintStream ps = new PrintStream(new ConsoleOutputStream(this.changesTA));
        System.setOut(ps);
        System.setErr(ps);

        this.stage.setTitle(this.model.getName() + " : Apotheke");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.8, this.apoInstance.getScreenHeight() * 0.8);
        this.stage.setScene(scene);

        if (this.model.getGeschaeftsfuehrer() == null) {
            this.fehlendeAttribute += "Geschäftsführer";
        }
        if (!this.model.getOeffnungszeiten().containsKey("Montag")) {
            if (!this.fehlendeAttribute.isEmpty()) {
                this.fehlendeAttribute += ", ";
            }
            this.fehlendeAttribute += "Öffnungszeiten";
        }

        if (!this.fehlendeAttribute.isEmpty()) {
            this.mainView.infoAlert("", "Informationen zu neuer Apotheke:\n" +
                    this.model.getName() +
                    " Bitte vervollständigen Sie folgende Attribute der Apotheke in den Optionen SOBALD WIE MÖGLICH!\n" +
                    "zu bearbeitende Attribute:\n\n" +
                    this.fehlendeAttribute);
        }

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu mitarbeiter = new Menu("Mitarbeiter");
        MenuItem manageEmployees = new MenuItem("verwalten");
        mitarbeiter.getItems().add(manageEmployees);

        Menu optionen = new Menu("Optionen");
        MenuItem geschaeftsfuehrerFestlegen = new MenuItem("Geschäftsführer ändern/festlegen");
        MenuItem geschaeftsfuehrerAnzeigen = new MenuItem("Details zu Geschäftsführer anzeigen");
        MenuItem oeffnungszeitenFestlegen = new MenuItem("Öffnungszeiten festlegen");
        optionen.getItems().addAll(geschaeftsfuehrerFestlegen, geschaeftsfuehrerAnzeigen, oeffnungszeitenFestlegen);

        Menu ansicht = new Menu("Ansicht");
        Menu fenster = new Menu("Fenster");
        CheckMenuItem medikamente = new CheckMenuItem("Medikamente");
        CheckMenuItem rezepte = new CheckMenuItem("Rezepte");
        CheckMenuItem bestellungen = new CheckMenuItem("Bestellungen");
        CheckMenuItem kunden = new CheckMenuItem("Kunden");
        CheckMenuItem mitarbeiterCMI = new CheckMenuItem("Mitarbeiter");
        fenster.getItems().addAll(medikamente, rezepte, bestellungen, kunden, mitarbeiterCMI);
        CheckMenuItem alleAnzeigen = new CheckMenuItem("alle Anzeigen lassen");
        ansicht.getItems().addAll(fenster, alleAnzeigen);

        menuBar.getMenus().addAll(mitarbeiter, optionen, ansicht);

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        VBox controlVBox = new VBox();

        Button apothekeBearbeiten = new Button("Apotheke bearbeiten");
        VBox aIZAVBox = new VBox(new Label("allgemeine Informationen zur Apotheke bearbeiten:"), apothekeBearbeiten);
        aIZAVBox.setSpacing(5);

        controlVBox.getChildren().addAll(aIZAVBox);
        controlVBox.setPadding(new Insets(10, 0, 0, 10));
        controlVBox.setSpacing(10);

        setRight(controlVBox);

        apothekeBearbeiten.setOnAction(e -> this.ctrl.apothekeBearbeiten());

        // -------------------------------------------------------------------------------------------------------------

        HBox listViewFensterHBox = new HBox();
        VBox medikamenteVBox = new VBox(new Label("Medikamente:"), this.medikamentenListView);
        VBox rezepteVBox = new VBox(new Label("Rezepte:"), this.rezepteListView);
        VBox bestellungenVBox = new VBox(new Label("Bestellungen:"), this.bestellungenListView);
        VBox kundenVBox = new VBox(new Label("Kunden:"), this.kundenListView);
        VBox mitarbeiterVBox = new VBox(new Label("Mitarbeiter:"), this.mitarbeiterListView);
        medikamenteVBox.setVisible(false);
        rezepteVBox.setVisible(false);
        bestellungenVBox.setVisible(false);
        kundenVBox.setVisible(false);
        mitarbeiterVBox.setVisible(false);
        listViewFensterHBox.getChildren().addAll(medikamenteVBox, rezepteVBox, bestellungenVBox, kundenVBox, mitarbeiterVBox);

        setCenter(listViewFensterHBox);

        // -------------------------------------------------------------------------------------------------------------

        VBox changesVBox = new VBox(new Label("Änderungen:"), this.changesTA);
        setBottom(changesVBox);

        // ----------------------------------------VERWALTEN DER MENÜS--------------------------------------------------

        manageEmployees.setOnAction(e -> this.ctrl.manageEmployees());

        geschaeftsfuehrerFestlegen.setOnAction(e -> this.ctrl.geschaeftsfuehrerFestlegen());
        geschaeftsfuehrerAnzeigen.setOnAction(e -> this.ctrl.geschaeftsfuehrerAnzeigen());
        oeffnungszeitenFestlegen.setOnAction(e -> this.ctrl.oeffnungszeitenFestlegen());

        medikamente.setOnAction(e -> {
            medikamenteVBox.setVisible(medikamente.isSelected());
            System.out.println("Fenster: Medikamente " + (medikamenteVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });
        rezepte.setOnAction(e -> {
            rezepteVBox.setVisible(rezepte.isSelected());
            System.out.println("Fenster: Rezepte " + (rezepteVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });
        bestellungen.setOnAction(e -> {
            bestellungenVBox.setVisible(bestellungen.isSelected());
            System.out.println("Fenster: Bestellungen " + (bestellungenVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });
        kunden.setOnAction(e -> {
            kundenVBox.setVisible(kunden.isSelected());
            System.out.println("Fenster: Kunden " + (kundenVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });
        mitarbeiterCMI.setOnAction(e -> {
            mitarbeiterVBox.setVisible(mitarbeiterCMI.isSelected());
            System.out.println("Fenster: Mitarbeiter " + (mitarbeiterVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });

        alleAnzeigen.setOnAction(e -> {
            medikamente.setSelected(alleAnzeigen.isSelected());
            rezepte.setSelected(alleAnzeigen.isSelected());
            bestellungen.setSelected(alleAnzeigen.isSelected());
            kunden.setSelected(alleAnzeigen.isSelected());
            mitarbeiterCMI.setSelected(alleAnzeigen.isSelected());
            medikamenteVBox.setVisible(alleAnzeigen.isSelected());
            rezepteVBox.setVisible(alleAnzeigen.isSelected());
            bestellungenVBox.setVisible(alleAnzeigen.isSelected());
            kundenVBox.setVisible(alleAnzeigen.isSelected());
            mitarbeiterVBox.setVisible(alleAnzeigen.isSelected());
            System.out.println("Alle Fenster " + (medikamenteVBox.isVisible() ? "anzeigen.\n" : "nicht mehr anzeigen.\n"));
        });

        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------

        this.stage.setOnCloseRequest(event -> {
            event.consume();
            saveConfirmation();
        });

        this.stage.show();
    }

    private void loadListViews() {
        if (!this.model.getMedikamente().isEmpty()) {
            for (Medikament medikament : this.model.getMedikamente()) {
                this.medikamentenListView.getItems().add(medikament);
            }
        }
        if (!this.model.getRezepte().isEmpty()) {
            for (Rezept rezept : this.model.getRezepte()) {
                this.rezepteListView.getItems().add(rezept);
            }
        }
        if (!this.model.getBestellungen().isEmpty()) {
            for (Bestellung bestellung : this.model.getBestellungen()) {
                this.bestellungenListView.getItems().add(bestellung);
            }
        }
        if (!this.model.getKunden().isEmpty()) {
            for (Kunde kunde : this.model.getKunden()) {
                this.kundenListView.getItems().add(kunde);
            }
        }
        if (!this.model.getMitarbeiter().isEmpty()) {
            for (Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
                this.mitarbeiterListView.getItems().add(mitarbeiter);
            }
        }
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
            System.out.println("Fehler: deepCopy des originalen Models zu erstellen ist fehlgeschlagen!");
        }
        return original;
    }

    private void saveConfirmation() {
        if (this.changed) {
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
                /**
                 * Problem hier:
                 * Wenn man den offiziellen Namen der Apotheke über den Dialog ändert, und es speichert,
                 * beim nächsten Mal Öffnen des Programms ist die "alte" Version der Apotheke (mit dem alten Namen) ebenfalls da.
                 * Eben zusätzlich halt die "neue" Version der Apotheke mit dem geupdateten Namen
                 */
                File file = new File("apotheken", this.model.getName() + ".apo");
                try {
                    this.model.speichern(file);
                    this.mainView.getApothekenListView().refresh();
                    this.stage.close();
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Fehler");
                    errorAlert.setHeaderText("Speichern");
                    errorAlert.setContentText("Es gab einen Fehler beim Speichern der Apotheke:\n" +
                            this.model.getName());
                    System.out.println("Fehler: Das Speichern der Apotheke ist fehlgeschlagen!");
                    errorAlert.showAndWait();
                }
            } else if (result.isPresent() && result.get() == no) { // NICHT SPEICHERN
                this.model = deepCopy(this.originalModel);
                this.stage.close();
            } else {
                // bei Abbrechen => nichts tun
            }
        } else {
            this.stage.close();
        }
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void errorAlert(String headerText, String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    public TextArea getChangesTA() {
        return this.changesTA;
    }
}
