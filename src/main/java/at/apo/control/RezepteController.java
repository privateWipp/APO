package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Rezept;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class RezepteController {
    private Apotheke model;
    private ApoView mainView;
    private manageRezepte view;

    public RezepteController(Apotheke model, ApoView mainView, manageRezepte view) {
        this.model = model;
        this.mainView = mainView;
        this.view = view;
    }

    public void addRezept() {
        addRezeptDialog addRezeptDialog = new addRezeptDialog(this.model);
        Optional<Rezept> r = addRezeptDialog.showAndWait();

        r.ifPresent(rezept -> {
            try {
                if (!this.model.getKunden().contains(rezept.getPatient())) {
                    this.model.addKunde(rezept.getPatient());
                }
                this.model.addRezept(rezept);
                this.view.loadRezepte();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Das gerade erstellte Rezept wurde erfolgreich in der Apotheke " + this.model.getName() + " gespeichert!");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Hinzufügen eines neuen Rezepts..", e.getMessage());
                System.out.println("Fehler: Beim Aufnehmen eines neuen Rezepts in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
        });
    }

    public void removeRezept(Rezept rezept) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Rezept entfernen");
        confirmation.setHeaderText("Sind Sie sicher?");
        confirmation.setContentText("Sind Sie sicher, dass sie das ausgewählte Rezept aus der Apotheke " + this.model.getName() + " entfernen möchten?");

        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType cancel = new ButtonType("Abbrechen");

        confirmation.getButtonTypes().setAll(yes, no, cancel);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            try {
                this.model.removeKunde(rezept.getPatient());
                this.model.removeRezept(rezept);
                this.view.loadRezepte();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Das ausgewählte Rezept wurde erfolgreich aus der Apotheke " + this.model.getName() + " entfernt!");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Entfernen eines Rezepts..", e.getMessage());
                System.out.println("Fehler: Beim Löschen eines Rezepts in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
        } else {
            confirmation.close();
        }
    }

    public void manageRezept(Rezept rezept) {
        manageRezeptDialog manageRezeptDialog = new manageRezeptDialog(this.model, rezept);
        Optional<Rezept> r = manageRezeptDialog.showAndWait();

        r.ifPresent(rezept1 -> {
            if(!rezept1.getPatient().equals(rezept.getPatient())) {
                try {
                    this.model.removeKunde(rezept.getPatient());
                    System.out.println("Das Rezept ist nun ausgestellt an den Kunden " + rezept1.getPatient().getName() + " und nicht mehr an " + rezept.getPatient().getName() + ".");
                } catch (APOException e) {
                    this.mainView.errorAlert("Fehler beim Entfernen eines Kunden..", e.getMessage());
                    System.out.println("Fehler: Beim Löschen eines Kunden in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
                }
            }
            this.view.loadRezepte();
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println("Das ausgewählte Rezept wurde erfolgreich geändert/verändert!");
        });
    }

    public void printRezepte() {
        if (this.model.getRezepte() != null && !this.model.getRezepte().isEmpty()) {
            printRezepte printRezepte = new printRezepte(this.model);
            System.out.println("Alle Mitarbeiter der Apotheke in Form einer Liste ausgegeben.");
        } else {
            this.mainView.errorAlert("Rezepte in der Apotheke", "Es gibt keine/nicht genug Rezepte in der Apotheke, daher kann auch keine Liste ausgegeben werden!");
        }
    }
}
