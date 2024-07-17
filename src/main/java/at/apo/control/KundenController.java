package at.apo.control;

import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.manageKunden;

public class KundenController {
    private ApoView mainView;
    private manageKunden view;
    private Apotheke model;

    public KundenController(ApoView mainView, manageKunden view, Apotheke model) {
        this.mainView = mainView;
        this.view = view;
        this.model = model;
    }
}
