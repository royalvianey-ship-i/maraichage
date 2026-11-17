<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Compte — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <h1><c:choose><c:when test="${empty compte}">Nouveau compte</c:when><c:otherwise>Modifier le compte</c:otherwise></c:choose></h1>

    <form class="formulaire" method="post"
          action="${pageContext.request.contextPath}/app/utilisateurs" accept-charset="UTF-8">
        <input type="hidden" name="id" value="${compte.id}">

        <label for="login">Identifiant</label>
        <c:choose>
            <c:when test="${empty compte}">
                <input id="login" name="login" type="text" required autocomplete="off">
            </c:when>
            <c:otherwise>
                <input id="login" type="text" value="${compte.login}" readonly>
                <small>L'identifiant ne peut pas être modifié.</small>
            </c:otherwise>
        </c:choose>

        <label for="motdepasse">
            <c:choose>
                <c:when test="${empty compte}">Mot de passe</c:when>
                <c:otherwise>Nouveau mot de passe (facultatif)</c:otherwise>
            </c:choose>
        </label>
        <input id="motdepasse" name="motdepasse" type="password"
               ${empty compte ? 'required' : ''} minlength="6" autocomplete="new-password">
        <small>6 caractères minimum. Stocké haché en PBKDF2, jamais en clair.</small>

        <label for="role">Rôle</label>
        <select id="role" name="role">
            <option value="EMPLOYE" ${compte.role eq 'EMPLOYE' ? 'selected' : ''}>Employé</option>
            <option value="ADMIN"   ${compte.role eq 'ADMIN'   ? 'selected' : ''}>Administrateur</option>
        </select>

        <c:if test="${not empty compte}">
            <label class="case">
                <input type="checkbox" name="actif" value="1" ${compte.actif ? 'checked' : ''}>
                Compte actif
            </label>
            <c:if test="${compte.login eq sessionScope.utilisateur}">
                <small>Votre propre compte conserve obligatoirement le rôle ADMIN et reste actif.</small>
            </c:if>
        </c:if>

        <div class="actions-formulaire">
            <button type="submit" class="bouton bouton-vert">
                <c:choose><c:when test="${empty compte}">Créer le compte</c:when><c:otherwise>Enregistrer</c:otherwise></c:choose>
            </button>
            <a class="bouton bouton-gris" href="${pageContext.request.contextPath}/app/utilisateurs">Annuler</a>
        </div>
    </form>
</main>
</body>
</html>
