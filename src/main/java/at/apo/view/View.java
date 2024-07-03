package at.apo.view;

import at.apo.control.Controller;
import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class View extends BorderPane {
    private Controller ctrl;
    private ListView<Apotheke> apothekenListView;

    public View() {
        this.ctrl = new Controller(this);
        this.apothekenListView = new ListView<Apotheke>();

        initGUI();
    }

    private void initGUI() {
        // Top: Menü(s)
        MenuBar menuBar = new MenuBar();

        Menu apotheke = new Menu("Apotheke");
        MenuItem createApo = new MenuItem("Erstellen");
        MenuItem importApo = new MenuItem("Importieren");
        MenuItem openApo = new MenuItem("Apotheke öffnen/verwalten");
        MenuItem deleteApo = new MenuItem("Löschen");
        apotheke.getItems().addAll(createApo, importApo, openApo, deleteApo);

        menuBar.getMenus().addAll(apotheke);

        setTop(menuBar);

        // Menü-Verwaltung
        createApo.setOnAction(e -> this.ctrl.createApo());
        importApo.setOnAction(e -> this.ctrl.importApo());
        openApo.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        openApo.setOnAction(e -> this.ctrl.openApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
        deleteApo.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteApo.setOnAction(e -> this.ctrl.deleteApo(this.apothekenListView.getSelectionModel().getSelectedItem()));

        // -------------------------------------------------------------------------------------------------------------

        // Center: ListView<Apotheke> an Apotheken
        setCenter(this.apothekenListView);

        // -------------------------------------------------------------------------------------------------------------

        // Right: mit der ausgewählten Apotheke (aus der ListView) interagieren (=ToolBar)
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));

        Button openApo2 = new Button("Öffnen/Verwalten");
        Button deleteApo2 = new Button("Löschen");

        toolBar.getItems().addAll(openApo2, deleteApo2);

        setRight(toolBar);

        // Verwalten der Funktionen, der Buttons in der ToolBar
        openApo2.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        openApo2.setOnAction(e -> this.ctrl.openApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
        deleteApo2.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteApo2.setOnAction(e -> this.ctrl.deleteApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
    }

    public ListView<Apotheke> getApothekenListView() {
        return this.apothekenListView;
    }

    public void errorAlert(String headerText, String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    public void infoAlert(String headerText, String contentText) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Info");
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(contentText);
        infoAlert.showAndWait();
    }
}
