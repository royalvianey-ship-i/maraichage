package com.maraichage.web;

import com.maraichage.dao.ParcelleDAO;
import com.maraichage.model.Parcelle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

/** CRUD des parcelles. */
@WebServlet("/app/parcelles")
public class ParcelleServlet extends HttpServlet {

    private final ParcelleDAO dao = new ParcelleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!estAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        String op = req.getParameter("operation");
        String base = req.getContextPath() + "/app/parcelles";
        try {
            if ("nouveau".equals(op)) {
                req.getRequestDispatcher("/WEB-INF/jsp/parcelle-form.jsp").forward(req, resp);
            } else if ("editer".equals(op)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("parcelle", dao.trouver(id));
                req.getRequestDispatcher("/WEB-INF/jsp/parcelle-form.jsp").forward(req, resp);
            } else if ("supprimer".equals(op)) {
                if (!estAdmin(req)) {
                    req.getSession().setAttribute("erreur",
                            "Suppression réservée à l'administrateur.");
                    resp.sendRedirect(base);
                    return;
                }
                try {
                    dao.supprimer(Integer.parseInt(req.getParameter("id")));
                } catch (Exception ex) {
                    req.getSession().setAttribute("erreur",
                            "Suppression impossible : cette parcelle a des cultures rattachées.");
                }
                resp.sendRedirect(base);
            } else {
                req.setAttribute("parcelles", dao.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/parcelles.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("erreur", "Erreur : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/parcelles.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (!estAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        try {
            Parcelle p = new Parcelle();
            String id = req.getParameter("id");
            p.setNom(req.getParameter("nom"));
            p.setSurface(new BigDecimal(req.getParameter("surface")));
            p.setTypeSol(req.getParameter("typeSol"));
            p.setLocalisation(req.getParameter("localisation"));
            if (id == null || id.isBlank()) {
                dao.inserer(p);
            } else {
                p.setId(Integer.parseInt(id));
                dao.modifier(p);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("erreur", "Enregistrement impossible : " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/app/parcelles");
    }

    private boolean estAdmin(HttpServletRequest req) {
        jakarta.servlet.http.HttpSession s = req.getSession(false);
        String role = s == null ? null : (String) s.getAttribute("role");
        return "ADMIN".equals(role);
    }
}
