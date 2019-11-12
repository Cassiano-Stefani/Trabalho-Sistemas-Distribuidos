<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<c:if test="${not empty usuario.nome and usuario.apiUsada eq 'google'}">
			<script src="https://apis.google.com/js/platform.js"></script>
			<meta name="google-signin-client_id" content="868339504339-rdfafplk7b4f1vve4r25kihuqo97mege.apps.googleusercontent.com">
		</c:if>
		
		<meta charset="ISO-8859-1">
		<title>Pesquisa CEP</title>
	</head>
	<body>
		<h1>Logado como ${usuario.nome} </h1>
		
		<a href="#" onclick="signOut();">Sign out</a>
		<c:if test="${not empty usuario.nome and usuario.apiUsada eq 'google'}">
			<script>
				if (!gapi.auth2) {
					gapi.load('auth2', function() {
						gapi.auth2.init();
					});
				}

				function signOut() {
					var auth2 = gapi.auth2.getAuthInstance();
					auth2.signOut().then(function() {
						auth2.disconnect();
					});
				}
			</script>
		</c:if>
		
		<form method="GET" action="/buscacepapp/pesquisa-servlet">
			CEP 1: <input type="text" name="cep1"> <br/>
			CEP 2: <input type="text" name="cep2"> <br/>
			<input type="submit" value="Pesquisar">
		</form>
	</body>
</html>