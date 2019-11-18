var googleUser = {};
var startGoogleOAuth2Api = function() {
	gapi.load('auth2', function() {
		auth2 = gapi.auth2.init({
			client_id : '868339504339-rdfafplk7b4f1vve4r25kihuqo97mege.apps.googleusercontent.com',
			cookiepolicy : 'single_host_origin',
		});
		attachSignin(document.getElementById('google'));
	});
};

function attachSignin(element) {
	auth2.attachClickHandler(element, {}, function(googleUser) {
		document.getElementById("sucesso-google-idtoken").value = googleUser.getAuthResponse().id_token;
		document.getElementById("sucesso-google").submit();
	}, function(error) {
		
	});
}

function signOutGoogle() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		auth2.disconnect();
	});
}