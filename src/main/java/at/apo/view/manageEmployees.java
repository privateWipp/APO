package at.apo.view;

import at.apo.APO;
import at.apo.control.EmployeeController;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Comparator;

public class manageEmployees extends BorderPane {
    private APO apoInstance;
    private ApoView mainView;
    private Apotheke originalModel;
    private Apotheke model;
    private EmployeeController ctrl;
    private ListView<Mitarbeiter> mitarbeiterListView;
    private TextArea mitarbeiterTA;
    private Stage stage;

    public manageEmployees(ApoView mainView, Apotheke originalModel) {
        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.originalModel = originalModel;
        this.model = this.originalModel.clone();
        this.ctrl = new EmployeeController(this.mainView, this, this.originalModel);
        this.mitarbeiterListView = new ListView<Mitarbeiter>();
        this.mitarbeiterListView.setCellFactory(new Callback<ListView<Mitarbeiter>, ListCell<Mitarbeiter>>() {
            @Override
            public ListCell<Mitarbeiter> call(ListView<Mitarbeiter> param) {
                return new ListCell<Mitarbeiter>() {
                    @Override
                    protected void updateItem(Mitarbeiter mitarbeiter, boolean empty) {
                        super.updateItem(mitarbeiter, empty);

                        if (mitarbeiter == null || empty) {
                            setText(null);
                        } else {
                            setText(mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + "\n" +
                                    mitarbeiter.getTelefonnummer() + "\n" +
                                    mitarbeiter.getEmail() + "\n" +
                                    "Gehalt: " + mitarbeiter.getGehalt() + " €");
                        }
                    }
                };
            }
        });
        updateMitarbeiterListView();
        this.mitarbeiterTA = new TextArea();
        this.mitarbeiterTA.setEditable(false);
        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle(this.originalModel.getName() + " : Mitarbeiter verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar und Buttons zum Verwalten der Mitarbeiter
        MenuBar menuBar = new MenuBar();

        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem sortID = new MenuItem("nach ID sortieren");
        MenuItem sortNachname = new MenuItem("nach Nachnamen sortieren");
        MenuItem sortVorname = new MenuItem("nach Vornamen sortieren");
        MenuItem sortGebDat = new MenuItem("nach Geb. Dat. sortieren");
        MenuItem sortGehalt = new MenuItem("nach Gehalt sortieren");
        bearbeiten.getItems().addAll(sortID, sortNachname, sortVorname, sortGebDat, sortGehalt);

        Menu list = new Menu("Liste");
        MenuItem refreshList = new MenuItem("aktualisieren");
        list.getItems().add(refreshList);

        menuBar.getMenus().addAll(bearbeiten, list);

        sortID.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getId));
            updateMitarbeiterListView();
        });
        sortNachname.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getNachname));
            updateMitarbeiterListView();
        });
        sortVorname.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getVorname));
            updateMitarbeiterListView();
        });
        sortGebDat.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getGeburtsdatum));
            updateMitarbeiterListView();
        });
        sortGehalt.setOnAction(e -> {
            this.model.getMitarbeiter().sort(Comparator.comparing(Mitarbeiter::getGehalt));
            updateMitarbeiterListView();
        });

        refreshList.setOnAction(e -> {
            updateMitarbeiterListView();
            System.out.println("Die Liste der Mitarbeiter wurde aktualisiert.");
        });

        HBox manageEmployeesHBox = new HBox();
        Button addEmployee = new Button("+ Mitarbeiter");
        Button removeEmployee = new Button("- Mitarbeiter");
        Button manageEmployee = new Button("anschauen/verwalten");
        manageEmployeesHBox.getChildren().addAll(addEmployee, removeEmployee, manageEmployee);
        manageEmployeesHBox.setPadding(new Insets(20, 10, 20, 10));
        manageEmployeesHBox.setSpacing(10);

        VBox topVBox = new VBox(menuBar, manageEmployeesHBox);

        setTop(topVBox);

        // Verwaltung der Buttons
        addEmployee.setOnAction(e -> this.ctrl.addEmployee());
        removeEmployee.disableProperty().bind(this.mitarbeiterListView.getSelectionModel().selectedItemProperty().isNull());
        removeEmployee.setOnAction(e -> this.ctrl.removeEmployee(this.mitarbeiterListView.getSelectionModel().getSelectedItem()));
        manageEmployee.disableProperty().bind(this.mitarbeiterListView.getSelectionModel().selectedItemProperty().isNull());
        manageEmployee.setOnAction(e -> this.ctrl.manageEmployee(this.mitarbeiterListView.getSelectionModel().getSelectedItem()));

        // -------------------------------------------------------------------------------------------------------------

        // Center: ListView<Mitarbeiter> an Mitarbeitern
        setCenter(this.mitarbeiterListView);

        // -------------------------------------------------------------------------------------------------------------

        // Right: Infos anzeigen lassen
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));

        Button printAllEmployees = new Button("alle Mitarbeiter anzeigen");

        toolBar.getItems().addAll(printAllEmployees);

        setRight(toolBar);

        printAllEmployees.setOnAction(e -> {
            this.ctrl.printAllEmployees();
        });

        // -------------------------------------------------------------------------------------------------------------

        // Bottom: Details zum ausgewähltem Mitarbeiter
        this.mitarbeiterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.mitarbeiterTA.setText(newValue.toString());
            } else {
                this.mitarbeiterTA.setText(null);
            }
        });
        setBottom(this.mitarbeiterTA);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    private void updateMitarbeiterListView() {
        this.mitarbeiterListView.getItems().clear();
        for (Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
            this.mitarbeiterListView.getItems().add(mitarbeiter);
        }
    }

    public ListView<Mitarbeiter> getMitarbeiterListView() {
        return this.mitarbeiterListView;
    }

    public TextArea getMitarbeiterTA() {
        return this.mitarbeiterTA;
    }

    public void errorAlert(String headerText, String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    public Apotheke getModel() {
        return this.model;
    }
}
