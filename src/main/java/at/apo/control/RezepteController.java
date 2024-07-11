package at.apo.control;

import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.addRezeptDialog;
import at.apo.view.manageRezepte;

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
    }
}
