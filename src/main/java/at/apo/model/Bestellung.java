package at.apo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Bestellung implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bestellnummer;
    private LocalDate datum;
    private ArrayList<Medikament> medikamente;
    private double gesamtkosten;
    private String bestellstatus;

    public Bestellung(ArrayList<Medikament> medikamente) {
        this.bestellnummer = 0;
        this.datum = LocalDate.now();
        this.medikamente = medikamente;
        if(this.medikamente != null) {
            for(Medikament medikament : this.medikamente) {
                this.gesamtkosten += medikament.getPreis();
            }
        }
        this.bestellstatus = "BESTELLT";
    }

    public void setBestellnummer(int bestellnummer) throws APOException {
        if(bestellnummer >= 1) {
            this.bestellnummer = bestellnummer;
        } else {
            throw new APOException("Ungültige Bestellnummer!");
        }
    }

    public void setBestellstatus(String bestellstatus) throws APOException {
        if(bestellstatus.equals("BESTELLT") || bestellstatus.equals("VERSANDT") || bestellstatus.equals("ERHALTEN")) {
            this.bestellstatus = bestellstatus;
        } else {
            throw new APOException("Gültige Bestellstatus:\n" +
                    "BESTELLT/VERSANDT/ERHALTEN");
        }
    }

    public int getBestellnummer() {
        return this.bestellnummer;
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
        return "Bestellung Nr.: " + getBestellnummer() + ",\n" +
                "bestellt am: " + getDatum() + ",\n" +
                "Gesamtkosten: " + getGesamtkosten() + " €,\n" +
                "aktueller Bestellstatus: " + getBestellstatus();
    }
}
