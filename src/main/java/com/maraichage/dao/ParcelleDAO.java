package com.maraichage.dao;

import com.maraichage.model.Parcelle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Accès à la table parcelle. */
public class ParcelleDAO {

    public List<Parcelle> lister() throws SQLException {
        String sql = "SELECT id, nom, surface, type_sol, localisation "
                   + "FROM parcelle ORDER BY nom";
        List<Parcelle> liste = new ArrayList<>();
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        }
        return liste;
    }

    public Parcelle trouver(int id) throws SQLException {
        String sql = "SELECT id, nom, surface, type_sol, localisation FROM parcelle WHERE id = ?";
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

    public void inserer(Parcelle p) throws SQLException {
        String sql = "INSERT INTO parcelle (nom, surface, type_sol, localisation) VALUES (?, ?, ?, ?)";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setBigDecimal(2, p.getSurface());
            ps.setString(3, p.getTypeSol());
            ps.setString(4, p.getLocalisation());
            ps.executeUpdate();
        }
    }

    public void modifier(Parcelle p) throws SQLException {
        String sql = "UPDATE parcelle SET nom = ?, surface = ?, type_sol = ?, localisation = ? WHERE id = ?";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setBigDecimal(2, p.getSurface());
            ps.setString(3, p.getTypeSol());
            ps.setString(4, p.getLocalisation());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        }
    }

    public void supprimer(int id) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM parcelle WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int compter() throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM parcelle");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Parcelle mapper(ResultSet rs) throws SQLException {
        Parcelle p = new Parcelle();
        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom"));
        BigDecimal s = rs.getBigDecimal("surface");
        p.setSurface(s);
        p.setTypeSol(rs.getString("type_sol"));
        p.setLocalisation(rs.getString("localisation"));
        return p;
    }
}
