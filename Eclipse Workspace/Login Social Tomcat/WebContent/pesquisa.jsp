<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BuscaRotas</title>
		<link rel="stylesheet" type="text/css" href="controle1.css">
		<link rel="stylesheet" type="text/css" href="normalize.css">
	</head>
	<body>
		
	
		<h1>Logado como ${usuario.nome} </h1>
		
		<div id="quadro1">
			<div id="caixa">
				<form method="GET" action="/buscacepapp/pesquisa-servlet">
					<fieldset>
						<legend>Digite as rotas</legend>
						<div id="icone">
							<img id="avatar" src="${usuario.imagem}"> </img>
						</div>
						<div id="ponto1">
							Origem <input type="text" name="cep1" placeholder="Digite ponto de inicio">
						</div>
						<div id="ponto2">
							Destino <input type="text" name="cep2" placeholder="Digite ponto de destino">
						</div>
						<div id="buscar">
							<input type="submit" value="Buscar">
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>