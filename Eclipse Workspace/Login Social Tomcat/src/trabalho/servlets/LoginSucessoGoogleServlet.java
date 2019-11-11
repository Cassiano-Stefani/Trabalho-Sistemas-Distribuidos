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

import org.json.JSONObject;

import trabalho.Usuario;

@WebServlet("/login-sucesso-google")
public class LoginSucessoGoogleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("idtoken");

		JSONObject pt = parseToken(token);
		// salvar secao e registrar usuario, mandar redirect para outro servlet
		if(pt.has("erro"))
			System.out.println("ERRRRRO ");
		else {
			Usuario u = new Usuario("google", pt.getString("sub"), pt.getString("name"));
			request.setAttribute("usuario", u);
			RequestDispatcher rd = request.getRequestDispatcher("/pesquisa.jsp");
			rd.forward(request, response);
		}
	}

	private JSONObject parseToken(String token) {
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
