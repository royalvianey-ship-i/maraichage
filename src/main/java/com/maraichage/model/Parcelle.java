package com.maraichage.model;

import java.math.BigDecimal;

/** Une parcelle de terrain (table parcelle). */
public class Parcelle {

    private int id;
    private String nom;
    private BigDecimal surface;
    private String typeSol;
    private String localisation;

    public Parcelle() {
    }

    public Parcelle(int id, String nom, BigDecimal surface, String typeSol, String localisation) {
        this.id = id;
        this.nom = nom;
        this.surface = surface;
        this.typeSol = typeSol;
        this.localisation = localisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public String getTypeSol() {
        return typeSol;
    }

    public void setTypeSol(String typeSol) {
        this.typeSol = typeSol;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
