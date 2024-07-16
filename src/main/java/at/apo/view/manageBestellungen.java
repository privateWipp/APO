package at.apo.view;

import at.apo.APO;
import at.apo.control.BestellungenController;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class manageBestellungen extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke originalModel;
    private Apotheke model;
    private BestellungenController ctrl;

    private ListView<Bestellung> bestellungenListView;

    private Stage stage;

    public manageBestellungen(ApoView view, Apotheke originalModel) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.originalModel = originalModel;
        this.model = this.originalModel.clone();
        this.ctrl = new BestellungenController(this.originalModel, this.view, this);

        this.bestellungenListView = new ListView<Bestellung>();
        updateBestellungen();

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle(this.originalModel.getName() + " : Bestellungen verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortBestellnummer = new MenuItem("sortieren nach Bestellnummer");
        MenuItem sortBezeichnung = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortDatum = new MenuItem("sortieren nach Datum");
        MenuItem sortKosten = new MenuItem("sortieren nach (Gesamt-)Kosten");
        MenuItem sortStatus = new MenuItem("sortieren nach (Bestell-)Status");
        bearbeiten.getItems().addAll(sortBestellnummer, sortBezeichnung, sortDatum, sortKosten, sortStatus);

        Menu liste = new Menu("Liste");
        MenuItem aktualisiereListe = new MenuItem("aktualisieren");
        liste.getItems().add(aktualisiereListe);

        menuBar.getMenus().addAll(bearbeiten, liste);

        Button addBestellung = new Button("+ neue Bestellung aufgeben");
        Button removeBestellung = new Button("- Bestellung stornieren");
        Button manageBestellung = new Button("ändern/verwalten"); // nur wenn Bestellstatus = "BESTELLT" ist!!!
        Button changeStatus = new Button("NUR Bestellstatus ändern");
        HBox manageBestellungenHBox = new HBox(addBestellung, removeBestellung, manageBestellung, changeStatus);
        manageBestellungenHBox.setPadding(new Insets(20, 20, 20, 20));
        manageBestellungenHBox.setSpacing(10);

        VBox topVBox = new VBox(menuBar, manageBestellungenHBox);
        topVBox.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        setTop(topVBox);

        sortBestellnummer.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBestellnummer));
            updateBestellungen();
        });
        sortBezeichnung.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBezeichnung));
            updateBestellungen();
        });
        sortDatum.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getDatum));
            updateBestellungen();
        });
        sortKosten.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getGesamtkosten));
            updateBestellungen();
        });
        sortStatus.setOnAction(e -> {
            this.model.getBestellungen().sort(Comparator.comparing(Bestellung::getBestellstatus));
            updateBestellungen();
        });

        aktualisiereListe.setOnAction(e -> {
            updateBestellungen();
        });

        addBestellung.setOnAction(e -> this.ctrl.addBestellung());
        removeBestellung.disableProperty().bind(this.bestellungenListView.getSelectionModel().selectedItemProperty().isNull());
        removeBestellung.setOnAction(e -> this.ctrl.removeBestellung(this.bestellungenListView.getSelectionModel().getSelectedItem()));
        manageBestellung.disableProperty().bind(this.bestellungenListView.getSelectionModel().selectedItemProperty().isNull());
        manageBestellung.setOnAction(e -> this.ctrl.manageBestellung(this.bestellungenListView.getSelectionModel().getSelectedItem()));
        changeStatus.disableProperty().bind(this.bestellungenListView.getSelectionModel().selectedItemProperty().isNull());
        changeStatus.setOnAction(e -> this.ctrl.changeStatus(this.bestellungenListView.getSelectionModel().getSelectedItem()));

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.bestellungenListView);

        // -------------------------------------------------------------------------------------------------------------

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        toolBar.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        Button printAllBestellungen = new Button("alle Bestellungen ausgeben");

        toolBar.getItems().add(printAllBestellungen);

        setRight(toolBar);

        printAllBestellungen.setOnAction(e -> this.ctrl.printAllBestellungen());

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void updateBestellungen() {
        this.bestellungenListView.getItems().clear();
        for(Bestellung bestellung : this.model.getBestellungen()) {
            this.bestellungenListView.getItems().add(bestellung);
        }
    }

    public Apotheke getModel() {
        return this.model;
    }
}
