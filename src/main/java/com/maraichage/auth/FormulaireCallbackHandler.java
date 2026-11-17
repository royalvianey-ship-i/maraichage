package com.maraichage.auth;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * Répond aux demandes du LoginModule : il fournit le login et le mot de passe
 * saisis dans le formulaire.
 */
public class FormulaireCallbackHandler implements CallbackHandler {

    private final String login;
    private final char[] motDePasse;

    public FormulaireCallbackHandler(String login, char[] motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb : callbacks) {
            if (cb instanceof NameCallback) {
                ((NameCallback) cb).setName(login);
            } else if (cb instanceof PasswordCallback) {
                ((PasswordCallback) cb).setPassword(motDePasse);
            } else {
                throw new UnsupportedCallbackException(cb, "Callback non supporté");
            }
        }
    }
}
