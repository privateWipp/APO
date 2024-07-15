package at.apo.view;

import at.apo.APO;
import at.apo.control.RezepteController;
import at.apo.model.Apotheke;
import at.apo.model.Rezept;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class manageRezepte extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke originalModel;
    private Apotheke model;
    private RezepteController ctrl;
    private Stage stage;

    private ListView<Rezept> rezeptListView;

    public manageRezepte(ApoView view, Apotheke originalModel) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.originalModel = originalModel;
        this.model = this.originalModel.clone();
        this.ctrl = new RezepteController(this.originalModel, this.view, this);
        this.stage = new Stage();

        this.rezeptListView = new ListView<Rezept>();
        loadRezepte();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Rezepte der Apotheke " + this.originalModel.getName() + " verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortRezeptnummer = new MenuItem("sortieren nach Rezeptnummer");
        MenuItem sortAusstellungsdatum = new MenuItem("sortieren nach Ausstellungsdatum");
        MenuItem sortGueltigBis = new MenuItem("sortieren nach 'gültig bis'");
        MenuItem sortRezeptart = new MenuItem("sortieren nach Rezeptart");
        MenuItem sortPreis = new MenuItem("sortieren nach Preis");
        bearbeiten.getItems().addAll(sortRezeptnummer, sortAusstellungsdatum, sortGueltigBis, sortRezeptart, sortPreis);

        Menu list = new Menu("Liste");
        MenuItem refreshList = new MenuItem("aktualisieren");
        list.getItems().add(refreshList);

        menuBar.getMenus().addAll(bearbeiten, list);

        setTop(menuBar);

        sortRezeptnummer.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getRezeptnummer));
            loadRezepte();
        });
        sortAusstellungsdatum.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getAusstellungsDatum));
            loadRezepte();
        });
        sortGueltigBis.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getGueltigBis));
            loadRezepte();
        });
        sortRezeptart.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getRezeptArt));
            loadRezepte();
        });
        sortPreis.setOnAction(e -> {
            this.model.getRezepte().sort(Comparator.comparing(Rezept::getPreis));
            loadRezepte();
        });

        refreshList.setOnAction(e -> {
            loadRezepte();
            System.out.println("Die Liste der Rezepte wurde aktualisiert.");
        });

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.rezeptListView);

        // -------------------------------------------------------------------------------------------------------------

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        toolBar.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        Button addRezept = new Button("neues Rezept freigeben");
        Button removeRezept = new Button("löschen");
        Button manageRezept = new Button("ändern/verwalten");
        Button printRezepte = new Button("Liste ausgeben");
        VBox buttonVBox = new VBox(addRezept, removeRezept, manageRezept, printRezepte);
        buttonVBox.setSpacing(10);

        toolBar.getItems().add(buttonVBox);

        setRight(toolBar);

        addRezept.setOnAction(e -> this.ctrl.addRezept());
        removeRezept.disableProperty().bind(this.rezeptListView.getSelectionModel().selectedItemProperty().isNull());
        removeRezept.setOnAction(e -> this.ctrl.removeRezept(this.rezeptListView.getSelectionModel().getSelectedItem()));
        manageRezept.disableProperty().bind(this.rezeptListView.getSelectionModel().selectedItemProperty().isNull());
        manageRezept.setOnAction(e -> this.ctrl.manageRezept(this.rezeptListView.getSelectionModel().getSelectedItem()));
        printRezepte.setOnAction(e -> this.ctrl.printRezepte());

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void loadRezepte() {
        this.rezeptListView.getItems().clear();
        for(Rezept rezept : this.originalModel.getRezepte()) {
            this.rezeptListView.getItems().add(rezept);
        }
    }

    public ListView<Rezept> getRezeptListView() {
        return this.rezeptListView;
    }

    public Apotheke getModel() {
        return this.model;
    }
}
