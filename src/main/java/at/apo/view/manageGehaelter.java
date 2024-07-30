package at.apo.view;

import at.apo.APO;
import at.apo.control.GehaelterController;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class manageGehaelter extends BorderPane {
    private APO apoInstance;
    private ApoView view;
    private Apotheke model;
    private GehaelterController ctrl;

    private Stage stage;

    public manageGehaelter(ApoView view, Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.view = view;
        this.model = model;
        this.ctrl = new GehaelterController(this.view, this, this.model);

        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Gehälter verwalten : " + this.model.getName());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.45, this.apoInstance.getScreenHeight() * 0.55);
        this.stage.setScene(scene);

        Text fixkosten = new Text("Fixkosten (durch Gehälter): " + fixkostenGehaelter() + "€");
        Text aktuellesBudget = new Text("aktuelles Budget der Apotheke: " + this.model.getBudget() + "€");
        applyStyle(fixkosten);
        applyStyle(aktuellesBudget);
        VBox introVBox = new VBox(fixkosten, aktuellesBudget);
        introVBox.setPadding(new Insets(20, 20, 20, 20));
        introVBox.setSpacing(6);
        introVBox.setStyle("-fx-font-size: " + this.apoInstance.getScreenWidth() * 0.003 + "px;");

        setCenter(introVBox);

        this.stage.show();
    }

    private String fixkostenGehaelter() {
        double gehaelter = 0;
        for(Mitarbeiter mitarbeiter : this.model.getMitarbeiter()) {
            gehaelter += mitarbeiter.getGehalt();
        }
        return String.valueOf(gehaelter);
    }

    private void applyStyle(Text text) {
        text.setStyle("-fx-font-size: 15px;");
    }
}
