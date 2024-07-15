package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import at.apo.model.Geschaeftsfuehrer;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
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
            geschaeftsfuehrerFestlegenDialog geschaeftsfuehrerFestlegenDialog = new geschaeftsfuehrerFestlegenDialog(this.model, false);
            Optional<Geschaeftsfuehrer> g = geschaeftsfuehrerFestlegenDialog.showAndWait();

            g.ifPresent(geschaeftsfuehrer -> {
                try {
                    this.model.setGeschaeftsfuehrer(geschaeftsfuehrer);
                    this.view.setChanged(true);
                    System.out.println("Geschäftsführer der Apotheke " + this.model.getName() + " wurde aktualisiert: " + this.model.getGeschaeftsfuehrer().getVorname() + " " + this.model.getGeschaeftsfuehrer().getNachname());
                } catch (APOException e) {
                    this.view.errorAlert("Fehler beim Festlegen des Geschäftsführers", e.getMessage());
                    System.out.println("Fehler: Das Festlegen des Geschäftsführers der Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
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
                geschaeftsfuehrerFestlegenDialog geschaeftsfuehrerFestlegenDialog = new geschaeftsfuehrerFestlegenDialog(this.model, true);
                Optional<Geschaeftsfuehrer> g = geschaeftsfuehrerFestlegenDialog.showAndWait();

                g.ifPresent(geschaeftsfuehrer -> {
                    try {
                        this.model.setGeschaeftsfuehrer(geschaeftsfuehrer);
                        this.view.setChanged(true);
                        System.out.println("Geschäftsführer der Apotheke " + this.model.getName() + " wurde aktualisiert: " + this.model.getGeschaeftsfuehrer().getVorname() + " " + this.model.getGeschaeftsfuehrer().getNachname());
                    } catch (APOException e) {
                        this.view.errorAlert("Fehler beim Festlegen des Geschäftsführers", e.getMessage());
                        System.out.println("Fehler: Das Festlegen des Geschäftsführers der Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
                    }
                });
            } else {
                confirmation.close();
            }
        }
    }

    public void geschaeftsfuehrerAnzeigen() {
        if(this.model.getGeschaeftsfuehrer() != null) {
            geschaeftsfuehrerAnzeigen geschaeftsfuehrerAnzeigen = new geschaeftsfuehrerAnzeigen(this.model);
        } else {
            this.view.errorAlert("Geschäftsführer", "Die Apotheke: " + this.model.getName()  + "\n" +
                    "hat zurzeit keinen Geschäftsführer, daher kann dieser auch nicht angezeigt werden!");
        }
    }

    public void oeffnungszeitenFestlegen() {
        if (!this.model.getOeffnungszeiten().containsKey("Montag")) {
            oeffnungszeitenFestlegenDialog oeffnungszeitenFestlegenDialog = new oeffnungszeitenFestlegenDialog(this.model,false);
            Optional<HashMap<String, String>> oe = oeffnungszeitenFestlegenDialog.showAndWait();

            oe.ifPresent(oeffnungszeiten -> {
                try {
                    this.model.setOeffnungszeiten(oeffnungszeiten);
                    this.view.setChanged(true);
                    System.out.println("Die Öffnungszeiten für die Apotheke " + this.model.getName() + " wurden aktualisiert.");
                } catch (APOException e) {
                    this.view.errorAlert("Fehler beim Festlegen der Öffnungszeiten", e.getMessage());
                    System.out.println("Fehler: Das Festlegen der Öffnungszeiten für die Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
                }
            });
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

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == yes) {
                oeffnungszeitenFestlegenDialog oeffnungszeitenFestlegenDialog = new oeffnungszeitenFestlegenDialog(this.model, true);
                Optional<HashMap<String, String>> oe = oeffnungszeitenFestlegenDialog.showAndWait();

                oe.ifPresent(oeffnungszeiten -> {
                    try {
                        this.model.setOeffnungszeiten(oeffnungszeiten);
                        this.view.setChanged(true);
                        System.out.println("Die Öffnungszeiten für die Apotheke " + this.model.getName() + " wurden aktualisiert.");
                    } catch (APOException e) {
                        this.view.errorAlert("Fehler beim Festlegen der Öffnungszeiten", e.getMessage());
                        System.out.println("Fehler: Das Festlegen der Öffnungszeiten für die Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
                    }
                });
            } else {
                confirmation.close();
            }
        }
    }

    public void apothekeBearbeiten() {
        apothekeBearbeiten apothekeBearbeiten = new apothekeBearbeiten(this.view, this.model);
    }

    public void clearChanges() {
        this.view.getChangesTA().clear();
    }

    public void manageMedikamente() {
        manageMedikamente manageMedikamente = new manageMedikamente(this.view, this.model);
    }

    public void manageRezepte() {
        manageRezepte manageRezepte = new manageRezepte(this.view, this.model);
    }

    public void newBestellung() {
        neueBestellungDialog neueBestellungDialog = new neueBestellungDialog(this.view, this.model);
        Optional<Bestellung> b = neueBestellungDialog.showAndWait();

        b.ifPresent(bestellung -> {
            try {
                this.model.addBestellung(bestellung);
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println("Die Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' wurde erfolgreich im Name der Apotheke " + this.model.getName() + " aufgegeben!");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Aufgeben einer neuen Bestellung", e.getMessage());
                System.out.println("Fehler: Das Aufgeben einer neuen Bestellung für die Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
            }
        });
    }
}
