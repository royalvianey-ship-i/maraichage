<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Récolte — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <h1><c:choose><c:when test="${empty recolte}">Nouvelle récolte</c:when><c:otherwise>Modifier la récolte</c:otherwise></c:choose></h1>

    <form class="formulaire" method="post"
          action="${pageContext.request.contextPath}/app/recoltes" accept-charset="UTF-8">
        <input type="hidden" name="id" value="${recolte.id}">

        <label for="cultureId">Culture</label>
        <select id="cultureId" name="cultureId" required>
            <c:forEach var="c" items="${cultures}">
                <option value="${c.id}" ${c.id eq recolte.cultureId ? 'selected' : ''}>${c.nom} — ${c.parcelleNom}</option>
            </c:forEach>
        </select>

        <label for="dateRecolte">Date de récolte</label>
        <input id="dateRecolte" name="dateRecolte" type="date" required value="${recolte.dateRecolte}">

        <label for="quantite">Quantité</label>
        <input id="quantite" name="quantite" type="number" step="0.01" min="0.01" required
               value="${recolte.quantite}">

        <label for="unite">Unité</label>
        <select id="unite" name="unite">
            <option value="kg"     ${recolte.unite eq 'kg'     ? 'selected' : ''}>kg</option>
            <option value="g"      ${recolte.unite eq 'g'      ? 'selected' : ''}>g</option>
            <option value="tonne"  ${recolte.unite eq 'tonne'  ? 'selected' : ''}>tonne</option>
            <option value="unite"  ${recolte.unite eq 'unite'  ? 'selected' : ''}>unité</option>
        </select>

        <div class="actions-formulaire">
            <button type="submit" class="bouton bouton-vert">Enregistrer</button>
            <a class="bouton bouton-gris" href="${pageContext.request.contextPath}/app/recoltes">Annuler</a>
        </div>
    </form>
</main>
</body>
</html>
