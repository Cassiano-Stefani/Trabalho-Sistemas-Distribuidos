package utilidade;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

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

	private String escreverVazio(String str) {
		if (str.trim().length() == 0)
			return "_";
		return str.trim();
	}

	private String parseVazio(String in) {
		if (in.trim().equals("_"))
			return "";
		return in.trim();
	}

	private String cepToString(CEP cep) throws Exception {
		StringBuilder str = new StringBuilder();
		str.append("cep=" + escreverVazio(cep.cep) + "\n");
		str.append("logradouro=" + escreverVazio(cep.logradouro) + "\n");
		str.append("localidade=" + escreverVazio(cep.localidade) + "\n");
		str.append("uf=" + escreverVazio(cep.uf));
		return str.toString();
	}

	private CEP cepFromString(String str) throws Exception {
		String[] linhas = str.split("\n");
		if (linhas.length != 4)
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

	private String userToString(Usuario usuario) throws Exception {
		StringBuilder str = new StringBuilder();
		str.append("apiUsada=" + escreverVazio(usuario.getApiUsada()) + "\n");
		str.append("id=" + escreverVazio(usuario.getId()) + "\n");
		str.append("nome=" + escreverVazio(usuario.getNome()) + "\n");
		str.append("imagem=" + escreverVazio(usuario.getImagem()));
		return str.toString();
	}

	private Usuario userFromString(String str) throws Exception {
		String[] linhas = str.split("\n");
		if (linhas.length != 4)
			throw new RuntimeException("Erro: usuario recebido com numero de atributos divergentes");
		Usuario usuario = new Usuario();
		for (String linha : linhas) {
			String[] par = linha.split("=");
			if (par[0].equals("apiUsada"))
				usuario.setApiUsada(parseVazio(par[1]));
			else if (par[0].equals("id"))
				usuario.setId(parseVazio(par[1]));
			else if (par[0].equals("nome"))
				usuario.setNome(parseVazio(par[1]));
			else if (par[0].equals("imagem"))
				usuario.setImagem(parseVazio(par[1]));
			else
				throw new RuntimeException("Erro ao ler usuario");
		}
		return usuario;
	}

	public void enviarUsuario(Usuario user) throws Exception {
		System.out.println("Montando mensagem a partir de Usuario...");
		enviarMensagem(new Mensagem(TipoMensagem.USUARIO.ordinal(), userToString(user)));
	}

	public Usuario parseUsuario(Mensagem m) throws Exception {
		System.out.println("Transformando mensagem recebida em Usuario...");
		if (m.tipo != TipoMensagem.USUARIO.ordinal())
			throw new RuntimeException("Erro: tentando ler Usuario em mensagem que não é um Usuario");
		return userFromString(m.mensagem);
	}

	public void enviarCEP(CEP cep) throws Exception {
		System.out.println("Montando mensagem a partir de CEP...");
		enviarMensagem(new Mensagem(TipoMensagem.CEP.ordinal(), cepToString(cep)));
	}

	public CEP parseCEP(Mensagem m) throws Exception {
		System.out.println("Transformando mensagem recebida em CEP...");
		if (m.tipo != TipoMensagem.CEP.ordinal())
			throw new RuntimeException("Erro: tentando ler CEP em mensagem que não é um CEP");
		return cepFromString(m.mensagem);
	}

	public void enviarPesquisa(Pesquisa pesquisa) throws Exception {
		System.out.println("Montando mensagem de pesquisa...");

		String u = userToString(pesquisa.getUsuario());
		String o = cepToString(pesquisa.getOrigem());
		String d = cepToString(pesquisa.getDestino());

		enviarMensagem(new Mensagem(TipoMensagem.PESQUISA.ordinal(), u + "|_|" + o + "|_|" + d));
	}

	public Pesquisa parsePesquisa(Mensagem m) throws Exception {
		System.out.println("Transformando mensagem recebida em Pesquisa...");

		if (m.tipo != TipoMensagem.PESQUISA.ordinal())
			throw new RuntimeException("Erro: tentando ler Pesquisa em mensagem que não é uma Pesquisa");
		String[] partes = m.mensagem.split(Pattern.quote("|_|"));

		if (partes.length != 3)
			throw new RuntimeException("Erro: pesquisa recebida com numero de atributos divergentes");

		Usuario u = userFromString(partes[0]);
		CEP o = cepFromString(partes[1]);
		CEP d = cepFromString(partes[2]);
		return new Pesquisa(u, o, d);
	}

	public void enviarCepReq(Usuario user, String o, String d) throws Exception {
		System.out.println("Montando mensagem de requisicao pesquisa...");

		String u = userToString(user);

		enviarMensagem(new Mensagem(TipoMensagem.CEP_REQ.ordinal(), u + "|_|" + o + "|_|" + d));
	}

	public Pesquisa parseCepReq(Mensagem m) throws Exception {
		System.out.println("Transformando mensagem recebida em requisicao de Pesquisa...");

		if (m.tipo != TipoMensagem.CEP_REQ.ordinal())
			throw new RuntimeException("Erro: tentando ler Pesquisa em mensagem que não é uma requisicao Pesquisa");
		String[] partes = m.mensagem.split(Pattern.quote("|_|"));

		if (partes.length != 3)
			throw new RuntimeException("Erro: requisicao de pesquisa recebida com numero de atributos divergentes");

		Usuario u = userFromString(partes[0]);
		CEP o = new CEP();
		o.setCep(partes[1]);
		CEP d = new CEP();
		d.setCep(partes[2]); // preenche apenas o cep, ja que sera usado para busca
		return new Pesquisa(u, o, d);
	}

	public void enviarMensagem(Mensagem mensagem) throws Exception {
		System.out.println("Enviando mensagem...");
		try {
			this.socketOut.writeInt(mensagem.tipo);
			this.socketOut.writeUTF(mensagem.mensagem);
			this.socketOut.flush();
			System.out.println("Mensagem enviada");
		} catch (Exception e) {
			throw new RuntimeException("Erro ao enviar mensagem");
		}
	}

	public Mensagem receberMensagem() throws Exception {
		System.out.println("Recebendo mensagem..");
		try {
			Mensagem mensagem = new Mensagem();
			mensagem.tipo = this.socketIn.readInt();
			mensagem.mensagem = this.socketIn.readUTF();
			System.out.println("Mensagem recebida");
			return mensagem;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao receber mensagem");
		}
	}

	public void fechar() throws Exception {
		System.out.println("Fechando socket...");
		if (this.socketOut != null)
			this.socketOut.close();

		if (this.socketIn != null)
			this.socketIn.close();

		this.socket.close();
		System.out.println("Socket fechado");
	}

	public static enum TipoMensagem {
		CEP_REQ, CEP, PESQUISA, USUARIO
	}
}
