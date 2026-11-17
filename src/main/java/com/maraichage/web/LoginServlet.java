package com.maraichage.web;

import com.maraichage.auth.FormulaireCallbackHandler;
import com.maraichage.auth.MaraichageConfiguration;
import com.maraichage.auth.RolePrincipal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

/**
 * Contrôleur de connexion : construit un LoginContext JAAS,
 * authentifie, puis place le Subject en session.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String VUE = "/WEB-INF/jsp/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(VUE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String mdp = req.getParameter("motdepasse");

        if (login == null || login.isBlank() || mdp == null || mdp.isEmpty()) {
            returnEchec(req, resp, login, "Veuillez saisir votre identifiant et votre mot de passe.");
            return;
        }

        char[] motDePasse = mdp.toCharArray();
        try {
            LoginContext contexte = new LoginContext(
                    MaraichageConfiguration.NOM,
                    null,
                    new FormulaireCallbackHandler(login.trim(), motDePasse),
                    new MaraichageConfiguration());

            contexte.login();
            Subject subject = contexte.getSubject();

            // Protection contre la fixation de session : on repart d'une session neuve.
            HttpSession ancienne = req.getSession(false);
            if (ancienne != null) {
                ancienne.invalidate();
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("utilisateur", login.trim());
            session.setAttribute("role", extraireRole(subject));
            session.setAttribute("subject", subject);

            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        } catch (LoginException e) {
            returnEchec(req, resp, login, "Identifiant ou mot de passe incorrect.");
        } finally {
            Arrays.fill(motDePasse, '\0');
        }
    }

    private String extraireRole(Subject subject) {
        for (Principal p : subject.getPrincipals()) {
            if (p instanceof RolePrincipal) {
                return p.getName();
            }
        }
        return "EMPLOYE";
    }

    private void returnEchec(HttpServletRequest req, HttpServletResponse resp,
                             String loginSaisi, String message)
            throws ServletException, IOException {
        req.setAttribute("erreur", message);
        req.setAttribute("loginSaisi", loginSaisi);
        req.getRequestDispatcher(VUE).forward(req, resp);
    }
}
