package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Medikament;
import at.apo.view.ApoView;
import at.apo.view.addMedikamentDialog;
import at.apo.view.manageMedikamente;
import at.apo.view.printAllMedikamente;

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
                if(medikament.isRezeptpflichtig()) {
                    this.view.getModelR().addMedikament(medikament);
                    this.view.updateRMedikamente();
                } else {
                    this.view.getModelNR().addMedikament(medikament);
                    this.view.updateNRMedikamente();
                }
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Das Medikament " + medikament.getBezeichnung() + " wurde in die Apotheke " + this.model.getName() + " mit " + medikament.getLagerbestand() + " Stück aufgenommen.");
            } catch (APOException e) {
                this.mainView.errorAlert("Fehler beim Hinzufügen eines neuen Medikaments..", e.getMessage());
                System.out.println("Fehler: Beim Aufnehmen eines neuen Medikaments in die Apotheke " + this.model.getName() + " ist ein Fehler aufgetreten!");
            }
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
