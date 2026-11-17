package com.maraichage.model;

import java.time.LocalDate;

/** Une culture pratiquée sur une parcelle (table culture). */
public class Culture {

    private int id;
    private int parcelleId;
    private String parcelleNom;   // champ de confort pour l'affichage (jointure)
    private String nom;
    private LocalDate dateSemis;
    private String statut;
    private LocalDate dateRecoltePrevue;

    public Culture() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateSemis() {
        return dateSemis;
    }

    public void setDateSemis(LocalDate dateSemis) {
        this.dateSemis = dateSemis;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDate getDateRecoltePrevue() {
        return dateRecoltePrevue;
    }

    public void setDateRecoltePrevue(LocalDate dateRecoltePrevue) {
        this.dateRecoltePrevue = dateRecoltePrevue;
    }
}
