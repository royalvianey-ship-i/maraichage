package com.maraichage.auth;

import com.maraichage.dao.UtilisateurDAO;
import com.maraichage.model.Utilisateur;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

/**
 * LoginModule JAAS personnalisé.
 * Vérifie les identifiants saisis contre la table "utilisateur" (PostgreSQL)
 * et ajoute un UserPrincipal et un RolePrincipal au Subject.
 */
public class MaraichageLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;

    private String login;
    private String role;
    private boolean succes;
    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.succes = false;
    }

    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("Aucun CallbackHandler fourni");
        }
        NameCallback nameCb = new NameCallback("Identifiant : ");
        PasswordCallback pwdCb = new PasswordCallback("Mot de passe : ", false);
        try {
            callbackHandler.handle(new Callback[]{nameCb, pwdCb});
        } catch (Exception e) {
            throw new LoginException("Erreur de lecture des identifiants : " + e.getMessage());
        }

        String identifiant = nameCb.getName();
        char[] motDePasse = pwdCb.getPassword();

        if (identifiant == null || identifiant.isBlank() || motDePasse == null || motDePasse.length == 0) {
            throw new FailedLoginException("Identifiant ou mot de passe incorrect.");
        }

        try {
            Utilisateur u = new UtilisateurDAO().trouverParLogin(identifiant.trim());
            if (u == null || !u.isActif()
                    || !MotsDePasse.verifier(motDePasse, u.getMotDePasseHash(), u.getSel())) {
                throw new FailedLoginException("Identifiant ou mot de passe incorrect.");
            }
            this.login = u.getLogin();
            this.role = u.getRole();
            this.succes = true;
            return true;
        } catch (FailedLoginException e) {
            throw e;
        } catch (Exception e) {
            throw new LoginException("Erreur d'accès à la base : " + e.getMessage());
        } finally {
            java.util.Arrays.fill(motDePasse, '\0');
        }
    }

    @Override
    public boolean commit() {
        if (!succes) {
            return false;
        }
        userPrincipal = new UserPrincipal(login);
        rolePrincipal = new RolePrincipal(role);
        subject.getPrincipals().add(userPrincipal);
        subject.getPrincipals().add(rolePrincipal);
        return true;
    }

    @Override
    public boolean abort() {
        if (succes) {
            retirerPrincipaux();
        }
        succes = false;
        login = null;
        role = null;
        return true;
    }

    @Override
    public boolean logout() {
        retirerPrincipaux();
        succes = false;
        login = null;
        role = null;
        return true;
    }

    private void retirerPrincipaux() {
        if (userPrincipal != null) {
            subject.getPrincipals().remove(userPrincipal);
        }
        if (rolePrincipal != null) {
            subject.getPrincipals().remove(rolePrincipal);
        }
        userPrincipal = null;
        rolePrincipal = null;
    }
}
