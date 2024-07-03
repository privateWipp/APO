package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.View;
import at.apo.view.createApoDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void createApo() {
        createApoDialog newApo = new createApoDialog();
        Optional<Apotheke> a = newApo.showAndWait();

        a.ifPresent(apotheke -> {
            this.view.getApothekenListView().getItems().add(apotheke);
            this.view.getApothekenListView().refresh();
            this.view.infoAlert("Informationen zu neuer Apotheke:\n" + apotheke.getName(), "Bitte vervollständigen Sie die Attribute der Apotheke\n" +
                    "in den Einstellungen SOBALD WIE MÖGLICH!\n\n" +
                    "zu bearbeitende Attribute:\n" +
                    "Öffnungszeiten");
        });
    }

    public void importApo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Apotheke laden");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("apo-Datei", "*.apo");
        fc.getExtensionFilters().add(extFilter);

        File desktopDir = new File(System.getProperty("user.home"), "Desktop");
        fc.setInitialDirectory(desktopDir);

        File file = fc.showOpenDialog(this.view.getScene().getWindow());

        if (file != null) {
            try {
                Apotheke apotheke = new Apotheke("Apotheke", "Musterstraße 1", "+43 677 62099198", "j.mader@apotronik.at", 1000000);
                apotheke.laden(file);
                if (!(this.view.getApothekenListView().getItems().contains(apotheke))) {
                    this.view.getApothekenListView().getItems().add(apotheke);
                    this.view.getApothekenListView().refresh();
                } else {
                    this.view.errorAlert("Importieren", "Die zu importierende Apotheke ist bereits in der Liste eingetragen!");
                }
            } catch (APOException e) {
                this.view.errorAlert("Importieren", "Die übergebene Apotheke konnte nicht importiert werden:\n" +
                        e.getMessage());
            }
        }
    }

    public void openApo(Apotheke apotheke) {
        ApoView apoView = new ApoView(apotheke);
    }

    public void deleteApo(Apotheke apotheke) {
        this.view.getApothekenListView().getItems().remove(apotheke);
        this.view.getApothekenListView().refresh();
    }
}