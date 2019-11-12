package buscacep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import utilidade.CEP;

public class BuscadorCEP {
	public static final String ERRO = "Erro";

	public static CEP buscarCEP(String cep) {

		String respostaViaCep = mandarRequisicaoHttp(cep);

		if (!respostaViaCep.startsWith(ERRO)) {
			JSONObject respostaJson = new JSONObject(respostaViaCep);

			if (!respostaJson.has("erro")) {

				return new CEP(	converterEncoding(respostaJson.getString("cep")), 
										converterEncoding(respostaJson.getString("logradouro")),
										converterEncoding(respostaJson.getString("complemento")), 
										converterEncoding(respostaJson.getString("bairro")),
										converterEncoding(respostaJson.getString("localidade")), 
										converterEncoding(respostaJson.getString("uf")),
										converterEncoding(respostaJson.getString("unidade")),
										converterEncoding(respostaJson.getString("ibge")), 
										converterEncoding(respostaJson.getString("gia")));
			} else {
				return new CEP(ERRO + ": CEP não encontrado no ViaCEP");
			}

		} else {
			return new CEP(respostaViaCep);
		}
	}
	
	private static String converterEncoding(String in) {
		return new String(in.getBytes(), StandardCharsets.UTF_8);
	}

	private static String mandarRequisicaoHttp(String cep) {
		String cepLimpo = validarCEP(cep); // testa se tem 8 digitos

		if (!cepLimpo.startsWith(ERRO)) {
			HttpURLConnection con;
			try {
				URL url = new URL("http://viacep.com.br/ws/" + cepLimpo + "/json/");
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
			} catch (Exception e) {
				return ERRO + " ao mandar URL para o ViaCEP";
			}

			try {
				// resposta
				StringBuffer resposta = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String linha;
				while ((linha = in.readLine()) != null)
					resposta.append(linha);
				in.close();
				con.disconnect();
				return resposta.toString(); // sucesso
			} catch (Exception e) {
				return ERRO + ": Não foi possível ler reposta do ViaCEP";
			}
		} else {
			return ERRO + ": CEP inválido";
		}
	}

	private static String validarCEP(String cep) {
		cep = cep.trim().replaceAll("-", ""); // remove tra

		if (cep.length() == 8) {
			try {
				Integer.parseInt(cep); // testa se eh um numero
				return cep; // sucesso
			} catch (NumberFormatException e) { // nao eh um numero
				return ERRO;
			}
		} else {
			return ERRO;
		}
	}
}
