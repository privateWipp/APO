package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.view.ApoView;
import at.apo.view.nameButtonDialog;

import java.util.Optional;

public class editApoController {
    private ApoView view;
    private Apotheke model;

    public editApoController(ApoView view, Apotheke model) {
        this.view = view;
        this.model = model;
    }

    public void nameButton() {
        nameButtonDialog nameButtonDialog = new nameButtonDialog(this.model);
        Optional<String> n = nameButtonDialog.showAndWait();

        n.ifPresent(name -> {
            try {
                String originalName = this.model.getName();
                this.model.setName(name);
                this.view.setChanged(true);
                System.out.println("Der offizielle Name der Apotheke wurde von " + originalName + " auf " + this.model.getName() + " geändert.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Ändern des offiziellen Namen der Apotheke: " + this.model.getName(), e.getMessage());
            }
        });
    }

    public void adresseButton() {

    }

    public void telefonnummerButton() {

    }

    public void emailButton() {

    }
}
