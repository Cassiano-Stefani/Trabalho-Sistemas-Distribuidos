package utilidade;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class CustomSocket {
	private Socket socket;
	private DataOutputStream socketOut;
	private DataInputStream socketIn;

	public CustomSocket(Socket socket) throws Exception {
		this.socket = socket;

		try {
			this.socketOut = new DataOutputStream(this.socket.getOutputStream());
			this.socketIn = new DataInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar fluxos de dados no socket");
		}
	}

	public void enviarCEP(CEP cep) throws Exception {
		Mensagem m = new Mensagem();

		StringBuilder str = new StringBuilder();
		str.append("cep=" + escreverVazio(cep.cep) + "\n");
		str.append("logradouro=" + escreverVazio(cep.logradouro) + "\n");
		str.append("localidade=" + escreverVazio(cep.localidade) + "\n");
		str.append("uf=" + escreverVazio(cep.uf) + "\n");

		m.tipo = TipoMensagem.CEP.ordinal();
		m.mensagem = str.toString();
		enviarMensagem(m);
	}

	private String escreverVazio(String str) {
		if (str.trim().length() == 0)
			return "_";
		return str.trim();
	}

	public CEP parseCEP(Mensagem m) throws Exception {
		if (m.tipo != TipoMensagem.CEP.ordinal())
			throw new RuntimeException("Erro: tentando ler CEP em mensagem que não é um CEP");

		String[] linhas = m.mensagem.split("\n");

		if (linhas.length != 9)
			throw new RuntimeException("Erro: cep recebido com numero de atributos divergentes");

		CEP cep = new CEP();

		for (String linha : linhas) {
			String[] par = linha.split("=");
			if (par[0].equals("cep"))
				cep.cep = parseVazio(par[1]);
			else if (par[0].equals("logradouro"))
				cep.logradouro = parseVazio(par[1]);
			else if (par[0].equals("localidade"))
				cep.localidade = parseVazio(par[1]);
			else if (par[0].equals("uf"))
				cep.uf = parseVazio(par[1]);
			else
				throw new RuntimeException("Erro ao ler cep");
		}

		return cep;
	}

	private String parseVazio(String in) {
		if (in.trim().equals("_"))
			return "";
		return in.trim();
	}

	public void enviarMensagem(Mensagem mensagem) throws Exception {
		try {
			this.socketOut.writeInt(mensagem.tipo);
			this.socketOut.writeUTF(mensagem.mensagem);
			this.socketOut.flush();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao enviar mensagem");
		}
	}

	public Mensagem receberMensagem() throws Exception {
		try {
			Mensagem mensagem = new Mensagem();
			mensagem.tipo = this.socketIn.readInt();
			mensagem.mensagem = this.socketIn.readUTF();
			return mensagem;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao receber mensagem");
		}
	}

	public void fechar() throws Exception {
		if (this.socketOut != null)
			this.socketOut.close();

		if (this.socketIn != null)
			this.socketIn.close();

		this.socket.close();
	}

	public static enum TipoMensagem {
		CEP, CEP_REQ, CEP_ERRO
	}
}
