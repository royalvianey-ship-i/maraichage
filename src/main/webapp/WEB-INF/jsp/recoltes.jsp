<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Récoltes — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <div class="barre-titre">
        <h1>Récoltes</h1>
        <a class="bouton bouton-vert"
           href="${pageContext.request.contextPath}/app/recoltes?operation=nouveau">Nouvelle récolte</a>
    </div>

    <p class="resume">Total du mois en cours : <strong>${totalMois} kg</strong></p>

    <table class="tableau">
        <thead>
        <tr><th>Date</th><th>Culture</th><th>Quantité</th><th>Unité</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${recoltes}">
            <tr>
                <td>${r.dateRecolte}</td>
                <td>${r.cultureNom}</td>
                <td>${r.quantite}</td>
                <td>${r.unite}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/app/recoltes?operation=editer&id=${r.id}">Modifier</a>
                    <a class="danger"
                       href="${pageContext.request.contextPath}/app/recoltes?operation=supprimer&id=${r.id}"
                       onclick="return confirm('Supprimer cette récolte ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty recoltes}">
            <tr><td colspan="5" class="vide">Aucune récolte enregistrée.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
