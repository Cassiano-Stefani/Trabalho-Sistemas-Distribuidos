<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BuscaRotas</title>
		<link rel="stylesheet" type="text/css" href="controle1.css">
	</head>
	<body>
		<div id="quadro1">
			<div id="caixa">
				<form method="GET" action="/buscacepapp/pesquisa-servlet">
					<fieldset>
						<legend>Digite as rotas</legend>
						<div id="icone">
							<img id="avatar" src="${usuario.imagem}"> </img>
						</div>
						<div id="nome">
							${usuario.nome}
						</div>
						<div id="sair">
							<a href="/buscacepapp/deslogar">Sair</a>
						</div>
						<div id="ponto1">
							Origem <input id="cep1" type="text" name="cep1" placeholder="Digite CEP inicial">
						</div>
						<div id="ponto2">
							Destino <input id="cep2" type="text" name="cep2" placeholder="Digite CEP final">
						</div>
						<div id="buscarDiv">
							<span id="buscar"> Buscar </span>
							<input id="sub" style="display:none;" type="submit">
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		
		<script>
			document.getElementById("buscar").addEventListener("click", function (e) {
				var cep1 = document.getElementById("cep1");
				var cep2 = document.getElementById("cep2");
				
				var erro = false;
				
				if (cep1.value.length == 0 || isNaN(cep1.value.replace("-", ""))) {
					cep1.classList.add("erro");
					erro = true;
				} else {
					cep1.classList.remove("erro");
				}
				
				if (cep2.value.length == 0 ||  isNaN(cep2.value.replace("-", ""))) {
					cep2.classList.add("erro");
					erro = true;
				} else {
					cep2.classList.remove("erro");
				}
				
				if (!erro) {
					document.getElementById("sub").click(); // submitar a pesquisa					
				}
			});
		</script>
	</body>
</html>