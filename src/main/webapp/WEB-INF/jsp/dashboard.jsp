<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tableau de bord — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <h1>Tableau de bord</h1>

    <section class="cartes">
        <div class="carte">
            <span class="valeur">${nbParcelles}</span>
            <span class="libelle">Parcelles</span>
        </div>
        <div class="carte">
            <span class="valeur">${nbCultures}</span>
            <span class="libelle">Cultures actives</span>
        </div>
        <div class="carte">
            <span class="valeur">${totalRecoltes} kg</span>
            <span class="libelle">Récoltes du mois</span>
        </div>
        <div class="carte">
            <span class="valeur">${fn:length(interventions)}</span>
            <span class="libelle">Interventions à venir</span>
        </div>
    </section>

    <h2>Prochaines interventions</h2>
    <table class="tableau">
        <thead>
        <tr><th>Date</th><th>Type</th><th>Parcelle</th></tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${interventions}">
            <tr>
                <td>${i.datePrevue}</td>
                <td>${i.type}</td>
                <td>${i.parcelleNom}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty interventions}">
            <tr><td colspan="3" class="vide">Aucune intervention planifiée.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
