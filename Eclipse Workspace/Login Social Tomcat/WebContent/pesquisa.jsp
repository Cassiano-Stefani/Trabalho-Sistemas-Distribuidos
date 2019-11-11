<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Pesquisa CEP</title>
		
		<c:if test="${not empty usuario and usuario.apiUsada eq 'google'}">
			<script src="https://apis.google.com/js/platform.js" async defer></script>
			<meta name="google-signin-client_id" content="868339504339-rdfafplk7b4f1vve4r25kihuqo97mege.apps.googleusercontent.com">
		</c:if>
		
		<c:if test="${not empty usuario and usuario.apiUsada eq 'google'}">
			<script>
				function signOut() {
					var auth2 = gapi.auth2.getAuthInstance();
					auth2.signOut().then(function () {
						console.log('User signed out.');
					});
				}
			</script>
		</c:if>
	</head>
	<body>
		<h1>Logado como ${usuario.nome} </h1>
		<a href="#" onclick="signOut();">Sign out</a>
		
		<form method="GET" action="/buscacepapp/pesquisa-servlet">
			CEP 1: <input type="text" name="cep1"> <br/>
			CEP 2: <input type="text" name="cep2"> <br/>
			<input type="submit" value="Pesquisar">
		</form>
	</body>
</html>