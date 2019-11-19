package utilidade;

/**
 * Representa a resposta dada pela API do ViaCEP
 */
public class CEP {
	public String cep;
	public String logradouro; // rua
	public String localidade; // cidade
	public String uf;

	public String erro;

	public CEP() {

	}

	public CEP(String cep, String logradouro, String localidade, String uf) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.localidade = localidade;
		this.uf = uf;
	}

	public CEP(String erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		if (isNotEmpty(erro)) {
			return erro;
		} else {
			String cepStr = cep;

			if (isNotEmpty(logradouro))
				cepStr += ", " + logradouro;

			if (isNotEmpty(localidade))
				cepStr += ", " + localidade;

			if (isNotEmpty(uf))
				cepStr += ", " + uf;

			return cepStr;
		}
	}

	private boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}
