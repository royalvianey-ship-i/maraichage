package com.maraichage.dao;

import com.maraichage.model.Intervention;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Accès à la table intervention. */
public class InterventionDAO {

    /** Les prochaines interventions non réalisées, triées par date. */
    public List<Intervention> prochaines(int limite) throws SQLException {
        String sql = "SELECT i.id, i.parcelle_id, p.nom AS parcelle_nom, i.date_prevue, "
                   + "i.type, i.realisee "
                   + "FROM intervention i JOIN parcelle p ON p.id = i.parcelle_id "
                   + "WHERE i.realisee = false AND i.date_prevue >= CURRENT_DATE "
                   + "ORDER BY i.date_prevue LIMIT ?";
        List<Intervention> liste = new ArrayList<>();
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Intervention i = new Intervention();
                    i.setId(rs.getInt("id"));
                    i.setParcelleId(rs.getInt("parcelle_id"));
                    i.setParcelleNom(rs.getString("parcelle_nom"));
                    i.setDatePrevue(rs.getDate("date_prevue").toLocalDate());
                    i.setType(rs.getString("type"));
                    i.setRealisee(rs.getBoolean("realisee"));
                    liste.add(i);
                }
            }
        }
        return liste;
    }

    public void inserer(Intervention i) throws SQLException {
        String sql = "INSERT INTO intervention (parcelle_id, date_prevue, type, realisee) VALUES (?, ?, ?, false)";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, i.getParcelleId());
            ps.setDate(2, Date.valueOf(i.getDatePrevue()));
            ps.setString(3, i.getType());
            ps.executeUpdate();
        }
    }

    public void marquerRealisee(int id) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE intervention SET realisee = true WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
