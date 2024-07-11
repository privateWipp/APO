package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Medikament;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MedikamenteController {
    private Apotheke model;
    private ApoView mainView;
    private manageMedikamente view;

    public MedikamenteController(Apotheke model, ApoView mainView, manageMedikamente view) {
        this.model = model;
        this.mainView = mainView;
        this.view = view;
    }

    public void addMedikament() {
        addMedikamentDialog addMedikamentDialog = new addMedikamentDialog();
        Optional<Medikament> m = addMedikamentDialog.showAndWait();

        m.ifPresent(medikament -> {
            try {
                this.model.addMedikament(medikament);
                this.view.updateMedikamente();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Das Medikament " + medikament.getBezeichnung() + " wurde in die Apotheke " + this.model.getName() + " mit " + medikament.getLagerbestand() + " Stück aufgenommen.");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Hinzufügen eines neuen Medikaments..", e.getMessage());
                System.out.println("Fehler: Beim Aufnehmen eines neuen Medikaments in die Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
        });
    }

    public void removeMedikament(Medikament medikament) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Medikament entfernen");
        confirmation.setHeaderText("Sind Sie sicher?");
        confirmation.setContentText("Sind Sie sicher, dass sie " + medikament.getLagerbestand() + " Stück von dem Medikament " + medikament.getBezeichnung() + " aus der Apotheke " + this.model.getName() + " entfernen wollen?");

        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType cancel = new ButtonType("Abbrechen");

        confirmation.getButtonTypes().setAll(yes, no, cancel);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            try {
                this.model.removeMedikament(medikament);
                this.view.updateMedikamente();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Das Medikament " + medikament.getBezeichnung() + " (" + medikament.getLagerbestand() + " Stück) wurde aus der Apotheke " + this.model.getName() + " entfernet.");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Entfernen des Medikaments " + medikament.getBezeichnung() + "..", e.getMessage());
                System.out.println("Fehler: Beim Entfernen von dem Medikament " + medikament.getBezeichnung() + " (" + medikament.getLagerbestand() + " Stück) ist ein Fehler aufgetreten!");
            }
        } else {
            confirmation.close();
        }
    }

    public void manageMedikament(Medikament medikament) {
        manageMedikamentDialog manageMedikamentDialog = new manageMedikamentDialog(medikament);
        Optional<Medikament> m = manageMedikamentDialog.showAndWait();

        m.ifPresent(medikament1 -> {
            this.view.updateMedikamente();
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println("Die Daten von dem Medikament " + medikament.getBezeichnung() + " wurden aktualisiert.");
        });
    }

    public void printMedikamente() {
        if(!this.model.getMedikamente().isEmpty()) {
            printAllMedikamente printAllMedikamente = new printAllMedikamente(this.model);
            System.out.println("Alle Medikamente der Apotheke in Form einer Liste ausgegeben.");
        } else {
            this.mainView.errorAlert("Medikamente in der Apotheke", "Es gibt keine/nicht genug Medikamente in der Apotheke, daher kann auch keine Liste ausgegeben werden!");
        }
    }
}
