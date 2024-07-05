package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Geschaeftsfuehrer;
import at.apo.view.ApoView;
import at.apo.view.geschaeftsfuehrerFestlegenDialog;
import at.apo.view.manageEmployees;
import at.apo.view.oeffnungszeitenFestlegenDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ApoController {
    private ApoView view;
    private Apotheke model;

    public ApoController(ApoView view, Apotheke model) {
        this.view = view;
        this.model = model;
    }

    public void manageEmployees() {
        manageEmployees manageEmployees = new manageEmployees(this.view, this.model);
    }

    public void geschaeftsfuehrerFestlegen() {
        if (this.model.getGeschaeftsfuehrer() == null) {
            geschaeftsfuehrerFestlegenDialog geschaeftsfuehrerFestlegenDialog = new geschaeftsfuehrerFestlegenDialog();
            Optional<Geschaeftsfuehrer> g = geschaeftsfuehrerFestlegenDialog.showAndWait();

            g.ifPresent(geschaeftsfuehrer -> {
                try {
                    this.model.setGeschaeftsfuehrer(geschaeftsfuehrer);
                    this.view.setChanged(true);
                } catch (APOException e) {
                    this.view.errorAlert("Fehler beim Festlegen des Geschäftsführers..", e.getMessage());
                }
            });
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Geschäftsführer festlegen");
            confirmation.setHeaderText("Geschäftsführer vorhanden");
            confirmation.setContentText("Für die Apotheke: " + this.model.getName() + "\n" +
                    "wurde bereits ein Geschäftsführer festgelegt (" + this.model.getGeschaeftsfuehrer().getVorname() + " " + this.model.getGeschaeftsfuehrer().getNachname() + ")!\n" +
                    "Sind Sie sicher, dass Sie einen neuen Geschäftsführer festlegen wollen?");

            ButtonType yes = new ButtonType("Ja");
            ButtonType no = new ButtonType("Nein");
            ButtonType cancel = new ButtonType("Abbrechen");

            confirmation.getButtonTypes().setAll(yes, no, cancel);

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == yes) {
                geschaeftsfuehrerFestlegenDialog geschaeftsfuehrerFestlegenDialog = new geschaeftsfuehrerFestlegenDialog();
                Optional<Geschaeftsfuehrer> g = geschaeftsfuehrerFestlegenDialog.showAndWait();

                g.ifPresent(geschaeftsfuehrer -> {
                    try {
                        this.model.setGeschaeftsfuehrer(geschaeftsfuehrer);
                    } catch (APOException e) {
                        this.view.errorAlert("Fehler beim Festlegen des Geschäftsführers..", e.getMessage());
                    }
                });
            } else {
                confirmation.close();
            }
        }
    }

    public void oeffnungszeitenFestlegen() {
        if (this.model.getOeffnungszeiten().isEmpty()) {
            oeffnungszeitenFestlegenDialog oeffnungszeitenFestlegenDialog = new oeffnungszeitenFestlegenDialog();
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Öffnungszeiten festlegen");
            confirmation.setHeaderText("bereits festgelegte Öffnungszeiten");
            confirmation.setContentText("Für die Apotheke: " + this.model.getName() + "\n" +
                    "wurden bereits Öffnungszeiten festgelegt.\n" +
                    "Sind Sie sicher, dass Sie diese ändern wollen?");

            ButtonType yes = new ButtonType("Ja");
            ButtonType no = new ButtonType("Nein");
            ButtonType cancel = new ButtonType("Abbrechen");

            confirmation.getButtonTypes().setAll(yes, no, cancel);
        }
    }
}
