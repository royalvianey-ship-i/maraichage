package com.maraichage.auth;

import java.io.Serializable;
import java.security.Principal;

/** Rôle de l'utilisateur (ADMIN ou EMPLOYE), porté par le Subject JAAS. */
public class RolePrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;

    public RolePrincipal(String name) {
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
        if (!(o instanceof RolePrincipal)) {
            return false;
        }
        return name.equals(((RolePrincipal) o).name);
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
