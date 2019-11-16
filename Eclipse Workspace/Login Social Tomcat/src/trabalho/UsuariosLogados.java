package trabalho;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import utilidade.Usuario;

public class UsuariosLogados {
	private static Map<Usuario, HttpSession> logadosGoogle = new HashMap<>();
	private static Map<Usuario, HttpSession> logadosGithub = new HashMap<>();

	public static void logarUsuario(Usuario usuario, HttpSession sessao) {
		if (usuario.getApiUsada().equals("google")) {
			logadosGoogle.put(usuario, sessao);
		} else if (usuario.getApiUsada().equals("github")) {
			logadosGithub.put(usuario, sessao);
		}
	}

	public static Usuario getUsuarioBySessao(HttpSession sessao) {

		for (Usuario u : logadosGoogle.keySet()) {
			if (logadosGoogle.get(u) != null && logadosGoogle.get(u).equals(sessao))
				return u;
		}

		for (Usuario u : logadosGithub.keySet()) {
			if (logadosGithub.get(u) != null && logadosGithub.get(u).equals(sessao))
				return u;
		}

		return null; // usuario nao esta logado
	}

	public static void deslogarUsuario(Usuario usuario) {
		if (usuario.getApiUsada().equals("google")) {
			logadosGoogle.put(usuario, null);
		} else if (usuario.getApiUsada().equals("github")) {
			logadosGithub.put(usuario, null);
		}
	}
}
