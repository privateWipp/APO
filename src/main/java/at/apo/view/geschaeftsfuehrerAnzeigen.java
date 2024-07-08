package at.apo.view;

import at.apo.APO;
import at.apo.model.Apotheke;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class geschaeftsfuehrerAnzeigen extends BorderPane {
    private APO apoInstance;
    private Apotheke model;
    private Stage stage;

    public geschaeftsfuehrerAnzeigen(Apotheke model) {
        this.apoInstance = APO.getInstance();
        this.model = model;
        this.stage = new Stage();

        initGUI();
    }

    private void initGUI() {
        this.stage.setTitle("Informationen zu Geschäftsführer " + this.model.getGeschaeftsfuehrer().getVorname() + " " + this.model.getGeschaeftsfuehrer().getNachname());
        this.stage.setResizable(false);
        Scene scene = new Scene(this, this.apoInstance.getScreenWidth() * 0.23, this.apoInstance.getScreenHeight() * 0.11);
        this.stage.setScene(scene);

        TextArea gFTA = new TextArea();
        gFTA.setEditable(false);
        gFTA.setText(this.model.getGeschaeftsfuehrer().toString());

        setCenter(gFTA);

        this.stage.show();
    }
}
