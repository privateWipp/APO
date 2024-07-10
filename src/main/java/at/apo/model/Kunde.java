package at.apo.model;

import java.io.Serializable;

public class Kunde implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;

    public Kunde(String name) throws APOException {
        setName(name);
    }

    @Override
    public Kunde clone() {
        try {
            return (Kunde) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setName(String name) throws APOException {
        if(name != null) {
            if(!name.isEmpty()) {
                this.name = name;
            } else {
                throw new APOException("Der angegebene Name des Kunden ist ungültig!");
            }
        } else {
            throw new APOException("Der Name des Kunden darf nicht null sein!");
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Name: " + getName();
    }
}
