<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BuscaRotas</title>
		<script src="https://apis.google.com/js/api:client.js"></script>
		<script type="text/javascript" src="google.js"></script>
		<link rel="stylesheet" type="text/css" href="controle.css">
	</head>
	
	<body>
		<form id="sucesso-google" style="display:none;" method="POST" action="/buscacepapp/login-sucesso-google">
			<input id="sucesso-google-idtoken" style="display:none;" type="text" name="idtoken">
		</form>
	
		<div id="quadro">
			<div id="nome">
				<h1> BuscaRotas</h1>
			</div>
			<div id="caixa">
				<fieldset>
					<legend>Entrar com:</legend>
					<div id="google" class="customGPlusSignIn"><img src="google.svg"></div>
					<div id="github"><a href="https://github.com/login/oauth/authorize?client_id=d27544783c62a3f8bf6b&redirect_uri=http://localhost:8080/buscacepapp/login-sucesso-github"><img src="github.png"></a></div>
				</fieldset>
				<span id="erro">${not empty erro ? 'Algum erro ocorreu ou a sessão expirou. Entre novamente!' : ''}</span>
			</div>
		</div>
			
		<script>startGoogleOAuth2Api();</script>
	</body>
</html>