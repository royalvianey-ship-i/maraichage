package com.maraichage.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Hachage des mots de passe : PBKDF2WithHmacSHA256, 100 000 itérations.
 * Le sel est stocké en Base64 dans la colonne "sel".
 */
public final class MotsDePasse {

    private static final int ITERATIONS = 100_000;
    private static final int LONGUEUR_CLE = 256;   // bits
    private static final int TAILLE_SEL = 16;      // octets

    private MotsDePasse() {
    }

    public static byte[] genererSel() {
        byte[] sel = new byte[TAILLE_SEL];
        new SecureRandom().nextBytes(sel);
        return sel;
    }

    public static String selEnBase64(byte[] sel) {
        return Base64.getEncoder().encodeToString(sel);
    }

    public static String hacher(char[] motDePasse, byte[] sel) {
        try {
            PBEKeySpec spec = new PBEKeySpec(motDePasse, sel, ITERATIONS, LONGUEUR_CLE);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] cle = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();
            return Base64.getEncoder().encodeToString(cle);
        } catch (Exception e) {
            throw new IllegalStateException("Hachage impossible", e);
        }
    }

    public static boolean verifier(char[] motDePasse, String hashBase64, String selBase64) {
        if (hashBase64 == null || selBase64 == null) {
            return false;
        }
        byte[] sel = Base64.getDecoder().decode(selBase64);
        String calcule = hacher(motDePasse, sel);
        return MessageDigest.isEqual(
                calcule.getBytes(StandardCharsets.UTF_8),
                hashBase64.getBytes(StandardCharsets.UTF_8));
    }
}
