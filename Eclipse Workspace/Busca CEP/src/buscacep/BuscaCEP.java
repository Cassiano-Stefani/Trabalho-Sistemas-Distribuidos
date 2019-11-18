package buscacep;

import java.net.ServerSocket;
import java.net.Socket;

import utilidade.CEP;
import utilidade.CustomSocket;
import utilidade.CustomSocket.TipoMensagem;
import utilidade.EnderecoVirtual;
import utilidade.Mensagem;
import utilidade.Pesquisa;

public class BuscaCEP {

	public static EnderecoVirtual endBuscaCEP = new EnderecoVirtual("localhost", 5110);
	public static ServerSocket socketServidorBuscaCEP;

	public static EnderecoVirtual endBancoDeDados = new EnderecoVirtual("localhost", 5111);
	public static CustomSocket socketBancoDeDados;

	public static CustomSocket socketFrontEnd;

	public static void main(String[] args) {
		try {
			socketServidorBuscaCEP = new ServerSocket(endBuscaCEP.porta);
			System.out.println("Conexao aberta");
		} catch (Exception e) {
			System.out.println("Erro ao abrir servidor de Busca CEP. Terminando aplicação!");
			return;
		}

		try {
			System.out.println("Conectando com BancoDeDados");
			socketBancoDeDados = new CustomSocket(new Socket(endBancoDeDados.ip, endBancoDeDados.porta));
			System.out.println("BancoDeDados Conectado");
		} catch (Exception e) {
			System.out.println("Erro ao conectar com Banco de Dados. Terminando aplicação!");
			return;
		}

		try {
			System.out.println("Esperando FrontEnd");
			socketFrontEnd = new CustomSocket(socketServidorBuscaCEP.accept());
			System.out.println("FrontEnd conectado");
		} catch (Exception e) {
			System.out.println("Erro ao receber conexões do FrontEnd. Terminando aplicação!");
			return;
		}

		while (true) {
			try {
				Mensagem m = socketFrontEnd.receberMensagem();
				if (m.tipo == TipoMensagem.CEP_REQ.ordinal()) {
					Pesquisa pesquisa = socketFrontEnd.parseCepReq(m);

					String c1 = pesquisa.getOrigem().getCep();
					String c2 = pesquisa.getDestino().getCep();

					boolean erro = false;
					CEP cep1 = BuscadorCEP.buscarCEP(c1);
					if (cep1.erro != null && !cep1.erro.isEmpty()) {
						System.out.println(cep1.erro);
						erro = true;
					}

					CEP cep2 = BuscadorCEP.buscarCEP(c2);
					if (cep2.erro != null && !cep2.erro.isEmpty()) {
						System.out.println(cep2.erro);
						erro = true;
					}

					socketFrontEnd.enviarPesquisa(new Pesquisa(pesquisa.getUsuario(), cep1, cep2)); // deixar o front
																									// tratar os erros

					if (!erro) { // salva no banco apenas se nao deu erro na busca
						socketBancoDeDados.enviarPesquisa(new Pesquisa(pesquisa.getUsuario(), cep1, cep2));
					}
				}

			} catch (Exception e) {
				if (e instanceof RuntimeException)
					System.out.println(e);
				else
					System.out.println("Erro ao lidar com CEPs vindo do FrontEnd");
			}
		}
	}
}
