package trabalho;

import java.net.InetSocketAddress;
import java.net.Socket;

import utilidade.CustomSocket;
import utilidade.EnderecoVirtual;

public class ConexaoBuscaCEP {

	private EnderecoVirtual endBuscaCep = new EnderecoVirtual("localhost", 5110);
	private CustomSocket socketBuscaCep;

	public ConexaoBuscaCEP() {
		try {
			Socket socket = new Socket();

			while (true) {
				try {
					socket.connect(new InetSocketAddress(endBuscaCep.ip, endBuscaCep.porta), 2000);
					socketBuscaCep = new CustomSocket(socket);
					break;
				} catch (Exception e) {
					System.out.println("Re-enviando solicitação de conexão para BuscaCEP");

					Thread.sleep(1000);
				}
			}

			socketBuscaCep = new CustomSocket(socket);
		} catch (Exception e) {
			System.err.println("Erro ao abrir socket TCP do login social");
			throw new RuntimeException(); // para JVM
		}
	}

	public CustomSocket getBuscaCEPSocket() {
		return socketBuscaCep;
	}

	// static

	private static ConexaoBuscaCEP instancia;

	private static synchronized void setInstancia(ConexaoBuscaCEP inst) {
		instancia = inst;
	}

	public static synchronized ConexaoBuscaCEP getInstancia() {
		if (instancia == null) {
			Thread conexaoBuscaCepThread = new Thread(new Runnable() {
				@Override
				public void run() {
					setInstancia(new ConexaoBuscaCEP());
				}
			});
			conexaoBuscaCepThread.setDaemon(true);
			conexaoBuscaCepThread.start();
		}
		return instancia;
	}
}
