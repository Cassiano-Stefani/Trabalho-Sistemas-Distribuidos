<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BuscaRotas</title>
	</head>
	<body>
		<h1>Logado como ${usuario.nome} </h1>
		
		
		<p> ${not empty erro1 ? erro1 : cep1} </p>
		
		<br/>
		<br/>
		
		<p> ${not empty erro2 ? erro2 : cep2} </p>
	</body>
</html>