package com.maraichage.dao;

import com.maraichage.model.Recolte;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Accès à la table recolte. */
public class RecolteDAO {

    public List<Recolte> lister() throws SQLException {
        String sql = "SELECT r.id, r.culture_id, c.nom AS culture_nom, r.date_recolte, "
                   + "r.quantite, r.unite "
                   + "FROM recolte r JOIN culture c ON c.id = r.culture_id "
                   + "ORDER BY r.date_recolte DESC";
        List<Recolte> liste = new ArrayList<>();
        try (Connection cn = Connexion.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        }
        return liste;
    }

    public Recolte trouver(int id) throws SQLException {
        String sql = "SELECT r.id, r.culture_id, c.nom AS culture_nom, r.date_recolte, "
                   + "r.quantite, r.unite "
                   + "FROM recolte r JOIN culture c ON c.id = r.culture_id WHERE r.id = ?";
        try (Connection cn = Connexion.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
            }
        }
        return null;
    }

    public void inserer(Recolte r) throws SQLException {
        String sql = "INSERT INTO recolte (culture_id, date_recolte, quantite, unite) VALUES (?, ?, ?, ?)";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, r.getCultureId());
            ps.setDate(2, Date.valueOf(r.getDateRecolte()));
            ps.setBigDecimal(3, r.getQuantite());
            ps.setString(4, r.getUnite());
            ps.executeUpdate();
        }
    }

    public void modifier(Recolte r) throws SQLException {
        String sql = "UPDATE recolte SET culture_id = ?, date_recolte = ?, quantite = ?, unite = ? "
                   + "WHERE id = ?";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, r.getCultureId());
            ps.setDate(2, Date.valueOf(r.getDateRecolte()));
            ps.setBigDecimal(3, r.getQuantite());
            ps.setString(4, r.getUnite());
            ps.setInt(5, r.getId());
            ps.executeUpdate();
        }
    }

    public void supprimer(int id) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM recolte WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Somme des récoltes du mois en cours, toutes cultures confondues. */
    public BigDecimal totalMoisEnCours() throws SQLException {
        String sql = "SELECT COALESCE(SUM(quantite), 0) FROM recolte "
                   + "WHERE date_trunc('month', date_recolte) = date_trunc('month', CURRENT_DATE)";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        }
    }

    private Recolte mapper(ResultSet rs) throws SQLException {
        Recolte r = new Recolte();
        r.setId(rs.getInt("id"));
        r.setCultureId(rs.getInt("culture_id"));
        r.setCultureNom(rs.getString("culture_nom"));
        LocalDate d = rs.getDate("date_recolte").toLocalDate();
        r.setDateRecolte(d);
        r.setQuantite(rs.getBigDecimal("quantite"));
        r.setUnite(rs.getString("unite"));
        return r;
    }
}
