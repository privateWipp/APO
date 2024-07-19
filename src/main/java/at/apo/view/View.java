package at.apo.view;

import at.apo.APO;
import at.apo.control.Controller;
import at.apo.model.APOException;
import at.apo.model.Apotheke;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Arrays;

public class View extends BorderPane {

    private APO apoInstance;
    private File directory;
    private Controller ctrl;
    private ListView<Apotheke> apothekenListView;

    public View() {
        this.apoInstance = APO.getInstance();

        this.directory = new File("apotheken");
        if(!this.directory.exists()) {
            this.directory.mkdir();
        }

        this.ctrl = new Controller(this);

        this.apothekenListView = new ListView<Apotheke>();
        loadApotheken();

        initGUI();
    }

    private void initGUI() {
        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu apotheke = new Menu("Apotheke");
        MenuItem createApo = new MenuItem("Erstellen");
        MenuItem importApo = new MenuItem("Importieren");
        MenuItem openApo = new MenuItem("Apotheke öffnen/verwalten");
        MenuItem deleteApo = new MenuItem("Löschen");
        MenuItem refreshList = new MenuItem("Liste aktualisieren");
        apotheke.getItems().addAll(createApo, importApo, openApo, deleteApo, refreshList);

        Menu help = new Menu("Hilfe");
        MenuItem aboutMe = new MenuItem("Über mich");
        help.getItems().add(aboutMe);

        menuBar.getMenus().addAll(apotheke, help);

        createApo.setOnAction(e -> this.ctrl.createApo());
        importApo.setOnAction(e -> this.ctrl.importApo());
        openApo.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        openApo.setOnAction(e -> this.ctrl.openApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
        deleteApo.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteApo.setOnAction(e -> this.ctrl.deleteApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
        refreshList.setOnAction(e -> loadApotheken());

        aboutMe.setOnAction(e -> this.ctrl.aboutMe());

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        // Center: ListView<Apotheke> an Apotheken
        setCenter(this.apothekenListView);

        // -------------------------------------------------------------------------------------------------------------

        // Right: mit der ausgewählten Apotheke (aus der ListView) interagieren (=ToolBar)
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(20, 20, 20, 20));
        toolBar.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        VBox toolBarItemsVBox = new VBox();
        toolBarItemsVBox.setSpacing(10);
        Button openApo2 = new Button("öffnen/verwalten");
        Button deleteApo2 = new Button("löschen");
        toolBarItemsVBox.getChildren().addAll(openApo2, deleteApo2);

        toolBar.getItems().addAll(toolBarItemsVBox);

        openApo2.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        openApo2.setOnAction(e -> this.ctrl.openApo(this.apothekenListView.getSelectionModel().getSelectedItem()));
        deleteApo2.disableProperty().bind(this.apothekenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteApo2.setOnAction(e -> this.ctrl.deleteApo(this.apothekenListView.getSelectionModel().getSelectedItem()));

        setRight(toolBar);
    }

    private void loadApotheken() {
        this.apothekenListView.getItems().clear();
        File[] files = this.directory.listFiles((dir, name) -> name.endsWith(".apo"));
        if(files != null) {
            Arrays.stream(files).forEach(file -> {
                try {
                    Apotheke apotheke = new Apotheke("Apotheke", "Musterstraße 1", "+43 677 62099198", "j.mader@apotronik.at", 1000000);
                    apotheke.laden(file);
                    this.apothekenListView.getItems().add(apotheke);
                } catch (APOException e) {
                    errorAlert("Fehler beim Laden der Apotheken aus dem Ordner 'apotheken'", e.getMessage());
                }
            });
        }
    }

    public ListView<Apotheke> getApothekenListView() {
        return this.apothekenListView;
    }

    public File getDirectory() {
        return this.directory;
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

    public APO getApoInstance() {
        return this.apoInstance;
    }
}
