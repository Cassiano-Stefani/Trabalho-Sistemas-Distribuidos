package trabalho.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trabalho.UsuariosLogados;
import utilidade.Usuario;

@WebServlet("/pesquisar-novamente")
public class PesquisarNovamenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuario = UsuariosLogados.getUsuarioBySessao(request.getSession(false));

		if (usuario == null) {
			response.sendRedirect("/buscacepapp/login?erro=true");
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("pesquisa.jsp");
			request.setAttribute("usuario", usuario);
			dispatcher.forward(request, response);
		}
	}
}
