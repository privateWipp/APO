package at.apo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Rezept implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private int rezeptnummer;
    private Kunde patient;
    private String arzt;
    private ArrayList<Medikament> medikamente;
    private LocalDate ausstellungsDatum;
    private LocalDate gueltigBis;
    private int anzDerWiederholungen;
    private String rezeptArt;
    private double preis;
    private String bemerkung;

    public Rezept(Kunde patient, String arzt, ArrayList<Medikament> medikamente, LocalDate gueltigBis, int anzDerWiederholungen, String rezeptArt, String bemerkung) throws APOException {
        this.rezeptnummer = 0;
        setPatient(patient);
        setArzt(arzt);
        setMedikamente(medikamente);
        this.ausstellungsDatum = LocalDate.now();
        setGueltigBis(gueltigBis);
        setAnzDerWiederholungen(anzDerWiederholungen);
        setRezeptArt(rezeptArt);
        berechnePreis();
        setBemerkung(bemerkung);
    }

    @Override
    public Rezept clone() {
        try {
            return (Rezept) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setRezeptnummer(int rezeptnummer) throws APOException {
        if (rezeptnummer >= 1) {
            this.rezeptnummer = rezeptnummer;
        } else {
            throw new APOException("Ungültige Rezeptnummer!");
        }
    }

    public void setPatient(Kunde patient) throws APOException {
        if (patient != null) {
            this.patient = patient;
        } else {
            throw new APOException("Der angegebene Patient ist ungültig!");
        }
    }

    public void setArzt(String arzt) throws APOException {
        if (arzt != null) {
            if (!arzt.isEmpty()) {
                this.arzt = arzt;
            } else {
                throw new APOException("Der angegebene Arzt ist ungültig!");
            }
        } else {
            throw new APOException("Der Arzt, der das Rezept ausstellt darf nicht null sein!");
        }
    }

    public void setMedikamente(ArrayList<Medikament> medikamente) throws APOException {
        if (medikamente == null || medikamente.isEmpty()) {
            throw new APOException("Der übergebene Parameter (Medikamente) darf nicht null bzw. leer sein!");
        } else {
            this.medikamente = medikamente;
        }
    }

    public void setGueltigBis(LocalDate gueltigBis) throws APOException {
        if (gueltigBis != null) {
            if (gueltigBis.isAfter(LocalDate.now())) {
                this.gueltigBis = gueltigBis;
            } else {
                throw new APOException("Das übergebene Datum, bis wann das Rezept gültig ist, ist ungültig!");
            }
        } else {
            throw new APOException("Das Datum, bis wann das Rezept gültig ist darf nicht null sein!");
        }
    }

    public void setAnzDerWiederholungen(int anzDerWiederholungen) throws APOException {
        if (anzDerWiederholungen >= 1) {
            this.anzDerWiederholungen = anzDerWiederholungen;
        } else {
            throw new APOException("Die Einnahme des Medikaments (erhalten durch dieses Rezept) muss mindestens einmal erfolgen!");
        }
    }

    public void setRezeptArt(String rezeptArt) throws APOException {
        if (rezeptArt != null) {
            if (!rezeptArt.isEmpty()) {
                this.rezeptArt = rezeptArt;
            } else {
                throw new APOException("Die angegebene Art des Rezepts ist ungültig!");
            }
        } else {
            throw new APOException("Die Art des Rezepts darf nicht null sein!");
        }
    }

    public void berechnePreis() {
        if (this.medikamente != null && !this.medikamente.isEmpty()) {
            for (Medikament medikament : this.medikamente) {
                this.preis += medikament.getPreis();
            }
        }
    }

    public void setBemerkung(String bemerkung) throws APOException {
        if (bemerkung != null) {
            this.bemerkung = bemerkung;
        } else {
            throw new APOException("Die Bemerkung für das jeweilige Rezept darf nicht null sein!");
        }
    }

    public int getRezeptnummer() {
        return this.rezeptnummer;
    }

    public Kunde getPatient() {
        return this.patient;
    }

    public String getArzt() {
        return this.arzt;
    }

    public ArrayList<Medikament> getMedikamente() {
        return this.medikamente;
    }

    public LocalDate getAusstellungsDatum() {
        return this.ausstellungsDatum;
    }

    public LocalDate getGueltigBis() {
        return this.gueltigBis;
    }

    public int getAnzDerWiederholungen() {
        return this.anzDerWiederholungen;
    }

    public String getRezeptArt() {
        return this.rezeptArt;
    }

    public double getPreis() {
        return this.preis;
    }

    public String getBemerkung() {
        return this.bemerkung;
    }

    public int getAnzMedikamenten() {
        return getMedikamente().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rezept rezept = (Rezept) o;
        return getRezeptnummer() == rezept.getRezeptnummer();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRezeptnummer());
    }

    @Override
    public String toString() {
        return "Rezept an: " + getPatient().getName() + ",\n" +
                "Rezeptnr.: " + getRezeptnummer() + ",\n" +
                "ausgestellt von: " + getArzt() + ",\n" +
                "gültig bis: " + getGueltigBis();
    }
}
