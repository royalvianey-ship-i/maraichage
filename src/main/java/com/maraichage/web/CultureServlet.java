package com.maraichage.web;

import com.maraichage.dao.CultureDAO;
import com.maraichage.dao.ParcelleDAO;
import com.maraichage.model.Culture;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/** CRUD des cultures. */
@WebServlet("/app/cultures")
public class CultureServlet extends HttpServlet {

    private final CultureDAO dao = new CultureDAO();
    private final ParcelleDAO parcelleDAO = new ParcelleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String op = req.getParameter("operation");
        String base = req.getContextPath() + "/app/cultures";
        try {
            if ("nouveau".equals(op)) {
                req.setAttribute("parcelles", parcelleDAO.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/culture-form.jsp").forward(req, resp);
            } else if ("editer".equals(op)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("culture", dao.trouver(id));
                req.setAttribute("parcelles", parcelleDAO.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/culture-form.jsp").forward(req, resp);
            } else if ("supprimer".equals(op)) {
                try {
                    dao.supprimer(Integer.parseInt(req.getParameter("id")));
                } catch (Exception ex) {
                    req.getSession().setAttribute("erreur",
                            "Suppression impossible : des récoltes sont rattachées à cette culture.");
                }
                resp.sendRedirect(base);
            } else {
                req.setAttribute("cultures", dao.lister());
                req.getRequestDispatcher("/WEB-INF/jsp/cultures.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("erreur", "Erreur : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/cultures.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Culture c = new Culture();
            String id = req.getParameter("id");
            c.setParcelleId(Integer.parseInt(req.getParameter("parcelleId")));
            c.setNom(req.getParameter("nom"));
            String semis = req.getParameter("dateSemis");
            c.setDateSemis(semis == null || semis.isBlank() ? null : LocalDate.parse(semis));
            c.setStatut(req.getParameter("statut"));
            String recolte = req.getParameter("dateRecoltePrevue");
            c.setDateRecoltePrevue(recolte == null || recolte.isBlank() ? null : LocalDate.parse(recolte));
            if (id == null || id.isBlank()) {
                dao.inserer(c);
            } else {
                c.setId(Integer.parseInt(id));
                dao.modifier(c);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("erreur", "Enregistrement impossible : " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/app/cultures");
    }
}
