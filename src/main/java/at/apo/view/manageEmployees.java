package at.apo.view;

import at.apo.APO;
import at.apo.control.EmployeeController;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class manageEmployees extends BorderPane {
    private APO apoInstance;
    private Apotheke model;
    private EmployeeController ctrl;
    private ListView<Mitarbeiter> mitarbeiterListView;
    private TextArea mitarbeiterTA;
    private Stage stage;

    public manageEmployees(Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.model = model;
        this.ctrl = new EmployeeController(this, this.model);
        this.mitarbeiterListView = new ListView<Mitarbeiter>();
        this.mitarbeiterListView.setCellFactory(new Callback<ListView<Mitarbeiter>, ListCell<Mitarbeiter>>() {
            @Override
            public ListCell<Mitarbeiter> call(ListView<Mitarbeiter> param) {
                return new ListCell<Mitarbeiter>() {
                    @Override
                    protected void updateItem(Mitarbeiter mitarbeiter, boolean empty) {
                        super.updateItem(mitarbeiter, empty);

                        if(mitarbeiter == null || empty) {
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
        this.stage.setTitle(this.model.getName() + " : Mitarbeiter verwalten");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.25, this.apoInstance.getScreenHeight() * 0.4);
        this.stage.setScene(scene);

        // Top: Buttons zum Verwalten der Mitarbeiter
        HBox manageEmployeesHBox = new HBox();
        Button addEmployee = new Button("+ Mitarbeiter");
        Button removeEmployee = new Button("- Mitarbeiter");
        Button manageEmployee = new Button("anschauen/verwalten");
        manageEmployeesHBox.getChildren().addAll(addEmployee, removeEmployee, manageEmployee);
        manageEmployeesHBox.setPadding(new Insets(10, 10, 10, 10));
        manageEmployeesHBox.setSpacing(10);

        setTop(manageEmployeesHBox);

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

        // Bottom: Details zum ausgewähltem Mitarbeiter
        this.mitarbeiterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
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
        if(this.model.getMitarbeiter() != null && !this.model.getMitarbeiter().isEmpty()) {
            for(Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
                this.mitarbeiterListView.getItems().add(mitarbeiter);
            }
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
}
