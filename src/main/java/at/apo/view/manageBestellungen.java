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

public class manageBestellungen extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke model;
    private BestellungenController ctrl;

    private ListView<Bestellung> bestellungenListView;

    private Stage stage;

    public manageBestellungen(ApoView view, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.model = model;
        this.ctrl = new BestellungenController(this.model, this.view, this);

        this.bestellungenListView = new ListView<Bestellung>();
        updateBestellungen();

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle(this.model.getName() + " : Bestellungen verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu liste = new Menu("Liste");
        MenuItem aktualisiereListe = new MenuItem("aktualisieren");
        liste.getItems().add(aktualisiereListe);

        menuBar.getMenus().add(liste);

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
}
