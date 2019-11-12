package utilidade;

/**
 * Representa a resposta dada pela API do ViaCEP
 */
public class CEP {
	public String cep;
	public String logradouro;
	public String complemento;
	public String bairro;
	public String localidade;
	public String uf;
	public String unidade;
	public String ibge;
	public String gia;

	public String erro;

	public CEP() {

	}

	public CEP(String cep, String logradouro, String complemento, String bairro, String localidade, String uf,
			String unidade, String ibge, String gia) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.unidade = unidade;
		this.ibge = ibge;
		this.gia = gia;
	}

	public CEP(String erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return "[cep=" + cep + "\n logradouro=" + logradouro + "\n complemento=" + complemento + "\n bairro=" + bairro
				+ "\n localidade=" + localidade + "\n uf=" + uf + "\n unidade=" + unidade + "\n ibge=" + ibge
				+ "\n gia=" + gia + "\n]";
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

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}
