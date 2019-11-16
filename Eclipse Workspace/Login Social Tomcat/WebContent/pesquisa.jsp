<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="ISO-8859-1">
		<title>Pesquisa CEP</title>
	</head>
	<body>
		<img src="${usuario.imagem}"> </img>
	
		<h1>Logado como ${usuario.nome} </h1>
		
		
		<form method="GET" action="/buscacepapp/pesquisa-servlet">
			CEP 1: <input type="text" name="cep1"> <br/>
			CEP 2: <input type="text" name="cep2"> <br/>
			<input type="submit" value="Pesquisar">
		</form>
	</body>
</html>