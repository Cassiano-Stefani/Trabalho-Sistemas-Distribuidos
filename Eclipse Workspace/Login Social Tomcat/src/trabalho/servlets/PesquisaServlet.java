package trabalho.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trabalho.ConexaoBuscaCEP;
import trabalho.UsuariosLogados;
import utilidade.CEP;
import utilidade.CustomSocket;
import utilidade.CustomSocket.TipoMensagem;
import utilidade.Mensagem;
import utilidade.Pesquisa;
import utilidade.Usuario;

@WebServlet("/pesquisa-servlet")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Usuario usuario = UsuariosLogados.getUsuarioBySessao(request.getSession(false));

		if (usuario == null) {
			response.sendRedirect("/buscacepapp/login?erro=true");
		} else {
			String cep1 = request.getParameter("cep1");
			String cep2 = request.getParameter("cep2");

			request.setAttribute("usuario", usuario);

			CustomSocket socketBuscaCEP = ConexaoBuscaCEP.getInstancia().getBuscaCEPSocket();

			if (cep1.isEmpty() && cep2.isEmpty()) {
				request.setAttribute("erro1", "Erro: CEP Vazio");
				request.setAttribute("erro2", "Erro: CEP Vazio");
			} else if (cep1.isEmpty()) {
				request.setAttribute("erro1", "Erro: CEP Vazio");
				request.setAttribute("erro2", "Erro: CEP Origem Vazio");
			} else if (cep2.isEmpty()) {
				request.setAttribute("erro1", "Erro: CEP Destino Vazio");
				request.setAttribute("erro2", "Erro: CEP Vazio");
			} else {
				try {
					socketBuscaCEP.enviarCepReq(usuario, cep1, cep2);
				} catch (Exception e) {
					System.out.println("Erro ao requisitar busca de cep");
				}

				try {
					Mensagem m = socketBuscaCEP.receberMensagem();
					if (m.tipo == TipoMensagem.PESQUISA.ordinal()) {
						Pesquisa p = socketBuscaCEP.parsePesquisa(m);
						CEP c1 = p.getOrigem();
						CEP c2 = p.getDestino();
						
						request.setAttribute("cep1", c1);
						request.setAttribute("cep2", c2);
					} else {
						System.out.println("Erro: mensagem recebida n�o era do tipo CEP");
					}
				} catch (Exception e) {
					System.out.println("Erro ao ler resposta do BuscaCEP");
				}
			}

			RequestDispatcher disp = request.getRequestDispatcher("/displayBusca.jsp");
			disp.forward(request, response);
		}
	}

}
