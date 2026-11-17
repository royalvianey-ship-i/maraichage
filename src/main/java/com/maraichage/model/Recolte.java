package com.maraichage.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Une récolte enregistrée pour une culture (table recolte). */
public class Recolte {

    private int id;
    private int cultureId;
    private String cultureNom;   // champ de confort (jointure)
    private LocalDate dateRecolte;
    private BigDecimal quantite;
    private String unite;

    public Recolte() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCultureId() {
        return cultureId;
    }

    public void setCultureId(int cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureNom() {
        return cultureNom;
    }

    public void setCultureNom(String cultureNom) {
        this.cultureNom = cultureNom;
    }

    public LocalDate getDateRecolte() {
        return dateRecolte;
    }

    public void setDateRecolte(LocalDate dateRecolte) {
        this.dateRecolte = dateRecolte;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}
