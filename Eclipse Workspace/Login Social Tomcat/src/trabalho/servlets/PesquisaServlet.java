package trabalho.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trabalho.ConexaoBuscaCEP;
import trabalho.Usuario;
import trabalho.UsuariosLogados;
import utilidade.CEP;
import utilidade.CustomSocket;
import utilidade.CustomSocket.TipoMensagem;
import utilidade.Mensagem;

@WebServlet("/pesquisa-servlet")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("login.html");
		} else {
			Usuario usuario = UsuariosLogados.getUsuarioBySessao(session);

			if (usuario == null) {
				response.sendRedirect("login.html");
			} else {
				String cep1 = request.getParameter("cep1");
				String cep2 = request.getParameter("cep2");

				request.setAttribute("usuario", usuario);

				CustomSocket socketBuscaCEP = ConexaoBuscaCEP.getInstancia().getBuscaCEPSocket();

				try {
					// TODO incluir usuario na busca etc...
					socketBuscaCEP.enviarMensagem(new Mensagem(TipoMensagem.CEP_REQ.ordinal(), cep1));
					socketBuscaCEP.enviarMensagem(new Mensagem(TipoMensagem.CEP_REQ.ordinal(), cep2));
				} catch (Exception e) {
					System.out.println("Erro ao requisitar busca de cep");
				}

				try {
					Mensagem m1 = socketBuscaCEP.receberMensagem();
					if (m1.tipo == TipoMensagem.CEP.ordinal()) {
						CEP c1 = socketBuscaCEP.parseCEP(m1);
						request.setAttribute("cep1", c1);
					} else if (m1.tipo == TipoMensagem.CEP_ERRO.ordinal()) {
						CEP c1 = new CEP(m1.mensagem);
						request.setAttribute("cep1", c1);
					} else {
						System.out.println("Erro: mensagem recebida não era do tipo CEP");
					}

					Mensagem m2 = socketBuscaCEP.receberMensagem();
					if (m2.tipo == TipoMensagem.CEP.ordinal()) {
						CEP c2 = socketBuscaCEP.parseCEP(m2);
						request.setAttribute("cep2", c2);
					} else if (m2.tipo == TipoMensagem.CEP_ERRO.ordinal()) {
						CEP c2 = new CEP(m2.mensagem);
						request.setAttribute("cep2", c2);
					} else {
						System.out.println("Erro: mensagem recebida não era do tipo CEP");
					}
				} catch (Exception e) {
					System.out.println("Erro ao ler resposta do BuscaCEP");
				}

				RequestDispatcher disp = request.getRequestDispatcher("/displayCep.jsp");
				disp.forward(request, response);
			}
		}

	}

}
