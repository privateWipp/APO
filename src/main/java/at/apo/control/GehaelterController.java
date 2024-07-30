package at.apo.control;

import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.manageGehaelter;

public class GehaelterController {
    private ApoView mainView;
    private manageGehaelter view;
    private Apotheke model;

    public GehaelterController(ApoView mainView, manageGehaelter view, Apotheke model) {
        this.mainView = mainView;
        this.view = view;
        this.model = model;
    }
}
