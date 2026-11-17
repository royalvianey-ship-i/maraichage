<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion — Gestion maraîchère</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page-connexion">
<main class="carte-connexion">
    <h1>Gestion maraîchère</h1>
    <p class="sous-titre">Connectez-vous pour accéder au tableau de bord</p>

    <c:if test="${not empty erreur}">
        <div class="alerte">${erreur}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login" accept-charset="UTF-8">
        <label for="login">Identifiant</label>
        <input id="login" name="login" type="text" required autofocus
               autocomplete="username" value="${loginSaisi}">

        <label for="motdepasse">Mot de passe</label>
        <input id="motdepasse" name="motdepasse" type="password" required
               autocomplete="current-password">

        <button type="submit" class="bouton bouton-vert">Se connecter</button>
    </form>
</main>
</body>
</html>
