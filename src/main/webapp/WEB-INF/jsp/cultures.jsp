<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cultures — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <div class="barre-titre">
        <h1>Cultures</h1>
        <a class="bouton bouton-vert"
           href="${pageContext.request.contextPath}/app/cultures?operation=nouveau">Nouvelle culture</a>
    </div>

    <table class="tableau">
        <thead>
        <tr><th>Parcelle</th><th>Culture</th><th>Semis</th><th>Statut</th><th>Récolte prévue</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${cultures}">
            <tr>
                <td>${c.parcelleNom}</td>
                <td>${c.nom}</td>
                <td>${c.dateSemis}</td>
                <td><span class="etiquette">${c.statut}</span></td>
                <td>${c.dateRecoltePrevue}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/app/cultures?operation=editer&id=${c.id}">Modifier</a>
                    <a class="danger"
                       href="${pageContext.request.contextPath}/app/cultures?operation=supprimer&id=${c.id}"
                       onclick="return confirm('Supprimer cette culture ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty cultures}">
            <tr><td colspan="6" class="vide">Aucune culture enregistrée.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
