package utilidade;

public class Usuario {
	private String apiUsada;
	private String id;
	private String nome;
	private String imagem;

	public Usuario() {

	}

	public Usuario(String apiUsada, String id, String nome, String imagem) {
		this.apiUsada = apiUsada;
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
	}

	public String getApiUsada() {
		return apiUsada;
	}

	public void setApiUsada(String apiUsada) {
		this.apiUsada = apiUsada;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return "[apiUsada=" + apiUsada + "\n id=" + id + "\n nome=" + nome + "\n imagem=" + imagem + "\n]";
	}

}
