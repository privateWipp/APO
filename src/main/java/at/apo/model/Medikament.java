package at.apo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Medikament implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private String bezeichnung;
    private double preis;
    private int lagerbestand;
    private LocalDate verfallsdatum;
    private String hersteller;
    private String wirkstoff;
    private String dosierung;
    private boolean rezeptpflichtig;
    private String nebenwirkungen;
    private String lagerbedingungen;
    private boolean verfuegbar;
    private String beschreibung;

    public Medikament(String bezeichnung, double preis, int lagerbestand, LocalDate verfallsdatum, String hersteller, String wirkstoff, String dosierung, boolean rezeptpflichtig, String nebenwirkungen, String lagerbedingungen, boolean verfuegbar, String beschreibung) throws APOException {
        setBezeichnung(bezeichnung);
        setPreis(preis);
        setLagerbestand(lagerbestand);
        setVerfallsdatum(verfallsdatum);
        setHersteller(hersteller);
        setWirkstoff(wirkstoff);
        setDosierung(dosierung);
        setRezeptpflichtig(rezeptpflichtig);
        setNebenwirkungen(nebenwirkungen);
        setLagerbedingungen(lagerbedingungen);
        setVerfuegbar(verfuegbar);
        setBeschreibung(beschreibung);
    }

    @Override
    public Medikament clone() {
        try {
            return (Medikament) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setBezeichnung(String bezeichnung) throws APOException {
        if (bezeichnung != null) {
            if (!bezeichnung.isEmpty()) {
                this.bezeichnung = bezeichnung;
            } else {
                throw new APOException("Die Bezeichnung des Medikaments ist ungültig!");
            }
        } else {
            throw new APOException("Die Bezeichnung des Medikaments darf nicht null sein!");
        }
    }

    public void setPreis(double preis) throws APOException {
        if (preis >= 0) {
            this.preis = preis;
        } else {
            throw new APOException("Der angegebene Preis für das Medikament ist ungültig!");
        }
    }

    public void setLagerbestand(int lagerbestand) throws APOException {
        if (lagerbestand >= 1) {
            this.lagerbestand = lagerbestand;
        } else {
            throw new APOException("Es muss mindestens ein Medikament solcher Art auf Lager sein!");
        }
    }

    public void setVerfallsdatum(LocalDate verfallsdatum) throws APOException {
        if (verfallsdatum != null) {
            if (verfallsdatum.isBefore(LocalDate.now())) {
                throw new APOException("Das angegebene Verfallsdatum für das Medikament ist ungültig!");
            } else {
                this.verfallsdatum = verfallsdatum;
            }
        } else {
            throw new APOException("Das Verfallsdatum des Medikaments darf nicht null sein!");
        }
    }

    public void setHersteller(String hersteller) throws APOException {
        if (hersteller != null) {
            if (!hersteller.isEmpty()) {
                this.hersteller = hersteller;
            } else {
                throw new APOException("Der angegebene Hersteller für das Medikament ist ungültig!");
            }
        } else {
            throw new APOException("Der Hersteller des Medikaments darf nicht null sein!");
        }
    }

    public void setWirkstoff(String wirkstoff) throws APOException {
        if (wirkstoff != null) {
            if (!wirkstoff.isEmpty()) {
                this.wirkstoff = wirkstoff;
            } else {
                throw new APOException("Der angegebene Wirkstoff für das Medikament ist ungültig!");
            }
        } else {
            throw new APOException("Der Wirkstoff des Medikaments darf nicht null sein!");
        }
    }

    public void setDosierung(String dosierung) throws APOException {
        if (dosierung != null) {
            if (!dosierung.isEmpty()) {
                this.dosierung = dosierung;
            } else {
                throw new APOException("Die angegebene Dosierung für das Medikament ist ungültig!");
            }
        } else {
            throw new APOException("Die Dosierung des Medikaments darf nicht null sein!");
        }
    }

    public void setRezeptpflichtig(boolean rezeptpflichtig) {
        this.rezeptpflichtig = rezeptpflichtig;
    }

    public void setNebenwirkungen(String nebenwirkungen) throws APOException {
        if (nebenwirkungen != null) {
            this.nebenwirkungen = nebenwirkungen;
        } else {
            throw new APOException("Die Nebenwirkung(en) des Medikaments darf/dürfen nicht null sein!");
        }
    }

    public void setLagerbedingungen(String lagerbedingungen) throws APOException {
        if (lagerbedingungen != null) {
            this.lagerbedingungen = lagerbedingungen;
        } else {
            throw new APOException("Die Lagerbedingungen für das Medikament dürfen nicht null sein!");
        }
    }

    public void setVerfuegbar(boolean verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    public void setBeschreibung(String beschreibung) throws APOException {
        if (beschreibung != null) {
            if (!beschreibung.isEmpty()) {
                this.beschreibung = beschreibung;
            } else {
                throw new APOException("Die angegebene Beschreibung für das Medikament ist ungültig!");
            }
        } else {
            throw new APOException("Die Beschreibung für das Medikament darf nicht null sein!");
        }
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public double getPreis() {
        return this.preis;
    }

    public int getLagerbestand() {
        return this.lagerbestand;
    }

    public LocalDate getVerfallsdatum() {
        return this.verfallsdatum;
    }

    public String getHersteller() {
        return this.hersteller;
    }

    public String getWirkstoff() {
        return this.wirkstoff;
    }

    public String getDosierung() {
        return this.dosierung;
    }

    public boolean isRezeptpflichtig() {
        return this.rezeptpflichtig;
    }

    public String getNebenwirkungen() {
        return this.nebenwirkungen;
    }

    public String getLagerbedingungen() {
        return this.lagerbedingungen;
    }

    public boolean isVerfuegbar() {
        return this.verfuegbar;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medikament that = (Medikament) o;
        return Double.compare(getPreis(), that.getPreis()) == 0 && getLagerbestand() == that.getLagerbestand() && isRezeptpflichtig() == that.isRezeptpflichtig() && isVerfuegbar() == that.isVerfuegbar() && Objects.equals(getBezeichnung(), that.getBezeichnung()) && Objects.equals(getVerfallsdatum(), that.getVerfallsdatum()) && Objects.equals(getHersteller(), that.getHersteller()) && Objects.equals(getWirkstoff(), that.getWirkstoff()) && Objects.equals(getDosierung(), that.getDosierung()) && Objects.equals(getNebenwirkungen(), that.getNebenwirkungen()) && Objects.equals(getLagerbedingungen(), that.getLagerbedingungen()) && Objects.equals(getBeschreibung(), that.getBeschreibung());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBezeichnung(), getPreis(), getLagerbestand(), getVerfallsdatum(), getHersteller(), getWirkstoff(), getDosierung(), isRezeptpflichtig(), getNebenwirkungen(), getLagerbedingungen(), isVerfuegbar(), getBeschreibung());
    }

    @Override
    public String toString() {
        return "Bezeichnung: " + getBezeichnung() + ",\n" +
                "Beschreibung: " + getBeschreibung() + ",\n" +
                "rezeptpflichtig: " + (isRezeptpflichtig() ? "Ja,\n" : "Nein,\n") +
                "Preis: " + getPreis() + " €";
    }
}
