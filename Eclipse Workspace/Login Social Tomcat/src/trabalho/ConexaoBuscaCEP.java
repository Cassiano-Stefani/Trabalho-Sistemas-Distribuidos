package trabalho;

import java.net.Socket;

import utilidade.CustomSocket;
import utilidade.EnderecoVirtual;

public class ConexaoBuscaCEP {

	private EnderecoVirtual endBuscaCep = new EnderecoVirtual("localhost", 5110);
	private CustomSocket socketBuscaCep;

	public ConexaoBuscaCEP() {
		try {
			Socket socket = new Socket(endBuscaCep.ip, endBuscaCep.porta);
			socketBuscaCep = new CustomSocket(socket);
		} catch (Exception e) {
			System.err.println("Erro ao abrir socket TCP para busca CEP");
			throw new RuntimeException(); // para JVM
		}
	}

	public CustomSocket getBuscaCEPSocket() {
		return socketBuscaCep;
	}

	// static

	private static ConexaoBuscaCEP instancia;

	public static synchronized ConexaoBuscaCEP getInstancia() {
		if (instancia == null) {
			instancia = new ConexaoBuscaCEP();
		}
		return instancia;
	}
}
