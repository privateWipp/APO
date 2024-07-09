package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.view.*;

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
        adresseButtonDialog adresseButtonDialog = new adresseButtonDialog(this.model);
        Optional<String> a = adresseButtonDialog.showAndWait();

        a.ifPresent(adresse -> {
            try {
                String originalAdresse = this.model.getAdresse();
                this.model.setAdresse(adresse);
                this.view.setChanged(true);
                System.out.println("Die aktuelle Adresse der Apotheke " + this.model.getName() + " wurde von " + originalAdresse + " auf " + this.model.getAdresse() + " geändert.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Ändern der aktuellen Adresse der Apotheke: " + this.model.getName(), e.getMessage());
            }
        });
    }

    public void telefonnummerButton() {
        telefonnummerButtonDialog telefonnummerButtonDialog = new telefonnummerButtonDialog(this.model);
        Optional<String> t = telefonnummerButtonDialog.showAndWait();

        t.ifPresent(telefonnummer -> {
            try {
                String originalTel = this.model.getTelefonnummer();
                this.model.setTelefonnummer(telefonnummer);
                this.view.setChanged(true);
                System.out.println("Die aktuelle Tel. Nr. der Apotheke " + this.model.getName() + " wurde von " + originalTel + " auf " + this.model.getTelefonnummer() + " geändert.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Ändern der aktuellen Tel. Nr. der Apotheke: " + this.model.getName(), e.getMessage());
            }
        });
    }

    public void emailButton() {
        emailButtonDialog emailButtonDialog = new emailButtonDialog(this.model);
        Optional<String> e = emailButtonDialog.showAndWait();

        e.ifPresent(email -> {
            try {
                String originalEmail = this.model.getEmail();
                this.model.setEmail(email);
                this.view.setChanged(true);
                System.out.println("Die aktuelle E-Mail Adresse der Apotheke " + this.model.getName() + " wurde von " + originalEmail + " auf " + this.model.getEmail() + " geändert.");
            } catch (APOException ex) {
                this.view.errorAlert("Fehler beim Ändern der aktuellen E-Mail Adresse der Apotheke: " + this.model.getName(), ex.getMessage());
            }
        });
    }
}
