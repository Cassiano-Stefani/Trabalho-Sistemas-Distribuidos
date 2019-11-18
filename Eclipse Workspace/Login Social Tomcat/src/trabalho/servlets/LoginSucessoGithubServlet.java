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

@WebServlet("/login-sucesso-github")
public class LoginSucessoGithubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String client = "d27544783c62a3f8bf6b";
	private static final String secret = "090a13e11b85612877367fa86adbdaad89396a8e";

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String access_token = getAccessToken(request.getParameter("code"));

		if (access_token != null) {
			JSONObject parsedToken = parseToken(access_token);

			if (parsedToken != null) {
				String nome = null;
				try {
					nome = parsedToken.getString("name"); // nome pode ser null
				} catch (Exception e) {
					nome = parsedToken.getString("login"); // usaremos login caso nome nao foi definido pelo usuario
				}

				String imagem = "";
				try {
					imagem = parsedToken.getString("avatar_url");
				} catch (Exception e) {
					System.out.println("Imagem do usuario " + nome + " não foi encontrada!");
				}

				Usuario usuario = new Usuario("github", parsedToken.getInt("id") + "", nome, imagem);

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
			} else {
				System.out.println("Erro ao validar token no Github");
				response.sendRedirect("/buscacepapp/login.html");
			}
		} else {
			System.out.println("Erro ao requisitar token de acesso ao Github");
			response.sendRedirect("/buscacepapp/login.html");
		}
	}

	private String getAccessToken(String token) {
		System.out.println("Requisitando token de acesso ao Github...");
		HttpURLConnection con;
		try {
			URL url = new URL("https://github.com/login/oauth/access_token?client_id=" + client + "&client_secret="
					+ secret + "&code=" + token);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");
		} catch (Exception e) {
			System.out.println("Erro ao pedir token de acesso ao Github");
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

			return new JSONObject(resposta.toString()).getString("access_token");
		} catch (Exception e) {
			System.out.println("Erro ao ler resposta do token de acesso do Github");
			return null;
		}
	}

	private JSONObject parseToken(String access_token) {
		System.out.println("Validando token no Github...");
		HttpURLConnection con;
		try {
			URL url = new URL("https://api.github.com/user");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "token " + access_token);
			con.setRequestProperty("Accept", "application/json");
		} catch (Exception e) {
			System.out.println("Erro ao mandar token para validação do Github");
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
			System.out.println("Erro ao ler resposta do token do Github");
			return null;
		}
	}
}
