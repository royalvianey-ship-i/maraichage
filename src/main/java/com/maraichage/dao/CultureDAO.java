package com.maraichage.dao;

import com.maraichage.model.Culture;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Accès à la table culture. */
public class CultureDAO {

    public List<Culture> lister() throws SQLException {
        String sql = "SELECT c.id, c.parcelle_id, p.nom AS parcelle_nom, c.nom, "
                   + "c.date_semis, c.statut, c.date_recolte_prevue "
                   + "FROM culture c JOIN parcelle p ON p.id = c.parcelle_id "
                   + "ORDER BY p.nom, c.nom";
        List<Culture> liste = new ArrayList<>();
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        }
        return liste;
    }

    public Culture trouver(int id) throws SQLException {
        String sql = "SELECT c.id, c.parcelle_id, p.nom AS parcelle_nom, c.nom, "
                   + "c.date_semis, c.statut, c.date_recolte_prevue "
                   + "FROM culture c JOIN parcelle p ON p.id = c.parcelle_id WHERE c.id = ?";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
            }
        }
        return null;
    }

    public void inserer(Culture c) throws SQLException {
        String sql = "INSERT INTO culture (parcelle_id, nom, date_semis, statut, date_recolte_prevue) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = Connexion.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, c.getParcelleId());
            ps.setString(2, c.getNom());
            ps.setDate(3, c.getDateSemis() == null ? null : Date.valueOf(c.getDateSemis()));
            ps.setString(4, c.getStatut());
            ps.setDate(5, c.getDateRecoltePrevue() == null ? null : Date.valueOf(c.getDateRecoltePrevue()));
            ps.executeUpdate();
        }
    }

    public void supprimer(int id) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM culture WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void modifier(Culture c) throws SQLException {
        String sql = "UPDATE culture SET parcelle_id = ?, nom = ?, date_semis = ?, "
                   + "statut = ?, date_recolte_prevue = ? WHERE id = ?";
        try (Connection cn = Connexion.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, c.getParcelleId());
            ps.setString(2, c.getNom());
            ps.setDate(3, c.getDateSemis() == null ? null : Date.valueOf(c.getDateSemis()));
            ps.setString(4, c.getStatut());
            ps.setDate(5, c.getDateRecoltePrevue() == null ? null : Date.valueOf(c.getDateRecoltePrevue()));
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        }
    }

    public int compterEnCours() throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT count(*) FROM culture WHERE statut = 'EN_COURS'");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Culture mapper(ResultSet rs) throws SQLException {
        Culture c = new Culture();
        c.setId(rs.getInt("id"));
        c.setParcelleId(rs.getInt("parcelle_id"));
        c.setParcelleNom(rs.getString("parcelle_nom"));
        c.setNom(rs.getString("nom"));
        Date d1 = rs.getDate("date_semis");
        c.setDateSemis(d1 == null ? null : d1.toLocalDate());
        c.setStatut(rs.getString("statut"));
        Date d2 = rs.getDate("date_recolte_prevue");
        c.setDateRecoltePrevue(d2 == null ? null : d2.toLocalDate());
        return c;
    }
}
