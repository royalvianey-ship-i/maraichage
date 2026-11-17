<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Parcelle — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="_menu.jspf" %>

<main class="conteneur">
    <h1><c:choose><c:when test="${empty parcelle}">Nouvelle parcelle</c:when><c:otherwise>Modifier la parcelle</c:otherwise></c:choose></h1>

    <form class="formulaire" method="post"
          action="${pageContext.request.contextPath}/app/parcelles" accept-charset="UTF-8">
        <input type="hidden" name="id" value="${parcelle.id}">

        <label for="nom">Nom</label>
        <input id="nom" name="nom" type="text" required value="${parcelle.nom}">

        <label for="surface">Surface (m²)</label>
        <input id="surface" name="surface" type="number" step="0.01" min="0.01" required
               value="${parcelle.surface}">

        <label for="typeSol">Type de sol</label>
        <input id="typeSol" name="typeSol" type="text" value="${parcelle.typeSol}">

        <label for="localisation">Localisation</label>
        <input id="localisation" name="localisation" type="text" value="${parcelle.localisation}">

        <div class="actions-formulaire">
            <button type="submit" class="bouton bouton-vert">Enregistrer</button>
            <a class="bouton bouton-gris" href="${pageContext.request.contextPath}/app/parcelles">Annuler</a>
        </div>
    </form>
</main>
</body>
</html>
