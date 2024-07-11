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

        menuBar.getMenus().add(bearbeiten);

        setTop(menuBar);

        sortRezeptnummer.setOnAction(e -> {
            this.rezeptListView.getItems().sort(Comparator.comparing(Rezept::getRezeptnummer));
            loadRezepte();
        });
        sortAusstellungsdatum.setOnAction(e -> {
            this.rezeptListView.getItems().sort(Comparator.comparing(Rezept::getAusstellungsDatum));
            loadRezepte();
        });
        sortGueltigBis.setOnAction(e -> {
            this.rezeptListView.getItems().sort(Comparator.comparing(Rezept::getGueltigBis));
            loadRezepte();
        });
        sortRezeptart.setOnAction(e -> {
            this.rezeptListView.getItems().sort(Comparator.comparing(Rezept::getRezeptArt));
            loadRezepte();
        });
        sortPreis.setOnAction(e -> {
            this.rezeptListView.getItems().sort(Comparator.comparing(Rezept::getPreis));
            loadRezepte();
        });

        // -------------------------------------------------------------------------------------------------------------

        setCenter(this.rezeptListView);

        // -------------------------------------------------------------------------------------------------------------

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));

        Button addRezept = new Button("neues Rezept freigeben");
        Button removeRezept = new Button("löschen");
        Button manageRezept = new Button("ändern/verwalten");
        Button printRezepte = new Button("Liste ausgeben");

        toolBar.getItems().addAll(addRezept, removeRezept, manageRezept, printRezepte);

        setRight(toolBar);

        addRezept.setOnAction(e -> this.ctrl.addRezept());

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    private void loadRezepte() {
        this.rezeptListView.getItems().clear();
        for(Rezept rezept : this.model.getRezepte()) {
            this.rezeptListView.getItems().add(rezept);
        }
    }
}
