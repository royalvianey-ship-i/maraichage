package com.maraichage.model;

import java.time.LocalDate;

/** Une intervention prévue sur une parcelle (table intervention). */
public class Intervention {

    private int id;
    private int parcelleId;
    private String parcelleNom;   // champ de confort (jointure)
    private LocalDate datePrevue;
    private String type;
    private boolean realisee;

    public Intervention() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParcelleId() {
        return parcelleId;
    }

    public void setParcelleId(int parcelleId) {
        this.parcelleId = parcelleId;
    }

    public String getParcelleNom() {
        return parcelleNom;
    }

    public void setParcelleNom(String parcelleNom) {
        this.parcelleNom = parcelleNom;
    }

    public LocalDate getDatePrevue() {
        return datePrevue;
    }

    public void setDatePrevue(LocalDate datePrevue) {
        this.datePrevue = datePrevue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRealisee() {
        return realisee;
    }

    public void setRealisee(boolean realisee) {
        this.realisee = realisee;
    }
}
