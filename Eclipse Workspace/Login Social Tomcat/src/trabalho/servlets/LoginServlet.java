package trabalho.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trabalho.UsuariosLogados;
import utilidade.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sessao = request.getSession(false);
		Usuario usuario = UsuariosLogados.getUsuarioBySessao(sessao);
		if (usuario != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("pesquisa.jsp");
			request.setAttribute("usuario", usuario);
			dispatcher.forward(request, response);
		} else {
			String erro = request.getParameter("erro");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			if (erro != null && !erro.isEmpty())
				request.setAttribute("erro", erro);
			dispatcher.forward(request, response);
		}
	}

}
