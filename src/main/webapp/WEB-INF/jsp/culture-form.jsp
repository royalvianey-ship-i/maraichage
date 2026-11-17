<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Culture — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <h1><c:choose><c:when test="${empty culture}">Nouvelle culture</c:when><c:otherwise>Modifier la culture</c:otherwise></c:choose></h1>

    <form class="formulaire" method="post"
          action="${pageContext.request.contextPath}/app/cultures" accept-charset="UTF-8">
        <input type="hidden" name="id" value="${culture.id}">

        <label for="parcelleId">Parcelle</label>
        <select id="parcelleId" name="parcelleId" required>
            <c:forEach var="p" items="${parcelles}">
                <option value="${p.id}" ${p.id eq culture.parcelleId ? 'selected' : ''}>${p.nom}</option>
            </c:forEach>
        </select>

        <label for="nom">Culture</label>
        <input id="nom" name="nom" type="text" required placeholder="Tomate, carotte…" value="${culture.nom}">

        <label for="dateSemis">Date de semis</label>
        <input id="dateSemis" name="dateSemis" type="date" value="${culture.dateSemis}">

        <label for="statut">Statut</label>
        <select id="statut" name="statut">
            <option value="PLANIFIEE" ${culture.statut eq 'PLANIFIEE' ? 'selected' : ''}>Planifiée</option>
            <option value="EN_COURS"  ${culture.statut eq 'EN_COURS'  ? 'selected' : ''}>En cours</option>
            <option value="RECOLTEE"  ${culture.statut eq 'RECOLTEE'  ? 'selected' : ''}>Récoltée</option>
            <option value="ABANDONNEE" ${culture.statut eq 'ABANDONNEE' ? 'selected' : ''}>Abandonnée</option>
        </select>

        <label for="dateRecoltePrevue">Récolte prévue</label>
        <input id="dateRecoltePrevue" name="dateRecoltePrevue" type="date" value="${culture.dateRecoltePrevue}">

        <div class="actions-formulaire">
            <button type="submit" class="bouton bouton-vert">Enregistrer</button>
            <a class="bouton bouton-gris" href="${pageContext.request.contextPath}/app/cultures">Annuler</a>
        </div>
    </form>
</main>
</body>
</html>
