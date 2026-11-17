package com.maraichage.web;

import com.maraichage.auth.MotsDePasse;
import com.maraichage.dao.UtilisateurDAO;
import com.maraichage.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Gestion des comptes — réservée au rôle ADMIN. */
@WebServlet("/app/utilisateurs")
public class UtilisateurServlet extends HttpServlet {

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!estAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        String op = req.getParameter("operation");
        String base = req.getContextPath() + "/app/utilisateurs";
        try {
            if ("nouveau".equals(op)) {
                req.getRequestDispatcher("/WEB-INF/jsp/utilisateur-form.jsp").forward(req, resp);
            } else if ("editer".equals(op)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("compte", dao.trouver(id));
                req.getRequestDispatcher("/WEB-INF/jsp/utilisateur-form.jsp").forward(req, resp);
            } else if ("supprimer".equals(op)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Utilisateur cible = dao.trouver(id);
                String connecte = loginConnecte(req);
                if (cible != null && !cible.getLogin().equals(connecte)) {
                    dao.supprimer(id);
                } else {
                    req.getSession().setAttribute("erreur",
                            "Impossible de supprimer votre propre compte.");
                }
                resp.sendRedirect(base);
            } else {
                req.setAttribute("utilisateurs", dao.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/utilisateurs.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("erreur", "Erreur : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/utilisateurs.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (!estAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        String id = req.getParameter("id");
        String login = req.getParameter("login");
        String mdp = req.getParameter("motdepasse");
        String role = req.getParameter("role");
        boolean actif = req.getParameter("actif") != null;

        try {
            if (id == null || id.isBlank()) {
                creer(req, login, mdp, role);
            } else {
                modifier(req, Integer.parseInt(id), mdp, role, actif);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("erreur", "Enregistrement impossible : " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/app/utilisateurs");
    }

    private void creer(HttpServletRequest req, String login, String mdp, String role) throws Exception {
        if (login == null || login.isBlank() || mdp == null || mdp.length() < 6) {
            req.getSession().setAttribute("erreur",
                    "Identifiant obligatoire et mot de passe d'au moins 6 caractères.");
            return;
        }
        if (dao.existe(login.trim())) {
            req.getSession().setAttribute("erreur", "Cet identifiant existe déjà.");
            return;
        }
        byte[] sel = MotsDePasse.genererSel();
        Utilisateur u = new Utilisateur();
        u.setLogin(login.trim());
        u.setMotDePasseHash(MotsDePasse.hacher(mdp.toCharArray(), sel));
        u.setSel(MotsDePasse.selEnBase64(sel));
        u.setRole("ADMIN".equals(role) ? "ADMIN" : "EMPLOYE");
        u.setActif(true);
        dao.inserer(u);
    }

    private void modifier(HttpServletRequest req, int id, String mdp, String role, boolean actif)
            throws Exception {
        Utilisateur u = dao.trouver(id);
        if (u == null) {
            req.getSession().setAttribute("erreur", "Compte introuvable.");
            return;
        }
        boolean estSoiMeme = u.getLogin().equals(loginConnecte(req));

        // Un administrateur ne peut pas se retirer ses droits ni se désactiver.
        if (!estSoiMeme) {
            u.setRole("ADMIN".equals(role) ? "ADMIN" : "EMPLOYE");
            u.setActif(actif);
        } else {
            u.setRole("ADMIN");
            u.setActif(true);
        }

        if (mdp != null && !mdp.isBlank()) {
            if (mdp.length() < 6) {
                req.getSession().setAttribute("erreur",
                        "Le nouveau mot de passe doit faire au moins 6 caractères.");
                return;
            }
            byte[] sel = MotsDePasse.genererSel();
            u.setMotDePasseHash(MotsDePasse.hacher(mdp.toCharArray(), sel));
            u.setSel(MotsDePasse.selEnBase64(sel));
        }
        dao.modifier(u);
    }

    private String loginConnecte(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (String) session.getAttribute("utilisateur");
    }

    private boolean estAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        String role = session == null ? null : (String) session.getAttribute("role");
        return "ADMIN".equals(role);
    }
}
