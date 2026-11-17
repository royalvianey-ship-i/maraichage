package com.maraichage.dao;

import com.maraichage.model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Accès à la table utilisateur : authentification JAAS et administration des comptes. */
public class UtilisateurDAO {

    public Utilisateur trouverParLogin(String login) throws SQLException {
        String sql = "SELECT id, login, mot_de_passe_hash, sel, role, actif "
                   + "FROM utilisateur WHERE login = ?";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
            }
        }
        return null;
    }

    public Utilisateur trouver(int id) throws SQLException {
        String sql = "SELECT id, login, mot_de_passe_hash, sel, role, actif "
                   + "FROM utilisateur WHERE id = ?";
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

    public List<Utilisateur> lister() throws SQLException {
        String sql = "SELECT id, login, mot_de_passe_hash, sel, role, actif "
                   + "FROM utilisateur ORDER BY login";
        List<Utilisateur> liste = new ArrayList<>();
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        }
        return liste;
    }

    public boolean existe(String login) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM utilisateur WHERE login = ?")) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void inserer(Utilisateur u) throws SQLException {
        String sql = "INSERT INTO utilisateur (login, mot_de_passe_hash, sel, role, actif) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getMotDePasseHash());
            ps.setString(3, u.getSel());
            ps.setString(4, u.getRole());
            ps.setBoolean(5, u.isActif());
            ps.executeUpdate();
        }
    }

    public void modifier(Utilisateur u) throws SQLException {
        String sql = "UPDATE utilisateur SET mot_de_passe_hash = ?, sel = ?, role = ?, actif = ? "
                   + "WHERE id = ?";
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getMotDePasseHash());
            ps.setString(2, u.getSel());
            ps.setString(3, u.getRole());
            ps.setBoolean(4, u.isActif());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
        }
    }

    public void supprimer(int id) throws SQLException {
        try (Connection c = Connexion.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM utilisateur WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Utilisateur mapper(ResultSet rs) throws SQLException {
        Utilisateur u = new Utilisateur();
        u.setId(rs.getInt("id"));
        u.setLogin(rs.getString("login"));
        u.setMotDePasseHash(rs.getString("mot_de_passe_hash"));
        u.setSel(rs.getString("sel"));
        u.setRole(rs.getString("role"));
        u.setActif(rs.getBoolean("actif"));
        return u;
    }
}
