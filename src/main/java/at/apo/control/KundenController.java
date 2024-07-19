package at.apo.control;

import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.manageKunden;
import at.apo.view.printListe;

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
}
