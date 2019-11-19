package trabalho.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import trabalho.UsuariosLogados;
import utilidade.Usuario;

@WebServlet("/login-sucesso-google")
public class LoginSucessoGoogleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject parsedToken = parseToken(request.getParameter("idtoken"));

		if (parsedToken.has("erro")) {
			System.out.println("Erro ao validar token na Google");
			response.sendRedirect("/buscacepapp/login?erro=true");
		} else {
			Usuario usuario = new Usuario("google", parsedToken.getString("sub"), parsedToken.getString("name"),
					parsedToken.getString("picture"));

			HttpSession oldSession = request.getSession(false);
			if (oldSession != null) {
				System.out.println("Deslogando usuario...");
				oldSession.invalidate();
				UsuariosLogados.deslogarUsuario(usuario);
			}

			System.out.println("Logando usuario " + usuario.getNome());

			// generate a new session
			HttpSession newSession = request.getSession(true);
			UsuariosLogados.logarUsuario(usuario, newSession);

			// setting session to expiry in 5 mins
			newSession.setMaxInactiveInterval(60 * 60 * 24);

			RequestDispatcher dispatcher = request.getRequestDispatcher("pesquisa.jsp");
			request.setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		}
	}

	private JSONObject parseToken(String token) {
		System.out.println("Validando token na Google...");
		HttpURLConnection con;
		try {
			URL url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + token);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		} catch (Exception e) {
			System.out.println("Erro ao mandar token para validação do Google");
			return null;
		}

		try {
			// resposta
			StringBuffer resposta = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String linha;
			while ((linha = in.readLine()) != null)
				resposta.append(linha);
			in.close();
			con.disconnect();

			return new JSONObject(resposta.toString());
		} catch (Exception e) {
			System.out.println("Erro ao ler resposta do token do Google");
			return null;
		}
	}
}
