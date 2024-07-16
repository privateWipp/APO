package at.apo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Bestellung implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private int bestellnummer;
    private String bezeichnung;
    private LocalDate datum;
    private ArrayList<Medikament> medikamente;
    private double gesamtkosten;
    private String bestellstatus;

    public Bestellung(String bezeichnung, ArrayList<Medikament> medikamente) throws APOException {
        this.bestellnummer = 0;
        setBezeichnung(bezeichnung);
        this.datum = LocalDate.now();
        setMedikamente(medikamente);
        berechneGesamtkosten();
        this.bestellstatus = "BESTELLT";
    }

    @Override
    public Bestellung clone() {
        try {
            return (Bestellung) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setBestellnummer(int bestellnummer) throws APOException {
        if (bestellnummer >= 1) {
            this.bestellnummer = bestellnummer;
        } else {
            throw new APOException("Ungültige Bestellnummer!");
        }
    }

    public void setBezeichnung(String bezeichnung) throws APOException {
        if (bezeichnung != null) {
            if (bezeichnung.isEmpty()) {
                throw new APOException("Die Bezeichnung der Bestellung darf nicht leer sein!");
            } else {
                this.bezeichnung = bezeichnung;
            }
        } else {
            throw new APOException("Die Bezeichnung der Bestellung darf nicht null sein!");
        }
    }

    public void setMedikamente(ArrayList<Medikament> medikamente) throws APOException {
        if (medikamente != null) {
            if (medikamente.isEmpty()) {
                throw new APOException("Die übergebenen Medikamente dürfen nicht leer sein (=> folglich keine Bestellung)!");
            } else {
                this.medikamente = medikamente;
            }
        } else {
            throw new APOException("Die übergebenen Medikamente dürfen nicht null sein!");
        }
    }

    public void setBestellstatus(String bestellstatus) throws APOException {
        if (bestellstatus.equals("BESTELLT") || bestellstatus.equals("VERSANDT") || bestellstatus.equals("ZUGESTELLT")) {
            this.bestellstatus = bestellstatus;
        } else {
            throw new APOException("Gültige Bestellstatus:\n" +
                    "BESTELLT/VERSANDT/ERHALTEN");
        }
    }

    public int getBestellnummer() {
        return this.bestellnummer;
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public ArrayList<Medikament> getMedikamente() {
        return this.medikamente;
    }

    public double getGesamtkosten() {
        return this.gesamtkosten;
    }

    public String getBestellstatus() {
        return this.bestellstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return getBestellnummer() == that.getBestellnummer();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBestellnummer());
    }

    @Override
    public String toString() {
        return getBezeichnung() + "\n" +
                "Bestellung Nr.: " + getBestellnummer() + ",\n" +
                "bestellt am: " + getDatum() + ",\n" +
                "Gesamtkosten: " + getGesamtkosten() + " €,\n" +
                "aktueller Bestellstatus: " + getBestellstatus();
    }

    public void berechneGesamtkosten() {
        double gesamtkosten = 0;
        for(Medikament medikament : this.medikamente) {
            gesamtkosten += medikament.getPreis();
        }
        this.gesamtkosten = gesamtkosten;
    }
}
