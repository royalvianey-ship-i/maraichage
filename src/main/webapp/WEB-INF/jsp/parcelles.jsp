<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Parcelles — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <div class="barre-titre">
        <h1>Parcelles</h1>
        <a class="bouton bouton-vert"
           href="${pageContext.request.contextPath}/app/parcelles?operation=nouveau">Nouvelle parcelle</a>
    </div>

    <table class="tableau">
        <thead>
        <tr><th>Nom</th><th>Surface (m²)</th><th>Type de sol</th><th>Localisation</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${parcelles}">
            <tr>
                <td>${p.nom}</td>
                <td>${p.surface}</td>
                <td>${p.typeSol}</td>
                <td>${p.localisation}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/app/parcelles?operation=editer&id=${p.id}">Modifier</a>
                    <c:if test="${sessionScope.role eq 'ADMIN'}">
                        <a class="danger"
                           href="${pageContext.request.contextPath}/app/parcelles?operation=supprimer&id=${p.id}"
                           onclick="return confirm('Supprimer cette parcelle ?');">Supprimer</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty parcelles}">
            <tr><td colspan="5" class="vide">Aucune parcelle enregistrée.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
