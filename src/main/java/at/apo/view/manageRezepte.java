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

public class manageRezepte extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke model;
    private RezepteController ctrl;
    private Stage stage;

    private ListView<Rezept> rezeptListView;

    public manageRezepte(ApoView view, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.model = model;
        this.ctrl = new RezepteController(this.model, this.view, this);
        this.stage = new Stage();

        this.rezeptListView = new ListView<Rezept>();
        loadRezepte();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Rezepte verwalten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu list = new Menu("Liste");
        MenuItem refreshList = new MenuItem("aktualisieren");
        list.getItems().add(refreshList);

        menuBar.getMenus().add(list);

        refreshList.setOnAction(e -> {
            loadRezepte();
            System.out.println("Die Liste der Rezepte wurde aktualisiert.");
        });

        setTop(menuBar);

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

        addRezept.setOnAction(e -> this.ctrl.addRezept());
        removeRezept.disableProperty().bind(this.rezeptListView.getSelectionModel().selectedItemProperty().isNull());
        removeRezept.setOnAction(e -> this.ctrl.removeRezept(this.rezeptListView.getSelectionModel().getSelectedItem()));
        manageRezept.disableProperty().bind(this.rezeptListView.getSelectionModel().selectedItemProperty().isNull());
        manageRezept.setOnAction(e -> this.ctrl.manageRezept(this.rezeptListView.getSelectionModel().getSelectedItem()));
        printRezepte.setOnAction(e -> this.ctrl.printRezepte());

        setRight(toolBar);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void loadRezepte() {
        this.rezeptListView.getItems().clear();
        for(Rezept rezept : this.model.getRezepte()) {
            this.rezeptListView.getItems().add(rezept);
        }
    }
}
