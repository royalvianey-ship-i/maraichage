package com.maraichage.auth;

import java.io.Serializable;
import java.security.Principal;

/** Identité de l'utilisateur connecté (portée par le Subject JAAS). */
public class UserPrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPrincipal)) {
            return false;
        }
        return name.equals(((UserPrincipal) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
