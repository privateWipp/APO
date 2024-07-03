package at.apo.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Apotheke {
    private String name;
    private String adresse;
    private String telefonnummer;
    private String email;
    private HashMap<String, String> oeffnungszeiten;
    private ArrayList<Mitarbeiter> mitarbeiter;
    private ArrayList<Medikament> medikamente;
    private ArrayList<Kunde> kunden;
    private double budget;
    private ArrayList<Bestellung> bestellungen;
    private HashMap<Medikament, Integer> lagerbestand;
    private ArrayList<Rezept> rezepte;

    public Apotheke(String name, String adresse, String telefonnummer, String email, double budget) throws APOException {
        setName(name);
        setAdresse(adresse);
        setTelefonnummer(telefonnummer);
        setEmail(email);
        this.oeffnungszeiten = new HashMap<String, String>();
        this.mitarbeiter = new ArrayList<Mitarbeiter>();
        this.medikamente = new ArrayList<Medikament>();
        this.kunden = new ArrayList<Kunde>();
        setBudget(budget);
        this.bestellungen = new ArrayList<Bestellung>();
        this.lagerbestand = new HashMap<Medikament, Integer>();
        this.rezepte = new ArrayList<Rezept>();
    }

    public void setName(String name) throws APOException {
        if(name != null) {
            if(name.length() >= 3) {
                this.name = name;
            } else {
                throw new APOException("Der Name der Apotheke ist ungültig! Der Name muss mindestens drei Zeichen lang sein!");
            }
        } else {
            throw new APOException("Der Name der Apotheke darf nicht null sein!");
        }
    }

    public void setAdresse(String adresse) throws APOException {
        if(adresse != null) {
            if(!adresse.isEmpty()) {
                this.adresse = adresse;
            } else {
                throw new APOException("Die Adresse der Apotheke ist ungültig!");
            }
        } else {
            throw new APOException("Die Adresse der Apotheke darf nicht null sein!");
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
            throw new APOException("Die Telefonnummer der Apotheke darf nicht null sein!");
        }
    }

    public void setEmail(String email) throws APOException {
        if(email != null) {
            if(!email.isEmpty()) {
                this.email = email;
            } else {
                throw new APOException("Die E-Mail-Adresse der Apotheke ist ungültig!");
            }
        } else {
            throw new APOException("Die E-Mail-Adresse der Apotheke darf nicht null sein!");
        }
    }

    public void setBudget(double budget) throws APOException {
        if(budget >= 0) {
            this.budget = budget;
        } else {
            throw new APOException("Das angegebene Budget für die Apotheke ist ungültig! Der Wert muss mindestens gleich Null sein!");
        }
    }

    public String getName() {
        return this.name;
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
            oos.writeObject(getAdresse());
            oos.writeObject(getTelefonnummer());
            oos.writeObject(getEmail());
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

    public void laden(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.name = (String) ois.readObject();
            this.adresse = (String) ois.readObject();
            this.telefonnummer = (String) ois.readObject();
            this.email = (String) ois.readObject();
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
        return Objects.equals(getName(), apotheke.getName()) && Objects.equals(getAdresse(), apotheke.getAdresse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAdresse());
    }

    @Override
    public String toString() {
        return getName() + "\n" +
                getAdresse() + "\n" +
                getEmail() + "\n" +
                getTelefonnummer();
    }
}