package com.maraichage.web;

import com.maraichage.dao.CultureDAO;
import com.maraichage.dao.RecolteDAO;
import com.maraichage.model.Recolte;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Création, consultation, modification et suppression des récoltes. */
@WebServlet("/app/recoltes")
public class RecolteServlet extends HttpServlet {

    private final RecolteDAO dao = new RecolteDAO();
    private final CultureDAO cultureDAO = new CultureDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String op = req.getParameter("operation");
        String base = req.getContextPath() + "/app/recoltes";
        try {
            if ("nouveau".equals(op)) {
                req.setAttribute("cultures", cultureDAO.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/recolte-form.jsp").forward(req, resp);
            } else if ("editer".equals(op)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("recolte", dao.trouver(id));
                req.setAttribute("cultures", cultureDAO.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/recolte-form.jsp").forward(req, resp);
            } else if ("supprimer".equals(op)) {
                dao.supprimer(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(base);
            } else {
                req.setAttribute("recoltes", dao.lister());
                req.setAttribute("totalMois", dao.totalMoisEnCours());
                req.getRequestDispatcher("/WEB-INF/jsp/recoltes.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("erreur", "Erreur : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/recoltes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Recolte r = new Recolte();
            String id = req.getParameter("id");
            r.setCultureId(Integer.parseInt(req.getParameter("cultureId")));
            r.setDateRecolte(LocalDate.parse(req.getParameter("dateRecolte")));
            r.setQuantite(new BigDecimal(req.getParameter("quantite")));
            String unite = req.getParameter("unite");
            r.setUnite(unite == null || unite.isBlank() ? "kg" : unite);
            if (id == null || id.isBlank()) {
                dao.inserer(r);
            } else {
                r.setId(Integer.parseInt(id));
                dao.modifier(r);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("erreur", "Enregistrement impossible : " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/app/recoltes");
    }
}
