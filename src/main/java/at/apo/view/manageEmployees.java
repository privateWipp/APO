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

public class manageEmployees extends BorderPane {
    private APO apoInstance;
    private ApoView mainView;
    private Apotheke model;
    private EmployeeController ctrl;
    private ListView<Mitarbeiter> mitarbeiterListView;
    private TextArea mitarbeiterTA;
    private Stage stage;

    public manageEmployees(ApoView mainView, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.mainView = mainView;
        this.model = model;
        this.ctrl = new EmployeeController(this.mainView, this, this.model);

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
        this.stage.setTitle("Mitarbeiter verwalten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: MenuBar
        MenuBar menuBar = new MenuBar();

        Menu list = new Menu("Liste");
        MenuItem refreshList = new MenuItem("aktualisieren");
        list.getItems().add(refreshList);

        menuBar.getMenus().add(list);

        Button addEmployee = new Button("+ Mitarbeiter");
        Button removeEmployee = new Button("- Mitarbeiter");
        Button manageEmployee = new Button("anschauen/verwalten");
        HBox manageEmployeesHBox = new HBox(addEmployee, removeEmployee, manageEmployee);
        manageEmployeesHBox.setPadding(new Insets(20, 10, 20, 10));
        manageEmployeesHBox.setSpacing(10);

        VBox topVBox = new VBox(menuBar, manageEmployeesHBox);
        topVBox.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        refreshList.setOnAction(e -> {
            updateMitarbeiterListView();
            System.out.println("Die Liste der Mitarbeiter wurde aktualisiert.");
        });

        addEmployee.setOnAction(e -> this.ctrl.addEmployee());
        removeEmployee.disableProperty().bind(this.mitarbeiterListView.getSelectionModel().selectedItemProperty().isNull());
        removeEmployee.setOnAction(e -> this.ctrl.removeEmployee(this.mitarbeiterListView.getSelectionModel().getSelectedItem()));
        manageEmployee.disableProperty().bind(this.mitarbeiterListView.getSelectionModel().selectedItemProperty().isNull());
        manageEmployee.setOnAction(e -> this.ctrl.manageEmployee(this.mitarbeiterListView.getSelectionModel().getSelectedItem()));

        setTop(topVBox);

        // -------------------------------------------------------------------------------------------------------------

        // Center: ListView<Mitarbeiter> an Mitarbeitern
        setCenter(this.mitarbeiterListView);

        // -------------------------------------------------------------------------------------------------------------

        // Right: Infos anzeigen lassen
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        toolBar.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        Button printAllEmployees = new Button("alle Mitarbeiter anzeigen");

        toolBar.getItems().add(printAllEmployees);

        printAllEmployees.setOnAction(e -> this.ctrl.printAllEmployees());

        setRight(toolBar);

        // -------------------------------------------------------------------------------------------------------------

        // Bottom: Details zum ausgewähltem Mitarbeiter
        this.mitarbeiterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.mitarbeiterTA.setText(newValue.toString());
            } else {
                this.mitarbeiterTA.setText(null);
            }
        });
        VBox mitarbeiterTAVBox = new VBox(this.mitarbeiterTA);
        mitarbeiterTAVBox.setStyle("-fx-font-size: " + (this.apoInstance.getScreenWidth() * 0.003) + "px;");

        setBottom(this.mitarbeiterTA);

        // -------------------------------------------------------------------------------------------------------------

        this.stage.show();
    }

    public void updateMitarbeiterListView() {
        this.mitarbeiterListView.getItems().clear();
        for (Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
            this.mitarbeiterListView.getItems().add(mitarbeiter);
        }
    }
}
