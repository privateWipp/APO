package at.apo.view;

import at.apo.APO;
import at.apo.control.KundenController;
import at.apo.model.Apotheke;
import at.apo.model.Kunde;
import at.apo.model.Rezept;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class manageKunden extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke model;
    private KundenController ctrl;

    private ListView<Kunde> kundenListView;
    private ListView<Rezept> rezepteListView;

    private Stage stage;

    public manageKunden(ApoView view, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.model = model;
        this.ctrl = new KundenController(this.view, this, this.model);

        this.kundenListView = new ListView<Kunde>();
        this.rezepteListView = new ListView<Rezept>();
        updateListViews();

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.kundenListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                this.rezepteListView.getItems().clear();
                for(Rezept rezept : this.model.getRezepte()) {
                    if(rezept.getPatient().equals(newValue)) {
                        this.rezepteListView.getItems().add(rezept);
                    }
                }
                this.rezepteListView.refresh();
            }
        });

        this.stage.setTitle("Kunden verwalten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu listen = new Menu("Listen");
        MenuItem refreshAll = new MenuItem("alle aktualisieren");
        listen.getItems().add(refreshAll);

        menuBar.getMenus().add(listen);

        refreshAll.setOnAction(e -> {
            updateListViews();
        });

        setTop(menuBar);

        // -------------------------------------------------------------------------------------------------------------

        setLeft(this.kundenListView);

        Text text = new Text("Kunden / Rezepte, die an ausgewählten Kunden gehen");
        text.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");
        setCenter(text);

        setRight(this.rezepteListView);

        this.stage.show();
    }

    public void updateListViews() {
        this.kundenListView.getItems().clear();
        this.rezepteListView.getItems().clear();
        for(Kunde kunde : this.model.getKunden()) {
            this.kundenListView.getItems().add(kunde);
        }
    }
}
