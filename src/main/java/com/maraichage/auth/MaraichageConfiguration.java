package com.maraichage.auth;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.util.HashMap;

/**
 * Configuration JAAS programmatique : évite tout fichier jaas.config
 * et toute configuration serveur. Le nom de contexte est "Maraichage".
 */
public class MaraichageConfiguration extends Configuration {

    public static final String NOM = "Maraichage";

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        if (!NOM.equals(name)) {
            return null;
        }
        HashMap<String, String> options = new HashMap<>();
        AppConfigurationEntry entree = new AppConfigurationEntry(
                MaraichageLoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                options);
        return new AppConfigurationEntry[]{entree};
    }
}
