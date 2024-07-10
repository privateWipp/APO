package at.apo.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Apotheke implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String originalName;
    private String adresse;
    private String telefonnummer;
    private String email;
    private Geschaeftsfuehrer geschaeftsfuehrer;
    private HashMap<String, String> oeffnungszeiten;
    private ArrayList<Mitarbeiter> mitarbeiter;
    private ArrayList<Medikament> medikamente;
    private ArrayList<Kunde> kunden;
    private double budget;
    private ArrayList<Bestellung> bestellungen;
    private HashMap<Medikament, Integer> lagerbestand;
    private ArrayList<Rezept> rezepte;
    private int nextMitarbeiter;
    private int nextBestellung;
    private int nextRezept;

    public Apotheke(String name, String adresse, String telefonnummer, String email, double budget) throws APOException {
        setName(name);
        setOriginalName(getName());
        setAdresse(adresse);
        setTelefonnummer(telefonnummer);
        setEmail(email);
        this.geschaeftsfuehrer = null;
        this.oeffnungszeiten = new HashMap<String, String>();
        this.mitarbeiter = new ArrayList<Mitarbeiter>();
        this.medikamente = new ArrayList<Medikament>();
        this.kunden = new ArrayList<Kunde>();
        setBudget(budget);
        this.bestellungen = new ArrayList<Bestellung>();
        this.lagerbestand = new HashMap<Medikament, Integer>();
        this.rezepte = new ArrayList<Rezept>();
        this.nextMitarbeiter = 1;
        this.nextBestellung = 1;
        this.nextRezept = 1;
    }

    @Override
    public Apotheke clone() {
        try {
            Apotheke cloned = (Apotheke) super.clone();

            cloned.oeffnungszeiten = new HashMap<>(this.oeffnungszeiten);
            cloned.lagerbestand = new HashMap<>(this.lagerbestand);

            cloned.mitarbeiter = new ArrayList<>();
            for (Mitarbeiter m : this.mitarbeiter) {
                cloned.mitarbeiter.add(m.clone());
            }

            cloned.medikamente = new ArrayList<>();
            for (Medikament med : this.medikamente) {
                cloned.medikamente.add(med.clone());
            }

            cloned.kunden = new ArrayList<>();
            for (Kunde k : this.kunden) {
                cloned.kunden.add(k.clone());
            }

            cloned.bestellungen = new ArrayList<>();
            for (Bestellung b : this.bestellungen) {
                cloned.bestellungen.add(b.clone());
            }

            cloned.rezepte = new ArrayList<>();
            for (Rezept r : this.rezepte) {
                cloned.rezepte.add(r.clone());
            }

            if (this.geschaeftsfuehrer != null) {
                cloned.geschaeftsfuehrer = this.geschaeftsfuehrer.clone();
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public void setName(String name) throws APOException {
        if (name != null) {
            if (name.length() >= 3) {
                this.name = name;
            } else {
                throw new APOException("Der Name der Apotheke ist ungültig! Der Name muss mindestens drei Zeichen lang sein!");
            }
        } else {
            throw new APOException("Der Name der Apotheke darf nicht null sein!");
        }
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setAdresse(String adresse) throws APOException {
        if (adresse != null) {
            if (!adresse.isEmpty()) {
                this.adresse = adresse;
            } else {
                throw new APOException("Die Adresse der Apotheke ist ungültig!");
            }
        } else {
            throw new APOException("Die Adresse der Apotheke darf nicht null sein!");
        }
    }

    public void setTelefonnummer(String telefonnummer) throws APOException {
        if (telefonnummer != null) {
            if (telefonnummer.length() >= 5) {
                this.telefonnummer = telefonnummer;
            } else {
                throw new APOException("Ungültige Telefonnummer!");
            }
        } else {
            throw new APOException("Die Telefonnummer der Apotheke darf nicht null sein!");
        }
    }

    public void setEmail(String email) throws APOException {
        if (email != null) {
            if (!email.isEmpty()) {
                this.email = email;
            } else {
                throw new APOException("Die E-Mail-Adresse der Apotheke ist ungültig!");
            }
        } else {
            throw new APOException("Die E-Mail-Adresse der Apotheke darf nicht null sein!");
        }
    }

    public void setGeschaeftsfuehrer(Geschaeftsfuehrer geschaeftsfuehrer) throws APOException {
        if (geschaeftsfuehrer != null) {
            this.geschaeftsfuehrer = geschaeftsfuehrer;
        } else {
            throw new APOException("Der festzulegende Geschäftsführer der Apotheke darf nicht null sein!");
        }
    }

    public void setOeffnungszeiten(HashMap<String, String> oeffnungszeiten) throws APOException {
        if (oeffnungszeiten != null) {
            this.oeffnungszeiten = oeffnungszeiten;
        } else {
            throw new APOException("Die übergebenen Öffnungszeiten sind ungültig (=null)!");
        }
    }

    public void addMitarbeiter(Mitarbeiter mitarbeiter) throws APOException {
        if (mitarbeiter != null) {
            this.mitarbeiter.add(mitarbeiter);
            mitarbeiter.setId(this.nextMitarbeiter);
            this.nextMitarbeiter++;
        } else {
            throw new APOException("Der übergebene Mitarbeiter ist ungültig (=null)!");
        }
    }

    public void removeMitarbeiter(Mitarbeiter mitarbeiter) throws APOException {
        if (mitarbeiter != null) {
            this.mitarbeiter.remove(mitarbeiter);
        } else {
            throw new APOException("Der übergebene Mitarbeiter ist ungültig (=null)!");
        }
    }

    public void addMedikament(Medikament medikament) throws APOException {
        if (medikament != null) {
            this.medikamente.add(medikament);
        } else {
            throw new APOException("Das übergebene Medikament ist ungültig (=null)!");
        }
    }

    public void removeMedikament(Medikament medikament) throws APOException {
        if (medikament != null) {
            this.medikamente.remove(medikament);
        } else {
            throw new APOException("Das übergebene Medikament ist ungültig (=null)!");
        }
    }

    public void addKunde(Kunde kunde) throws APOException {
        if (kunde != null) {
            this.kunden.add(kunde);
        } else {
            throw new APOException("Der übergebene Kunde ist ungültig (=null)!");
        }
    }

    public void removeKunde(Kunde kunde) throws APOException {
        if (kunde != null) {
            this.kunden.remove(kunde);
        } else {
            throw new APOException("Der übergebene Kunde ist ungültig (=null)!");
        }
    }

    public void setBudget(double budget) throws APOException {
        if (budget >= 0) {
            this.budget = budget;
        } else {
            throw new APOException("Das angegebene Budget für die Apotheke ist ungültig! Der Wert muss mindestens gleich Null sein!");
        }
    }

    public void addBestellung(Bestellung bestellung) throws APOException {
        if (bestellung != null) {
            this.bestellungen.add(bestellung);
            bestellung.setBestellnummer(this.nextBestellung);
            this.nextBestellung++;
        } else {
            throw new APOException("Die übergebene Bestellung ist ungültig (=null)!");
        }
    }

    public void removeBestellung(Bestellung bestellung) throws APOException {
        if (bestellung != null) {
            this.bestellungen.remove(bestellung);
        } else {
            throw new APOException("Die übergebene Bestellung ist ungültig (=null)!");
        }
    }

    public void setLagerbestand(HashMap<Medikament, Integer> lagerbestand) throws APOException {
        if (lagerbestand != null) {
            this.lagerbestand = lagerbestand;
        } else {
            throw new APOException("Der übergebene Lagerbestand ist ungültig (=null)!");
        }
    }

    public void addRezept(Rezept rezept) throws APOException {
        if (rezept != null) {
            this.rezepte.add(rezept);
            rezept.setRezeptnummer(this.nextRezept);
            this.nextRezept++;
        } else {
            throw new APOException("Das übergebene Rezept ist ungültig (=null)!");
        }
    }

    public void removeRezept(Rezept rezept) throws APOException {
        if (rezept != null) {
            this.rezepte.remove(rezept);
        } else {
            throw new APOException("Das übergebene Rezept ist ungültig (=null)!");
        }
    }

    public String getName() {
        return this.name;
    }

    public String getOriginalName() {
        return this.originalName;
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

    public Geschaeftsfuehrer getGeschaeftsfuehrer() {
        return this.geschaeftsfuehrer;
    }

    public HashMap<String, String> getOeffnungszeiten() {
        return this.oeffnungszeiten;
    }

    public ArrayList<Mitarbeiter> getMitarbeiter() {
        return this.mitarbeiter;
    }

    public ArrayList<Medikament> getMedikamente() {
        return this.medikamente;
    }

    public ArrayList<Kunde> getKunden() {
        return this.kunden;
    }

    public double getBudget() {
        return this.budget;
    }

    public ArrayList<Bestellung> getBestellungen() {
        return this.bestellungen;
    }

    public HashMap<Medikament, Integer> getLagerbestand() {
        return this.lagerbestand;
    }

    public ArrayList<Rezept> getRezepte() {
        return this.rezepte;
    }

    public void speichern(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(getName());
            oos.writeObject(getOriginalName());
            oos.writeObject(getAdresse());
            oos.writeObject(getTelefonnummer());
            oos.writeObject(getEmail());
            oos.writeObject(getGeschaeftsfuehrer());
            oos.writeObject(getOeffnungszeiten());
            oos.writeObject(getMitarbeiter());
            oos.writeObject(getMedikamente());
            oos.writeObject(getKunden());
            oos.writeObject(getBudget());
            oos.writeObject(getBestellungen());
            oos.writeObject(getLagerbestand());
            oos.writeObject(getRezepte());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void laden(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.name = (String) ois.readObject();
            this.originalName = (String) ois.readObject();
            this.adresse = (String) ois.readObject();
            this.telefonnummer = (String) ois.readObject();
            this.email = (String) ois.readObject();
            this.geschaeftsfuehrer = (Geschaeftsfuehrer) ois.readObject();
            this.oeffnungszeiten = (HashMap<String, String>) ois.readObject();
            this.mitarbeiter = (ArrayList<Mitarbeiter>) ois.readObject();
            this.medikamente = (ArrayList<Medikament>) ois.readObject();
            this.kunden = (ArrayList<Kunde>) ois.readObject();
            this.budget = (Double) ois.readObject();
            this.bestellungen = (ArrayList<Bestellung>) ois.readObject();
            this.lagerbestand = (HashMap<Medikament, Integer>) ois.readObject();
            this.rezepte = (ArrayList<Rezept>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apotheke apotheke = (Apotheke) o;
        return Objects.equals(getName(), apotheke.getName()) && Objects.equals(getAdresse(), apotheke.getAdresse()) && Objects.equals(getGeschaeftsfuehrer(), apotheke.getGeschaeftsfuehrer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAdresse(), getGeschaeftsfuehrer());
    }

    @Override
    public String toString() {
        return getName() + "\n" +
                getAdresse() + "\n" +
                getTelefonnummer() + "\n" +
                getEmail() +
                (getGeschaeftsfuehrer() != null ? "\nGeschäftsführer: " + getGeschaeftsfuehrer().getVorname() + " " + getGeschaeftsfuehrer().getNachname() : "");
    }
}
