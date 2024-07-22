package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Rezept;
import at.apo.view.ApoView;
import at.apo.view.manageKunden;
import at.apo.view.manageRezeptDialog;
import at.apo.view.printListe;

import java.util.Optional;

public class KundenController {
    private ApoView mainView;
    private manageKunden view;
    private Apotheke model;

    public KundenController(ApoView mainView, manageKunden view, Apotheke model) {
        this.mainView = mainView;
        this.view = view;
        this.model = model;
    }

    public void printListe() {
        printListe printListe = new printListe(this.model);
        System.out.println("Es wurden alle Kunden und damit verbundenen Rezepte von der Apotheke " + this.model.getName() + " ausgegeben.");
    }

    public void showRezept(Rezept rezept) {
        Rezept before = rezept.clone();

        manageRezeptDialog manageRezeptDialog = new manageRezeptDialog(this.mainView, this.model, rezept);
        Optional<Rezept> r = manageRezeptDialog.showAndWait();

        r.ifPresent(rezept1 -> {
            rezept1.berechnePreis();
            if (!(before.getPatient().getName().equals(rezept1.getPatient().getName()))) {
                try {
                    this.model.removeKunde(before.getPatient());
                    this.model.addKunde(rezept1.getPatient());
                    System.out.println("Das Rezept ist nun ausgestellt an den Kunden " + rezept1.getPatient().getName() + " und nicht mehr an " + before.getPatient().getName() + ".");
                } catch (APOException e) {
                    this.mainView.errorAlert("Fehler beim Entfernen eines Kunden", e.getMessage());
                    System.out.println("Fehler: Beim Löschen eines Kunden in der Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten.");
                }
            }
            this.view.updateListViews();
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println("Das ausgewählte Rezept wurde erfolgreich verändert!");
        });
    }
}
