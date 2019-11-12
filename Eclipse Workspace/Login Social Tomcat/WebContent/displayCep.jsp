<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Logado como ${usuario.nome} </h1>
		
		
		<p> ${not empty cep1.erro ? cep1.erro : cep1} </p>
		
		<br/>
		<br/>
		
		<p> ${not empty cep2.erro ? cep2.erro : cep2} </p>
	</body>
</html>