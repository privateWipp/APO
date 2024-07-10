package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import at.apo.view.*;

import java.util.Optional;

public class EmployeeController {
    private ApoView mainView;
    private manageEmployees view;
    private Apotheke model;

    public EmployeeController(ApoView mainView, manageEmployees view, Apotheke model) {
        this.mainView = mainView;
        this.view = view;
        this.model = model;
    }

    public void addEmployee() {
        addEmployeeDialog addEmployeeDialog = new addEmployeeDialog();
        Optional<Mitarbeiter> m = addEmployeeDialog.showAndWait();

        m.ifPresent(mitarbeiter -> {
            try {
                validateMitarbeiter(mitarbeiter);
                this.model.addMitarbeiter(mitarbeiter);
                this.view.getModel().addMitarbeiter(mitarbeiter);
                this.view.getMitarbeiterListView().getItems().add(mitarbeiter);
                this.view.getMitarbeiterListView().refresh();
                this.mainView.loadListViews();
                this.mainView.setChanged(true);
                System.out.println("Der Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " wurde in die Apotheke " + this.model.getName() + " aufgenommen.");
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Hinzufügen eines neuen Mitarbeiters..", e.getMessage());
                System.out.println("Fehler: Das Aufnehmen eines neuen Mitarbeiters in die Apotheke " + this.model.getName() + " ist fehlgeschlagen!");
            }
        });
    }

    private void validateMitarbeiter(Mitarbeiter mitarbeiter) throws APOException {
        if (isDuplicateWithModel(mitarbeiter) || isDuplicateWithGeschaeftsfuehrer(mitarbeiter) || isDuplicateWithExistingMitarbeiter(mitarbeiter)) {
            throw new APOException("Mitarbeiter konnte nicht hinzugefügt werden, da es bereits einen Mitarbeiter mit entweder der gleichen Tel. Nr. oder E-Mail Adresse gibt oder diese mit der Apotheke oder dem Geschäftsführer übereinstimmen!");
        }
    }

    private boolean isDuplicateWithModel(Mitarbeiter mitarbeiter) {
        return mitarbeiter.getEmail().equalsIgnoreCase(this.model.getEmail())
                || mitarbeiter.getTelefonnummer().equalsIgnoreCase(this.model.getTelefonnummer())
                || mitarbeiter.getAdresse().equalsIgnoreCase(this.model.getAdresse());
    }

    private boolean isDuplicateWithGeschaeftsfuehrer(Mitarbeiter mitarbeiter) {
        if (this.model.getGeschaeftsfuehrer() != null) {
            return mitarbeiter.getEmail().equalsIgnoreCase(this.model.getGeschaeftsfuehrer().getEmail())
                    || mitarbeiter.getTelefonnummer().equalsIgnoreCase(this.model.getGeschaeftsfuehrer().getTelefonnummer());
        }
        return false;
    }

    private boolean isDuplicateWithExistingMitarbeiter(Mitarbeiter mitarbeiter) {
        if (this.model.getMitarbeiter() != null && !this.model.getMitarbeiter().isEmpty()) {
            for (Mitarbeiter mitarbeiter1 : this.model.getMitarbeiter()) {
                if (mitarbeiter1.getEmail().equalsIgnoreCase(mitarbeiter.getEmail())
                        || mitarbeiter1.getTelefonnummer().equalsIgnoreCase(mitarbeiter.getTelefonnummer())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeEmployee(Mitarbeiter mitarbeiter) {
        try {
            this.model.removeMitarbeiter(mitarbeiter);
            this.view.getModel().removeMitarbeiter(mitarbeiter);
            this.view.getMitarbeiterListView().getItems().remove(mitarbeiter);
            this.view.getMitarbeiterListView().refresh();
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println(mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " ist nun kein Teil mehr der Apotheke: " + this.model.getName());
        } catch (APOException e) {
            this.view.errorAlert("Fehler beim Löschen/Entfernen/Feuern eines Mitarbeiters..", e.getMessage());
            System.out.println("Fehler: Der Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname() + " konnte nicht gefeuert/entfernt werden!");
        }
    }

    public void manageEmployee(Mitarbeiter mitarbeiter) {
        manageEmployeeDialog manageEmployeeDialog = new manageEmployeeDialog(mitarbeiter);
        Optional<Mitarbeiter> m = manageEmployeeDialog.showAndWait();

        m.ifPresent(mitarbeiter1 -> {
            this.view.getMitarbeiterListView().refresh();
            this.view.getMitarbeiterTA().setText(this.view.getMitarbeiterListView().getSelectionModel().getSelectedItem().toString());
            this.mainView.loadListViews();
            this.mainView.setChanged(true);
            System.out.println("Die Daten von " + mitarbeiter1.getVorname() + " " + mitarbeiter1.getNachname() + " wurden aktualisiert.");
        });
    }

    public void printAllEmployees() {
        if(this.model.getMitarbeiter() != null && !this.model.getMitarbeiter().isEmpty()) {
            printAllEmployees printAllEmployees = new printAllEmployees(this.model);
            System.out.println("Alle Mitarbeiter der Apotheke in Form einer Liste ausgegeben.");
        } else {
            this.view.errorAlert("Mitarbeiter in der Apotheke", "Es gibt keine/nicht genug Mitarbeiter in der Apotheke, daher kann auch keine Liste ausgegeben werden!");
        }
    }
}
