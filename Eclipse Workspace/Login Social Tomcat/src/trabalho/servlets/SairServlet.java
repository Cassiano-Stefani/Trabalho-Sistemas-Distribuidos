package trabalho.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trabalho.UsuariosLogados;
import utilidade.Usuario;

@WebServlet("/deslogar")
public class SairServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuario = UsuariosLogados.getUsuarioBySessao(request.getSession(false));
		if (usuario == null) {
			System.out.println("Usuario requisitou sair sem estar logado o.O");
		} else {
			System.out.println("Deslogando usuario " + usuario.getNome());
			UsuariosLogados.deslogarUsuario(usuario);
			System.out.println("Usuario deslogado");
		}
		response.sendRedirect("/buscacepapp/login");
	}
}
