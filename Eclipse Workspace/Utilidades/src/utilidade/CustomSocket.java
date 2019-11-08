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
}
