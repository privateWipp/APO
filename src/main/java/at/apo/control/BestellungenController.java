package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class BestellungenController {
    private Apotheke model;
    private ApoView mainView;
    private manageBestellungen view;

    public BestellungenController(Apotheke model, ApoView mainView, manageBestellungen view) {
        this.model = model;
        this.mainView = mainView;
        this.view = view;
    }

    public void addBestellung() {
        neueBestellungDialog neueBestellungDialog = new neueBestellungDialog(this.mainView, this.model);
        Optional<Bestellung> b = neueBestellungDialog.showAndWait();

        b.ifPresent(bestellung -> {
            try {
                this.model.addBestellung(bestellung);
                this.view.updateBestellungen();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Die Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' wurde erfolgreich aufgegeben.");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Aufgeben einer neuen Bestellung", e.getMessage());
                System.out.println("Fehler: Beim Aufgeben einer neuen Bestellung für die Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
        });
    }

    public void removeBestellung(Bestellung bestellung) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Bestellung stornieren");
        confirmation.setHeaderText("Sind Sie sicher?");
        confirmation.setContentText("Sind Sie sicher, dass sie die ausgewählte Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' stornieren wollen?");

        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType cancel = new ButtonType("Abbrechen");

        confirmation.getButtonTypes().setAll(yes, no, cancel);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            try {
                this.model.removeBestellung(bestellung);
                this.view.updateBestellungen();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Die ausgewählte Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' wurde erfolgreich storniert.");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Stornieren einer Bestellung", e.getMessage());
                System.out.println("Fehler: Beim Stornieren der Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' ist ein Fehler aufgetreten!");
            }
        } else {
            confirmation.close();
        }
    }

    public void manageBestellung(Bestellung bestellung) {
        if (bestellung.getBestellstatus().equals("BESTELLT")) {
            Bestellung bestellungBefore = bestellung.clone();

            manageBestellungDialog manageBestellungDialog = new manageBestellungDialog(bestellung, this.mainView, this.model);
            Optional<Bestellung> b = manageBestellungDialog.showAndWait();

            b.ifPresent(bestellung1 -> {
                this.view.updateBestellungen();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Die Bestellung (" + bestellungBefore.getBezeichnung() + ") wurde geändert.");
            });
        } else {
            this.mainView.errorAlert("Bestellung verändern", "Die ausgewählte Bestellung ('" + bestellung.getBezeichnung() + "') kann NICHT verändert werden, da sie schon " + bestellung.getBestellstatus() + " ist (nur möglich wenn Bestellstatus = 'BESTELLT')!");
        }
    }

    public void changeStatus(Bestellung bestellung) {
        changeStatusDialog changeStatusDialog = new changeStatusDialog(bestellung);
        Optional<Bestellung> b = changeStatusDialog.showAndWait();

        b.ifPresent(bestellung2 -> {
            this.view.updateBestellungen();
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println("Die Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' hat nun den Bestellstatus: " + bestellung2.getBestellstatus());
        });
    }

    public void printAllBestellungen() {
        if (!this.model.getBestellungen().isEmpty()) {
            printAllBestellungen printAllBestellungen = new printAllBestellungen(this.model);
            System.out.println("Es wurden alle Bestellungen der Apotheke " + this.model.getName() + " in Form einer Liste ausgegeben.");
        } else {
            this.mainView.errorAlert("Bestellungen ausgeben", "Es sind momentan keine/zu wenige Bestellungen vorhanden, daher kann auch keine Liste ausgegeben werden!");
        }
    }
}
