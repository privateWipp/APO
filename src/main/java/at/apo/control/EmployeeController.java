package at.apo.control;

import at.apo.model.APOException;
import at.apo.model.Apotheke;
import at.apo.model.Mitarbeiter;
import at.apo.view.addEmployeeDialog;
import at.apo.view.manageEmployeeDialog;
import at.apo.view.manageEmployees;

import java.util.Optional;

public class EmployeeController {
    private manageEmployees view;
    private Apotheke model;

    public EmployeeController(manageEmployees view, Apotheke model) {
        this.view = view;
        this.model = model;
    }

    public void addEmployee() {
        addEmployeeDialog addEmployeeDialog = new addEmployeeDialog();
        Optional<Mitarbeiter> m = addEmployeeDialog.showAndWait();

        m.ifPresent(mitarbeiter -> {
            try {
                if (mitarbeiter.getEmail().equalsIgnoreCase(this.model.getEmail()) || mitarbeiter.getTelefonnummer().equalsIgnoreCase(this.model.getTelefonnummer()) || mitarbeiter.getAdresse().equalsIgnoreCase(this.model.getAdresse())) {
                    throw new APOException("Mitarbeiter konnte nicht hinzugefügt werden da es nicht möglich ist, dass (E-Mail)Adresse/Tel. Nr. mit der der Apotheke übereinstimmt/übereinstimmen!");
                } else {
                    if (this.model.getMitarbeiter() != null && !this.model.getMitarbeiter().isEmpty()) {
                        for (Mitarbeiter mitarbeiter1 : this.model.getMitarbeiter()) {
                            if (mitarbeiter1.getEmail().equalsIgnoreCase(mitarbeiter.getEmail()) || mitarbeiter1.getTelefonnummer().equalsIgnoreCase(mitarbeiter.getTelefonnummer())) {
                                throw new APOException("Mitarbeiter konnte nicht hinzugefügt werden da es bereits einen Mitarbeiter mit entweder der gleichen Tel. Nr.\n" +
                                        "oder E-Mail Adresse gibt!\n" +
                                        "Das kann nicht sein!");
                            }
                        }
                    }
                    this.model.addMitarbeiter(mitarbeiter);
                    this.view.getMitarbeiterListView().getItems().add(mitarbeiter);
                    this.view.getMitarbeiterListView().refresh();
                }
            } catch (APOException e) {
                this.view.errorAlert("Fehler beim Hinzufügen eines neuen Mitarbeiters..", e.getMessage());
            }
        });
    }

    public void removeEmployee(Mitarbeiter mitarbeiter) {
        try {
            this.model.removeMitarbeiter(mitarbeiter);
            this.view.getMitarbeiterListView().getItems().remove(mitarbeiter);
            this.view.getMitarbeiterListView().refresh();
        } catch (APOException e) {
            this.view.errorAlert("Fehler beim Löschen/Entfernen/Feuern eines Mitarbeiters..", e.getMessage());
        }
    }

    public void manageEmployee(Mitarbeiter mitarbeiter) {
        manageEmployeeDialog manageEmployeeDialog = new manageEmployeeDialog(mitarbeiter);
        Optional<Mitarbeiter> m = manageEmployeeDialog.showAndWait();

        m.ifPresent(mitarbeiter1 -> {
            this.view.getMitarbeiterListView().refresh();
            this.view.getMitarbeiterTA().setText(this.view.getMitarbeiterListView().getSelectionModel().getSelectedItem().toString());
        });
    }
}
