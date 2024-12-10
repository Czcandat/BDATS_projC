package me.bdats_projc;

import javafx.beans.property.*;

public class Obec {
    private final IntegerProperty psc;
    private final StringProperty name;
    private final IntegerProperty muziPocet;
    private final IntegerProperty zenyPocet;
    private final IntegerProperty celkemPocet;

    public Obec(int psc, String name, int muziPocet, int zenyPocet, int celkemPocet) {
        this.psc = new SimpleIntegerProperty(psc);
        this.name = new SimpleStringProperty(name);
        this.muziPocet = new SimpleIntegerProperty(muziPocet);
        this.zenyPocet = new SimpleIntegerProperty(zenyPocet);
        this.celkemPocet = new SimpleIntegerProperty(celkemPocet);
    }

    public int getPsc() { return psc.get(); }
    public String getName() { return name.get(); }
    public int getMuziPocet() { return muziPocet.get(); }
    public int getZenyPocet() { return zenyPocet.get(); }
    public int getPopulation() { return celkemPocet.get(); }

    // Getters pro Property objekty
    public IntegerProperty pscProperty() { return psc; }
    public StringProperty nameProperty() { return name; }
    public IntegerProperty muziPocetProperty() { return muziPocet; }
    public IntegerProperty zenyPocetProperty() { return zenyPocet; }
    public IntegerProperty populationProperty() { return celkemPocet; }

    @Override
    public String toString() {
        return getName();
    }
}

