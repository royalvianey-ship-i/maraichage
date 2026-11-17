<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Utilisateurs — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <div class="barre-titre">
        <h1>Utilisateurs</h1>
        <a class="bouton bouton-vert"
           href="${pageContext.request.contextPath}/app/utilisateurs?operation=nouveau">Nouveau compte</a>
    </div>

    <p class="resume">Les mots de passe ne sont jamais affichés : ils sont stockés hachés (PBKDF2, 100 000 itérations).</p>

    <table class="tableau">
        <thead>
        <tr><th>Identifiant</th><th>Rôle</th><th>Actif</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${utilisateurs}">
            <tr>
                <td>${u.login}</td>
                <td><span class="etiquette">${u.role}</span></td>
                <td>${u.actif ? 'Oui' : 'Non'}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/app/utilisateurs?operation=editer&id=${u.id}">Modifier</a>
                    <c:choose>
                        <c:when test="${u.login eq sessionScope.utilisateur}">
                            <em>compte courant</em>
                        </c:when>
                        <c:otherwise>
                            <a class="danger"
                               href="${pageContext.request.contextPath}/app/utilisateurs?operation=supprimer&id=${u.id}"
                               onclick="return confirm('Supprimer ce compte ?');">Supprimer</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
</html>
