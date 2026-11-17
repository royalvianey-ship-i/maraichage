package com.maraichage.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Déconnexion : destruction de la session et retour au formulaire. */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("subject");
            session.removeAttribute("utilisateur");
            session.removeAttribute("role");
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
