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

import java.util.Comparator;

public class manageMedikamente extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke originalModel;
    private Apotheke modelR;
    private Apotheke modelNR;
    private MedikamenteController ctrl;
    private Stage stage;

    private ListView<Medikament> rMedikamente;
    private ListView<Medikament> nrMedikamente;

    public manageMedikamente(ApoView view, Apotheke originalModel) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.originalModel = originalModel;
        this.modelR = this.originalModel.clone();
        this.modelNR = this.originalModel.clone();
        this.ctrl = new MedikamenteController(this.originalModel, this.view, this);
        this.stage = new Stage();

        this.rMedikamente = new ListView<Medikament>();
        this.nrMedikamente = new ListView<Medikament>();
        updateRMedikamente();
        updateNRMedikamente();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Medikamente der Apotheke " + this.originalModel.getName() + " verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");

        Menu rezeptpflichtig = new Menu("rezeptpflichtig");
        MenuItem sortBezeichnungR = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortPreisR = new MenuItem("sortieren nach Preis");
        MenuItem sortLagerbestandR = new MenuItem("sortieren nach Lagerbestand/Anzahl");
        MenuItem sortVerfallsdatumR = new MenuItem("sortieren nach Verfallsdatum");
        MenuItem sortVerfuegbarR = new MenuItem("sortieren nach Verfügbarkeit");
        rezeptpflichtig.getItems().addAll(sortBezeichnungR, sortPreisR, sortLagerbestandR, sortVerfallsdatumR, sortVerfuegbarR);

        Menu nichtRezeptpflichtig = new Menu("nicht rezeptpflichtig");
        MenuItem sortBezeichnungNR = new MenuItem("sortieren nach Bezeichnung");
        MenuItem sortPreisNR = new MenuItem("sortieren nach Preis");
        MenuItem sortLagerbestandNR = new MenuItem("sortieren nach Lagerbestand/Anzahl");
        MenuItem sortVerfallsdatumNR = new MenuItem("sortieren nach Verfallsdatum");
        MenuItem sortVerfuegbarNR = new MenuItem("sortieren nach Verfügbarkeit");
        nichtRezeptpflichtig.getItems().addAll(sortBezeichnungNR, sortPreisNR, sortLagerbestandNR, sortVerfallsdatumNR, sortVerfuegbarNR);

        bearbeiten.getItems().addAll(rezeptpflichtig, nichtRezeptpflichtig);

        Menu listen = new Menu("Listen");
        MenuItem refreshLists = new MenuItem("alle aktualisieren");
        listen.getItems().add(refreshLists);

        menuBar.getMenus().addAll(bearbeiten, listen);

        sortBezeichnungR.setOnAction(e -> {
            this.modelR.getMedikamente().sort(Comparator.comparing(Medikament::getBezeichnung));
            updateRMedikamente();
        });
        sortBezeichnungNR.setOnAction(e -> {
            this.modelNR.getMedikamente().sort(Comparator.comparing(Medikament::getBezeichnung));
            updateNRMedikamente();
        });
        sortPreisR.setOnAction(e -> {
            this.modelR.getMedikamente().sort(Comparator.comparing(Medikament::getPreis));
            updateRMedikamente();
        });
        sortPreisNR.setOnAction(e -> {
            this.modelNR.getMedikamente().sort(Comparator.comparing(Medikament::getPreis));
            updateNRMedikamente();
        });
        sortLagerbestandR.setOnAction(e -> {
            this.modelR.getMedikamente().sort(Comparator.comparing(Medikament::getLagerbestand));
            updateRMedikamente();
        });
        sortLagerbestandNR.setOnAction(e -> {
            this.modelNR.getMedikamente().sort(Comparator.comparing(Medikament::getLagerbestand));
            updateNRMedikamente();
        });
        sortVerfallsdatumR.setOnAction(e -> {
            this.modelR.getMedikamente().sort(Comparator.comparing(Medikament::getVerfallsdatum));
            updateRMedikamente();
        });
        sortVerfallsdatumNR.setOnAction(e -> {
            this.modelNR.getMedikamente().sort(Comparator.comparing(Medikament::getVerfallsdatum));
            updateNRMedikamente();
        });
        sortVerfuegbarR.setOnAction(e -> {
            this.modelR.getMedikamente().sort(Comparator.comparing(Medikament::isVerfuegbar));
            updateRMedikamente();
        });
        sortVerfuegbarNR.setOnAction(e -> {
            this.modelNR.getMedikamente().sort(Comparator.comparing(Medikament::isVerfuegbar));
            updateNRMedikamente();
        });

        refreshLists.setOnAction(e -> {
            updateRMedikamente();
            updateNRMedikamente();
            System.out.println("Die rezeptfreie und nicht rezeptfreie Liste an Medikamenten wurde aktualisiert.");
        });

        HBox manageMedikamenteHBox = new HBox();
        Button addMedikament = new Button("+ Medikament");
        Button removeMedikament = new Button("- Medikament");
        Button manageMedikament = new Button("anschauen/verwalten");
        Button printMedikamente = new Button("alle Medikamente ausgeben");
        manageMedikamenteHBox.getChildren().addAll(addMedikament, removeMedikament, manageMedikament, printMedikamente);
        manageMedikamenteHBox.setPadding(new Insets(20, 10, 20, 10));
        manageMedikamenteHBox.setSpacing(10);

        VBox topVBox = new VBox(menuBar, manageMedikamenteHBox);

        setTop(topVBox);

        // Verwaltung der Buttons
        addMedikament.setOnAction(e -> this.ctrl.addMedikament());
        removeMedikament.disableProperty().bind(
                this.rMedikamente.getSelectionModel().selectedItemProperty().isNull()
                        .and(this.nrMedikamente.getSelectionModel().selectedItemProperty().isNull())
        );
        //removeMedikament.setOnAction(e -> this.ctrl.removeMedikament());
        manageMedikament.disableProperty().bind(
                this.rMedikamente.getSelectionModel().selectedItemProperty().isNull()
                        .and(this.nrMedikamente.getSelectionModel().selectedItemProperty().isNull())
        );
        printMedikamente.setOnAction(e -> this.ctrl.printMedikamente());

        // -------------------------------------------------------------------------------------------------------------

        setLeft(this.rMedikamente);
        Text text = new Text("rezeptpflichtig / NICHT rezeptpflichtig");
        text.setStyle("-fx-font-size: 18px;");
        setCenter(text);
        setRight(this.nrMedikamente);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void updateRMedikamente() {
        this.rMedikamente.getItems().clear();
        for (Medikament medikament : this.modelR.getMedikamente()) {
            if (medikament.isRezeptpflichtig()) {
                this.rMedikamente.getItems().add(medikament);
            }
        }
    }

    public void updateNRMedikamente() {
        this.nrMedikamente.getItems().clear();
        for (Medikament medikament : this.modelNR.getMedikamente()) {
            if (!medikament.isRezeptpflichtig()) {
                this.nrMedikamente.getItems().add(medikament);
            }
        }
    }

    public ListView<Medikament> getrMedikamente() {
        return this.rMedikamente;
    }

    public ListView<Medikament> getNRMedikamente() {
        return this.nrMedikamente;
    }

    public Apotheke getModelR() {
        return this.modelR;
    }

    public Apotheke getModelNR() {
        return this.modelNR;
    }
}
