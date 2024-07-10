package at.apo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Geschaeftsfuehrer implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nachname;
    private String vorname;
    private LocalDate geburtsdatum;
    private String geschlecht;
    private String adresse;
    private String telefonnummer;
    private String email;

    public Geschaeftsfuehrer(String nachname, String vorname, LocalDate geburtsdatum, String geschlecht, String adresse, String telefonnummer, String email) throws APOException {
        this.id = 0;
        setNachname(nachname);
        setVorname(vorname);
        setGeburtsdatum(geburtsdatum);
        setGeschlecht(geschlecht);
        setAdresse(adresse);
        setTelefonnummer(telefonnummer);
        setEmail(email);
    }

    @Override
    public Geschaeftsfuehrer clone() {
        try {
            return (Geschaeftsfuehrer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setNachname(String nachname) throws APOException {
        if(nachname != null) {
            if(!nachname.isEmpty()) {
                this.nachname = nachname;
            } else {
                throw new APOException("Der Nachname des Geschäftsführers ist ungültig!");
            }
        } else {
            throw new APOException("Der Nachname des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setVorname(String vorname) throws APOException {
        if(vorname != null) {
            if(!vorname.isEmpty()) {
                this.vorname = vorname;
            } else {
                throw new APOException("Der Vorname des Geschäftsführers ist ungültig!");
            }
        } else {
            throw new APOException("Der Vorname des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) throws APOException {
        if(geburtsdatum != null) {
            if(geburtsdatum.isBefore(LocalDate.now())) {
                this.geburtsdatum = geburtsdatum;
            } else {
                throw new APOException("Des Geschäftsführers Geburtsdatum ist ungültig!");
            }
        } else {
            throw new APOException("Das Geburtsdatum des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setGeschlecht(String geschlecht) throws APOException {
        if(geschlecht != null) {
            if(!geschlecht.isEmpty()) {
                if(geschlecht.equals("Männlich") || geschlecht.equals("Weiblich") || geschlecht.equals("Inter") || geschlecht.equals("Divers") || geschlecht.equals("Offen") || geschlecht.equals("keine Angabe")) {
                    this.geschlecht = geschlecht;
                } else {
                    throw new APOException("gültige Geschlechter:\n" +
                            "Männlich/Weiblich/Inter/Divers/Offen/keine Angabe");
                }
            } else {
                throw new APOException("Das Geschlecht des Geschäftsführers darf nicht leer sein!");
            }
        } else {
            throw new APOException("Das Geschlecht des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setAdresse(String adresse) throws APOException {
        if(adresse != null) {
            if(!adresse.isEmpty()) {
                this.adresse = adresse;
            } else {
                throw new APOException("Die Adresse des Geschäftsführers ist ungültig!");
            }
        } else {
            throw new APOException("Die Adresse des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setTelefonnummer(String telefonnummer) throws APOException {
        if(telefonnummer != null) {
            if(telefonnummer.length() >= 5) {
                this.telefonnummer = telefonnummer;
            } else {
                throw new APOException("Ungültige Telefonnummer!");
            }
        } else {
            throw new APOException("Die Telefonnummer des Geschäftsführers darf nicht null sein!");
        }
    }

    public void setEmail(String email) throws APOException {
        if(email != null) {
            if(!email.isEmpty()) {
                this.email = email;
            } else {
                throw new APOException("Die E-Mail-Adresse des Geschäftsführers ist ungültig!");
            }
        } else {
            throw new APOException("Die E-Mail-Adresse des Geschäftsführers darf nicht null sein!");
        }
    }

    public int getId() {
        return this.id;
    }

    public String getNachname() {
        return this.nachname;
    }

    public String getVorname() {
        return this.vorname;
    }

    public LocalDate getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public String getGeschlecht() {
        return this.geschlecht;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String getTelefonnummer() {
        return this.telefonnummer;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geschaeftsfuehrer that = (Geschaeftsfuehrer) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        LocalDate heute = LocalDate.now();
        Period periode = Period.between(getGeburtsdatum(), heute);

        int alter = periode.getYears();

        return "Vor- und Nachname: " + getVorname() + " " + getNachname() + ",\n" +
                "Alter: " + alter + " Jahre (geb. am " + getGeburtsdatum() + "),\n" +
                "Geschlecht: " + getGeschlecht() + ",\n" +
                "Adresse: " + getAdresse() + ",\n" +
                "Tel. Nr.: " + getTelefonnummer() + ",\n" +
                "E-Mail: " + getEmail();
    }
}
