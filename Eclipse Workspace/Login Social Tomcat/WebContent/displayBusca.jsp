<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BuscaRotas</title>
		<link rel="stylesheet" type="text/css" href="controle2.css">
	</head>
	<body>
		<div id="quadro">
			<div id="user">
				<img src="${usuario.imagem}"></img>
				<span id="nome">${usuario.nome}</span>
				<span id="list">
					<a id="pesq" href="/buscacepapp/pesquisar-novamente">Pesquisar Novamente</a>
					<a id="sair" href="/buscacepapp/deslogar">Sair</a>
				</span>
			</div>
			<div id="info">
				<h4>Resultados da busca:</h4> 
				<ul>
					<c:set var="origem" value="${cep1}"></c:set>
					<c:if test="${not empty cep1.erro}">
						<c:set var="origem" value="<span style='color: red;'>${cep1.erro}</span>"></c:set>
					</c:if>
					
					<c:set var="destino" value="${cep2}"></c:set>
					<c:if test="${not empty cep2.erro}">
						<c:set var="destino" value="<span style='color: red;'>${cep2.erro}</span>"></c:set>
					</c:if>
				
					<li>Origem: ${origem}</li>
					<li>Destino: ${destino}</li>
				</ul>
				
			</div>
			<div id="map"></div>
		</div>

	<c:choose>
		<c:when test="${not empty cep1.erro and empty cep2.erro}">
			<script type="text/javascript">
				function initMap() {
					var geocoder = new google.maps.Geocoder();
	  				var map = new google.maps.Map(document.getElementById('map'), {zoom: 4, center: {lat: -14.2350, lng: -51.9253}});
	  				
			  		geocoder.geocode( { 'address': '${cep2}'}, function(results, status) {
					    if (status == 'OK') {
							map.setCenter(results[0].geometry.location);
							var marker = new google.maps.Marker({
								map: map,
								position: results[0].geometry.location
							});
					    }
					});
				}
			</script>
		</c:when>
		<c:when test="${empty cep1.erro and not empty cep2.erro}">
			<script type="text/javascript">
				function initMap() {
					var geocoder = new google.maps.Geocoder();
	  				var map = new google.maps.Map(document.getElementById('map'), {zoom: 4, center: {lat: -14.2350, lng: -51.9253}});
	  				
	  				geocoder.geocode( { 'address': '${cep1}'}, function(results, status) {
					    if (status == 'OK') {
							map.setCenter(results[0].geometry.location);
							var marker = new google.maps.Marker({
								map: map,
								position: results[0].geometry.location
							});
					    }
					});
				}
			</script>
		</c:when>
		<c:when test="${not empty cep1.erro and not empty cep2.erro}">
			<script type="text/javascript">
				function initMap() {
	  				var map = new google.maps.Map(document.getElementById('map'), {zoom: 4, center: {lat: -14.2350, lng: -51.9253}});
				}
			</script>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				function initMap() {
					var geocoder = new google.maps.Geocoder();
	  				var directionsService = new google.maps.DirectionsService();
	  				var directionsRenderer = new google.maps.DirectionsRenderer();
					var map = new google.maps.Map(document.getElementById('map'), {
	   					zoom: 4,
	    				center: {lat: -14.2350, lng: -51.9253}
	  				});
					directionsRenderer.setMap(map);
					
				  	var request = {
				    	origin: '${cep1}',
				    	destination: '${cep2}',
				    	travelMode: 'DRIVING'
				  	};
				  	directionsService.route(request, function(result, status) {
				    	if (status == 'OK') {
				      		directionsRenderer.setDirections(result);
				    	}
				  	});
				  	
				  	geocoder.geocode( { 'address': '${cep1}'}, function(results, status) {
					    if (status == 'OK') {
							map.setCenter(results[0].geometry.location);
							var marker = new google.maps.Marker({
								map: map,
								position: results[0].geometry.location
							});
					    }
					});
				}
			</script>
		</c:otherwise>
	</c:choose>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBBZ1BlmXorJsZrAi2zMJ-sp6ZsPyLkdLU&callback=initMap">
	</script>
</body>
</html>