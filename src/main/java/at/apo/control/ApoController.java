package at.apo.control;

import at.apo.model.*;
import at.apo.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author Jonas Mader
 */
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
        if (this.model.getGeschaeftsfuehrer() != null) {
            geschaeftsfuehrerAnzeigen geschaeftsfuehrerAnzeigen = new geschaeftsfuehrerAnzeigen(this.model);
        } else {
            this.view.errorAlert("Geschäftsführer", "Die Apotheke: " + this.model.getName() + "\n" +
                    "hat zurzeit keinen Geschäftsführer, daher können auch keine Informationen angezeigt werden!");
        }
    }

    public void oeffnungszeitenFestlegen() {
        if (!this.model.getOeffnungszeiten().containsKey("Montag")) {
            oeffnungszeitenFestlegenDialog oeffnungszeitenFestlegenDialog = new oeffnungszeitenFestlegenDialog(this.model, false);
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
            ButtonType wsdoz = new ButtonType("Was sind die Öffnungszeiten?");
            ButtonType cancel = new ButtonType("Abbrechen");

            confirmation.getButtonTypes().setAll(yes, no, wsdoz, cancel);

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
            } else if(result.isPresent() && result.get() == wsdoz) {
                displayOeffnungszeiten oeffnungszeiten = new displayOeffnungszeiten(this.model);
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
                System.out.println("Die Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' wurde erfolgreich aufgegeben.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Aufgeben einer neuen Bestellung", e.getMessage());
                System.out.println("Fehler: Das Aufgeben einer neuen Bestellung für die Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
            }
        });
    }

    public void manageBestellungen() {
        manageBestellungen manageBestellungen = new manageBestellungen(this.view, this.model);
    }

    public void manageKunden() {
        manageKunden manageKunden = new manageKunden(this.view, this.model);
    }

    public void manageEmployee(Mitarbeiter mitarbeiter) {
        manageEmployeeDialog manageEmployeeDialog = new manageEmployeeDialog(this.model, mitarbeiter);
        Optional<Mitarbeiter> m = manageEmployeeDialog.showAndWait();

        m.ifPresent(mitarbeiter1 -> {
            this.view.loadListViews();
            this.view.setChanged(true);
            System.out.println("Die Daten von " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " wurden aktualisiert.");
        });
    }

    public void manageMedikament(Medikament medikament) {
        manageMedikamentDialog manageMedikamentDialog = new manageMedikamentDialog(this.model, medikament);
        Optional<Medikament> m = manageMedikamentDialog.showAndWait();

        m.ifPresent(medikament1 -> {
            this.view.loadListViews();
            this.view.setChanged(true);
            System.out.println("Die Daten von dem Medikament " + medikament.getBezeichnung() + " wurden aktualisiert.");
        });
    }

    public void manageRezept(Rezept rezept) {
        Rezept rezeptBefore = rezept.clone();

        manageRezeptDialog manageRezeptDialog = new manageRezeptDialog(this.view, this.model, rezept);
        Optional<Rezept> r = manageRezeptDialog.showAndWait();

        r.ifPresent(rezept1 -> {
            rezept1.berechnePreis();
            if (!(rezeptBefore.getPatient().getName().equals(rezept1.getPatient().getName()))) {
                try {
                    this.model.removeKunde(rezeptBefore.getPatient());
                    this.model.addKunde(rezept1.getPatient());
                    System.out.println("Das Rezept ist nun ausgestellt an den Kunden " + rezept1.getPatient().getName() + " und nicht mehr an " + rezeptBefore.getPatient().getName() + ".");
                } catch (APOException e) {
                    this.view.errorAlert("Fehler beim Entfernen eines Kunden", e.getMessage());
                    System.out.println("Fehler: Beim Löschen eines Kunden in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten.");
                }
            }
            this.view.loadListViews();
            this.view.setChanged(true);
            System.out.println("Das ausgewählte Rezept wurde erfolgreich verändert!");
        });
    }

    public void manageBestellung(Bestellung bestellung) {
        if (bestellung.getBestellstatus().equals("BESTELLT")) {
            Bestellung bestellungBefore = bestellung.clone();

            manageBestellungDialog manageBestellungDialog = new manageBestellungDialog(bestellung, this.view, this.model);
            Optional<Bestellung> b = manageBestellungDialog.showAndWait();

            b.ifPresent(bestellung1 -> {
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println("Die Bestellung (" + bestellungBefore.getBezeichnung() + ") wurde geändert.");
            });
        } else {
            this.view.errorAlert("Bestellung verändern", "Die ausgewählte Bestellung ('" + bestellung.getBezeichnung() + "') kann NICHT verändert werden, da sie schon " + bestellung.getBestellstatus() + " ist (nur möglich wenn Bestellstatus = 'BESTELLT')!");
        }
    }

    public void deleteEmployee(Mitarbeiter mitarbeiter) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Mitarbeiter feuern");
        confirmation.setHeaderText("Sind Sie sicher?");
        confirmation.setContentText("Sind Sie sicher, dass sie den ausgewählten Mitarbeiter (" + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + ") feuern wollen?");

        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType cancel = new ButtonType("Abbrechen");

        confirmation.getButtonTypes().setAll(yes, no, cancel);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            try {
                this.model.removeMitarbeiter(mitarbeiter);
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println(mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " ist nun kein Teil mehr der Apotheke: " + this.model.getName());
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Feuern eines Mitarbeiters", e.getMessage());
                System.out.println("Fehler: Der Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " konnte nicht gefeuert werden!");
            }
        } else {
            confirmation.close();
        }
    }

    public void deleteMedikament(Medikament medikament) {
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
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println("Das Medikament " + medikament.getBezeichnung() + " (" + medikament.getLagerbestand() + " Stück) wurde aus der Apotheke " + this.model.getName() + " entfernt.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Entfernen des Medikaments " + medikament.getBezeichnung(), e.getMessage());
                System.out.println("Fehler: Beim Entfernen von dem Medikament " + medikament.getBezeichnung() + " (" + medikament.getLagerbestand() + " Stück) ist ein Fehler aufgetreten!");
            }
        } else {
            confirmation.close();
        }
    }

    public void deleteRezept(Rezept rezept) {
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
                this.model.removeRezept(rezept);
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println("Das ausgewählte Rezept wurde erfolgreich aus der Apotheke " + this.model.getName() + " entfernt.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Entfernen eines Rezepts", e.getMessage());
                System.out.println("Fehler: Beim Löschen eines Rezepts in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
        } else {
            confirmation.close();
        }
    }

    public void deleteBestellung(Bestellung bestellung) {
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
                this.view.loadListViews();
                this.view.setChanged(true);
                System.out.println("Die ausgewählte Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' wurde erfolgreich storniert.");
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
                        this.view.loadListViews();
                        this.view.setChanged(true);
                    } catch (APOException e) {
                        this.view.errorAlert("Fehler beim Stornieren einer Bestellung", e.getMessage());
                        System.out.println("Fehler: Beim Stornieren der Bestellung mit der Bezeichnung '" + bestellung.getBezeichnung() + "' ist ein Fehler aufgetreten!");
                    }
                } else if (result2.isPresent() && result2.get() == stornieren) {
                    for (Medikament medikament : bestellung.getMedikamente()) {
                        try {
                            this.model.removeMedikament(medikament);
                        } catch (APOException e) {
                            this.view.errorAlert("Fehler beim Stornieren eines Medikaments", e.getMessage());
                            System.out.println("Fehler: Beim Stornieren des Medikaments mit der Bezeichnung '" + medikament.getBezeichnung() + "' ist ein Fehler aufgetreten!");
                        }
                    }
                    try {
                        this.model.removeBestellung(bestellung);
                        this.view.loadListViews();
                        this.view.setChanged(true);
                    } catch (APOException e) {
                        this.view.errorAlert("Fehler beim Stornieren einer Bestellung", e.getMessage());
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

    public void deleteKunde(Kunde kunde) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Kunde entfernen");
        confirmation.setHeaderText("Sind Sie sicher?");
        confirmation.setContentText("Sind Sie sicher, dass sie den ausgewählten Kunden aus der Apotheke " + this.model.getName() + " entfernen wollen?\n" +
                "Seien Sie sich bewusst, dass alle Rezepte, die mit dem Kunden zusammenhängen bestehen bleiben!");

        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType cancel = new ButtonType("Abbrechen");

        confirmation.getButtonTypes().setAll(yes, no, cancel);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            try {
                this.model.removeKunde(kunde);
                this.view.loadListViews();
                this.view.setChanged(true);
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Löschen des Kunden " + kunde.getName(), e.getMessage());
                System.out.println("Fehler: Beim Löschen des ausgewählten Kunden (" + kunde.getName() + ") ist ein Fehler aufgetreten.");
            }
        } else {
            confirmation.close();
        }
    }
}
