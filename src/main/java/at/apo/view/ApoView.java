package at.apo.view;

import at.apo.APO;
import at.apo.ConsoleOutputStream;
import at.apo.control.ApoController;
import at.apo.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.model = apotheke;
        this.ctrl = new ApoController(this, this.model);
        this.changed = false;
        this.originalModel = deepCopy(apotheke);
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

        this.stage.setTitle(this.model.getName() + " : APO");
        this.stage.setResizable(true);
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
        MenuItem manageEmployees = new MenuItem("Mitarbeiter verwalten");
        mitarbeiter.getItems().add(manageEmployees);

        Menu medikamente = new Menu("Medikamente");
        MenuItem manageMedikamente = new MenuItem("Medikamente verwalten");
        medikamente.getItems().add(manageMedikamente);

        Menu rezepte = new Menu("Rezepte");
        MenuItem manageRezepte = new MenuItem("Rezepte verwalten");
        rezepte.getItems().add(manageRezepte);

        Menu bestellungen = new Menu("Bestellungen");
        MenuItem newBestellung = new MenuItem("neue Bestellung aufgeben");
        MenuItem manageBestellungen = new MenuItem("Bestellungen verwalten/ansehen");
        bestellungen.getItems().addAll(newBestellung, manageBestellungen);

        Menu kunden = new Menu("Kunden");
        MenuItem manageKunden = new MenuItem("Kunden verwalten");
        kunden.getItems().add(manageKunden);

        Menu optionen = new Menu("Optionen");
        MenuItem geschaeftsfuehrerFestlegen = new MenuItem("Geschäftsführer ändern/festlegen");
        MenuItem geschaeftsfuehrerAnzeigen = new MenuItem("Details zu Geschäftsführer anzeigen");
        MenuItem oeffnungszeitenFestlegen = new MenuItem("Öffnungszeiten festlegen");
        optionen.getItems().addAll(geschaeftsfuehrerFestlegen, geschaeftsfuehrerAnzeigen, oeffnungszeitenFestlegen);

        Menu ansicht = new Menu("Ansicht");
        Menu fenster = new Menu("Fenster");
        CheckMenuItem medikamenteCMI = new CheckMenuItem("Medikamente");
        CheckMenuItem rezepteCMI = new CheckMenuItem("Rezepte");
        CheckMenuItem bestellungenCMI = new CheckMenuItem("Bestellungen");
        CheckMenuItem kundenCMI = new CheckMenuItem("Kunden");
        CheckMenuItem mitarbeiterCMI = new CheckMenuItem("Mitarbeiter");
        fenster.getItems().addAll(medikamenteCMI, rezepteCMI, bestellungenCMI, kundenCMI, mitarbeiterCMI);
        CheckMenuItem alleAnzeigen = new CheckMenuItem("alle Anzeigen lassen");
        MenuItem refreshLists = new MenuItem("Listen aktualisieren");
        ansicht.getItems().addAll(fenster, alleAnzeigen, refreshLists);

        menuBar.getMenus().addAll(mitarbeiter, medikamente, rezepte, bestellungen, kunden, optionen, ansicht);

        manageEmployees.setOnAction(e -> this.ctrl.manageEmployees());

        manageMedikamente.setOnAction(e -> this.ctrl.manageMedikamente());
        manageRezepte.setOnAction(e -> this.ctrl.manageRezepte());
        newBestellung.setOnAction(e -> this.ctrl.newBestellung());
        manageBestellungen.setOnAction(e -> this.ctrl.manageBestellungen());
        manageKunden.setOnAction(e -> this.ctrl.manageKunden());

        geschaeftsfuehrerFestlegen.setOnAction(e -> this.ctrl.geschaeftsfuehrerFestlegen());
        geschaeftsfuehrerAnzeigen.setOnAction(e -> this.ctrl.geschaeftsfuehrerAnzeigen());
        oeffnungszeitenFestlegen.setOnAction(e -> this.ctrl.oeffnungszeitenFestlegen());

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(15, 15, 15, 15));

        Button apothekeBearbeiten = new Button("Apotheke bearbeiten");
        VBox aIZAVBox = new VBox(new Label("allgemeine Informationen zur Apotheke bearbeiten:"), apothekeBearbeiten);
        aIZAVBox.setSpacing(5);

        toolBar.getItems().addAll(aIZAVBox);

        apothekeBearbeiten.setOnAction(e -> this.ctrl.apothekeBearbeiten());

        setRight(toolBar);

        // -------------------------------------------------------------------------------------------------------------

        Label medikamenteLabel = new Label("Medikamente:");
        Label rezepteLabel = new Label("Rezepte:");
        Label bestellungenLabel = new Label("Bestellungen:");
        Label kundenLabel = new Label("Kunden:");
        Label mitarbeiterLabel = new Label("Mitarbeiter:");

        double fontSize = 20;
        medikamenteLabel.setStyle("-fx-font-size: " + fontSize + "px;");
        rezepteLabel.setStyle("-fx-font-size: " + fontSize + "px;");
        bestellungenLabel.setStyle("-fx-font-size: " + fontSize + "px;");
        kundenLabel.setStyle("-fx-font-size: " + fontSize + "px;");
        mitarbeiterLabel.setStyle("-fx-font-size: " + fontSize + "px;");

        double listViewWidth = 350;
        double listViewHeight = 500;

        this.medikamentenListView.setPrefSize(listViewWidth, listViewHeight);
        this.rezepteListView.setPrefSize(listViewWidth, listViewHeight);
        this.bestellungenListView.setPrefSize(listViewWidth, listViewHeight);
        this.kundenListView.setPrefSize(listViewWidth, listViewHeight);
        this.mitarbeiterListView.setPrefSize(listViewWidth, listViewHeight);

        VBox medikamenteVBox = new VBox(medikamenteLabel, this.medikamentenListView);
        medikamenteVBox.setPadding(new Insets(10));

        VBox rezepteVBox = new VBox(rezepteLabel, this.rezepteListView);
        rezepteVBox.setPadding(new Insets(10));

        VBox bestellungenVBox = new VBox(bestellungenLabel, this.bestellungenListView);
        bestellungenVBox.setPadding(new Insets(10));

        VBox kundenVBox = new VBox(kundenLabel, this.kundenListView);
        kundenVBox.setPadding(new Insets(10));

        VBox mitarbeiterVBox = new VBox(mitarbeiterLabel, this.mitarbeiterListView);
        mitarbeiterVBox.setPadding(new Insets(10));

        medikamenteVBox.setVisible(false);
        rezepteVBox.setVisible(false);
        bestellungenVBox.setVisible(false);
        kundenVBox.setVisible(false);
        mitarbeiterVBox.setVisible(false);

        HBox listViewFensterHBox = new HBox(medikamenteVBox, rezepteVBox, bestellungenVBox, kundenVBox, mitarbeiterVBox);
        listViewFensterHBox.setPadding(new Insets(10, 0, 0, 10));

        medikamenteCMI.setOnAction(e -> {
            medikamenteVBox.setVisible(medikamenteCMI.isSelected());
            System.out.println("Fenster: Medikamente " + (medikamenteVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });
        rezepteCMI.setOnAction(e -> {
            rezepteVBox.setVisible(rezepteCMI.isSelected());
            System.out.println("Fenster: Rezepte " + (rezepteVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });
        bestellungenCMI.setOnAction(e -> {
            bestellungenVBox.setVisible(bestellungenCMI.isSelected());
            System.out.println("Fenster: Bestellungen " + (bestellungenVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });
        kundenCMI.setOnAction(e -> {
            kundenVBox.setVisible(kundenCMI.isSelected());
            System.out.println("Fenster: Kunden " + (kundenVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });
        mitarbeiterCMI.setOnAction(e -> {
            mitarbeiterVBox.setVisible(mitarbeiterCMI.isSelected());
            System.out.println("Fenster: Mitarbeiter " + (mitarbeiterVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });

        alleAnzeigen.setOnAction(e -> {
            medikamenteCMI.setSelected(alleAnzeigen.isSelected());
            rezepteCMI.setSelected(alleAnzeigen.isSelected());
            bestellungenCMI.setSelected(alleAnzeigen.isSelected());
            kundenCMI.setSelected(alleAnzeigen.isSelected());
            mitarbeiterCMI.setSelected(alleAnzeigen.isSelected());
            medikamenteVBox.setVisible(alleAnzeigen.isSelected());
            rezepteVBox.setVisible(alleAnzeigen.isSelected());
            bestellungenVBox.setVisible(alleAnzeigen.isSelected());
            kundenVBox.setVisible(alleAnzeigen.isSelected());
            mitarbeiterVBox.setVisible(alleAnzeigen.isSelected());
            System.out.println("Alle Fenster " + (medikamenteVBox.isVisible() ? "anzeigen." : "nicht mehr anzeigen."));
        });

        refreshLists.setOnAction(e -> {
            loadListViews();
            System.out.println("Die Listen: Medikamente, Rezepte, Bestellungen, Kunden und Mitarbeiter wurden aktualisiert.");
        });

        setCenter(listViewFensterHBox);

        // -------------------------------------------------------------------------------------------------------------

        Label changes = new Label("Änderungen:");
        changes.setStyle("-fx-font-size: 13px;");
        Button clearChanges = new Button("leeren");
        HBox aenderungenHBox = new HBox(changes, clearChanges);
        aenderungenHBox.setSpacing(10);
        aenderungenHBox.setPadding(new Insets(0, 0, 5, 15));
        VBox changesVBox = new VBox(aenderungenHBox, this.changesTA);

        clearChanges.setOnAction(e -> this.ctrl.clearChanges());

        setBottom(changesVBox);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.setOnCloseRequest(event -> {
            event.consume();
            saveConfirmation();
        });

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void loadListViews() {
        if (!this.model.getMedikamente().isEmpty()) {
            this.medikamentenListView.getItems().clear();
            for (Medikament medikament : this.model.getMedikamente()) {
                this.medikamentenListView.getItems().add(medikament);
            }
        }
        if (!this.model.getRezepte().isEmpty()) {
            this.rezepteListView.getItems().clear();
            for (Rezept rezept : this.model.getRezepte()) {
                this.rezepteListView.getItems().add(rezept);
            }
        }
        if (!this.model.getBestellungen().isEmpty()) {
            this.bestellungenListView.getItems().clear();
            for (Bestellung bestellung : this.model.getBestellungen()) {
                this.bestellungenListView.getItems().add(bestellung);
            }
        }
        if (!this.model.getKunden().isEmpty()) {
            this.kundenListView.getItems().clear();
            for (Kunde kunde : this.model.getKunden()) {
                this.kundenListView.getItems().add(kunde);
            }
        }
        if (!this.model.getMitarbeiter().isEmpty()) {
            this.mitarbeiterListView.getItems().clear();
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
            confirmation.setHeaderText("NICHT gespeicherte Arbeit");
            confirmation.setContentText("Es wurden (noch nicht gespeicherte) Änderungen vorgenommen.\n" +
                    "Wollen Sie diese speichern?");

            ButtonType yes = new ButtonType("Ja");
            ButtonType no = new ButtonType("Nein");
            ButtonType cancel = new ButtonType("Abbrechen");

            confirmation.getButtonTypes().setAll(yes, no, cancel);

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == yes) { // SPEICHERN
                File newFile = new File("apotheken", this.model.getName() + ".apo");
                File oldFile = new File("apotheken", this.model.getOriginalName() + ".apo");
                try {
                    this.model.setOriginalName(this.model.getName());
                    this.model.speichern(newFile);
                    if (!(oldFile.getCanonicalPath().equals(newFile.getCanonicalPath()))) {
                        oldFile.delete();
                    }
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
            } else if (result.isPresent() && result.get() == no) {
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
