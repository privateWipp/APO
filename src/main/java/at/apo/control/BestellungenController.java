package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Bestellung;
import at.apo.model.Medikament;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author Jonas Mader
 */
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
            if (bestellung.getBestellstatus().equals("BESTELLT") || bestellung.getBestellstatus().equals("VERSANDT")) {
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
                Alert confirmation2 = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation2.setTitle("Bestellung stornieren");
                confirmation2.setHeaderText("Alles oder Nichts");
                confirmation2.setContentText("Sollen die durch die Bestellung erhaltenen Medikamente ebenfalls 'storniert' werden, oder sollen sie ihren Platz in der Apotheke behalten?");

                ButtonType behalten = new ButtonType("Behalten");
                ButtonType stornieren = new ButtonType("Stornieren");

                confirmation2.getButtonTypes().setAll(behalten, stornieren, cancel);

                Optional<ButtonType> result2 = confirmation2.showAndWait();
                if (result2.isPresent() && result2.get() == behalten) {
                    try {
                        this.model.removeBestellung(bestellung);
                        this.view.updateBestellungen();
                        this.mainView.loadListViews();
                        this.mainView.setChanged(true);
                    } catch (APOException e) {
                        this.mainView.errorAlert("Fehler beim Stornieren einer Bestellung", e.getMessage());
                        System.out.println("Fehler: Beim Stornieren der Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' ist ein Fehler aufgetreten!");
                    }
                } else if (result2.isPresent() && result2.get() == stornieren) {
                    for(Medikament medikament : bestellung.getMedikamente()) {
                        try {
                            this.model.removeMedikament(medikament);
                        } catch (APOException e) {
                            this.mainView.errorAlert("Fehler beim Stornieren eines Medikaments", e.getMessage());
                            System.out.println("Fehler: Beim Stornieren des Medikaments mit der Bezeichnung '" + medikament.getBezeichnung() + "' ist ein Fehler aufgetreten!");
                        }
                    }
                    try {
                        this.model.removeBestellung(bestellung);
                        this.view.updateBestellungen();
                        this.mainView.loadListViews();
                        this.mainView.setChanged(true);
                    } catch (APOException e) {
                        this.mainView.errorAlert("Fehler beim Stornieren einer Bestellung", e.getMessage());
                        System.out.println("Fehler: Beim Stornieren der Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' ist ein Fehler aufgetreten!");
                    }
                } else {
                    confirmation2.close();
                }
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
            if (bestellung2.getBestellstatus().equals("ZUGESTELLT")) {
                for (Medikament medikament : bestellung2.getMedikamente()) {
                    try {
                        this.model.addMedikament(medikament);
                    } catch (APOException e) {
                        this.mainView.errorAlert("Medikament von Bestellung", "Das Medikament " + medikament.getBezeichnung() + " konnte nicht aufgenommen werden");
                        System.out.println("Fehler: Beim Aufnehmen des Medikaments " + medikament.getBezeichnung() + " von der Bestellung Nr. " + bestellung2.getBestellnummer() + " ist ein Fehler aufgetreten!\n" +
                                "Das Medikament " + medikament.getBezeichnung() + " konnte nicht hinzugefügt werden!");
                    }
                }
            }
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
