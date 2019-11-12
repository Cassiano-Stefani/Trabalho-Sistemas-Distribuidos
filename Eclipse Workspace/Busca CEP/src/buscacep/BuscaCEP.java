package buscacep;

import java.net.ServerSocket;

import utilidade.CEP;
import utilidade.CustomSocket;
import utilidade.CustomSocket.TipoMensagem;
import utilidade.EnderecoVirtual;
import utilidade.Mensagem;

public class BuscaCEP {

	public static EnderecoVirtual endBuscaCEP = new EnderecoVirtual("localhost", 5110);
	public static ServerSocket socketServidorBuscaCEP;

	public static EnderecoVirtual endBancoDeDados = new EnderecoVirtual("localhost", 5111);
	public static CustomSocket socketBancoDeDados;

	public static CustomSocket socketFrontEnd;

	public static void main(String[] args) {
		try {
			socketServidorBuscaCEP = new ServerSocket(endBuscaCEP.porta);
		} catch (Exception e) {
			System.out.println("Erro ao abrir servidor de Busca CEP");
			return;
		}

//		try {
//			socketBancoDeDados = new CustomSocket(new Socket(endBancoDeDados.ip, endBancoDeDados.porta));
//			
//		} catch (Exception e) {
//			System.out.println("Erro ao conectar com Banco de Dados");
//		}

		try {
			socketFrontEnd = new CustomSocket(socketServidorBuscaCEP.accept());
		} catch (Exception e) {
			System.out.println("Erro ao receber conexões do FrontEnd");
			return;
		}

		while (true) {
			try {
				Mensagem m = socketFrontEnd.receberMensagem();
				if (m.tipo == TipoMensagem.CEP_REQ.ordinal()) {
					String c = m.mensagem.trim();
					CEP cep = BuscadorCEP.buscarCEP(c);
					if (cep.erro == null || cep.erro.isEmpty()) {
						socketFrontEnd.enviarCEP(cep);
					} else {
						System.out.println(cep.erro);
						socketFrontEnd.enviarMensagem(new Mensagem(TipoMensagem.CEP_ERRO.ordinal(), cep.erro));
					}
				}

			} catch (Exception e) {
				System.out.println("Erro ao lidar com CEPs vindo do FrontEnd");
			}
		}
	}
}
