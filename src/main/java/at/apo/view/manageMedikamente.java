package at.apo.view;

import at.apo.APO;
import at.apo.control.MedikamenteController;
import at.apo.model.Apotheke;
import at.apo.model.Medikament;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class manageMedikamente extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke model;
    private MedikamenteController ctrl;
    private Stage stage;

    private ListView<Medikament> rMedikamente;
    private ListView<Medikament> nrMedikamente;

    public manageMedikamente(ApoView view, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.model = model;
        this.ctrl = new MedikamenteController(this.model, this.view, this);
        this.stage = new Stage();

        this.rMedikamente = new ListView<Medikament>();
        this.nrMedikamente = new ListView<Medikament>();
        updateMedikamente();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Medikamente verwalten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu listen = new Menu("Listen");
        MenuItem refreshLists = new MenuItem("alle aktualisieren");
        listen.getItems().add(refreshLists);

        menuBar.getMenus().addAll(listen);

        HBox manageMedikamenteHBox = new HBox();
        Button addMedikament = new Button("+ Medikament");
        Button removeMedikament = new Button("- Medikament");
        Button manageMedikament = new Button("anschauen/verwalten");
        Button printMedikamente = new Button("alle Medikamente ausgeben");
        manageMedikamenteHBox.getChildren().addAll(addMedikament, removeMedikament, manageMedikament, printMedikamente);
        manageMedikamenteHBox.setPadding(new Insets(20, 10, 20, 10));
        manageMedikamenteHBox.setSpacing(10);

        VBox topVBox = new VBox(menuBar, manageMedikamenteHBox);
        topVBox.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        refreshLists.setOnAction(e -> {
            updateMedikamente();
            System.out.println("Die rezeptfreie und nicht rezeptfreie Liste an Medikamenten wurde aktualisiert.");
        });

        addMedikament.setOnAction(e -> this.ctrl.addMedikament());
        removeMedikament.disableProperty().bind(
                this.rMedikamente.getSelectionModel().selectedItemProperty().isNull()
                        .and(this.nrMedikamente.getSelectionModel().selectedItemProperty().isNull())
        );
        removeMedikament.setOnAction(e -> {
            Medikament selectR = this.rMedikamente.getSelectionModel().getSelectedItem();
            Medikament selectNR = this.nrMedikamente.getSelectionModel().getSelectedItem();
            if(selectR != null) {
                this.ctrl.removeMedikament(selectR);
            } else if (selectNR != null) {
                this.ctrl.removeMedikament(selectNR);
            } else {
                this.view.errorAlert("kein ausgewähltes Medikament", "Es ist momentan kein Medikament ausgewählt, daher kann auch keines verwaltet werden!");
            }
        });
        manageMedikament.disableProperty().bind(
                this.rMedikamente.getSelectionModel().selectedItemProperty().isNull()
                        .and(this.nrMedikamente.getSelectionModel().selectedItemProperty().isNull())
        );
        manageMedikament.setOnAction(e -> {
            Medikament selectR = this.rMedikamente.getSelectionModel().getSelectedItem();
            Medikament selectNR = this.nrMedikamente.getSelectionModel().getSelectedItem();
            if(selectR != null) {
                this.ctrl.manageMedikament(selectR);
            } else if (selectNR != null) {
                this.ctrl.manageMedikament(selectNR);
            } else {
                this.view.errorAlert("kein ausgewähltes Medikament", "Es ist momentan kein Medikament ausgewählt, daher kann auch keines verwaltet werden!");
            }
        });
        printMedikamente.setOnAction(e -> this.ctrl.printMedikamente());

        setTop(topVBox);

        // -------------------------------------------------------------------------------------------------------------

        setLeft(this.rMedikamente);

        Text text = new Text("rezeptpflichtig / NICHT rezeptpflichtig");
        text.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.005) + "px;");
        setCenter(text);

        setRight(this.nrMedikamente);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void updateMedikamente() {
        this.rMedikamente.getItems().clear();
        this.nrMedikamente.getItems().clear();
        for(Medikament medikament : this.model.getMedikamente()) {
            if(medikament.isRezeptpflichtig()) {
                this.rMedikamente.getItems().add(medikament);
            } else {
                this.nrMedikamente.getItems().add(medikament);
            }
        }
    }
}
