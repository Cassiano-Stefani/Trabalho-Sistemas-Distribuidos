package utilidade;

public class Pesquisa {
	private Usuario usuario;
	private CEP origem;
	private CEP destino;

	public Pesquisa() {

	}

	public Pesquisa(Usuario usuario, CEP origem, CEP destino) {
		this.usuario = usuario;
		this.origem = origem;
		this.destino = destino;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CEP getOrigem() {
		return origem;
	}

	public void setOrigem(CEP origem) {
		this.origem = origem;
	}

	public CEP getDestino() {
		return destino;
	}

	public void setDestino(CEP destino) {
		this.destino = destino;
	}
}
