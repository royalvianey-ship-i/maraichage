package com.maraichage.web;

import com.maraichage.dao.CultureDAO;
import com.maraichage.dao.InterventionDAO;
import com.maraichage.dao.ParcelleDAO;
import com.maraichage.dao.RecolteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Tableau de bord : agrège quatre indicateurs métier. */
@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ParcelleDAO parcelleDAO = new ParcelleDAO();
    private final CultureDAO cultureDAO = new CultureDAO();
    private final RecolteDAO recolteDAO = new RecolteDAO();
    private final InterventionDAO interventionDAO = new InterventionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("nbParcelles", parcelleDAO.compter());
            req.setAttribute("nbCultures", cultureDAO.compterEnCours());
            req.setAttribute("totalRecoltes", recolteDAO.totalMoisEnCours());
            req.setAttribute("interventions", interventionDAO.prochaines(5));
        } catch (Exception e) {
            req.setAttribute("erreur", "Base de données indisponible : " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(req, resp);
    }
}
