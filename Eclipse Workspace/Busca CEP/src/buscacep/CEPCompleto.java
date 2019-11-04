package buscacep;

/**
 * Representa a resposta dada pela API do ViaCEP
 */
public class CEPCompleto {
	public String cep;
	public String logradouro;
	public String complemento;
	public String bairro;
	public String localidade;
	public String uf;
	public String ibge;
	public String gia;

	public String erro;
	
	public CEPCompleto(String cep, String logradouro, String complemento, String bairro, String localidade, String uf,
			String ibge, String gia) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.ibge = ibge;
		this.gia = gia;
	}

	public CEPCompleto(String erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return "[cep=" + cep + "\n logradouro=" + logradouro + "\n complemento=" + complemento + "\n bairro=" + bairro
				+ "\n localidade=" + localidade + "\n uf=" + uf + "\n ibge=" + ibge + "\n gia=" + gia + "\n]";
	}
}
